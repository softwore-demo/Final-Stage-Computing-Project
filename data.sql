/*
MySQL Backup
Source Server Version: 5.6.43
Source Database: campusofficemanagementsystem
Date: 2020/5/29 01:37:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `t_apply`
-- ----------------------------
DROP TABLE IF EXISTS `t_apply`;
CREATE TABLE `t_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL COMMENT 'time',
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_course`
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `persons` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_leave`
-- ----------------------------
DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE `t_leave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_meeting`
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting`;
CREATE TABLE `t_meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `persons` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `MENU_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'MENU_ID',
  `PARENT_ID` bigint(20) NOT NULL COMMENT 'PARENT_ID',
  `MENU_NAME` varchar(50) NOT NULL COMMENT 'MENU_NAME',
  `URL` varchar(50) DEFAULT NULL COMMENT 'URL',
  `PERMS` text COMMENT 'PERMS',
  `ICON` varchar(50) DEFAULT NULL COMMENT 'ICON',
  `TYPE` char(2) NOT NULL COMMENT 'TYPE',
  `ORDER_NUM` bigint(20) DEFAULT NULL COMMENT 'ORDER',
  `CREATE_TIME` datetime NOT NULL COMMENT 'CREATE TIME',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT 'MODIFY TIME',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_notification`
-- ----------------------------
DROP TABLE IF EXISTS `t_notification`;
CREATE TABLE `t_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ROLE_NAME` varchar(100) NOT NULL COMMENT 'NAME',
  `REMARK` varchar(100) DEFAULT NULL COMMENT 'DESCRIPTION',
  `CREATE_TIME` datetime NOT NULL COMMENT 'CREATE TIME',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT 'MODIFY TIME',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT 'ID',
  `MENU_ID` bigint(20) NOT NULL COMMENT 'MENU ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `USERNAME` varchar(50) NOT NULL COMMENT 'USERNAME',
  `PASSWORD` varchar(128) NOT NULL COMMENT 'PASSWORD',
  `DEPT_ID` bigint(20) DEFAULT NULL COMMENT 'DEPT ID',
  `EMAIL` varchar(128) DEFAULT NULL COMMENT 'E-MAIL',
  `MOBILE` varchar(20) DEFAULT NULL COMMENT 'MOBILE',
  `STATUS` char(1) NOT NULL COMMENT 'STATUS',
  `CREATE_TIME` datetime NOT NULL COMMENT 'CREATE TIME',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT 'MODIFY TIME',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT 'LAST LOGIN TIME',
  `IS_TAB` char(1) DEFAULT NULL COMMENT 'TAB',
  `THEME` varchar(10) DEFAULT NULL COMMENT 'THEME',
  `AVATAR` varchar(100) DEFAULT NULL COMMENT 'AVATAR',
  `DESCRIPTION` varchar(100) DEFAULT NULL COMMENT 'DESCRIPTION',
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `USER_ID` bigint(20) NOT NULL COMMENT 'ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT 'ROLE ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1','0','System','','','layui-icon-setting','0','1','2017-12-27 16:39:07','2020-04-24 21:31:34'), ('3','1','User Management','/system/user','user:view','','0','1','2017-12-27 16:47:13','2020-04-24 21:31:41'), ('287','0','Notification','/office/notification','','layui-icon-sound-fill','0','2','2020-03-26 23:34:53','2020-04-25 12:00:55'), ('298','0','Attendance','/office/apply','','layui-icon-dashboard','0','3','2020-04-04 22:39:32','2020-04-26 10:50:59'), ('300','0','Courses','/office/course','','layui-icon-detail','0','4','2020-04-07 06:11:03','2020-04-28 21:44:14'), ('302','0','Upload','/office/upload','','layui-icon-upload','0','10','2020-04-21 17:45:37','2020-04-28 22:47:52'), ('304','0','Attendance','/office/teacherapply','','layui-icon-dashboard','0','3','2020-04-27 06:32:26','2020-04-27 07:34:59'), ('305','298','Leave','/office/leave','','layui-icon-play-square','0','5','2020-04-28 20:55:12','2020-04-28 20:55:57'), ('306','304','Leave Apply','/office/leaveApply','','layui-icon-copyright','0','6','2020-04-28 21:41:23',NULL), ('307','0','Meeting','/office/meeting','','layui-icon-deleteteam','0','7','2020-04-28 22:21:19','2020-04-28 22:21:27'), ('308','0','Meeting','/office/meetingteacher','','layui-icon-deleteteam','0','9','2020-04-29 00:44:51','2020-04-29 00:45:00'), ('309','0','Courses','/office/courseteacher','','layui-icon-detail','0','8','2020-04-29 00:45:33','2020-04-29 00:45:47');
INSERT INTO `t_role` VALUES ('1','Admin','admin','2019-06-14 16:23:11','2020-04-28 22:21:38'), ('82','Teacher','Teacher','2020-04-20 11:33:34','2020-04-29 00:46:48');
INSERT INTO `t_role_menu` VALUES ('1','1'), ('1','3'), ('1','4'), ('1','5'), ('1','287'), ('1','298'), ('1','300'), ('1','305'), ('1','307'), ('1','302'), ('82','304'), ('82','306'), ('82','308'), ('82','309'), ('82','302'), ('1','1');
INSERT INTO `t_user` VALUES ('1','admin','856aea89ad509f163284abb75579dcfc',NULL,'test@163.com','18566335599','1','2020-02-25 22:23:01','2020-05-27 16:15:26','2020-05-29 01:07:57','1','black','default.jpg','test','Tom');
INSERT INTO `t_user_role` VALUES ('1','1');
