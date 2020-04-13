<?php
// session_start();
    if (!isset($_SESSION['user'])) {
        header("Location:index.html");
    }
    if(array_key_exists('logout', $_POST)) { 
        session_destroy();
        header("Location:index.html");
    } 
    if(array_key_exists('create_post', $_POST)) { 
        require('connect.php');
        $email = $_SESSION['user'];
        $heading = $_POST['heading'];
        $heading = str_replace("'","\'",$heading);
        $description = $_POST['description'];
        $description = str_replace("'","\'",$description);
        $date = date('d-m-Y');
        date_default_timezone_set("Asia/Kolkata");
        $time = date("h:i:sa");
        try {
            $sql = "INSERT INTO post(email,heading,description,date,time,comment,upvote,downvote) VALUES('$email','$heading','$description','$date','$time','',0,0)";
            $stmt = $conn->prepare($sql);
            $stmt->execute();
            header("Location:home.php");
            }
        catch(PDOException $e)
            {
            $mssg = $e->getMessage();
            echo $mssg; 
            }
    } 
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Code 19 | Create Post</title>
</head>
<body>  
    <!-- <p>Post your opinion to battle againt COVID-19</p>
    <form method="POST">
        <input type="submit" name="logout" value="logout"><br><br>
    </form> -->
    <form method="POST">
        <!-- <input type="text"  placeholder=""><br><br> -->
        <div class="input-field col s6">
          <input id="heading" type="text" class="validate" name="heading">
          <label for="heading">Enter the heading for post</label>
        </div>
        <br><br>
        <div class="row">
        <div class="input-field col s12">
          <textarea id="textarea1" class="materialize-textarea" name="description"></textarea>
          <label for="textarea1">Enter the detailed description</label>
        </div>
      </div>
      <br><br>
        <input type="submit" value="Post" name = "create_post" class="waves-effect waves-light btn">
        <br><br>
    </form>
</body>
</html>