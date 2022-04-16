<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
function addRowAttachFile(targetTblObjId){
    var tableObj;
    if (targetTblObjId != undefined && targetTblObjId != "")
    	tableObj = $(targetTblObjId); 
    else
    	tableObj = $('attachFileTable');

    var tdCount = tableObj.down('tbody').down('tr').down('td').nextSiblings().length + 1 ;
    
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}

	newRow.bgColor ='#FFFF80';
	newCellArr[0].innerHTML = "<input type='checkbox' name='fileAttachCheck'/>";
	newCellArr[1].innerHTML = "<select name='attachIsEssential'><option value='0'>필수</option><option value='1'>비필수</option></select>";
	newCellArr[2].innerHTML = "<select name='attachOutputType'><option value='0'>보고서</option><option value='1'>기타</option></select>";
	newCellArr[3].innerHTML = "<input type='text' name='attachOutputName'/>";
	newCellArr[4].innerHTML = "<input type='file' name='file_"+(tableObj.rows.length-1)+"'/>";
	newCellArr[5].innerHTML = "img";
}
//첨부파일 다운로드
function fileDownLoad(uuid){
	if($$("#_targetDownload").length == 0)
		document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    $("_targetDownload").src = "/servlet/RepositoryDownLoad?fileId=" + uuid;
}



function deleteRowAttachFile(clickObj, targetTblObjId ){
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input["name=fileAttachCheck"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().remove();
		}
	}
}

//실제삭제할 파일을 담아놓는다.
function realDeleteRowAttachFile(imgEl, divObjId){
	
	var deleteFileId = $(imgEl).getAttribute("fileId");
	var trEl = $(imgEl).up().up();
	var newInputEl = document.createElement("<INPUT type='text' name='deleteFileId' value='"+deleteFileId+"' >");
	if(divObjId)
		$(divObjId).insertAdjacentElement('beforeEnd',newInputEl);
	else
		$('divDeltetFileList').insertAdjacentElement('beforeEnd',newInputEl);
	trEl.remove();
}

</script>
</head> 

<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<form name="_FORM" method="post"  enctype="multipart/form-data" action="<c:url value="/action/RepositoryTestAction.do?mode=uploadTestResult"/>">
	<table border="1">
		<tr>
	 		<td>
				<table id="attachFileTable" width="100%" border="1">
					<thead id="attachFileTableHeader">
						<tr>
							<td>선택</td>
							<td>필수여부</td>
							<td>첨부문서명</td>
							<td>첨부타입</td>
							<td>파일명</td>
							<td>내려받기</td> 
						</tr>
					</thead>
					<tbody id="attachFileTableBody">
						<tr>
							<td><input type="checkbox" name="fileAttachCheck"/></td>
							<td><select name="attachIsEssential">
										<option value="0">필수</option>
										<option value="1">비필수</option>
									</select></td>
							<td><select name="attachOutputType">
										<option value="0">보고서</option>
										<option value="1">기타</option>
									</select></td>
							<td><input type="text" name="attachOutputName"/></td>
							<td><input type="file" name='file_1'/></td>
							<td>img</td>
						</tr>
					</tbody>
				</table>
	 		</td> 
	 	</tr>
	 	<tr>
			<td align="right"><input type="button" value="행추가" onclick="javascript:addRowAttachFile()"/><input type="button" value="행삭제" onclick="javascript:deleteRowAttachFile()"/></td>
		</tr>	
	</table>
	<br>
 <input type="submit">
</form>
 
 
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>

</html>					 