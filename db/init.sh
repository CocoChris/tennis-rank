#!/bin/bash
cd /code && mysql -uroot -p$MYSQL_ROOT_PASSWORD <<EOF
USE ita;
source current_phase.sql;
source event_info.sql;
source event_level.sql;
source grade_record.sql;
source player_info.sql;
source pts_record.sql;
source score_board.sql;

EOF
