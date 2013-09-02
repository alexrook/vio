/*use \copy because we don't have admin rights*/
\copy format (id,val) from 'format.csv' with delimiter '#'
