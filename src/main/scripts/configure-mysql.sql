## Use to run mysql db docker image, optional if you're not using a local mysqldb
# $ docker run --name budgetfile_mysqldb -p 3307:3307 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE mybudget_dev;
CREATE DATABASE mybudget_prod;

#Create database service accounts (identified by -> staat voor het paswoord)
CREATE USER 'mybudget_dev_user'@'localhost' IDENTIFIED BY 'annelyse';
CREATE USER 'mybudget_prod_user'@'localhost' IDENTIFIED BY 'annelyse';
CREATE USER 'mybudget_dev_user'@'%' IDENTIFIED BY 'annelyse';
CREATE USER 'mybudget_prod_user'@'%' IDENTIFIED BY 'annelyse';

#Database grants
GRANT SELECT ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT INSERT ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT DELETE ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';
GRANT UPDATE ON mybudget_dev.* to 'mybudget_dev_user'@'localhost';' ||
 '
GRANT SELECT ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT INSERT ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT DELETE ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';
GRANT UPDATE ON mybudget_prod.* to 'mybudget_prod_user'@'localhost';' ||
 '
GRANT SELECT ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT INSERT ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT DELETE ON mybudget_dev.* to 'mybudget_dev_user'@'%';
GRANT UPDATE ON mybudget_dev.* to 'mybudget_dev_user'@'%';' ||
 '
GRANT SELECT ON mybudget_prod.* to 'mybudget_prod_user'@'%';
GRANT INSERT ON mybudget_prod.* to 'mybudget_prod_user'@'%';
GRANT DELETE ON mybudget_prod.* to 'mybudget_prod_user'@'%';
GRANT UPDATE ON mybudget_prod.* to 'mybudget_prod_user'@'%';