<?php
$hostname='localhost';
$database='developeru_bd';
$username='root';
$password='';
$port = 3310;

$conexion=new mysqli($hostname,$username,$password,$database,$port);
if($conexion->connect_errno){
    echo "El sitio web está experimentado problemas";
}
?>