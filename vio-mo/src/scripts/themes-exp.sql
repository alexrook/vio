set termout off
set hea off
set pagesize 0
set feed off

spool theme.csv

select 
    a.codf||'#'||a.name
from clfavorite a;

spool off

quit
