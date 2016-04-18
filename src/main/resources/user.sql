SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(24) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gmt_modified` date DEFAULT NULL,
  `gmt_created` date DEFAULT NULL,
  `creator` varchar(24) DEFAULT NULL,
  `modifier` varchar(24) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
