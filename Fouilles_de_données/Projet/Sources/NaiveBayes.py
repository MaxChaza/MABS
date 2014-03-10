#!/usr/bin/python

import argparse
import csv
import numpy
import scipy.stats
import sys

# SCRIPT PARAMETERS
parser = argparse.ArgumentParser(description='Naive Bayesian learner and classifier.')
parser.add_argument('--training', required=True, help='File in Orange format containing training examples.')
parser.add_argument('--sample', required=True, help='File containing new objects to be classified.')
parser.add_argument('--model', nargs='?', const=True, help='Only displays the probability table.')
opt = vars(parser.parse_args())

# GLOBAL VARIABLES
targetClass = ''
attributeClass = {}
model = {} 
N = 0 # N: size of training set

# LOAD TRAINING SET AND BUILD MODEL (START TO BUILD PROBABILITY TABLE)
# training[class][attribute_name][attribute_value] = occurrences
# e.g. training[G1][population][Uppsala] = 22
with open(opt['training']) as f:
	reader = csv.DictReader(f, delimiter='\t')
	# LOAD ATTRIBUTE TYPES (continuous, discrete, ignore)
	attributeClass = reader.next()
	# DETERMINE TARGET CLASS
	classLine = reader.next()
	for i in attributeClass:
		if classLine[i]=='class':
			targetClass = i
	# START BUILDING model
	for row in reader:
		N += 1
		sampleClass = row[targetClass]
		if sampleClass not in model: model[sampleClass] = {}
		for field,value in row.iteritems():
			if value is not None and value!='' and value!='NA':
				if attributeClass[field]=='discrete':
					if field not in model[sampleClass]: model[sampleClass][field] = {}
					if value not in model[sampleClass][field]: model[sampleClass][field][value] = 1
					else: model[sampleClass][field][value] += 1
				elif attributeClass[field]=='continuous':
					if field not in model[sampleClass]: model[sampleClass][field] = []
					model[sampleClass][field].append(float(value))

# BUILD PROBABILITY MODEL FOR NUMERIC ATTRIBUTES
for field in attributeClass:
	if attributeClass[field]=='continuous':
		for c in model:
			mean = numpy.mean(model[c][field])
			sd   = numpy.std(model[c][field])
			model[c][field] = {}
			model[c][field]['mean'] = mean
			model[c][field]['sd'] = sd


def printProbabilityTable(model):
	for c in model:
		print 'P[class='+c+'] = '+str(model[c][targetClass][c])
	print
	for f in attributeClass:
		for c in model:
			if attributeClass[f] == 'continuous':
				print 'P['+f+'|class='+c+'] = mean: '+str(model[c][f]['mean'])+', sd: '+str(model[c][f]['sd'])
			elif attributeClass[f] == 'discrete':
				for v in model[c][f]:
					print 'P['+f+'='+v+'|class='+c+'] = '+str(model[c][f][v])
		print

if opt['model']:
	printProbabilityTable(model)
	exit()

# EVERYTHING'S READY FOR CLASSIFICATION
def classify(sample, model, targetClass):
	bestProb = 0
	bestClass = ''
	for c in model:
		p=1
		for f in model[c]:
			# ensure we have a value
			if sample[f] is not None and len(sample[f])>0 and f!=targetClass:
				# p(X=f / class=c)
				if attributeClass[f] == 'continuous': # USE NORMAL
					pf = scipy.stats.norm.pdf( float(sample[f]), loc=model[c][f]['mean'], scale=model[c][f]['sd'])
				elif attributeClass[f] == 'discrete': # USE FREQUENCY IN TRAINING
					if sample[f] in model[c][f]: pf = float(model[c][f][ sample[f] ]) / model[c][targetClass][c]
					else: pf = 0
				# p(class=c)
				pc = float(model[c][targetClass][c]) / N
				# posterior probability (not divided by p(X) though)
				#~ print f, 'p(',f,'=', sample[f],'/class=',c,') = ', pf
				p *= pf * pc
		#~ print 'p(',c,')=',p # display probability of each class
		if p>bestProb:
			bestProb = p
			bestClass = c
	return bestClass

with open(opt['sample']) as f:
	reader = csv.DictReader(f, delimiter='\t')
	fields = reader.fieldnames[:]
	fields.append('prediction')
	writer = csv.DictWriter(sys.stdout, fieldnames=fields, delimiter='\t')
	writer.writeheader()
	for row in reader:
		res = classify(row, model, targetClass)
		row['prediction'] = res
		writer.writerow(row)
