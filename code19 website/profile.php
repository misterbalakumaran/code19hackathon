<?php
require('connect.php');
if (isset($_POST)) {
session_start();
$dbname = "code19";
$first_name = $_POST['first_name'];
$last_name = $_POST['last_name'];
$email = $_POST['email'];
$mobile = $_POST['mobile'];
$dob = $_POST['dob'];
$gender = $_POST['gender'];
$domain = $_POST['domain'];
$organization = $_POST['organization'];
$designation = $_POST['designation'];
$password = $_POST['password'];

// echo $_SESSION["topic_name"];
try {
    $sql = "INSERT INTO profile(first_name,last_name,email,mobile,dob,gender,domain,organization,designation,password) VALUES('$first_name','$last_name','$email','$mobile','$dob','$gender','$domain','$organization','$designation','$password')";
    $stmt = $conn->prepare($sql);
    $stmt->execute();
    session_start();
    $_SESSION["user"] = $email;
    header("Location:home.php");
    }
catch(PDOException $e)
    {
    // echo 'inside catch';
    // echo $sql . "<br>" . 
    $mssg = $e->getMessage();
    if(strpos($mssg, 'Duplicate entry') !== false){
        // header("Location:login.php?exist=01110101");
        echo $mssg;
    } else{
        echo "Something went wrong. Please try again or contact your developer";
    }  
    }
}
?>