<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Code-19 | Signup</title>
</head>
<body>
    <center>
        <h1>Please Signup and start contributing new ideas to figth against COVID - 19</h1>
        <h1 style="text-transform: uppercase;"><u>signup</u></h1>
        <form action="profile.php" method="POST">
            <input type="text" name="first_name" id="" placeholder="First Name"> <br><br>
            <input type="text" name="last_name" id="" placeholder="Last Name"> <br><br>
            <input type="email" name="email" id="" placeholder="Email-ID"> <br><br>
            <input type="number" name="mobile" id="" placeholder="Mobile Number"> <br><br>
            <label for="dob">Date of Birth</label>
            <input type="date" name="dob" id="dob" placeholder="Date of Birth"><br><br>
            <label for="">Select Gender:</label><br>
            <input type="radio" id="male" name="gender" value="male">
            <label for="male">Male</label><br>
            <input type="radio" id="female" name="gender" value="female">
            <label for="female">Female</label><br>
            <input type="radio" id="other" name="gender" value="other">
            <label for="other">Other</label><br><br>
            <label for="domain">Choose a working domain:</label><br>
            <select id="domain" name='domain'>
            <option value="Govt Offcials">Govt Offcials</option>
            <option value="Doctors & Medicine Field">Doctors & Medicine Field</option>
            <option value="Professors & Teachers">Professors & Teachers</option>
            <option value="Entrepreneur & Business">Entrepreneur & Business</option>
            <option value="Engineers">Engineers</option>
            <option value="Management">Management</option>
            <option value="Others">Others</option>
            </select><br><br>
            <input type="text" name="organization" id="" placeholder="Organization"> <br><br>
            <input type="text" name="designation" id="" placeholder="Designation"> <br><br>
            <input type="password" name="password" id="" placeholder="Password"><br><br>
            <input type="password" name="retype_password" id="" placeholder="Re-Password"><br><br>
            <input type="submit" value="SignUp"><br><br>
            <a href="login.php">Existing User?</a><br><br>
        </form>
    </center>
</body>
</html>