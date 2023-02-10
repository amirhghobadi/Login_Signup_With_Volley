<?php

include './myPDO.php' ;

if (!isset($_REQUEST['username']) || !isset($_REQUEST['email']) || !isset($_REQUEST['password'])) {
    die("Error") ;
}
$username    = $_REQUEST['username']    ;
$email    = $_REQUEST['email']    ;
$password = $_REQUEST['password'] ;

$conn = myPDO::getInstance() ;
$query = " INSERT INTO users (username,email, password) VALUES(:username,:email, MD5(:passowrd)) " ;
$stmt  = $conn ->prepare($query) ;
$stmt  ->bindParam(":username", $username) ;
$stmt  ->bindParam(":email", $email) ;
$stmt  ->bindParam(":passowrd", $password) ;

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