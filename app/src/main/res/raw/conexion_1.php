<?php
$hostname='khushiconfecciones.com';
$database='u506324710_Khushi_android';
$username='u506324710_David';
$password='Tamedotothepa1';


$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "El sitio web está experimentado problemas";
}
?>