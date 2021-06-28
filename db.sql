CREATE DATABASE stonith;

CREATE TABLE `servers` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `serial_number` varchar(100) DEFAULT NULL,
    `stonith_group_id` varchar(100) DEFAULT NULL,
    `login_user` varchar(100) DEFAULT NULL,
    `login_password` varchar(100) DEFAULT NULL,
    `api_token` varchar(100) DEFAULT NULL,
`management_url` varchar(100) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `serial_number` (`serial_number`)
);
