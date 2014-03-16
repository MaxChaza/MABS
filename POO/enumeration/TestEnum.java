package fr.irit.ens.m1mabs.enumeration;

import java.util.Scanner;

public class TestEnum 
{
	public static void main(String[] args) 
	{
		for(TypeObjet type : TypeObjet.values())
			System.out.println(type.getNom());
	}
}
