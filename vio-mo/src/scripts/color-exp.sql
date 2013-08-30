set termout off
set hea off
set pagesize 0
SET feed off

spool color.csv

select a.color||'#'||a.colordoc
from colordoc a;

spool off

quit
