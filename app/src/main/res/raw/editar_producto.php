<?php
include 'conexion_1.php';
$codigo=$_POST['codigo'];
$producto=$_POST['producto'];
$precio=$_POST['precio'];
$fabricante=$_POST['fabricante'];

$consulta="update producto set producto = '".$producto."',precio = ' ".$precio."', fabricante = '".$fabricante."' where codigo = '".$codigo."' ";

mysqli_query($conexion, $consulta)or die (mysqli_error());
mysql_close($conexion);

?>