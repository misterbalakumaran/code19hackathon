<?php
require('nav.html');
session_start();
    if (!isset($_SESSION['user'])) {
        header("Location:index.html");
    }
    if(array_key_exists('logout', $_POST)) { 
        session_destroy();
        header("Location:index.html");
    } 
    if(array_key_exists('sort', $_POST)) { 
        $sql = "select profile.first_name,profile.last_name,profile.domain,profile.designation,post.heading,post.description,post.date,post.time,post.id,post.upvote,post.downvote,post.comment from post,profile where profile.email= post.email ORDER BY upvote DESC";
    } 
    elseif(array_key_exists('recent', $_POST)){
        $sql = "select profile.first_name,profile.last_name,profile.domain,profile.designation,post.heading,post.description,post.date,post.time,post.id,post.upvote,post.downvote,post.comment from post,profile where profile.email= post.email ORDER BY id DESC";
    }
    else{
        $sql = "select profile.first_name,profile.last_name,profile.domain,profile.designation,post.heading,post.description,post.date,post.time,post.id,post.upvote,post.downvote,post.comment from post,profile where profile.email= post.email";
    }
?>
<style>
    .black_text{
        color:black!important;
    }
    @media only screen and (min-width: 768px) {/*desktop*/
        .person{
            width: 3%!important;
            top:18px!important;
        }
        .sort_recent{
            position:absolute;
            right:20%;
        }
}
</style>
<br><br>
<a class="waves-effect waves-light btn modal-trigger" href="#modal1">Post your opinion to battle against COVID-19</a>
<form method="POST">
    <input type="submit" name="logout" value="logout" class="waves-effect waves-light btn" style="position:absolute;right:5%;"><br><br>
</form>
<form method="POST">
    <input type="submit" name="sort" value="Sort by Upvotes" class="waves-effect waves-light btn" style="position:absolute;right:5%;">
</form>
<form method="POST">
    <input type="submit" name="recent" value="Sort by Recent post" class="waves-effect waves-light btn sort_recent">
</form>
<br><br>
<?php
    require('connect.php');
    try {
        
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $result = $stmt->setFetchMode(PDO::FETCH_ASSOC);
        foreach($stmt->fetchAll() as $k=>$v) {
        $_SESSION['user_name'] = $v['first_name']. " ". $v['last_name'];
        echo '<div class="row">
        <div class="col m12">
          <div class="card">
            <div class="card-content white-text" id="'.$v['id'].'">
                <p class="black_text">
                    <img src="img/person.png" alt="" class="responsive-img person" style="width:7%;position:relative;top:9px;right:4px;">
                    '.$v['first_name']. " ". $v['last_name']. ", Works as ". $v['designation'].", in ". $v['domain'].' posted on '. $v['date'].', '.$v['time'].'<br><br>
                </p>
              <span class="card-title black_text">'.$v['heading'].'</span>
              <p class="black_text">'.$v['description'].'</p>
            </div>
            <div class="card-action">
            <div class="row">
                <div class = "col m1">
                    <form action = "vote.php?x=1&id='.$v['id'].'" method="POST">
                    <input class="waves-effect waves-light btn" type="submit" value="UpVote '.$v['upvote'].'">
                </form>
                </div>
                <div class = "col m1">
                    <form action = "vote.php?x=0&id='.$v['id'].'" method="POST">
                    <input class="waves-effect waves-light btn" type="submit" value="DownVote '.$v['downvote'].'">
                </form>
                </div> <br><br><br>
                <div class = "col m6">
                    <form action="comment.php?id='.$v['id'].'" method="POST">
                        <input type = "text" name = "comment" placeholder="Add your comment here">
                        <input class="waves-effect waves-light btn" type = "submit" value="post">
                    </form>
                </form>
                </div>
            </div><b>COMMENTS</b><br>';
            $arr = explode("///",$v['comment']);
            foreach($arr as $a){
                echo '<img src="img/person.png" alt="" class="responsive-img person" style="width:7%;position:relative;top:8px;right:5px;"> <span>'.$a.'</span><br>';
            }
              
            echo '</div>
          </div>
        </div>
      </div>';                      
            
        }
        }
    catch(PDOException $e)
        {
        echo $e->getMessage(); 
    }
?>
<div id="modal1" class="modal">
    <div class="modal-content">
      <h4>Post your opinion</h4><br><br>
      <?php require('create_post.php')?>
    </div>
  </div>
<script>
      $(document).ready(function(){
    $('.modal').modal();
  });
</script>