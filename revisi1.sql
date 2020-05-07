-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2019 at 03:21 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `revisi1`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kodeBarang` varchar(11) NOT NULL,
  `namaBarang` varchar(30) DEFAULT NULL,
  `jenis` varchar(20) DEFAULT NULL,
  `stokBarang` int(5) DEFAULT NULL,
  `hargaJual` int(10) DEFAULT NULL,
  `satuan` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kodeBarang`, `namaBarang`, `jenis`, `stokBarang`, `hargaJual`, `satuan`) VALUES
('BR-1', 'Fotocopy A4', 'fotocopy', 96, 250, 'lembar'),
('BR-10', 'penghapus Fabel Castle', 'barang', 100, 1000, 'pcs'),
('BR-11', 'penghapus Kenko', 'barang', 60, 3500, 'pcs'),
('BR-2', 'Print A4', 'print', 96, 1000, 'lembar'),
('BR-3', 'Soft Cover', 'jilid', 96, 3000, 'rangkap'),
('BR-4', 'Pena mygel', 'barang', 98, 4500, 'pcs'),
('BR-5', 'Pensil Faber Castle', 'barang', 98, 4500, 'pcs'),
('BR-6', 'Fotocpy A4 Warna', 'fotocopy', 500, 1000, 'lembar'),
('BR-7', 'Print A4 Warna', 'print', 520, 1000, 'lembar'),
('BR-8', 'Pena Pilot', 'barang', 100, 1500, 'pcs'),
('BR-9', 'Pensil Grebel', 'barang', 100, 2500, 'pcs');

-- --------------------------------------------------------

--
-- Table structure for table `jualbeli`
--

CREATE TABLE `jualbeli` (
  `nota` varchar(11) DEFAULT NULL,
  `deskripsi` varchar(1024) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `pemasukan` int(11) DEFAULT NULL,
  `pengeluaran` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jualbeli`
--

INSERT INTO `jualbeli` (`nota`, `deskripsi`, `tanggal`, `pemasukan`, `pengeluaran`) VALUES
('PB-1', 'Penambahan Barang mamank					20\n', '2019-06-19', 0, 30000),
('PB-2', '', '2019-06-19', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `nota` varchar(11) DEFAULT NULL,
  `kodeBarang` varchar(11) DEFAULT NULL,
  `jumlahBarang` int(5) DEFAULT NULL,
  `hargaBarang` int(10) DEFAULT NULL,
  `keterangan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `namaLengkap` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `namaLengkap`) VALUES
('admin', 'admin', 'Muhammad Yofi Indrawan'),
('broFadhil', 'qwerty123', 'Fadhil Madany'),
('mamank', 'mamank', 'Muhammad Alfaridzi');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kodeBarang`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD KEY `kodeBarang` (`kodeBarang`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`kodeBarang`) REFERENCES `barang` (`kodeBarang`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
