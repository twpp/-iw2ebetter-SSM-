/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.5.37 : Database - iw2ebetter
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `iw2ebetter` DEFAULT CHARACTER SET utf8mb4;

USE `iw2ebetter`;

/*Table structure for table `appraise_like` */

DROP TABLE IF EXISTS `appraise_like`;

CREATE TABLE `appraise_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NOT NULL COMMENT '点赞的点评id',
  `uid` int(11) NOT NULL COMMENT '点赞的用户id',
  `status` tinyint(1) DEFAULT '1' COMMENT '0表示未点赞，1表示点赞',
  PRIMARY KEY (`id`),
  KEY `idx_of_appraise_aid` (`aid`),
  KEY `idx_of_appraise_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `appraises` */

DROP TABLE IF EXISTS `appraises`;

CREATE TABLE `appraises` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8,
  `create_time` datetime NOT NULL,
  `available` tinyint(1) DEFAULT '0',
  `title` varchar(20) CHARACTER SET utf8 NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `likes` int(11) DEFAULT '0',
  PRIMARY KEY (`aid`),
  KEY `appraises_ibfk_1` (`uid`),
  CONSTRAINT `appraises_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `imgs` */

DROP TABLE IF EXISTS `imgs`;

CREATE TABLE `imgs` (
  `iid` int(11) NOT NULL AUTO_INCREMENT,
  `src` varchar(256) DEFAULT '/images/photos/user.png',
  `aid` int(11) DEFAULT '0',
  PRIMARY KEY (`iid`),
  KEY `imgs_ibfk_1` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `responses` */

DROP TABLE IF EXISTS `responses`;

CREATE TABLE `responses` (
  `reid` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(1600) DEFAULT NULL,
  `response_time` datetime DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`reid`),
  KEY `responses_ibfk_1` (`aid`),
  KEY `responses_ibfk_3` (`uid`),
  CONSTRAINT `responses_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `appraises` (`aid`) ON DELETE CASCADE,
  CONSTRAINT `responses_ibfk_3` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(256) NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user2role` */

DROP TABLE IF EXISTS `user2role`;

CREATE TABLE `user2role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '用户密码',
  `telephone` varchar(11) NOT NULL COMMENT '用户私人电话',
  `name` varchar(50) NOT NULL,
  `enable` tinyint(1) DEFAULT '1',
  `address` varchar(256) DEFAULT NULL,
  `src` varchar(256) DEFAULT '/images/photos/user.png' COMMENT '用户头像链接',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert  into `roles`(`rid`,`role`) values (1,'host'),(2,'admin'),(3,'user');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
