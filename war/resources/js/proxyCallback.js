/**
 * This file provide the function for an asynchronous
 * communication between the client and the server.
 * 
 * @author: Simone Spaccarotella
 * @version: 1.0
 * @releaseDate: 2009-17-06
 */

/*
 * Retrieve the object for the AJAX comunication
 */
function getHttpRequest() {
	var http_request = null;
	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		http_request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		// code for IE6, IE5
		http_request = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var stringa = "";
	return http_request;
}

function callProxy(url) {
	if (url != null && url != "") {
		var http_request = getHttpRequest();
		var parameters = "url=" + encodeURIComponent(url);
		// call the servlet by a POST method, in asynchronous mode
		http_request.open("POST", "proxy", true);
		// set the header
		http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		http_request.setRequestHeader("Content-length", parameters.length);
		http_request.setRequestHeader("Connection", "close");
		http_request.onreadystatechange = function(){
			if (http_request.readyState == 4) {
				document.getElementById("html").innerHTML = http_request.responseText;
			}
		}
		// and send the parameter
		http_request.send(parameters);
	}
}
