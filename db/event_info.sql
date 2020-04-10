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

 Date: 04/10/2020 11:57:11 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `event_info`
-- ----------------------------
DROP TABLE IF EXISTS `event_info`;
CREATE TABLE `event_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `event_id` int(5) NOT NULL,
  `event_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `level_code` varchar(10) NOT NULL,
  `event_type` int(1) NOT NULL COMMENT '0-单打 1-双打 2-团体',
  `event_mode` int(1) unsigned zerofill NOT NULL COMMENT '0-单败淘汰赛 1-小组赛 2-双败淘汰赛',
  `week` int(2) NOT NULL,
  `season` int(1) NOT NULL,
  `date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `event_info`
-- ----------------------------
BEGIN;
INSERT INTO `event_info` VALUES ('1', '1001', '开元盛世赛', 'CT', '2', '3', '1', '1', '2019-01-20'), ('2', '1002', '翠微鸣柳大师赛', 'BDG+', '1', '1', '2', '1', '2019-03-03'), ('3', '1003', '玫瑰少年杯赛', 'CST', '0', '2', '3', '1', '2019-03-16'), ('4', '1004', '东临青云杯大师赛', 'BS32', '0', '0', '4', '1', '2019-03-30'), ('5', '1005', '春日大满贯赛', 'AS64', '0', '0', '5', '1', '2019-04-14'), ('6', '1006', '星源杯赛', 'CST', '0', '2', '6', '1', '2019-05-04'), ('7', '1007', '天使之翼大师赛', 'BD32', '1', '0', '7', '1', '2019-05-11'), ('8', '1008', '闲云野鹤杯赛', 'CT', '2', '3', '8', '1', '2019-05-25'), ('9', '1009', '天马山大满贯赛', 'AS32', '0', '0', '9', '1', '2019-06-22'), ('10', '1010', '花月百草杯赛', 'CST', '0', '2', '10', '1', '2019-09-15'), ('11', '1011', '兄弟同心大师赛', 'BS32', '0', '0', '11', '1', '2019-10-20'), ('12', '1012', '枫与火之歌赛', 'CD16', '1', '0', '12', '1', '2019-11-03'), ('13', '1013', '阳澄湖半岛大满贯赛', 'ASG', '0', '1', '13', '1', '2019-11-16'), ('14', '1014', '冰雪奇缘杯赛', 'CT', '2', '3', '14', '1', '2019-12-01'), ('15', '1015', '紫气东来赛', 'CST', '0', '2', '15', '1', '2019-12-08'), ('16', '1016', '单打年终总决赛', 'YECS', '0', '1', '16', '1', '2019-12-15'), ('17', '2001', '山河无恙杯赛', 'BS16', '0', '0', '2', '2', '2020-04-05');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
