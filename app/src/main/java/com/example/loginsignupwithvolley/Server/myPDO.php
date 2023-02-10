<?php

include './config.php' ;

class myPDO extends PDO {

    private static $instance ;

    public function __construct() {
        try {
            parent::__construct("mysql:host=".config::$host.";dbname=".config::$dbname, config::$username, config::$password) ;
            self::$instance = $this ;
            self::$instance ->exec("set names utf8") ;
            self::$instance ->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION) ;
        } catch (Exception $ex) {
            echo "Error PDO" ;
        }
    }

    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new PDO("mysql:host=".config::$host.";dbname=".config::$dbname, config::$username, config::$password) ;
            self::$instance ->exec("set names utf8") ;
            self::$instance ->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION) ;
        }

        return self::$instance ;
    }
}