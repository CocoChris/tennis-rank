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

 Date: 05/03/2020 15:25:02 PM
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

-- ----------------------------
--  Records of `event_level`
-- ----------------------------
BEGIN;
INSERT INTO `event_level` VALUES ('1', 'T1', 'AS32', null, null, null, null, null, '90', '180', '360', '720', '1200', '2000'), ('2', 'T2', 'BS32', '1', null, '10', '25', null, '35', '70', '150', '300', '600', '1000'), ('3', 'T2', 'BS16', '1', null, '25', '70', null, null, '70', '150', '300', '600', '1000'), ('4', 'T2', 'BD32', null, null, null, null, null, '30', '60', '120', '240', '480', '800'), ('5', 'T2', 'BD16', '1', null, '20', '60', null, null, '60', '120', '240', '480', '800'), ('6', 'T3', 'CD16', null, null, null, null, null, null, '30', '60', '120', '240', '400'), ('7', 'T1', 'ASG', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('8', 'T1', 'ADG', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('9', 'T1.5', 'BDG+', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('10', 'T2', 'BSG', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('11', 'T2', 'BDG', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('12', 'T3', 'CST', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('13', 'T3', 'CDG', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('14', 'T3', 'CT', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('15', 'YEC', 'YECS', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('16', 'YEC', 'YECD', null, null, null, null, null, null, null, '0', '0', '0', '0'), ('17', 'T1', 'AS64', null, null, null, null, '45', '90', '180', '360', '720', '1200', '2000');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
