#!/bin/bash
mysql -uroot -p$MYSQL_ROOT_PASSWORD <<EOF
USE ita;
source *.sql;

EOF
