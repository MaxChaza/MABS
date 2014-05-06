<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr" xml:lang="fr">
    <head>
        <title>Fisrt php</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="./styleCss.css" />
        <script language="javascript" type="text/javascript" src="./js/checkParam.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <!--<script type="text/javascript" src="./js/jquery.js"></script>-->
    </head>
    <body>
        <div id="ligneNoir" >
	</div>
        
	<div id="ligne" >
	</div>

        <div id="header">
            <?php 
                include("./include/header.php");
            ?> 
        </div>
        <div id="content">
        <?php 
        // Connects to your Database
        if(isset($_POST['nextPage']))
        {
            $nextPage = $_POST['nextPage'];
            session_start();
            if($nextPage!='deconnecter' && isset($_SESSION["username"])){
                // User connecté 
                echo 'Bonjour '.$_SESSION["username"].' !</br>';
            ?>
            <form  action="index.php" method="post">
                <input type="submit" name="nextPage" value="deconnecter" class="myButton"/>
            </form>
            <?php 
            }
            else
            {
                if($nextPage=='deconnecter' && isset($_SESSION["username"]))
                {
                     // STOP les COOKIES
                    //setcookie("yourname", '', time() - 3600);
                    if(isset($_SESSION["username"])){
                        unset($_SESSION["username"]);
                    }
                    session_destroy();
                    echo "Vous avez été déconecté.";
                }
                else
                {
                    if($nextPage=='register')
                    {
                        ?>
                        <!--<form   name="log" method="post">-->
                        <form  onsubmit="return checkParamRegister()" name="log" method="post">
                            <table border="0">
                                <tr>
                                    <td id="colorUsername">Username:</td>
                                    <td>
                                        <input type="text" name="username" id="username" maxlength="60"/>
                                        <script  type="text/javascript" >
                                            var keyUsername = document.getElementById('username');
                                            keyUsername.focus();
                                            keyUsername.select();
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="colorPassword">Password:</td>
                                    <td><input type="password" name="password" id="password" maxlength="10"/></td>
                                    <td><div id="erreurPassword"></div></td>
                                </tr>
                                <tr>
                                    <td id="colorPassword2">Confirm Password:</td>
                                    <td><input type="password" name="password2" id="password2" maxlength="10"/></td>
                                    <td><input type="hidden" name="nextPage" id="nextPage" value="reponseRegister"/></td>
                                </tr>
                                <tr>
                                    <td id="colorQ1">Q1:Quel est le nom de jeune fille de votre mère?</td>
                                </tr>
                                <tr>
                                    <td id="colorR1">Reponse:</td>
                                    <td>
                                        <input type="password" name="r1" id="r1" maxlength="60"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="colorQ2">Q2:Quel est votre animal préférer?</td>
                                </tr>
                                <tr>
                                    <td id="colorR2">Reponse:</td>
                                    <td>
                                        <input type="password" name="r2" id="r2" maxlength="60"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="colorQ3">Q3:Quel est votre ville natale?</td>
                                </tr>
                                <tr>
                                    <td id="colorR3">Reponse:</td>
                                    <td>
                                        <input type="password" name="r3" id="r3" maxlength="60"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th colspan=2>
                                        <input type="submit" name="submitRegister" class="myButton" value="register" />
                                    </th>
                                </tr>
                            </table>
                        </form>
                    <?php
                    }
                    else
                    {
                        if($nextPage=='reponseRegister')
                        {

                            // Connects to your Database
                            require ("./src/config.php");
                            /*
                             * To change this template, choose Tools | Templates
                             * and open the template in the editor.
                             */
                            // Connects to your Database
                            $connection=mysql_connect($host, htmlentities($login), htmlentities($passwd)) or die("impossible de se connecter");
                            mysql_select_db($dbname) or die("impossible d'aller sur la bd");
                            // checks if the username is in use

                            $usercheck = addslashes($_POST['username']);
                            $query="SELECT username FROM users WHERE username = '$usercheck'";
                            $check = mysql_query($query) or die(mysql_error());
                            $numberOfRow = mysql_num_rows($check);

                            //if the name exists it gives an error
                            if ($numberOfRow == 0) {//on doit faire mieux
                                // now we insert it into the database
                                $insert = "INSERT INTO users (username, password, reponse1, reponse2, reponse3) VALUES ('".addslashes($_POST['username'])."','".md5($_POST['password'])."','".md5($_POST['r1'])."','".md5($_POST['r2'])."','".md5($_POST['r3'])."')";
                                $add_member = mysql_query($insert);
                                echo "Vous etes bien inscrit.";
                            }
                            else
                            {
                                echo "Ce nom d'utilisateur est déjà utilisé.";
                            }
                            mysql_close($connection);
                        }
                        else
                        {
                            if($nextPage=='connecter')
                            {
                                require("./src/config.php");
                                $trouve=TRUE;

                                if (isset($_POST['username'])) { 
                                    $connection=mysql_connect($host, $login, $passwd) or die("impossible de se connecter");
                                    mysql_select_db($dbname) or die("impossible d'aller sur la bd");

                                //    $connection=mysqli_connect($host, $login, $passwd, $dbname) or die("impossible de se connecter");

                                    //RECHERCHE DU USER
                                    $query="SELECT * FROM ".$table."  WHERE username = '".$_POST['username']."'";
                                    $check = mysql_query($query) or die("essayer plus tard!");
                                //    $check = mysqli_query($connection, $query) or die("essayer plus tard!");

                                    //si le client n'est pas ds la bd il doit s'enregistrer
                                    $numberOfRow = mysql_num_rows($check);
                                    if ($numberOfRow == 0) {
                                        //il n'y a aucune ligne correspondante
                                        die("Vous n'existez pas. Cliquer <a href=\"\" \">ici</a> pour vous enregistrer");
                                    }

                                    //Recherche du password
                                    $trouve=FALSE;

                                    while(($result = mysql_fetch_array($check)) && ($trouve==FALSE)){
                                        $idQuestion = $_POST['idQuestion'];
                                        if (md5($_POST['pass']) == $result["password"] && md5($_POST['reponse']) == $result["reponse".$idQuestion] ){
                                            //on doit maintenant checker le password maison le fait pas pour l'instant
                                            //tout est OK - on ferme la connection et on envoie le client sur la page input.php
                                            mysql_close($connection);
                                            $trouve=TRUE;
                                            // Avec COOKIES 
                                            setcookie("yourname", $_POST['username'], time() + 3600);
        //                                  avec session
                                            $_SESSION['username']=$_POST['username'];
                                            header("Location: index.php");
                                        }
                                        else 
                                        {
                                            echo "Vos identifiants ou votre reponse ne correspondent pas.";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            session_start();
            if(isset($_SESSION["username"]))
            {
                
                
                    // User connecté 
                echo 'Bonjour '.$_SESSION["username"].' !</br>';
                ?>
                <form  action="index.php" method="post">
                    <input type="submit" name="nextPage" value="deconnecter" class="myButton"/>
                </form>
            <?php 
            }
            else
            {
            ?>
                       
                <!--<form  action="index.php" method="post">-->
                <form onsubmit="return checkParamLogin()" id="login" name="login" method="post">
                    <table border="0">
                        <tr>
                            <td>
                                <h1>Login form</h1>
                            </td>
                        </tr>
                        <tr>
                            <td id="colorUsername" >Username:</td>
                            <td>
                                <!--onsubmit="checkParamLogin()"-->
                                <input type="text" name="username" id="username"  maxlength="20">
                                <script  type="text/javascript" >
                                    var keyUsername = document.getElementById('username');
                                    keyUsername.focus();
                                    keyUsername.select();
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td id="colorPassword" >Password:</td>
                            <td><input type="password" name="pass" id="pass" maxlength="20"/></td>
                            <td><input type="hidden" name="nextPage" id="nextPage" value="connecter"/></td>
                        </tr>
                        <?php
                            if (isset($_COOKIE['yourname'])) {

                                ?>
                                <script  type="text/javascript" >
                                    var name = 'yourname' + "=";
                                    var ca = document.cookie.split(';');
                                    var monCookie;
                                    for(var i=0; i<ca.length; i++)
                                    {
                                        var c = ca[i].trim();
                                        if (c.indexOf(name)==0) 
                                            monCookie = c.substring(name.length,c.length);
                                    }
                                    document.getElementById('username').value = monCookie;
                                    pass.focus();
                                    pass.select();
                                </script>
                                <?php
                            }
                        ?>
                        <tr>
                            <?php
                                $number=rand(0,2);
                                if($number==0)
                                {
                                    ?>
                                        <td id="question">Quel est le nom de jeune fille de votre mère?</td>
                                        <td><input type="hidden" name="idQuestion" id="idQuestion" value="1"/></td>
                                    <?php 
                                }
                                if($number==1)
                                {
                                    ?>
                                        <td id="question">Quel est votre animal préférer?</td>
                                        <td><input type="hidden" name="idQuestion" id="idQuestion" value="2"/></td>
                                    <?php 
                                }
                                if($number==2)
                                {
                                    ?>
                                        <td id="question">Quel est votre ville natale?</td>
                                        <td><input type="hidden" name="idQuestion" id="idQuestion" value="3"/></td>
                                    <?php 
                                }
                            ?>
                        </tr>
                        <tr>
                            <td id="colorR" >Reponse:</td>
                            <td><input type="password" name="reponse" id="reponse" maxlength="60"/></td>
                        </tr>
                        <tr>
                            <td ><input type="submit" name="submit" value="Login" class="myButton"></td>
                        </tr>
                    </table>
                </form>
                <form  action="index.php" method="post">
                    <table border="0">
                        <td><input type="hidden" name="nextPage" id="nextPage" value="register"/></td>
                        </tr>

                        <tr>
                            <td align="right"><input type="submit" name="submit" value="S'enregistrer" class="myButton"></td>
                        </tr>
                    </table>
                </form>

        <?php
            }
        }
        ?>
        
        </div>
        
        <div id="footer">
        </div> 
    </body>
</html>

