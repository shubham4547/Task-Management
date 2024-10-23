CREATE TABLE IF NOT EXISTS `Employee` (
  `employee_id` int AUTO_INCREMENT  PRIMARY KEY,
  `username` varchar(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `created_at` date NOT NULL
);
