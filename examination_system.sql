/*
Navicat MySQL Data Transfer

Source Server         : ctgu
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : examination_system

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-06-06 12:08:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `course_name` varchar(50) DEFAULT NULL,
  `teacher_id` varchar(20) DEFAULT NULL,
  `classroom` varchar(20) DEFAULT NULL,
  `course_week` smallint(6) DEFAULT NULL,
  `course_time` smallint(6) DEFAULT NULL,
  `credit` float DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `course_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '100001', 'c语言程序设计', '1001', 'J3301', '15', '2', '2', '101', '必修课');
INSERT INTO `course` VALUES ('2', '100002', 'Python程序设计', '1002', 'B3419', '12', '3', '3', '101', '必修课');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT NULL,
  `department_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '101', '计算机学院');
INSERT INTO `department` VALUES ('2', '102', '电气与新能源学院');
INSERT INTO `department` VALUES ('3', '103', '文学与传媒学院');
INSERT INTO `department` VALUES ('4', '104', '艺术学院');
INSERT INTO `department` VALUES ('5', '105', '经济与管理学院');
INSERT INTO `department` VALUES ('6', '106', '医学院');

-- ----------------------------
-- Table structure for selectedcourse
-- ----------------------------
DROP TABLE IF EXISTS `selectedcourse`;
CREATE TABLE `selectedcourse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) DEFAULT NULL,
  `teacher_id` varchar(20) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `score` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of selectedcourse
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `gendar` smallint(6) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `enter_date` datetime DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('13', '10001', '汪俊', '1', '1997-09-02 00:00:00', '2015-09-02 00:00:00', '101');
INSERT INTO `student` VALUES ('14', '10002', '郑翠', '0', '1998-09-02 00:00:00', '2015-09-02 00:00:00', '102');
INSERT INTO `student` VALUES ('15', '10003', '吴钻', '0', '1997-07-02 00:00:00', '2015-09-02 00:00:00', '104');
INSERT INTO `student` VALUES ('16', '10004', '张华厂', '1', '1997-09-02 00:00:00', '2015-09-02 00:00:00', '103');
INSERT INTO `student` VALUES ('17', '10005', '张三', '1', '1992-09-02 00:00:00', '2015-09-02 00:00:00', '106');
INSERT INTO `student` VALUES ('18', '10006', '李四', '0', '1999-09-02 00:00:00', '2015-09-02 00:00:00', '101');
INSERT INTO `student` VALUES ('19', '10007', '王五', '1', '1997-09-04 00:00:00', '2015-09-02 00:00:00', '101');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `gendar` smallint(6) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `enter_date` datetime DEFAULT NULL,
  `degree` varchar(10) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('5', '1001', '陈翔', '1', '1985-02-15 00:00:00', '2015-09-02 00:00:00', '本科', '普通教师', '103');
INSERT INTO `teacher` VALUES ('9', '1002', '王安慧', '1', '1979-03-25 00:00:00', '2015-09-02 00:00:00', '硕士', '副教授', '101');
INSERT INTO `teacher` VALUES ('10', '1003', '徐守志', '1', '1975-09-02 00:00:00', '2015-09-02 00:00:00', '本科', '普通教师', '106');
INSERT INTO `teacher` VALUES ('11', '1004', '刘国强', '1', '1987-09-02 00:00:00', '2015-09-02 00:00:00', '本科', '普通教师', '104');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `role` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '654321', '0');
INSERT INTO `user` VALUES ('17', '1001', '123456', '1');
INSERT INTO `user` VALUES ('21', '10001', '654321', '2');
INSERT INTO `user` VALUES ('22', '10002', '123456', '2');
INSERT INTO `user` VALUES ('23', '10003', '123456', '2');
INSERT INTO `user` VALUES ('24', '10004', '123456', '2');
INSERT INTO `user` VALUES ('25', '10005', '123456', '2');
INSERT INTO `user` VALUES ('26', '10006', '123456', '2');
INSERT INTO `user` VALUES ('27', '10007', '123456', '2');
INSERT INTO `user` VALUES ('28', '1002', '123456', '1');
INSERT INTO `user` VALUES ('29', '1003', '123456', '1');
INSERT INTO `user` VALUES ('30', '1004', '123456', '1');
