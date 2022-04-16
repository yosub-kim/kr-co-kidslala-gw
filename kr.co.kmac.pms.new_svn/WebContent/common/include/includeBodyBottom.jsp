</div>
<script>
//alert(UserAgent);
if (UserAgent.match(/SHW-M380S/) != null){
	var textAreaProp = document.getElementsByTagName('textarea');
	//alert(textAreaProp.length);
	for(var i=0; textAreaProp.length > i; i++){
		textAreaProp[i].readOnly = false;
	}
}
</script>