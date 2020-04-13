<?php
require('connect.php');
if (isset($_POST)) {
session_start();
$dbname = "code19";
$email = $_POST['email'];
$password = $_POST['password'];
try {
    $sql = "select password from profile where email='$email'";
    $stmt = $conn->prepare($sql);
    $stmt->execute();
    $result = $stmt->setFetchMode(PDO::FETCH_ASSOC);
    foreach($stmt->fetchAll() as $k=>$v) {
        $pass =  $v['password'];
    }
    if ($pass == $password) {
        session_start();
        $_SESSION["user"] = $email;
        header("Location:home.php");
    }
    else{
        header("Location:login.php?error=110110");
    }
    }
catch(PDOException $e)
    {
    echo $e->getMessage(); 
}
}
?>