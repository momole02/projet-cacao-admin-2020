-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  lun. 12 oct. 2020 à 16:39
-- Version du serveur :  5.7.17
-- Version de PHP :  5.6.30

/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
AUTOCOMMIT := 0;
START TRANSACTION;
time_zone := "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `concoursagricole`
--

-- --------------------------------------------------------

--
-- Structure de la table `avoir`
--

CREATE TABLE avoir (
  NUM_CAND char(20) NOT NULL,
  NUM_RECO number(10) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--

CREATE TABLE candidat (
  NUM_CAND varchar2(9) NOT NULL,
  NUM_JURY number(10) DEFAULT NULL,
  NUM_PL number(10) DEFAULT NULL,
  NUM_MC number(10) DEFAULT NULL,
  PHOTO_CAND varchar2(100) NOT NULL,
  EXTRAIT_CAND varchar2(255) NOT NULL,
  NOM_CAND varchar2(30) NOT NULL,
  PRENOM_CAND varchar2(255) NOT NULL,
  SEXE_CAND char(2) NOT NULL,
  DATENAISSANCE_CAND date NOT NULL,
  LIEUDN_CAND varchar2(255) DEFAULT NULL,
  ADD_CAND varchar2(10) NOT NULL,
  CONT_CAND varchar2(8) NOT NULL,
  NOTE_NT number(10) DEFAULT NULL,
  OBSERVATIONS_NT varchar2(255) DEFAULT NULL,
  EMAIL_CAND varchar2(100) NOT NULL,
  MDP_CAND varchar2(100) NOT NULL
) ;

--
-- Déchargement des données de la table `candidat`
--

INSERT INTO candidat (NUM_CAND, NUM_JURY, NUM_PL, NUM_MC, PHOTO_CAND, EXTRAIT_CAND, NOM_CAND, PRENOM_CAND, SEXE_CAND, DATENAISSANCE_CAND, LIEUDN_CAND, ADD_CAND, CONT_CAND, NOTE_NT, OBSERVATIONS_NT, EMAIL_CAND, MDP_CAND)
 SELECT 'CANDko142', NULL, NULL, NULL, '2016-11-13-14-58-33-929.jpg', '', 'kouassi', 'cisca', 'F', '2020-09-15', NULL, '', '', NULL, NULL, 'hklmo@hn', '00000' FROM dual UNION ALL 
 SELECT 'CANDko562', NULL, NULL, NULL, '2016-11-13-14-58-33-929.jpg', '', 'kouassi', 'cisca', 'F', '2020-09-13', NULL, '', '', NULL, NULL, 'hklmo@hn', '00000' FROM dual UNION ALL 
 SELECT 'CANDko298', NULL, NULL, NULL, '2016-11-13-14-58-33-929.jpg', '', 'kouassi', 'adoubla', 'F', '2020-09-17', NULL, '', '', NULL, NULL, 'kouass12@120', '00000' FROM dual UNION ALL 
 SELECT 'CANDKo669', NULL, NULL, NULL, '2016-11-13-14-58-33-929.jpg', '', 'Konan', 'Lionel', 'H', '2020-09-10', NULL, '', '', NULL, NULL, 'houndjilionel@gmail.com', 'leo' FROM dual UNION ALL 
 SELECT 'CANDKo215', NULL, NULL, NULL, '2016-11-13-14-58-33-929.jpg', '', 'Konan', 'Lionel', 'H', '2020-09-10', NULL, '', '', NULL, NULL, 'houndjilionel@gmail.com', 'leo' FROM dual UNION ALL 
 SELECT 'CANDKo312', NULL, NULL, NULL, '2016-11-13-14-58-33-929.jpg', '', 'Kouassi', 'Lionel', 'H', '2020-09-10', NULL, '', '', NULL, NULL, 'houndjilionel@gmail.com', 'aaaa' FROM dual UNION ALL 
 SELECT 'CANDko287', NULL, NULL, NULL, '433.JPG', '', 'kouassi', 'theya', 'F', '2021-06-07', NULL, '', '', NULL, NULL, 'hklmo@hn', '010101' FROM dual;

-- --------------------------------------------------------

--
-- Structure de la table `candidature`
--

CREATE TABLE candidature (
  NUM_CDR char(25) NOT NULL,
  NUM_TC number(10) NOT NULL,
  LIBELLE_CDR varchar2(255) DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `critere_candidat`
--

CREATE TABLE critere_candidat (
  NUM_CRI number(10) NOT NULL,
  LIBELLE_CRI varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `cultiver`
--

CREATE TABLE cultiver (
  NUM_PL char(20) NOT NULL,
  NUM_PROD char(25) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `deposer`
--

CREATE TABLE deposer (
  NUM_CAND char(20) NOT NULL,
  NUM_CDR char(25) NOT NULL,
  DATE_DEP timestamp(0) DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `emballage`
--

CREATE TABLE emballage (
  NUMEMB number(10) NOT NULL,
  NUM_MED number(10) NOT NULL,
  POIDS_EMB number(10,2) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `medaillon`
--

CREATE TABLE medaillon (
  NUM_MED number(10) NOT NULL,
  LIBELLE_MED varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `membre_jury`
--

CREATE TABLE membre_jury (
  NUM_JURY number(10) NOT NULL,
  NOM_JURY varchar2(255) NOT NULL,
  PRENOM_JURY varchar2(255) NOT NULL,
  DATE_NAISS_JURY date NOT NULL,
  LOCALITE_JURY varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `methode_culture`
--

CREATE TABLE methode_culture (
  NUM_MC number(10) NOT NULL,
  NUM_TMC number(10) NOT NULL,
  LIBELLE_MC varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `plantation`
--

CREATE TABLE plantation (
  NUM_PLAN char(25) NOT NULL,
  NUM_PL char(20) NOT NULL,
  LOCALISATION_PLAN varchar2(255) NOT NULL,
  SUPERFICIE_PLAN number(10,0) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `planteur`
--

CREATE TABLE planteur (
  NUM_PL number(10) NOT NULL,
  NUM_CAND number(10) NOT NULL,
  NOM_PL varchar2(255) NOT NULL,
  PRENOM_PL varchar2(255) NOT NULL,
  SEXE_PL char(2) NOT NULL,
  DATEDENAISSANCE_PL date NOT NULL,
  LOCALITE_PL varchar2(255) DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE produit (
  NUM_PROD char(25) NOT NULL,
  NUMEMB number(10) DEFAULT NULL,
  LIBELLE_PROD varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `recompense`
--

CREATE TABLE recompense (
  NUM_RECO number(10) NOT NULL,
  NUM_TREC number(10) NOT NULL,
  NUM_JURY char(20) NOT NULL,
  RANG_RECO number(10) NOT NULL,
  LIBELLE_RECO varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `remplir`
--

CREATE TABLE remplir (
  NUM_CRI number(10) NOT NULL,
  NUM_CAND char(20) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `type_candidature`
--

CREATE TABLE type_candidature (
  NUM_TC number(10) NOT NULL,
  LIBELLE_TC varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `type_methode_culture`
--

CREATE TABLE type_methode_culture (
  NUM_TMC number(10) NOT NULL,
  LIBELLE_TMC varchar2(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Structure de la table `type_recompense`
--

CREATE TABLE type_recompense (
  NUM_TREC number(10) NOT NULL,
  LIBELLE_TREC varchar2(255) DEFAULT NULL
) ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `avoir`
--
ALTER TABLE avoir
  ADD PRIMARY KEY (NUM_CAND,NUM_RECO),
  ADD KEY `FK_AVOIR2` (NUM_RECO);

--
-- Index pour la table `candidat`
--
ALTER TABLE candidat
  ADD PRIMARY KEY (NUM_CAND),
  ADD KEY `FK_ADOPTER` (NUM_MC),
  ADD KEY `FK_ETRE` (NUM_PL),
  ADD KEY `FK_NOTER` (NUM_JURY);

--
-- Index pour la table `candidature`
--
ALTER TABLE candidature
  ADD PRIMARY KEY (NUM_CDR),
  ADD KEY `FK_AVOIR_TC` (NUM_TC);

--
-- Index pour la table `critere_candidat`
--
ALTER TABLE critere_candidat
  ADD PRIMARY KEY (NUM_CRI);

--
-- Index pour la table `cultiver`
--
ALTER TABLE cultiver
  ADD PRIMARY KEY (NUM_PL,NUM_PROD),
  ADD KEY `FK_CULTIVER2` (NUM_PROD);

--
-- Index pour la table `deposer`
--
ALTER TABLE deposer
  ADD PRIMARY KEY (NUM_CAND,NUM_CDR),
  ADD KEY `FK_DEPOSER2` (NUM_CDR);

--
-- Index pour la table `emballage`
--
ALTER TABLE emballage
  ADD PRIMARY KEY (NUMEMB),
  ADD KEY `FK_COLLER` (NUM_MED);

--
-- Index pour la table `medaillon`
--
ALTER TABLE medaillon
  ADD PRIMARY KEY (NUM_MED);

--
-- Index pour la table `membre_jury`
--
ALTER TABLE membre_jury
  ADD PRIMARY KEY (NUM_JURY);

--
-- Index pour la table `methode_culture`
--
ALTER TABLE methode_culture
  ADD PRIMARY KEY (NUM_MC),
  ADD KEY `FK_AVOIR_TMC` (NUM_TMC);

--
-- Index pour la table `plantation`
--
ALTER TABLE plantation
  ADD PRIMARY KEY (NUM_PLAN),
  ADD KEY `FK_POSSEDER` (NUM_PL);

--
-- Index pour la table `planteur`
--
ALTER TABLE planteur
  ADD PRIMARY KEY (NUM_PL),
  ADD KEY `FK_ETRE2` (NUM_CAND);

--
-- Index pour la table `produit`
--
ALTER TABLE produit
  ADD PRIMARY KEY (NUM_PROD),
  ADD KEY `FK_EMBALLER` (NUMEMB);

--
-- Index pour la table `recompense`
--
ALTER TABLE recompense
  ADD PRIMARY KEY (NUM_RECO),
  ADD KEY `FK_AVOIR_TR` (NUM_TREC),
  ADD KEY `FK_DELIVRER` (NUM_JURY);

--
-- Index pour la table `remplir`
--
ALTER TABLE remplir
  ADD PRIMARY KEY (NUM_CRI,NUM_CAND),
  ADD KEY `FK_REMPLIR2` (NUM_CAND);

--
-- Index pour la table `type_candidature`
--
ALTER TABLE type_candidature
  ADD PRIMARY KEY (NUM_TC);

--
-- Index pour la table `type_methode_culture`
--
ALTER TABLE type_methode_culture
  ADD PRIMARY KEY (NUM_TMC);

--
-- Index pour la table `type_recompense`
--
ALTER TABLE type_recompense
  ADD PRIMARY KEY (NUM_TREC);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `membre_jury`
--
ALTER TABLE membre_jury
  MODIFY NUM_JURY trunc(to_number(9)) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `methode_culture`
--
ALTER TABLE methode_culture
  MODIFY NUM_MC trunc(to_number(9)) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `planteur`
--
ALTER TABLE planteur
  MODIFY NUM_PL trunc(to_number(9)) NOT NULL AUTO_INCREMENT;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
