<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript" src='<c:url value="/js/expertPool_Form2.js"/>'></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function addSpecialField(){
	var specialFieldList= $$('input[id="specialChk"]');
	var checkedCount = 0;
	if(specialFieldList.length > 0) {
		for(var i = 0; i < specialFieldList.length ; i++) {
			if(specialFieldList[i].checked) {
				var newRow=$('mySpecialFieldListTbl').insertRow(-1);
	        	var newCellArr = new Array();
        		newCellArr[0] = newRow.insertCell(-1);
        		newCellArr[1] = newRow.insertCell(-1);
        		
	        	newCellArr[0].innerHTML = "<input type='checkbox' id='mySpecialChk' specialId='"+specialFieldList[i].getAttribute('specialId')+"' specialName='"+specialFieldList[i].getAttribute('specialName')+"'>"
	        							 +"<input type='hidden' name='specialId' value='"+specialFieldList[i].getAttribute('specialId')+"'>"
	        							 +"<input type='hidden' name='specialName' value='"+specialFieldList[i].getAttribute('specialName')+"'>";
	        	newCellArr[1].innerHTML = specialFieldList[i].getAttribute('specialName');
	        	
	        	newCellArr[0].className = "detailTableField_center";
	        	newCellArr[1].className = "detailTableField_left";
	        	checkedCount++;
			}
		}
	}
	if(checkedCount == 0) {
		alert("추가할 전문분야를 선택해주세요.");
		return false;
	} else {
		document.all.chkBlank.style.display="none";
	}
	// 저장 실행
	specialField_Save();
}
function removeSpecialField(){
	var specialFieldList= $$('input[id="mySpecialChk"]');
	var checkedCount = 0;
	if(specialFieldList.length > 0) {
		for(var i = 0; i < specialFieldList.length ; i++) {
			if(specialFieldList[i].checked) {
				specialFieldList[i].up('td').up('tr').remove();
				checkedCount++;
			}
		}
	}
	if(checkedCount == 0) {
		alert("삭제할 전문분야를 선택해주세요.");
		return false;
	}
	// 저장 실행
	specialField_Save();
	if(specialFieldList.length == 0) {
		document.all.chkBlank.style.display="";
	}
}
function specialField_Save(){
	var sFrm = document.frm;
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':"/action/ExpertPoolSpecialFieldAction.do?mode=storeSpecialField"
				,'onSuccess':function(obj){
					alert("저장하였습니다.");
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("저장할 수 없습니다.");
				}
			}
		); status = null;	
}
function changeSpecialField(val){
	AjaxRequest.get(
		{
			'url':"/action/ExpertPoolSpecialFieldAction.do?mode=searchSpecialField&deptId="+val
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){   
				//alert(obj.responseText);
	        	
	           	var tableObj = $('specialFieldListTbl');
	        	while($('specialFieldListBody').childElements().size() > 0){
			   		$('specialFieldListBody').down('tr').remove();   		
	        	}
	        	
	           	var specialFieldList = eval('(' + obj.responseText + ')').specialField;
	           	specialFieldList.each(function(specialField) {
		            var newRow=tableObj.insertRow(-1);
		        	var newCellArr = new Array();
		        	for ( var i=0;i<2;i++ ){
		        		newCellArr[i] = newRow.insertCell(-1);
		        	}
		        	newCellArr[0].innerHTML = "<input type=checkbox id=specialChk specialId="+specialField.specialId+" specialName='"+specialField.specialName+"'>";
		        	newCellArr[1].innerHTML = specialField.specialName;
		        	
		        	newCellArr[0].className = "detailTableField_center";
		        	newCellArr[1].className = "detailTableField_left";
	    		});
			}
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert("데이터를 가져오지 못 했습니다.");
			}
		});
}
</script>
</head> 
<body>
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<!-- SPACER -->
	<tr>
		<td height="7"></td>
	</tr>
	<form name="frm" method="post">	
	<input type="hidden" name="ssn" value="<authz:authentication operation="username" />">	
	
	<tr>
		<td>
			<table width="480" cellpadding="0" cellspacing="0" >
				<!-- 칼럼스타일 정의 시작 -->
				<colgroup>
					<col style="width: 46%;"/>
					<col style="width: 8%;"/>
					<col style="width: 46%;"/>
				</colgroup>
				<!-- 칼럼스타일 정의 종료 -->
				<tr>
					<td><img src="/images/title_de02.jpg" align="absmiddle"> <span class="subTitle">전문가 전문분야</span></td>
					<td></td>
					<td><img src="/images/title_de02.jpg" align="absmiddle"> <span class="subTitle">나의 전문분야</span></td>
				</tr>
				<tr>
					<td class="detailTableField_center" style="vertical-align: top; padding-top: 4px;">
						<select class="selectbox" style="width:100%" onchange="changeSpecialField(this.value)" >
							<option>전문분야를 선택하세요</option>
							<c:forEach var="rs" items="${groupList}">
								<option value="<c:out value="${rs.id }"/>"><c:out value="${rs.name }"/></option>
							</c:forEach>
						</select>
						<div style="border-color: c7c7c7; border-width: 1px; border-style: none; height: 365px; overflow: auto;">
							<table style="width: 100%" id="specialFieldListTbl">
								<colgroup>
									<col style="width: 16%;"/>
									<col style="width: 84%;"/>
								</colgroup>
								<thead>
									<tr>
										<td class="searchTitle_center">선택</td>
										<td class="searchTitle_center">상세 전문분야</td>
									</tr>
								</thead>
								<tbody id="specialFieldListBody">
									<tr>
										<td class="detailTableField_center" colspan="2">나의 전문분야에 추가하려면 ▶ 클릭</td> 
									</tr>
								</tbody>
							</table>
						</div>
					</td>
					<td valign="middle" align="center">
				    	<a href="#" onclick="addSpecialField();"><img src="/images/btn_right_arrow.gif" alt="추가" class="IMGBTN"></a>
				    	<br><br>
				    	<a href="#" onclick="removeSpecialField();"><img src="/images/btn_left_arrow.gif" alt="삭제" class="IMGBTN"></a>
					</td>					
					<td class="detailTableField_center" style="vertical-align: top; padding-top: 4px;">
						<div style="border-color: c7c7c7; border-width: 1px; border-style: none; height: 365px; overflow: auto;">
							<table style="width: 100%" id="mySpecialFieldListTbl">
								<colgroup>
									<col style="width:16%;"/>
									<col style="width:84%;"/>
								</colgroup>
								<thead>
									<tr>
										<td class="searchTitle_center">선택</td>
										<td class="searchTitle_center">상세 전문분야</td>
									</tr>
								</thead>
								<tbody id="mySpecialFieldListBody">
									<c:forEach var="rs" items="${expertSecialFieldList}">
										<tr>
											<td class="detailTableField_center">
												<input type="checkbox" id="mySpecialChk" specialId="<c:out value="${rs.specialId}"/>" specialName="<c:out value="${rs.specialName}"/>">
												<input type="hidden" name="specialId" value='<c:out value="${rs.specialId}"/>'>
												<input type="hidden" name="specialName" value='<c:out value="${rs.specialName}"/>'>
											</td>
											<td class="detailTableField_left"><c:out value="${rs.specialName}"/></td>
										</tr>
									</c:forEach>
									<tr id="chkBlank" style="display:<c:if test="${!empty expertSecialFieldList}">none</c:if>;"><td colspan="2" class="detailTableField_center">전문분야가 없습니다.</td></tr>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</form>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td>
			<table width='100%' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">
						<img alt="저장" src="/images/btn_save.jpg" border="0" onclick="specialField_Save();" style="cursor: hand;">&nbsp;
						<img alt="닫기" src="/images/btn_close.jpg" border="0" onclick="parent.window.close();" style="cursor: hand;">&nbsp; 
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>