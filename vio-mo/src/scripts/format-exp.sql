set termout off
set hea off
set pagesize 0
SET feed off

spool format.csv

select a.format||','||a.formdoc
from formatdoc a;

spool off

quit
