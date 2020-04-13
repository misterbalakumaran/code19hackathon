<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Code-19 | Login</title>
</head>
<body>
    <center>
        <h1>Please login and contribute new ideas to figth against COVID - 19</h1>
        <h1 style="text-transform: uppercase;"><u>login</u></h1>
        <form action="auth.php" method="POST">
            <?php
            error_reporting(0);
            if ($_GET['exist'] == 01110101) {
                echo '<p style="color:red;">Account already exist </p>';
            }
            elseif ($_GET['error'] == 110110) {
                echo '<p style="color:red;">Wrong Login Details</p>';
            }
            error_reporting(1);
            ?>
            <input type="email" name="email" id="" placeholder="Enter your Email-ID"> <br><br>
            <input type="password" name="password" id="" placeholder="Enter your Password"><br><br>
            <a href="signup.html">New User?</a><br><br>
            <input type="submit" value="Login">
        </form>
    </center>
</body>
</html>