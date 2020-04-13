<?php
require('connect.php');
session_start();
try {
    $id =$_GET['id']; 
    $comment = $_SESSION['user_name'];
    $comment = $comment.' commented - ';
    $comment = $comment.$_POST['comment'];
    $comment = $comment.'///';
    $sql = "UPDATE post SET comment=CONCAT(comment,'$comment') WHERE id=$id";
    $stmt = $conn->prepare($sql);
    $stmt->execute();
    $url = 'Location:home.php#'.$id;
    header($url);
    }
catch(PDOException $e)
    {
    echo $sql . "<br>" . $e->getMessage();
    }
?>