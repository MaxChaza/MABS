-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 30 Avril 2014 à 13:55
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `biobook`
--

-- --------------------------------------------------------

--
-- Structure de la table `chercheur`
--

CREATE TABLE IF NOT EXISTS `chercheur` (
  `login` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(20) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `mail` varchar(40) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `chercheur`
--

INSERT INTO `chercheur` (`login`, `password`, `name`, `firstName`, `mail`) VALUES
('?', '?', '?', '?', '?');

-- --------------------------------------------------------

--
-- Structure de la table `chercheur_experience`
--

CREATE TABLE IF NOT EXISTS `chercheur_experience` (
  `login` varchar(20) NOT NULL,
  `labelExperience` varchar(20) NOT NULL,
  PRIMARY KEY (`login`,`labelExperience`),
  KEY `fk_cher` (`labelExperience`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `experience`
--

CREATE TABLE IF NOT EXISTS `experience` (
  `labelExperience` varchar(20) NOT NULL,
  `problem` varchar(100) NOT NULL,
  `context` varchar(100) NOT NULL,
  `stateOfArt` varchar(100) NOT NULL,
  `assumption` varchar(100) NOT NULL,
  `createur` varchar(20) NOT NULL,
  PRIMARY KEY (`labelExperience`),
  KEY `fk_exp_crea` (`createur`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `materiel`
--

CREATE TABLE IF NOT EXISTS `materiel` (
  `labelMateriel` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `materiel_experience`
--

CREATE TABLE IF NOT EXISTS `materiel_experience` (
  `labelMateriel` varchar(20) NOT NULL,
  `labelExperience` varchar(20) NOT NULL,
  KEY `fk_exp` (`labelExperience`),
  KEY `fk_mat` (`labelMateriel`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
