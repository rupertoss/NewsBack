DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `fav_category` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`,`username`,`password`,`fav_category`) VALUES (1,'Number1', 'password', 'health');
INSERT INTO `users` (`id`,`username`,`password`,`fav_category`) VALUES (2,'SecondUser', 'abcdef', 'business');
INSERT INTO `users` (`id`,`username`,`password`) VALUES (3,'NoFavouriteCategory', '1234456789');
INSERT INTO `users` (`id`,`username`,`password`,`fav_category`) VALUES (4,'TheFourth', 'asdffghjl', 'sports');