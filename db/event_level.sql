/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost
 Source Database       : ita

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : utf-8

 Date: 05/03/2020 15:22:07 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `event_level`
-- ----------------------------
DROP TABLE IF EXISTS `event_level`;
CREATE TABLE `event_level` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `level` varchar(10) NOT NULL,
  `code` varchar(10) NOT NULL,
  `QR1` int(10) DEFAULT NULL,
  `QR2` int(10) DEFAULT NULL,
  `QFi` int(10) DEFAULT NULL,
  `Q` int(10) DEFAULT NULL,
  `R64` int(10) DEFAULT NULL,
  `R32` int(10) DEFAULT NULL,
  `R16` int(10) DEFAULT NULL,
  `QF` int(10) NOT NULL,
  `SF` int(10) NOT NULL,
  `F` int(10) NOT NULL,
  `W` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
