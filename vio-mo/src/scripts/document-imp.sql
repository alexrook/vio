/* import document see document-exp.sql */

\copy document (id,doctypeId,formatId,colorId,code,name) from 'document.csv' with delimiter '#'
