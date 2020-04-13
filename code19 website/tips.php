<?php
require('nav.html');
?>
<br><br>
<div class="row">
    <div class="col m1"></div>
    <div class="col m4">  <img class="materialboxed" width="650" src="img/1.png"></div>
    <div class="col m1"></div>
    <div class="col m4">  <img class="materialboxed" width="650" src="img/2.png"></div>
</div>
<br><br>
<div class="row">
    <div class="col m1"></div>
    <div class="col m4">  <img class="materialboxed" width="650" src="img/3.png"></div>
    <div class="col m1"></div>
    <div class="col m4">  <img class="materialboxed" width="650" src="img/5.png"></div>
</div>
<br><br>
<center><img class="materialboxed" width="650" src="img/4.png"></center>
<script>
 $(document).ready(function(){
    $('.materialboxed').materialbox();
  });
</script>