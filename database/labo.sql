-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2025 at 10:20 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `labo`
--

-- --------------------------------------------------------

--
-- Table structure for table `analyses`
--

CREATE TABLE `analyses` (
  `id_analyse` int(11) NOT NULL,
  `nom_analyse` varchar(500) NOT NULL,
  `prix` int(10) NOT NULL,
  `nom_courant` varchar(500) NOT NULL,
  `échantillon` varchar(500) NOT NULL,
  `descr` varchar(500) NOT NULL,
  `unite` varchar(500) NOT NULL,
  `valeur_us` varchar(2500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `analyses`
--

INSERT INTO `analyses` (`id_analyse`, `nom_analyse`, `prix`, `nom_courant`, `échantillon`, `descr`, `unite`, `valeur_us`) VALUES
(1, 'Flore polymorphe dans les urines', 800, 'FPU', 'Héparinate', '(ALT),(AST),(ALP),Bilirubine Totale,Amylase,Lipase,Créatinine,(BUN),Clairance de la Créatinine', 'U/L,U/L,U/L,mg/dL,U/L,U/L,mg/dL,mg/dL,mL/min', '7 à 56,10 à 40,44 à 147,0.1 à 1.2,23 à 85,0 à 160,0.7 à 1.3,6 à 20,97 à 137'),
(2, 'Numeration formulaire sanguine', 500, 'FNS', 'Edta', 'Globules Rouges (RBCs),Globules Blancs (WBCs),Hémoglobine (Hgb ou Hb),Hématocrite (Hct),Plaquettes,MCV,MCH,MCHC,RDW', '10^6 cellules/µL,10^3 cellules/µL,g/dL,Pourcentage (%),10^3 cellules/µL,Femtolitres (fL),Picogrammes (pg),(g/dL),Pourcentage (%)', '4.7 à 6.1,4.5 à 11,13.8 à 17.2,40.7 à 50.3,150 à 450,80 à 100,27 à 33,33.4 à 35.5,11.5 à 14.5 '),
(3, 'Hépatite C Virus', 700, 'HCV', 'Héparinate', 'Anti-HCV,HCV ARN,Génotypage du HCV', 'Positif ou Négatif,UI/mL,Résultat qualitatif', 'Négatif,< 15,N/A'),
(4, 'Gamma-Glutamyl Transferase', 600, 'GGT', 'Edta', 'GGT,', 'UI/L,', '8 à 61,');

-- --------------------------------------------------------

--
-- Table structure for table `bilan`
--

CREATE TABLE `bilan` (
  `id` int(11) NOT NULL,
  `id_patient` int(11) DEFAULT NULL,
  `id_caisse` int(10) NOT NULL,
  `code_echantillon` varchar(500) NOT NULL,
  `id_tests` varchar(500) NOT NULL,
  `medcin_r` varchar(500) NOT NULL,
  `remarque` varchar(5000) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bilan`
--

INSERT INTO `bilan` (`id`, `id_patient`, `id_caisse`, `code_echantillon`, `id_tests`, `medcin_r`, `remarque`, `date`) VALUES
(1, 16, 2, '00120240518123,00220240518123,', '1,2,', '000', '123123123', '2024-05-14'),
(2, 2, 3, '00120240522123,', '2,', 'mohcin charef', 'rien de remarque', '2024-05-08'),
(3, 2, 4, '00220240522123,', '1,', 'kerkouch', ',fndkl,sdk,sppsfl,l,f,d', '2024-05-22'),
(4, 4, 5, '001202405234,002202405234,', '4,3,2,1,', 'mohcin', 'rien de remarque', '2024-05-23'),
(5, 17, 6, '0022024052717,', '1,3,', 'samoud ishak', 'rien a remarquer', '2024-05-29'),
(6, 18, 7, '0022024052718,', '1,3,', 'Samoud Ishak', 'rien a remarquer', '2024-05-22'),
(7, 17, 8, '0012024052817,0022024052817,', '1,2,', 'samoudishak', 'rien', '2024-05-21'),
(8, 3, 9, '001202405283,002202405283,', '3,4,', 'Ishak', 'rerzerz', '2024-05-01'),
(9, 19, 10, '0012025021019,', '2,', 'samoud ishak', 'none', '2025-02-09');

-- --------------------------------------------------------

--
-- Table structure for table `caisse`
--

CREATE TABLE `caisse` (
  `id_caisse` int(11) NOT NULL,
  `date` varchar(10) DEFAULT NULL,
  `montant` int(11) NOT NULL,
  `paye` int(11) NOT NULL,
  `tests` varchar(500) NOT NULL,
  `patient` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `caisse`
--

INSERT INTO `caisse` (`id_caisse`, `date`, `montant`, `paye`, `tests`, `patient`) VALUES
(1, '123', 123, 10, 'abc', '000'),
(2, '2024-05-22', 1300, 1300, 'FPU,FNS,', 'mohcin charef'),
(3, '2024-05-22', 500, 200, 'FNS,', 'Jones Michael'),
(4, '2024-05-22', 800, 0, 'FPU,', 'Jones Michael'),
(5, '2024-05-23', 2600, 1500, 'GGT,HCV,FNS,FPU,', 'Brown Christopher'),
(6, '2024-05-27', 1500, 1500, 'FPU,HCV,', 'Lahlou Salah'),
(7, '2024-05-27', 1500, 1000, 'FPU,HCV,', 'Samoud idris'),
(8, '2024-05-28', 1300, 500, 'FPU,FNS,', 'Lahlou Salah'),
(9, '2024-05-28', 1300, 1000, 'HCV,GGT,', 'Williams Jessica'),
(10, '2025-02-10', 500, 0, 'FNS,', 'moh bab el wad');

-- --------------------------------------------------------

--
-- Table structure for table `echantillon`
--

CREATE TABLE `echantillon` (
  `code` varchar(20) NOT NULL,
  `id_tests` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `echantillon`
--

INSERT INTO `echantillon` (`code`, `id_tests`, `type`) VALUES
('00120240518123', '2,', 'Edta'),
('00120240522123', '2,', 'Edta'),
('001202405234', '4,2,', 'Edta'),
('0012024052817', '2,', 'Edta'),
('001202405283', '4,', 'Edta'),
('0012025021019', '2,', 'Edta'),
('00220240518123', '1,', 'Héparinate'),
('00220240522123', '1,', 'Héparinate'),
('002202405234', '3,1,', 'Héparinate'),
('0022024052717', '1,3,', 'Héparinate'),
('0022024052718', '1,3,', 'Héparinate'),
('0022024052817', '1,', 'Héparinate'),
('002202405283', '3,', 'Héparinate');

-- --------------------------------------------------------

--
-- Table structure for table `paiementemploye`
--

CREATE TABLE `paiementemploye` (
  `id_paiement_employe` int(11) NOT NULL,
  `employé` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `montant` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `paiementemploye`
--

INSERT INTO `paiementemploye` (`id_paiement_employe`, `employé`, `position`, `montant`, `date`) VALUES
(1, 'mohcin charef', 'medecin', '22000', '2024-05-05'),
(2, 'Bounar Ahmed', 'biologiste', '45000', '2024-05-08');

-- --------------------------------------------------------

--
-- Table structure for table `paiementfournisseur`
--

CREATE TABLE `paiementfournisseur` (
  `id_paiement_fournisseur` int(11) NOT NULL,
  `fournisseur` varchar(255) DEFAULT NULL,
  `produit` varchar(255) DEFAULT NULL,
  `montant` decimal(10,2) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `paiementfournisseur`
--

INSERT INTO `paiementfournisseur` (`id_paiement_fournisseur`, `fournisseur`, `produit`, `montant`, `date`) VALUES
(1, 'mohcin charef', 'tube 10mm', 3800.00, '2024-05-07'),
(2, 'mohmoh', 'tube 10 mm', 2000.00, '2024-05-01'),
(3, 'Mohammed', 'produit2', 5000.00, '2024-05-22');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `id_patient` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `date_de_naissance` date DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `genre` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id_patient`, `nom`, `prenom`, `date_de_naissance`, `telephone`, `email`, `genre`) VALUES
(1, 'Smith', 'Sarah', '1992-03-15', '555-112-3456', 'sarah.smith@example.com', 'Female'),
(2, 'Jones', 'Michael', '1980-10-22', '555-987-6543', 'michael.jones@example.com', 'Male'),
(3, 'Williams', 'Jessica', '1978-08-09', '555-567-1234', 'jessica.w@example.com', 'Female'),
(4, 'Brown', 'Christopher', '1984-12-17', '555-786-9012', 'chris.b@example.com', 'Male'),
(5, 'John', 'Doe', '1985-07-24', '555-1234', 'john.doe@example.com', 'Male'),
(16, 'mohcin', 'cha', '2003-08-20', '012346', '123@123.123', 'male'),
(17, 'Lah', 'Salah', '2003-10-19', '0552142386', 'salah@gmail.com', 'Male'),
(18, 'Sam', 'idris', '2010-12-08', '0544215346', 'idris@gmail.com', 'Male'),
(19, 'moh', 'bab el wad', '2002-03-26', '0554632864', 'babelwad@gmail.com', 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `resultat`
--

CREATE TABLE `resultat` (
  `id` int(10) NOT NULL,
  `id_analyse` int(10) NOT NULL,
  `id_echantillon` varchar(20) NOT NULL,
  `v_biologist` varchar(50) NOT NULL,
  `v_medcin` varchar(50) NOT NULL,
  `resultat` varchar(5000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resultat`
--

INSERT INTO `resultat` (`id`, `id_analyse`, `id_echantillon`, `v_biologist`, `v_medcin`, `resultat`) VALUES
(1, 2, '00120240518123', 'valide', 'valide', '40,30,100,0.1,40,50,1,10,120,'),
(2, 1, '00220240518123', 'valide', 'valide', '40,30,100,0.1,40,50,1,10,120,'),
(3, 2, '00120240522123', 'valide', 'valide', '4.8,4.6,13.9,41,160,90,30,33.5,12,'),
(4, 1, '00220240522123', 'valide', 'valide', '40,30,100,0.1,40,50,1,10,120,'),
(5, 4, '001202405234', 'valide', '', '20,'),
(6, 2, '001202405234', '', '', ''),
(7, 3, '002202405234', 'valide', 'valide', 'Négatif,10,génotypage 1,'),
(8, 1, '002202405234', '', '', ''),
(9, 1, '0022024052717', '', '', ''),
(10, 3, '0022024052717', 'valide', 'valide', 'Négatif,12,N,'),
(11, 1, '0022024052718', '', '', ''),
(12, 3, '0022024052718', '', '', ''),
(13, 2, '0012024052817', 'valide', 'valide', '5,5,14,41,160,90,28,35,15,'),
(14, 1, '0022024052817', 'valide', 'valide', '8,12,45,0.1,24,1,1,7,98,'),
(15, 4, '001202405283', 'valide', 'valide', '10,'),
(16, 3, '002202405283', 'valide', 'valide', 'Négatif,14,N,'),
(17, 2, '0012025021019', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `id_stock` int(11) NOT NULL,
  `produit` varchar(255) DEFAULT NULL,
  `quantité` varchar(50) DEFAULT NULL,
  `fournisseur` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`id_stock`, `produit`, `quantité`, `fournisseur`, `Date`) VALUES
(1, 'tube 25mm', '22', 'mohcin charef', '2024-05-14'),
(2, 'tube 15mm', '40', 'Ahmed', '2024-05-08'),
(5, 'tube 20mm', '20', 'mohammed', '2024-05-08'),
(6, 'tube 30mm', '15', 'Ahmed', '2024-05-15'),
(7, 'tube 25mm', '12', 'mohcin charef', '2024-05-07'),
(8, 'produit2', '40', 'Mohammed', '2024-05-08');

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `Telephone` varchar(20) DEFAULT NULL,
  `Genre` varchar(20) DEFAULT NULL,
  `DateNaissance` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `mot_de_passe`, `email`, `position`, `Telephone`, `Genre`, `DateNaissance`) VALUES
(1, 'john', 'doe', 'password1', 'john@example.com', 'receptionist', '', '', NULL),
(2, 'jane', 'doe', 'password2', 'jane@example.com', 'medcine', '', '', NULL),
(3, 'alice', 'smith', 'password3', 'alice@example.com', 'biologist', '', '', NULL),
(4, 'bob', 'johnson', 'password4', 'bob@example.com', 'gerant', '', '', NULL),
(5, 'emily', 'davis', 'password5', 'emily@example.com', NULL, '', '', NULL),
(6, 'moh', 'cha', '112003', 'moh@gmail.com', 'biologist', '0554275786', 'Homme', '2024-05-06'),
(7, 'Sam', 'isaac', '2003', 'Samoudishak@gmail.com', 'medecine', '0541386050', 'Homme', '2003-08-13'),
(8, 'el7o', 'aymen', 'password8', 'el7oaymen@gmail.com', 'biologist', '0543672894', 'Homme', '2002-08-18');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `analyses`
--
ALTER TABLE `analyses`
  ADD PRIMARY KEY (`id_analyse`);

--
-- Indexes for table `bilan`
--
ALTER TABLE `bilan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_patient` (`id_patient`),
  ADD KEY `id_patient_2` (`id_patient`);

--
-- Indexes for table `caisse`
--
ALTER TABLE `caisse`
  ADD PRIMARY KEY (`id_caisse`);

--
-- Indexes for table `echantillon`
--
ALTER TABLE `echantillon`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `paiementemploye`
--
ALTER TABLE `paiementemploye`
  ADD PRIMARY KEY (`id_paiement_employe`);

--
-- Indexes for table `paiementfournisseur`
--
ALTER TABLE `paiementfournisseur`
  ADD PRIMARY KEY (`id_paiement_fournisseur`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id_patient`);

--
-- Indexes for table `resultat`
--
ALTER TABLE `resultat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id_stock`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `analyses`
--
ALTER TABLE `analyses`
  MODIFY `id_analyse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `bilan`
--
ALTER TABLE `bilan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `caisse`
--
ALTER TABLE `caisse`
  MODIFY `id_caisse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `paiementemploye`
--
ALTER TABLE `paiementemploye`
  MODIFY `id_paiement_employe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `paiementfournisseur`
--
ALTER TABLE `paiementfournisseur`
  MODIFY `id_paiement_fournisseur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id_patient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `resultat`
--
ALTER TABLE `resultat`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `id_stock` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bilan`
--
ALTER TABLE `bilan`
  ADD CONSTRAINT `bilan_ibfk_3` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id_patient`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
