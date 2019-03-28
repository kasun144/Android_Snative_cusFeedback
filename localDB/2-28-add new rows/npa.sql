-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 28, 2019 at 04:00 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `npa`
--
CREATE DATABASE IF NOT EXISTS `npa` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `npa`;

-- --------------------------------------------------------

--
-- Table structure for table `comm`
--

CREATE TABLE `comm` (
  `id` int(255) NOT NULL,
  `comment` varchar(1000) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commenttwo`
--

CREATE TABLE `commenttwo` (
  `id` int(255) NOT NULL,
  `commtwo` varchar(1000) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE `locations` (
  `id` int(11) NOT NULL,
  `location` varchar(1000) DEFAULT NULL,
  `lid` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `names`
--

CREATE TABLE `names` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contactno` varchar(100) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `radio`
--

CREATE TABLE `radio` (
  `id` int(11) NOT NULL,
  `red` varchar(255) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rate`
--

CREATE TABLE `rate` (
  `id` int(255) NOT NULL,
  `rate1` varchar(1000) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rates3`
--

CREATE TABLE `rates3` (
  `id` int(255) NOT NULL,
  `rateone` varchar(1000) NOT NULL,
  `ratetwo` varchar(1000) NOT NULL,
  `ratethree` varchar(1000) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tenrate`
--

CREATE TABLE `tenrate` (
  `id` int(255) NOT NULL,
  `tenrates` varchar(1000) NOT NULL,
  `locid` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comm`
--
ALTER TABLE `comm`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- Indexes for table `commenttwo`
--
ALTER TABLE `commenttwo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `names`
--
ALTER TABLE `names`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- Indexes for table `radio`
--
ALTER TABLE `radio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- Indexes for table `rate`
--
ALTER TABLE `rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- Indexes for table `rates3`
--
ALTER TABLE `rates3`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- Indexes for table `tenrate`
--
ALTER TABLE `tenrate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locid` (`locid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comm`
--
ALTER TABLE `comm`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `commenttwo`
--
ALTER TABLE `commenttwo`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `names`
--
ALTER TABLE `names`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `radio`
--
ALTER TABLE `radio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rate`
--
ALTER TABLE `rate`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rates3`
--
ALTER TABLE `rates3`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tenrate`
--
ALTER TABLE `tenrate`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comm`
--
ALTER TABLE `comm`
  ADD CONSTRAINT `comm_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);

--
-- Constraints for table `commenttwo`
--
ALTER TABLE `commenttwo`
  ADD CONSTRAINT `commenttwo_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);

--
-- Constraints for table `names`
--
ALTER TABLE `names`
  ADD CONSTRAINT `names_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);

--
-- Constraints for table `radio`
--
ALTER TABLE `radio`
  ADD CONSTRAINT `radio_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);

--
-- Constraints for table `rate`
--
ALTER TABLE `rate`
  ADD CONSTRAINT `rate_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);

--
-- Constraints for table `rates3`
--
ALTER TABLE `rates3`
  ADD CONSTRAINT `rates3_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);

--
-- Constraints for table `tenrate`
--
ALTER TABLE `tenrate`
  ADD CONSTRAINT `tenrate_ibfk_1` FOREIGN KEY (`locid`) REFERENCES `locations` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
