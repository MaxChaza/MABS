/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function checkParamRegister(){
    var ok = true;
    usernameIsSet=true;
    passwordIsSet=true;
    password2IsSet=true;
    samePassword=true;
    r1IsSet=true;
    r2IsSet=true;
    r3IsSet=true;
    // Test de la validité du login
    if(document.log.username.value==""){
        ok=false;
        usernameIsSet=false;
        document.getElementById('colorUsername').style.color = 'red';
        var keyUsername = document.getElementById('username');
        keyUsername.focus();
        keyUsername.select();
    }
    else
    {
        document.getElementById("colorUsername").style.color = 'black';
        usernameIsSet=true;
    }
    // Test de la validité du 1er password
    if(document.log.password.value==""){
        ok=false;
        passwordIsSet=false;
        document.getElementById('colorPassword').style.color = 'red';
        if(usernameIsSet){
            var keyPassword = document.getElementById('password');
            keyPassword.focus();
            keyPassword.select();
        }
    }
    else{     
        document.getElementById("colorPassword").style.color = 'black';
        passwordIsSet=true;
    }


    // Test de la validité du 2eme password
    if(document.log.password2.value==""){
        ok=false;
        password2IsSet=false;
        document.getElementById('colorPassword2').style.color = 'red';
        if(passwordIsSet && usernameIsSet){
            var keyPassword2 = document.getElementById('password2');
            keyPassword2.focus();
            keyPassword2.select();
        }
    }
    else{
        password2IsSet=true;
        document.getElementById("colorPassword2").style.color = 'black';
    }
       
    // Test de la correspondance des 2 passwords
    if(!(document.log.password.value==document.log.password2.value)){
        ok=false;
        samePassword=false;
        document.log.password.value="";
        document.log.password2.value="";
        document.getElementById("erreurPassword").style.color = 'red';
        document.getElementById("erreurPassword").innerHTML="Password mal confirm&eacute;!";
                       
        var keyPassword = document.getElementById('password');
            keyPassword.focus();
            keyPassword.select();
    }
    else
    {
        if(passwordIsSet){
            samePassword=true;
            document.getElementById("colorPassword").style.color = 'black';
            document.getElementById("colorPassword2").style.color = 'black';
        }
    }
    
//    // Test de la validité de r1
    if(document.log.r1.value==""){
        ok=false;
        r1IsSet=false;
        document.getElementById('colorR1').style.color = 'red';
    }
    else{     
        document.getElementById("colorR1").style.color = 'black';
        r1IsSet=true;
    }
    
   
//    // Test de la validité de r2
    if(document.log.r2.value==""){
        ok=false;
        r2IsSet=false;
        document.getElementById('colorR2').style.color = 'red';
    }
    else{     
        document.getElementById("colorR2").style.color = 'black';
        r2IsSet=true;
    }
   
//    // Test de la validité de r3
    if(document.log.r3.value==""){
        ok=false;
        r3IsSet=false;
        document.getElementById('colorR3').style.color = 'red';
    }
    else{     
        document.getElementById("colorR3").style.color = 'black';
        r3IsSet=true;
    }
    
    return ok;
}


function checkParamLogin(){
    var ok = true;
    usernameIsSet=true;
    passwordIsSet=true;
    reponseIsSet=true;
    
    // Test de la validité du prenom
    if(document.login.username.value==""){
        ok=false;
        usernameIsSet=false;
        document.getElementById('colorUsername').style.color = 'red';
        var keyUsername = document.getElementById('username');
        keyUsername.focus();
        keyUsername.select();
    }
    else
    {
        document.getElementById("colorUsername").style.color = 'black';
        usernameIsSet=true;
    }
    // Test de la validité du pass
    if(document.login.pass.value==""){
        ok=false;
        passwordIsSet=false;
        document.getElementById('colorPassword').style.color = 'red';
        if(usernameIsSet){
            var keyPassword = document.getElementById('pass');
            keyPassword.focus();
            keyPassword.select();
        }
    }
    else{     
        document.getElementById("colorPassword").style.color = 'black';
        passwordIsSet=true;
    }
 
 // Test de la validité de la reponse
    if(document.login.reponse.value==""){
        ok=false;
        reponseIsSet=false;
        document.getElementById('colorR').style.color = 'red';
        if(usernameIsSet && passwordIsSet){
            var keyReponse = document.getElementById('reponse');
            keyReponse.focus();
            keyReponse.select();
        }
    }
    else{     
        document.getElementById("colorR").style.color = 'black';
        reponseIsSet=true;
    }
    return ok;
}
