/*use \copy because we don't have admin rights*/
\copy theme (id,val) from 'theme.csv' with delimiter '#'
