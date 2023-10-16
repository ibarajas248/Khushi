<?php
include 'conexion_1.php';
$codigo=$_POST['codigo'];

$consulta="delete from producto where codigo ='".$codigo."' ";

mysqli_query($conexion, $consulta)or die (mysqli_error());
mysql_close($conexion);

?>