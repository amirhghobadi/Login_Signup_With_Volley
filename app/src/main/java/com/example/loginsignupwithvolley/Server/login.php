<?php

include './myPDO.php' ;

//$json = array("status" => "ok", "login" => "failed") ;

//$json['status'] = "ok" ;
//$json['login'] = "failed" ;

//echo json_encode($json) ;

if (!isset($_REQUEST["email"]) || !isset($_REQUEST['password'])) {
    die("Error") ;
}

$email = $_REQUEST["email"] ;
$password = $_REQUEST["password"] ;

$conn = myPDO::getInstance() ;
$query = " SELECT email FROM users WHERE email = :email AND password = MD5(:pass) " ;
$stmt  = $conn ->prepare($query) ;
$stmt  ->bindParam(":email", $email) ;
$stmt  ->bindParam(":pass", $password) ;

$stmt  ->execute() ;

$response = $stmt ->rowCount() ;

$json = array() ;

if ($response == 1) {
    $json['message'] = "login_ok" ;
    echo json_encode($json) ;
    exit ;
}
else {
    $json['message'] = "failed_login" ;
    echo json_encode($json) ;
    exit ;
}