<html>
<body>
<script>
var url = "post.php";
var binary;
var filename;
var mytext;

function upload() {
	filename = document.getElementById('myfile').value;
	mytext = document.getElementById('mytext').value;
	document.getElementById('ajaxbutton').disabled = true;

	// request local file read permission
	try {
		netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	} catch (e) {
		alert("Permission to read file was denied.");
	}
	
	// open the local file
	var file = Components.classes["@mozilla.org/file/local;1"]
		.createInstance(Components.interfaces.nsILocalFile);
	file.initWithPath( filename );		
	stream = Components.classes["@mozilla.org/network/file-input-stream;1"]
		.createInstance(Components.interfaces.nsIFileInputStream);
	stream.init(file,	0x01, 00004, null);
	var bstream =  Components.classes["@mozilla.org/network/buffered-input-stream;1"]
		.getService();
	bstream.QueryInterface(Components.interfaces.nsIBufferedInputStream);
	bstream.init(stream, 1000);
	bstream.QueryInterface(Components.interfaces.nsIInputStream);
	binary = Components.classes["@mozilla.org/binaryinputstream;1"]
		.createInstance(Components.interfaces.nsIBinaryInputStream);
	binary.setInputStream (stream);

	// start AJAX file upload in 1 second
	window.setTimeout("ajax_upload()", 1000);
}

function ajax_upload() {
	// request more permissions
	try {
		netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	} catch (e) {
		alert("Permission to read file was denied.");
	}

	http_request = false;
	http_request = new XMLHttpRequest();
	if (!http_request) {
		alert('Cannot create XMLHTTP instance');
		return false;
	}

	// prepare the MIME POST data
	var boundaryString = 'capitano';
	var boundary = '--' + boundaryString;
	var requestbody = boundary + '\n' 
	+ 'Content-Disposition: form-data; name="mytext"' + '\n' 
	+ '\n' 
	+ mytext + '\n' 
	+ '\n' 
	+ boundary + '\n' 
	+ 'Content-Disposition: form-data; name="myfile"; filename="' 
		+ filename + '"' + '\n' 
	+ 'Content-Type: application/octet-stream' + '\n' 
	+ '\n'
	+ escape(binary.readBytes(binary.available()))
	+ '\n'
	+ boundary;

	document.getElementById('sizespan').innerHTML = 
		"requestbody.length=" + requestbody.length;
	
	// do the AJAX request
	http_request.onreadystatechange = requestdone;
	http_request.open('POST', url, true);
	http_request.setRequestHeader("Content-type", "multipart/form-data; \
		boundary=\"" + boundaryString + "\"");
	http_request.setRequestHeader("Connection", "close");
	http_request.setRequestHeader("Content-length", requestbody.length);
	http_request.send(requestbody);

}

function requestdone() {
	if (http_request.readyState == 4) {
		if (http_request.status == 200) {
			result = http_request.responseText;
			document.getElementById('myspan').innerHTML = result;            
		} else {
			alert('There was a problem with the request.');
		}
		document.getElementById('ajaxbutton').disabled = false;
	}
}

</script>

<form>
Text: <input type="text" id="mytext" name="mytext" size="40">
<br>
File: <input type="file" id="myfile" name="datafile" size="40"><br>
<input type="button" id="ajaxbutton" value="AJAX IT" onclick="upload();">
</form>

<div id="sizespan"></div>
<hr>
<div id="myspan"></div>

</body>
</html>

