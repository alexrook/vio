set termout off
set hea off
set pagesize 0
SET feed off

spool themes.csv

select a.codf||','||a.name
from clfavorite a;

spool off

quit
