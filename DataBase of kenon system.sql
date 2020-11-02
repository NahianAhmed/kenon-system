-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 02, 2020 at 01:24 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java`
--

-- --------------------------------------------------------

--
-- Table structure for table `m_user`
--

CREATE TABLE `m_user` (
  `社員番号` varchar(255) NOT NULL,
  `管理権限` bit(1) NOT NULL,
  `作成日時` datetime NOT NULL,
  `部門` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `mail` varchar(255) NOT NULL,
  `氏名` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `氏名カナ` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `更新日時` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `m_user`
--

INSERT INTO `m_user` (`社員番号`, `管理権限`, `作成日時`, `部門`, `mail`, `氏名`, `氏名カナ`, `更新日時`) VALUES
('u001', b'0', '2020-10-21 22:33:40', 'ソフトウェア', 'nahianofficially@gmail.com', 'Nahian Ahmed', 'ナヒアンアハメド', '2020-10-28 20:05:50'),
('u002', b'0', '2020-10-28 20:05:48', '会計', 'mailingtonahian@gmail.com', 'Momin san', 'モミン', '2020-10-28 20:05:50'),
('u003', b'1', '2020-10-21 22:33:41', 'ソフトウェア', 'nahianahmedcse@gmail.com', 'Aoyagi san', '青柳', '2020-10-28 20:05:50');

-- --------------------------------------------------------

--
-- Table structure for table `m_user_password`
--

CREATE TABLE `m_user_password` (
  `社員番号` varchar(255) NOT NULL,
  `パスワード` varchar(255) NOT NULL,
  `トークン` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `m_user_password`
--

INSERT INTO `m_user_password` (`社員番号`, `パスワード`, `トークン`) VALUES
('u001', '530eada1f20dfdb29db65a4f874ce411278ebde1d9d080036feb186c694ed003', ''),
('u002', '12809674aac5b402efa417ae4ee476fd534861bf921bdc1359169312a3334145', ''),
('u003', '1bc24cd877ce69d5e4fc0e9e4c30f0b7864a8b6a0e2f7930959a72857f0e5abf', '');

-- --------------------------------------------------------

--
-- Table structure for table `temparature_mesurement`
--

CREATE TABLE `temparature_mesurement` (
  `id` bigint(20) NOT NULL,
  `更新日時` datetime NOT NULL,
  `症状の有無` bit(1) NOT NULL,
  `体温` double NOT NULL,
  `社員番号` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `temparature_mesurement`
--

INSERT INTO `temparature_mesurement` (`id`, `更新日時`, `症状の有無`, `体温`, `社員番号`) VALUES
(1, '2020-10-26 00:48:15', b'0', 33.33, 'u003'),
(2, '2020-10-26 00:48:36', b'0', 33, 'u003'),
(3, '2020-10-26 01:32:26', b'0', 99.9, 'u003'),
(4, '2020-10-26 01:34:40', b'0', 55.56, 'u001'),
(5, '2020-10-26 01:35:26', b'0', 44.89, 'u001'),
(6, '2020-10-26 04:25:48', b'0', 33.4, 'u001'),
(7, '2020-10-26 04:26:42', b'0', 44.9, 'u003'),
(8, '2020-10-28 10:21:20', b'0', 33.8, 'u003'),
(9, '2020-10-30 00:19:35', b'0', 44.44, 'u003'),
(10, '2020-10-30 00:28:17', b'0', 33.56, 'u001'),
(11, '2020-11-01 19:37:26', b'0', 34.44, 'u003'),
(12, '2020-11-01 19:38:00', b'1', 36.7, 'u001');

-- --------------------------------------------------------

--
-- Table structure for table `temp_capture`
--

CREATE TABLE `temp_capture` (
  `id` bigint(20) NOT NULL,
  `day1` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `day2` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `day3` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `day4` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `day5` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `dept` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `kananame` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `userid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `m_user`
--
ALTER TABLE `m_user`
  ADD PRIMARY KEY (`社員番号`);

--
-- Indexes for table `m_user_password`
--
ALTER TABLE `m_user_password`
  ADD PRIMARY KEY (`社員番号`);

--
-- Indexes for table `temparature_mesurement`
--
ALTER TABLE `temparature_mesurement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `temp_capture`
--
ALTER TABLE `temp_capture`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `temparature_mesurement`
--
ALTER TABLE `temparature_mesurement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `temp_capture`
--
ALTER TABLE `temp_capture`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
