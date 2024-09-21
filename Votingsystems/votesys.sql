/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - votesys
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`votesys` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `votesys`;

/*Table structure for table `applications` */

DROP TABLE IF EXISTS `applications`;

CREATE TABLE `applications` (
  `appl_id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) DEFAULT NULL,
  `candid_id` int(11) DEFAULT NULL,
  `appl_status` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`appl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `applications` */

insert  into `applications`(`appl_id`,`cat_id`,`candid_id`,`appl_status`,`description`) values (1,1,2,'accept','asdfghjzxcvbnmwerty'),(2,1,3,'accept','qwerty');

/*Table structure for table `candidates` */

DROP TABLE IF EXISTS `candidates`;

CREATE TABLE `candidates` (
  `candid_id` int(11) NOT NULL AUTO_INCREMENT,
  `stud_id` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `cand_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`candid_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `candidates` */

insert  into `candidates`(`candid_id`,`stud_id`,`cat_id`,`cand_status`) values (2,2,1,'candidate'),(3,3,1,'candidate');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `election_id` int(11) DEFAULT NULL,
  `cat_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `category` */

insert  into `category`(`cat_id`,`election_id`,`cat_name`) values (1,1,'chairman'),(3,1,'cc');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `com_id` int(11) NOT NULL AUTO_INCREMENT,
  `candid_id` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `solution` varchar(100) DEFAULT NULL,
  `com_date` date DEFAULT NULL,
  PRIMARY KEY (`com_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`com_id`,`candid_id`,`description`,`solution`,`com_date`) values (1,2,'hai','hello','2020-02-05'),(2,2,'hellooooo','hai','2020-02-05'),(3,3,'hai','pending','2020-02-05');

/*Table structure for table `election` */

DROP TABLE IF EXISTS `election`;

CREATE TABLE `election` (
  `election_id` int(11) NOT NULL AUTO_INCREMENT,
  `titile` varchar(50) DEFAULT NULL,
  `ele_date` date DEFAULT NULL,
  `vot_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`election_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `election` */

insert  into `election`(`election_id`,`titile`,`ele_date`,`vot_status`) values (1,'cvb','2020-02-12','finish');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`log_id`,`username`,`password`,`usertype`) values (1,'admin','admin','admin'),(2,'90917','123karthi','voter'),(3,'987650','kashi123','candidate'),(4,'12345','anju','candidate'),(5,'23456','123','voter'),(6,'345678','123','voter'),(7,'345678','123','voter'),(8,'23456','123','voter'),(9,'74125','123','voter'),(10,'345678','123','voter'),(11,'1','123','voter'),(12,'23456','123','voter'),(13,'74125','123','candidate'),(14,'1','123','voter'),(15,'345678','123','voter'),(16,'345678','123','voter'),(17,'345678','123','voter'),(18,'4567','123','voter'),(19,'4567','123','voter'),(20,'asdf@kk.wdfk','rRQ=\'','candidate'),(21,'dgh@df.dfg','15940','voter');

/*Table structure for table `results` */

DROP TABLE IF EXISTS `results`;

CREATE TABLE `results` (
  `result_id` int(11) NOT NULL AUTO_INCREMENT,
  `candid_id` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `no_votes` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `results` */

insert  into `results`(`result_id`,`candid_id`,`cat_id`,`no_votes`) values (1,2,1,'810');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `stud_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` int(11) DEFAULT NULL,
  `reg_no` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  `batch` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `sec_key` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`stud_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `student` */

insert  into `student`(`stud_id`,`log_id`,`reg_no`,`fname`,`lname`,`photo`,`course`,`batch`,`phone`,`email`,`sec_key`) values (1,2,'90917','Karthik','Surya','/static/uploads/df21703d-be36-4e84-b871-aab88712191c11.jpg','BCA','2016-2018','6547893210','karthi@gmail.com','5656'),(2,3,'987650','Kashi','Subramahnyam','/static/uploads/f56798ab-3aa3-4c92-a9ef-518d588df12d37.jpg','MCom','2017-2019','8956231470','kashi@gmail.com','1111'),(17,20,'iuyiug','kigh','uhg','/static/uploads/6041e2eb-cd03-44ac-ac01-a86f3dc8e89eapple.jpg','kjsbdci','dufg','9875987667','asdf@kk.wdfk','rRQ=\''),(18,21,'3456789876','dfjvhbv','dfgj','/static/uploads/b5d2bdef-6d29-4ddf-bede-038681d82462cal.jpg','gh','u','9876789876','dgh@df.dfg','15940');

/*Table structure for table `voters` */

DROP TABLE IF EXISTS `voters`;

CREATE TABLE `voters` (
  `vot_id` int(11) NOT NULL AUTO_INCREMENT,
  `stud_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `voters` */

insert  into `voters`(`vot_id`,`stud_id`) values (1,1);

/*Table structure for table `voting` */

DROP TABLE IF EXISTS `voting`;

CREATE TABLE `voting` (
  `voting_id` int(11) NOT NULL AUTO_INCREMENT,
  `stud_id` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `candid_id` int(11) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`voting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `voting` */

insert  into `voting`(`voting_id`,`stud_id`,`cat_id`,`candid_id`,`datetime`) values (1,1,1,1,'2005-02-20 09:10:12');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
