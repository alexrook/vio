set termout off
set hea off
set pagesize 0
SET feed off

/* theme<--->doc many-to-many binding*/
spool themes-m-m.csv

select a.codf||','||a.shifr
from favorite a;

spool off

quit
