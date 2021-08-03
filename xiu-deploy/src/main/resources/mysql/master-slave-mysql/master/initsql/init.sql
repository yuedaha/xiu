CREATE USER 'root'@'db_slaver1' IDENTIFIED BY '123456';
grant replication slave, replication client on *.* to 'root'@'db_slaver1';
CREATE USER 'root'@'db_slaver2' IDENTIFIED BY '123456';
grant replication slave, replication client on *.* to 'root'@'db_slaver2';
flush privileges;