<DIV id="divShowInstall" style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; Z-INDEX: 0; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; POSITION: absolute"><EMBED src="/components/namo/images/NamoBanner.swf" width=741 height=525 type=application/x-shockwave-flash></EMBED></Div>
<script>
function Namo_draw(nWidth,nHeight){
	if(nWidth == "") nWidth = "741";
	if(nHeight == "") nHeight = "525";
	var strScripts ="<OBJECT ID='Wec' CLASSID='CLSID:415D9FA9-8FF5-42C1-A5B1-77E009D45CA7' WIDTH='" + nWidth + "' HEIGHT='" + nHeight + "' CodeBase='/components/namo/NamoWec.cab#Version=7,0,0,66' OnInitCompleted='wec_OnInitCompleted();'>";
	strScripts +="<PARAM NAME='UserLang' VALUE=kor>";
	strScripts +="<PARAM NAME='InitFileURL' VALUE='/components/namo/As7Init.xml'>";
	strScripts +="<PARAM NAME='InstallSourceURL' VALUE='http://help.namo.co.kr/activesquare/techlist/help/AS7_update'>";
	strScripts +="</OBJECT>";

	document.write(strScripts);
}
document.onreadystatechange=function()
{
 if (document.readyState == 'complete')
 {
      if (document.all['divShowInstall']){
        document.all['divShowInstall'].style.visibility = 'hidden';
        wec_OnInitCompleted();
      }
  }
}
</script>