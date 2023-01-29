-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 29, 2023 at 05:25 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hy360`
--

-- --------------------------------------------------------

--
-- Table structure for table `BankInfo`
--

CREATE TABLE `BankInfo` (
  `BankId` int(10) NOT NULL,
  `IBAN` int(10) NOT NULL,
  `bankName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `BankInfo`
--

INSERT INTO `BankInfo` (`BankId`, `IBAN`, `bankName`) VALUES
(1, 8888888, 'beta'),
(2, 9876, 'eurobank'),
(3, 55555, 'epsilon'),
(4, 123456, 'eurobank'),
(5, 33333, 'blahblah'),
(6, 2323, 'papapa'),
(7, 121212, 'papapalala'),
(8, 3133232, 'fefeefe'),
(9, 9867, 'eurobank'),
(10, 565656, 'alphabank'),
(11, 12345, 'eurobank'),
(12, 898989, 'alphabank'),
(13, 87654, 'kyprou'),
(14, 98123, 'pireus'),
(15, 234566, 'rethymnou'),
(16, 234567, 'heraklion'),
(17, 65432, 'eurobank'),
(18, 98765, 'pagkritia'),
(19, 45678, 'pagkritia'),
(20, 654321, 'alpha'),
(21, 56789, 'euroeuro');

-- --------------------------------------------------------

--
-- Table structure for table `Bonus`
--

CREATE TABLE `Bonus` (
  `BonusId` int(10) NOT NULL,
  `familyBonus` double NOT NULL,
  `searchBonus` double NOT NULL,
  `libraryBonus` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Bonus`
--

INSERT INTO `Bonus` (`BonusId`, `familyBonus`, `searchBonus`, `libraryBonus`) VALUES
(1, 0, 0, 0),
(2, 0.1, 1000, 0),
(3, 0.15, 0, 0),
(4, 0, 0, 0),
(5, 0.15, 0, 0),
(6, 0.15, 1000, 2000),
(7, 0.15, 0, 0),
(8, 0.2, 0, 0),
(9, 0.1, 1000, 0),
(10, 0.05, 0, 0),
(11, 0.15, 1000, 2000),
(12, 0.1, 1000, 2000),
(13, 0.05, 1000, 2000),
(14, 0.2, 1000, 2000),
(15, 0.1, 0, 0),
(16, 0, 1000, 2000),
(17, 0, 0, 0),
(18, 0, 0, 2000),
(19, 0, 0, 2000),
(20, 0, 0, 2000),
(21, 0, 0, 2000);

-- --------------------------------------------------------

--
-- Table structure for table `ContractorEducator`
--

CREATE TABLE `ContractorEducator` (
  `CEId` int(10) NOT NULL,
  `EmployeeId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ContractorEducator`
--

INSERT INTO `ContractorEducator` (`CEId`, `EmployeeId`) VALUES
(1, 7),
(2, 10),
(3, 11),
(4, 12),
(5, 15);

-- --------------------------------------------------------

--
-- Table structure for table `ContractorManager`
--

CREATE TABLE `ContractorManager` (
  `CMId` int(10) NOT NULL,
  `EmployeeId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ContractorManager`
--

INSERT INTO `ContractorManager` (`CMId`, `EmployeeId`) VALUES
(1, 6),
(2, 9),
(3, 14),
(4, 17),
(5, 18);

-- --------------------------------------------------------

--
-- Table structure for table `Employee`
--

CREATE TABLE `Employee` (
  `EmployeeId` int(10) NOT NULL,
  `firstName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `lastName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `beginHiringDate` date NOT NULL,
  `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `phoneNumber` int(10) NOT NULL,
  `salary` double NOT NULL,
  `active` int(1) NOT NULL,
  `LoginId` int(10) NOT NULL,
  `BankId` int(10) NOT NULL,
  `StateId` int(10) NOT NULL,
  `BonusId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Employee`
--

INSERT INTO `Employee` (`EmployeeId`, `firstName`, `lastName`, `beginHiringDate`, `address`, `phoneNumber`, `salary`, `active`, `LoginId`, `BankId`, `StateId`, `BonusId`) VALUES
(1, 'root', 'root', '1990-09-23', 'UOC', 2810, 32467.11, 1, 4, 1, 1, 1),
(2, 'Marilena', 'Nistikaki', '2023-01-04', 'boutes', 210000, 8925, 0, 5, 3, 4, 5),
(3, 'Tarso', 'Kelezi', '2020-09-08', 'kaminiiii', 343434, 4698.01, 1, 3, 7, 5, 9),
(4, 'Alexandra', 'Stamataki', '2020-01-01', 'kokkini', 111111, 4550, 0, 8, 2, 3, 2),
(5, 'Konstantina', 'Papafragkaki', '2012-10-01', 'bbbb', 2147483647, 16866.89, 1, 6, 6, 8, 7),
(6, 'iordanis', 'sapidis', '2022-12-03', 'aa34567', 34567, 4550, 0, 1, 9, 6, 8),
(7, 'Marios', 'Alexios', '2019-01-16', 'Kavalas', 696969, 2660, 1, 12, 12, 12, 12),
(8, 'Ioanna', 'Jonson', '2023-01-04', 'Syntagma', 6987654, 1725, 1, 10, 4, 7, 4),
(9, 'Riri', 'Konsta', '2000-01-01', 'Metaxourgeio', 222222, 49882.91, 0, 9, 5, 2, 3),
(10, 'Nikos', 'Antoniou', '2022-11-02', 'leykosias', 697825, 2630, 1, 13, 13, 13, 13),
(11, 'maria', 'papadaki', '2023-01-02', 'rethymnis', 1234567, 4465.83, 1, 11, 11, 11, 6),
(12, 'Eugenia', 'Markatatou', '2022-11-03', 'mastrapa', 697666, 2720, 1, 14, 14, 14, 14),
(13, 'Panagiotis', 'Zirouras', '2023-01-02', 'salaminas', 691234, 1725, 1, 19, 19, 21, 21),
(14, 'Ntina', 'Ntinaki', '2023-01-02', 'ntinoiiii', 698765, 660, 1, 15, 15, 15, 15),
(15, 'Maripo', 'Maripo', '2023-01-12', 'mapapapa', 697654, 2600, 1, 16, 17, 16, 16),
(16, 'frfrfr', 'frfrfrfr', '2023-03-01', '4343', 434343, 4065.02, 1, 7, 8, 9, 10),
(17, 'Antonis', 'Papadakis', '2023-01-03', 'makria', 694322, 600, 1, 21, 16, 19, 19),
(18, 'Maria', 'Marigo', '2023-01-03', 'qwerty', 690876, 600, 1, 17, 21, 17, 17),
(19, 'konstantina', 'papa', '2023-03-01', 'rapampam', 7878787, 4215.02, 1, 2, 10, 10, 11),
(20, 'Eleni', 'Lilaki', '2023-01-10', 'qwer4567', 697876, 1725, 1, 18, 18, 18, 18),
(21, 'Maria', 'Lilaki', '2023-01-02', 'qwerty12345', 698764, 1725, 1, 20, 20, 20, 20);

-- --------------------------------------------------------

--
-- Table structure for table `FamilyState`
--

CREATE TABLE `FamilyState` (
  `StateId` int(10) NOT NULL,
  `state` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `numberKids` int(10) NOT NULL,
  `ages` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `FamilyState`
--

INSERT INTO `FamilyState` (`StateId`, `state`, `numberKids`, `ages`) VALUES
(1, 'unmarried', 0, ''),
(2, 'married', 3, '12,18,7'),
(3, 'married', 2, '15,21'),
(4, 'unmarried', 4, '12,12,12,40'),
(5, 'married', 2, '12,40'),
(6, 'married', 3, '12,13,14'),
(7, 'unmarried', 0, ''),
(8, 'married', 5, '23,26,12,23,11'),
(9, 'unmarried', 5, '11,12,13,18,19'),
(10, 'married', 3, '25,1,7'),
(11, 'unmarried', 3, '12,12,12'),
(12, 'unmarried', 3, '12,20,8'),
(13, 'married', 0, ''),
(14, 'married', 4, '12,1,17,30'),
(15, 'unmarried', 2, '1,5'),
(16, 'unmarried', 0, ''),
(17, 'unmarried', 0, ''),
(18, 'unmarried', 0, ''),
(19, 'unmarried', 0, ''),
(20, 'unmarried', 0, ''),
(21, 'unmarried', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `LoginInfo`
--

CREATE TABLE `LoginInfo` (
  `LoginId` int(10) NOT NULL,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `LoginInfo`
--

INSERT INTO `LoginInfo` (`LoginId`, `username`, `password`) VALUES
(1, '15', '15a'),
(2, 'cm', 'cm'),
(3, 'username', 'password'),
(4, 'root', 'root'),
(5, '1', '1'),
(6, 'pm', 'pm'),
(7, 'ce', 'ce'),
(8, 'pe', 'pe'),
(9, 'username1', 'password1'),
(10, 'useruser', 'passpass'),
(11, 'aaaa', 'bbbb'),
(12, 'userrrr', 'passss'),
(13, 'nikos', 'antoniou'),
(14, 'tzeni', 'mast'),
(15, 'father', 'father'),
(16, 'marsoupilami', 'marsoupilami'),
(17, 'muttt', 'muttt'),
(18, 'useruseruser', 'passpasspass'),
(19, 'user123', 'pass123'),
(20, 'user12345', 'pass12345'),
(21, 'users', 'passwords');

-- --------------------------------------------------------

--
-- Table structure for table `PermanentEducator`
--

CREATE TABLE `PermanentEducator` (
  `PEId` int(10) NOT NULL,
  `EmployeeId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `PermanentEducator`
--

INSERT INTO `PermanentEducator` (`PEId`, `EmployeeId`) VALUES
(1, 3),
(2, 4),
(3, 20),
(4, 21),
(5, 13);

-- --------------------------------------------------------

--
-- Table structure for table `PermanentManager`
--

CREATE TABLE `PermanentManager` (
  `PMId` int(10) NOT NULL,
  `EmployeeId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `PermanentManager`
--

INSERT INTO `PermanentManager` (`PMId`, `EmployeeId`) VALUES
(1, 1),
(2, 2),
(3, 16),
(4, 19),
(5, 8),
(6, 5);

-- --------------------------------------------------------

--
-- Table structure for table `Raise`
--

CREATE TABLE `Raise` (
  `RaiseId` int(10) NOT NULL,
  `date` date NOT NULL,
  `raiseFamBonus` double NOT NULL,
  `raiseSearchBonus` double NOT NULL,
  `raiseLibraryBonus` double NOT NULL,
  `raiseSalary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Raise`
--

INSERT INTO `Raise` (`RaiseId`, `date`, `raiseFamBonus`, `raiseSearchBonus`, `raiseLibraryBonus`, `raiseSalary`) VALUES
(1, '2023-01-19', 0, 1000, 0, 0),
(2, '2023-01-19', 0, 0, 1600, 0),
(3, '2023-03-01', 0.09999999999999999, 0, 0, 0),
(4, '2023-03-01', 0, 0, 0, 384.97),
(5, '2023-03-01', 0, 0, 0, 308.25),
(6, '2023-03-01', 0, 0, 0, 1905),
(7, '2023-02-01', 0, 0, 0, 3825);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `BankInfo`
--
ALTER TABLE `BankInfo`
  ADD PRIMARY KEY (`BankId`);

--
-- Indexes for table `Bonus`
--
ALTER TABLE `Bonus`
  ADD PRIMARY KEY (`BonusId`);

--
-- Indexes for table `ContractorEducator`
--
ALTER TABLE `ContractorEducator`
  ADD PRIMARY KEY (`CEId`);

--
-- Indexes for table `ContractorManager`
--
ALTER TABLE `ContractorManager`
  ADD PRIMARY KEY (`CMId`);

--
-- Indexes for table `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`EmployeeId`);

--
-- Indexes for table `FamilyState`
--
ALTER TABLE `FamilyState`
  ADD PRIMARY KEY (`StateId`);

--
-- Indexes for table `LoginInfo`
--
ALTER TABLE `LoginInfo`
  ADD PRIMARY KEY (`LoginId`);

--
-- Indexes for table `PermanentEducator`
--
ALTER TABLE `PermanentEducator`
  ADD PRIMARY KEY (`PEId`);

--
-- Indexes for table `PermanentManager`
--
ALTER TABLE `PermanentManager`
  ADD PRIMARY KEY (`PMId`);

--
-- Indexes for table `Raise`
--
ALTER TABLE `Raise`
  ADD PRIMARY KEY (`RaiseId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `BankInfo`
--
ALTER TABLE `BankInfo`
  MODIFY `BankId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `Bonus`
--
ALTER TABLE `Bonus`
  MODIFY `BonusId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `ContractorEducator`
--
ALTER TABLE `ContractorEducator`
  MODIFY `CEId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `ContractorManager`
--
ALTER TABLE `ContractorManager`
  MODIFY `CMId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Employee`
--
ALTER TABLE `Employee`
  MODIFY `EmployeeId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `FamilyState`
--
ALTER TABLE `FamilyState`
  MODIFY `StateId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `LoginInfo`
--
ALTER TABLE `LoginInfo`
  MODIFY `LoginId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `PermanentEducator`
--
ALTER TABLE `PermanentEducator`
  MODIFY `PEId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `PermanentManager`
--
ALTER TABLE `PermanentManager`
  MODIFY `PMId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `Raise`
--
ALTER TABLE `Raise`
  MODIFY `RaiseId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
