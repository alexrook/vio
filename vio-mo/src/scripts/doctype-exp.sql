set termout off
set hea off
set pagesize 0
SET feed off

/*document type roots*/
spool doctype.csv

select a.shifrvid||','||a.viddoc
from clviddoc a;

spool off

quit
