/*
 run this to clean all tables and reimport data
 i.e:
    'psql -U user -h host db -f clean_and_import_all.sql'
*/

\echo removing data
truncate document,documenttype,format,color,docthemes,theme cascade;

\echo importing data
\i theme-imp.sql
\i color-imp.sql
\i format-imp.sql
\i doctype-imp.sql
\i doctype-childs-imp.sql
\i document-imp.sql

\echo cleanup data
\i after-imp.sql

\echo clean and import done




