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

 Date: 04/10/2020 11:57:03 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `current_phase`
-- ----------------------------
DROP TABLE IF EXISTS `current_phase`;
CREATE TABLE `current_phase` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `current_week` int(2) unsigned zerofill NOT NULL,
  `current_season` int(1) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `current_phase`
-- ----------------------------
BEGIN;
INSERT INTO `current_phase` VALUES ('1', '2', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
