set termout off
set hea off
set pagesize 0
SET feed off

/*document type childs*/
spool doctype-childs.csv

select a.shifrvid||','||a.podvid||','||a.podvdoc
from clpodvid a;

spool off

quit
