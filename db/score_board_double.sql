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

 Date: 05/03/2020 14:03:19 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `score_board_double`
-- ----------------------------
DROP TABLE IF EXISTS `score_board_double`;
CREATE TABLE `score_board_double` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `event_id` int(5) NOT NULL,
  `event_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `round` varchar(10) CHARACTER SET utf8 NOT NULL,
  `week` int(2) NOT NULL,
  `season` int(1) NOT NULL,
  `player_1_id_a` int(4) NOT NULL,
  `player_1_name_a` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `player_1_rank_a` int(4) NOT NULL,
  `player_1_id_b` int(11) NOT NULL,
  `player_1_name_b` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `player_1_rank_b` int(4) NOT NULL,
  `player_1_score` int(2) NOT NULL,
  `player_2_id_a` int(4) NOT NULL,
  `player_2_name_a` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `player_2_rank_a` int(4) NOT NULL,
  `player_2_id_b` int(4) NOT NULL,
  `player_2_name_b` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `player_2_rank_b` int(4) NOT NULL,
  `player_2_score` int(2) NOT NULL,
  `wo` int(1) NOT NULL DEFAULT '0' COMMENT '是否赛前退赛：0-未退赛 1-退赛',
  `ret` int(1) NOT NULL DEFAULT '0' COMMENT '是否赛中退赛：0-无退赛 1-player1退赛 2-player2退赛',
  `event_level` varchar(10) CHARACTER SET utf8 NOT NULL,
  `level_code` varchar(10) CHARACTER SET utf8 NOT NULL,
  `handle` int(1) unsigned zerofill NOT NULL,
  `match_mode` int(1) NOT NULL COMMENT '0-单打 1-双打',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
