<?php

include './myPDO.php' ;

if (!isset($_REQUEST['email']) || !isset($_REQUEST['password'])) {
    die("Error") ;
}

$email    = $_REQUEST['email']    ;
$password = $_REQUEST['password'] ;

$conn = myPDO::getInstance() ;
$query = " UPDATE users SET password = MD5(:pass) WHERE email = :email " ;
$stmt  = $conn ->prepare($query) ;
$stmt  ->bindParam(":pass", $password) ;
$stmt  ->bindParam(":email", $email) ;

$stmt ->execute() ;

$json = array() ;
$res = $stmt ->rowCount() ;
if ($res == 1) { // ok
    $json['message'] = "OK" ;
    echo json_encode($json) ;
    exit ;
}
else {
    $json['message'] = "FAILED" ;
    echo json_encode($json) ;
    exit ;
}