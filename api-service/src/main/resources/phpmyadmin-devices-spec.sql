-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 04, 2023 at 02:25 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `devices-spec`
--

-- --------------------------------------------------------

--
-- Table structure for table `brandmodelmap`
--

CREATE TABLE `brandmodelmap` (
  `mapid` int(11) NOT NULL,
  `brandname` text DEFAULT NULL,
  `brandurl` text DEFAULT NULL,
  `modelname` text DEFAULT NULL,
  `modelurl` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Contains mapping of Brand Model with mapId';

-- --------------------------------------------------------

--
-- Table structure for table `briefspecification`
--

CREATE TABLE `briefspecification` (
  `specificationid` int(11) NOT NULL,
  `mapid` text NOT NULL,
  `devicename` text DEFAULT NULL,
  `dimensions` text DEFAULT NULL,
  `weight` text DEFAULT NULL,
  `soc` text DEFAULT NULL,
  `cpu` text DEFAULT NULL,
  `gpu` text DEFAULT NULL,
  `ram` text DEFAULT NULL,
  `storage` text DEFAULT NULL,
  `memorycards` text DEFAULT NULL,
  `display` text DEFAULT NULL,
  `battery` text DEFAULT NULL,
  `os` text DEFAULT NULL,
  `camera` text DEFAULT NULL,
  `simcards` text DEFAULT NULL,
  `wifi` text DEFAULT NULL,
  `usb` text DEFAULT NULL,
  `bluetooth` text DEFAULT NULL,
  `positioning` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Contains Brief Specifications';

-- --------------------------------------------------------

--
-- Table structure for table `detailspecification`
--

CREATE TABLE `detailspecification` (
  `specificationid` int(11) NOT NULL,
  `mapid` text NOT NULL,
  `category` text DEFAULT NULL,
  `categorydescription` text DEFAULT NULL,
  `propertyname` text DEFAULT NULL,
  `propertydescription` text DEFAULT NULL,
  `valuesarray` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Contains Detailed Specifications';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brandmodelmap`
--
ALTER TABLE `brandmodelmap`
  ADD PRIMARY KEY (`mapid`);

--
-- Indexes for table `briefspecification`
--
ALTER TABLE `briefspecification`
  ADD PRIMARY KEY (`specificationid`);

--
-- Indexes for table `detailspecification`
--
ALTER TABLE `detailspecification`
  ADD PRIMARY KEY (`specificationid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brandmodelmap`
--
ALTER TABLE `brandmodelmap`
  MODIFY `mapid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `briefspecification`
--
ALTER TABLE `briefspecification`
  MODIFY `specificationid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `detailspecification`
--
ALTER TABLE `detailspecification`
  MODIFY `specificationid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
