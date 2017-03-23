function httpGetAsync(theUrl, callback){
             var xmlHttp = new XMLHttpRequest();
             xmlHttp.onreadystatechange = function() { 
                 if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
                     callback(xmlHttp.responseText);
             }
             xmlHttp.open("GET", theUrl, true); // true for asynchronous 
             xmlHttp.send(null);
         }

function httpAsync(theUrl, bodyObject, callback, method){
	var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
	xmlhttp.onreadystatechange = function() { 
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			callback(xmlhttp.responseText);
	}
	xmlhttp.open(method, theUrl, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.send( JSON.stringify( bodyObject ) );
	
}

function httpPostAsync(theUrl, bodyObject, callback){
	httpAsync(theUrl, bodyObject, callback, 'POST');
}

function httpPutAsync(theUrl, bodyObject, callback){
	httpAsync(theUrl, bodyObject, callback, 'PUT');
}