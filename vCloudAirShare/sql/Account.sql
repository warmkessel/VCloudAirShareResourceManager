DROP TABLE IF EXISTS `Account`;
create table Account (
   id INT NOT NULL auto_increment,
   version int default 0,
   lastUpdated  Date default NULL,
   ipaddress    VARCHAR(255)  default NULL,
   lastloggedin     VARCHAR(255)  default NULL,
   userId     INT   default 0,
   name     VARCHAR(40)  default NULL,
   screen_name     VARCHAR(40)  default NULL,
   location     VARCHAR(128)  default NULL,
   description     VARCHAR(2000)  default NULL,
   url     VARCHAR(256)  default NULL,
   usercondition     INT  default 0,
   userlanguage     VARCHAR(20)  default NULL,
   status     INT  default 0,
   useragent     VARCHAR(20)   default NULL,
   PRIMARY KEY (id)
);