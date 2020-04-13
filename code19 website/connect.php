<?php
$servername = "localhost";
$username = "root";
$pass = "";
$dbname = "code19";
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $pass);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    // echo "DBCONNECT-> Connected successfully <br>";
    }
catch(PDOException $e)
    {
        $conn = null;
        // echo "DBCONNECT-> Connection failed: " . $e->getMessage() .'<br>';
    }
?>