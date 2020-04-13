<?php
require('connect.php');

try {
    $id = $_GET['id'];
    if ($_GET['x'] == 1) {
        $vote = 'upvote';
        $sql = "UPDATE post SET $vote=$vote + 1 WHERE id=$id";
    }
    else{
        $vote = 'downvote';
        $sql = "UPDATE post SET $vote=$vote + 1 WHERE id=$id";
    }
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