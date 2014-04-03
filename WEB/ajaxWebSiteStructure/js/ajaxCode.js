function CreateAjaxObject(){//we test if AJAX supported and we create and object
//syntax browser dependent;-)
var ajax;//global variable here
if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
  {
  ajax= new XMLHttpRequest(); 
  }  else {
          if (window.ActiveXObject) // code for IE6, IE5
                {ajax = new ActiveXObject("Microsoft.XMLHTTP");
                 } else ajax=null;
          };
return ajax;
}

function showContent(content)
{
ajax=CreateAjaxObject();
if (ajax==null)
  {
  alert ("Your browser does not support XMLHTTP!");
  }
var url="./html/"+content+".html";
ajax.onreadystatechange=displayOnly; //we execute this function in case...
ajax.open("GET",url,true); //we open the request
ajax.send(null); //we send the request
}

function displayOnly()
{
if (ajax.readyState==4) //we have received the answer
  {
  document.getElementById("content").innerHTML=ajax.responseText; //we display the content
  }
}

