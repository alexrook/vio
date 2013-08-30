set termout off
set hea off
set pagesize 0
SET feed off
set linesize 500
/*document type childs*/
spool doctype-childs.csv

select 
    a.shifrvid||'#'||a.shifrvid||a.podvid||'#'||trim(a.podvdoc)
from clpodvid a;

spool off


