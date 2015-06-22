DROP TABLE IF EXISTS `VirtualMachine`;
create table VirtualMachine (
   id INT NOT NULL auto_increment,
   version int default 0,
   lastUpdated  Date default NULL,
   currentUser     INT  default 0,
   machinename     VARCHAR(255)  default NULL,
   pass     VARCHAR(20)   default NULL,
   airId     VARCHAR(20)  default NULL,
   currentUserName     VARCHAR(20)  default NULL,
   machinetype     INT  default 0,
   vmcondition     INT  default 0,
   status     INT  default 0,
   PRIMARY KEY (id)
);
