-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2025 at 07:45 AM
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
-- Database: `filecompression`
--
CREATE DATABASE IF NOT EXISTS `filecompression` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci;
USE `filecompression`;

-- --------------------------------------------------------

--
-- Table structure for table `compression_jobs`
--

CREATE TABLE `compression_jobs` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `original_filename` varchar(255) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `compressed_path` varchar(500) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `compression_jobs`
--

INSERT INTO `compression_jobs` (`id`, `user_id`, `original_filename`, `file_path`, `compressed_path`, `status`, `created_at`) VALUES
(1, 1, 'Folder_1763887118872 (1 files)', 'D:\\Uploads\\Folder_1763887118872', 'D:\\Uploads\\Folder_1763887118872.zip', 'COMPLETED', '2025-11-23 08:38:38'),
(2, 1, 'Folder_1763887907114 (2 files)', 'D:\\Uploads\\Folder_1763887907114', 'D:\\Uploads\\Folder_1763887907114.zip', 'COMPLETED', '2025-11-23 08:51:47'),
(3, 1, 'Folder_1763892403768 (1 files)', 'D:\\Uploads\\Folder_1763892403768', 'D:\\Uploads\\Folder_1763892403768.zip', 'COMPLETED', '2025-11-23 10:06:43'),
(4, 1, 'Folder_1763892446998 (1 files)', 'D:\\Uploads\\Folder_1763892446998', 'D:\\Uploads\\Folder_1763892446998.zip', 'COMPLETED', '2025-11-23 10:07:27'),
(5, 1, 'Folder_1763892459258 (1 files)', 'D:\\Uploads\\Folder_1763892459258', 'D:\\Uploads\\Folder_1763892459258.zip', 'COMPLETED', '2025-11-23 10:07:39'),
(6, 3, 'Folder_1763904604953 (1 files)', 'D:\\Uploads\\Folder_1763904604953', 'D:\\Uploads\\Folder_1763904604953.zip', 'COMPLETED', '2025-11-23 13:30:04'),
(7, 3, 'Folder_1763955625561 (4 files)', 'D:\\Uploads\\Folder_1763955625561', 'D:\\Uploads\\Folder_1763955625561.zip', 'COMPLETED', '2025-11-24 03:40:25'),
(8, 3, 'Folder_1763955663924 (7 files)', 'D:\\Uploads\\Folder_1763955663924', 'D:\\Uploads\\Folder_1763955663924.zip', 'COMPLETED', '2025-11-24 03:41:03'),
(9, 6, 'Folder_1763957281367 (6 files)', 'D:\\Uploads\\Folder_1763957281367', 'D:\\Uploads\\Folder_1763957281367.zip', 'COMPLETED', '2025-11-24 04:08:01'),
(10, 5, 'Folder_1763959069818 (1 files)', 'D:\\Uploads\\Folder_1763959069818', 'D:\\Uploads\\Folder_1763959069818.zip', 'COMPLETED', '2025-11-24 04:37:49'),
(11, 2, 'Folder_1763965341374 (3 files)', 'D:\\Uploads\\Folder_1763965341374', 'D:\\Uploads\\Folder_1763965341374.zip', 'COMPLETED', '2025-11-24 06:22:21'),
(12, 2, 'Folder_1763965559502 (2 files)', 'D:\\Uploads\\Folder_1763965559502', 'D:\\Uploads\\Folder_1763965559502.zip', 'COMPLETED', '2025-11-24 06:25:59'),
(13, 7, 'Folder_1763965877284 (9 files)', 'D:\\Uploads\\Folder_1763965877284', 'D:\\Uploads\\Folder_1763965877284.zip', 'COMPLETED', '2025-11-24 06:31:17');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `full_name`) VALUES
(1, 'admin', '123', 'Đô Rê Mon'),
(2, 'user1', '123', 'Lê Văn Mem'),
(3, 'user2', '123', 'Nguyễn Diu Sơ'),
(4, 'user3', '123', 'Nguyễn Đăng Anh Tiến'),
(5, 'user4', '123', 'Nô Bi Ta'),
(6, 'user5', '123', 'Đô Nan Chum'),
(7, 'tue123', '123', 'Moww');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `compression_jobs`
--
ALTER TABLE `compression_jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `compression_jobs`
--
ALTER TABLE `compression_jobs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `compression_jobs`
--
ALTER TABLE `compression_jobs`
  ADD CONSTRAINT `compression_jobs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
