-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 14 Décembre 2017 à 23:24
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `smart_selling`
--
CREATE DATABASE IF NOT EXISTS `smart_selling` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `smart_selling`;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id_client` int(11) NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `societe` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `adresse` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `code_postal` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `ville` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `pays` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `telephone_fixe` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone_mobile` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`id_client`, `nom`, `prenom`, `societe`, `adresse`, `code_postal`, `ville`, `pays`, `email`, `telephone_fixe`, `telephone_mobile`, `actif`) VALUES
(1, 'Guillaume', 'Michel', '', '66 Avenue Jean Jaurès', '94200', 'Ivry-sur-Seine', 'France', 'michel.guillaume--foucaud@hotmail.fr', '', '0651930585', 1);

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_commande` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `date_enregistrement` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `id_client`, `date_enregistrement`) VALUES
(2, 1, '2017-07-07 17:31:36'),
(7, 1, '2017-07-07 17:32:41');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(12),
(12),
(12),
(12),
(12),
(12),
(12),
(12),
(12);

-- --------------------------------------------------------

--
-- Structure de la table `ligne_commande`
--

CREATE TABLE `ligne_commande` (
  `id_ligne_commande` int(11) NOT NULL,
  `id_commande` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `ligne_commande`
--

INSERT INTO `ligne_commande` (`id_ligne_commande`, `id_commande`, `id_produit`, `quantite`) VALUES
(5, 2, 1, 2),
(10, 7, 3, 5);

-- --------------------------------------------------------

--
-- Structure de la table `ligne_livraison`
--

CREATE TABLE `ligne_livraison` (
  `id_livraison` int(11) NOT NULL,
  `date_envoi` datetime DEFAULT NULL,
  `id_ligne_commande` int(11) NOT NULL,
  `id_ligne_livraison` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `ligne_livraison`
--

INSERT INTO `ligne_livraison` (`id_livraison`, `date_envoi`, `id_ligne_commande`, `id_ligne_livraison`) VALUES
(3, '2017-07-07 17:33:28', 5, 6),
(8, '2017-07-07 17:33:28', 10, 11);

-- --------------------------------------------------------

--
-- Structure de la table `livraison`
--

CREATE TABLE `livraison` (
  `id_livraison` int(11) NOT NULL,
  `id_commande` int(11) NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `adresse` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `code_postal` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ville` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `pays` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `livraison`
--

INSERT INTO `livraison` (`id_livraison`, `id_commande`, `nom`, `prenom`, `adresse`, `code_postal`, `ville`, `pays`) VALUES
(3, 2, 'Guillaume', 'Michel', '66 Avenue Jean Jaurès', '94200', 'Ivry-sur-Seine', 'France'),
(8, 7, 'Guillaume', 'Michel', '66 Avenue Jean Jaurès', '94200', 'Ivry-sur-Seine', 'France');

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id_paiement` int(11) NOT NULL,
  `id_commande` int(11) NOT NULL,
  `prixTTC` float NOT NULL,
  `date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `paiement`
--

INSERT INTO `paiement` (`id_paiement`, `id_commande`, `prixTTC`, `date`) VALUES
(4, 2, 6, '2017-07-07 17:31:55'),
(9, 7, 5, '2017-07-07 17:33:14');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(11) NOT NULL,
  `reference` varchar(14) COLLATE utf8_unicode_ci NOT NULL,
  `designation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `prix` float NOT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `reference`, `designation`, `description`, `stock`, `prix`, `actif`) VALUES
(1, 'RF000159852456', 'Micro servo de 9 grammes Tower Pro SG90', 'Dimensions : 26.0 x 13.0 x 24.0 mm\r\nPoids : 9 g\r\nVitesse ang. : 0.12sec/60° (4.8V)\r\nCouple : 1.6kg/cm\r\nReactivité : 2 ms\r\nVCC : 3.0V~5.0V\r\nCable : 3 poles, 15cm\r\nEngrenages : nylon', 1683, 3, 1),
(2, 'RF000896542634', 'Carte Arduino UNO R3', 'Atmel ATmega328P-AU\r\n6 entrées analogiques\r\n14 E/S digitales (6 PWM)\r\nMémoire Flash : 32K\r\nSRAM : 2K\r\nEEPROM : 1K\r\nSupporte l\'alimentation et le téléchargement USB\r\nAlimentation externe 5V/12V DC\r\n(accepte pile 9V)', 7140, 5, 1),
(3, 'RF000159542634', 'LED Bleu 3mm x20', 'Lot de 20 LED Bleu 3mm.\r\n\r\nTension: 3.0~3.2 V\r\nCourant: 20 mA Max\r\nTaille: 3 mm', 148, 1, 1),
(4, 'RF000159542635', 'LED Verte 3mm x20', 'Lot de 20 LED Verte 3mm.\r\n\r\nTension: 3.0~3.2 V\r\nCourant: 20 mA Max\r\nTaille: 3 mm', 3000, 1, 1),
(5, 'RF000159542636', 'LED Rouge 3mm x20', 'Lot de 20 LED Rouge 3mm.\r\n\r\nTension: 3.0~3.2 V\r\nCourant: 20 mA Max\r\nTaille: 3 mm', 160, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id_role` int(11) NOT NULL,
  `nom` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `role`
--

INSERT INTO `role` (`id_role`, `nom`, `description`, `actif`) VALUES
(1, 'commercial', 'Le commercial peut enregistrer de nouvelles commandes', 1),
(2, 'comptable', 'Le comptable peut voir les envois de commandes, transmettre la facture au client et encaisser', 1),
(3, 'responsable des stocks', 'Le responsable des stocks peut voir les commande et enregistre les envois', 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int(11) NOT NULL,
  `identifiant` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `mot_de_passe` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gds1` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `gds2` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_role` int(11) NOT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `identifiant`, `mot_de_passe`, `gds1`, `gds2`, `id_role`, `actif`) VALUES
(1, 'commercial@mgf-corp.fr', 'f7f53ade941a03b0b1c165aac4a5e3e56103a6fc539939266c5817d26faed9303d053201667a57859676bad17b372d2ac1a31132a61c815bf775b1c4eae7aaef', 'azertyuiopqsdfghjklmwxcvbn012345', '[B@2751440e', 1, 1),
(2, 'comptable@mgf-corp.fr', 'f7f53ade941a03b0b1c165aac4a5e3e56103a6fc539939266c5817d26faed9303d053201667a57859676bad17b372d2ac1a31132a61c815bf775b1c4eae7aaef', 'azertyuiopqsdfghjklmwxcvbn012345', '[B@491f59a2', 2, 1),
(3, 'resp-stock@mgf-corp.fr', 'f7f53ade941a03b0b1c165aac4a5e3e56103a6fc539939266c5817d26faed9303d053201667a57859676bad17b372d2ac1a31132a61c815bf775b1c4eae7aaef', 'azertyuiopqsdfghjklmwxcvbn012345', '[B@5baca351', 3, 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id_client`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `FKe8envugnnmymjt2tpsmtyudbo` (`id_client`);

--
-- Index pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD PRIMARY KEY (`id_ligne_commande`),
  ADD KEY `FKe72mfq36v6sp3th4dk1cbl5h7` (`id_commande`),
  ADD KEY `FKa3fqpulyp9fydxpeyk9y675fv` (`id_produit`);

--
-- Index pour la table `ligne_livraison`
--
ALTER TABLE `ligne_livraison`
  ADD PRIMARY KEY (`id_ligne_livraison`),
  ADD KEY `FKrhewtspvogs4iao3xbjivjvnm` (`id_livraison`);

--
-- Index pour la table `livraison`
--
ALTER TABLE `livraison`
  ADD PRIMARY KEY (`id_livraison`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id_paiement`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id_role`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `FK7sxlk1d5a5kr3x3esojejg0s1` (`id_role`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id_client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  MODIFY `id_ligne_commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT pour la table `livraison`
--
ALTER TABLE `livraison`
  MODIFY `id_livraison` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id_paiement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
