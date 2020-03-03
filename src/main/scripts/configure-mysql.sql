/*Use to run mysql db docker image, optional if you're not using a local mysqldb
  3307(hostport) wordt gekoppeld aan 3306(dockerport)*/
/*$ docker run --name mybudget_mysqldb -p 3307:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql*/

/*connect to mysql and run as root user*/
/*Create Databases*/
CREATE DATABASE mybudget_dev;
CREATE DATABASE mybudget_prod;

#Create database service accounts (identified by -> staat voor het paswoord)
/*any host --> % , want docker container denkt dat we van een ander ip-adres komen*/
CREATE USER 'mybudget_dev_user'@'localhost' IDENTIFIED BY 'Dev123456!?';
CREATE USER 'mybudget_prod_user'@'localhost' IDENTIFIED BY 'Prod123456!?';
CREATE USER 'mybudget_dev_user'@'%' IDENTIFIED BY 'Dev123456!?';
CREATE USER 'mybudget_prod_user'@'%' IDENTIFIED BY 'Prod123456!?';

/*Database grants*/
/*minimal acces -> DML*/
/*any host --> % , want docker container denkt dat we van een ander ip-adres komen*/
GRANT SELECT ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT INSERT ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT DELETE ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT UPDATE ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT SELECT ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT INSERT ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT DELETE ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT UPDATE ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT SELECT ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT INSERT ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT DELETE ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT UPDATE ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT SELECT ON mybudget_prod.* to 'mybudget_prod_user'@'%';
GRANT INSERT ON mybudget_prod.* to 'mybudget_prod_user'@'%';
GRANT DELETE ON mybudget_prod.* to 'mybudget_prod_user'@'%';
GRANT UPDATE ON mybudget_prod.* to 'mybudget_prod_user'@'%';