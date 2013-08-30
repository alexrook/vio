set termout off
set hea off
set pagesize 0
SET feed off
set linesize 250

/*document,(description not implemented now)*/
spool document.csv
rem SHIFR,SHIFRVID, PODVID, FORMAT,COLOR, SHIFRDOC, NAMEDOC// DESCRIPT
select 
 a.SHIFR||'#'||a.SHIFRVID||a.PODVID||'#'||a.FORMAT||'#'||a.COLOR||'#'||a.SHIFRDOC||'#'||a.NAMEDOC
from opisan a;

spool off

