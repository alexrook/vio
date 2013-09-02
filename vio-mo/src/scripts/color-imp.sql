/*use \copy because we don't have admin rights*/
\copy color (id,val) from 'color.csv' with delimiter '#'
