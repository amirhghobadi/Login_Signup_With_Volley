<?php

include './myPDO.php' ;

if (!isset($_REQUEST['email']) || !isset($_REQUEST['code'])) {
    die("Error") ;
}

$email = $_REQUEST['email'] ;
$code  = $_REQUEST['code']  ;

$conn  = myPDO::getInstance() ;
$query = " SELECT email FROM users WHERE email = :email " ;
$stmt  = $conn ->prepare($query) ;

$stmt ->bindParam(":email", $email) ;

$stmt ->execute() ;

$res = $stmt ->rowCount() ;

$json = array() ;

if ($res == 1) { // email exists
     $message = "Your Code is $code" ;
    mail($email, "Validation Code", $message) ;

    $json['message'] = "email_ok" ;
    echo json_encode($json) ;
    exit ;
}
 else { //email not exists
    $json['message'] = "email_notExists" ;
    echo json_encode($json) ;
    exit ;
}