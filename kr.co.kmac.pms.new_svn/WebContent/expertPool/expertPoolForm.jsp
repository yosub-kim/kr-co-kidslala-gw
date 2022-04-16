<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.expertpool.data.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript" src='<c:url value="/js/expertPool_Form.js"/>'></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var varSsn = "<c:out value="${ssn }" />";

var myDate = new Date();
var nowYear = myDate.getFullYear();

j$(document).ready(function () {
    var tag = {};
    var counter = 0;

    // 태그를 추가한다.
    function addTag (value) {
        tag[counter] = value; // 태그를 Object 안에 추가
        counter++; // counter 증가 삭제를 위한 del-btn 의 고유 id 가 된다.
        j$.ajax({
            url:'/action/HashTagAction.do?mode=addExpertHashTag',
            type:'post', 
            data:{ "hashTag": value, "expertSsn":'<c:out value="${expertPool.ssn}"/>', "tagType": "ByUSER"},
            success: function(data) {
            },
            error: function(err) {
            	alert(err);
            }
        });
    }
    
    j$("#tag").on("keypress", function (e) {
        var self = j$(this);
        // input 에 focus 되있을 때 엔터 및 스페이스바 입력시 구동
        if (e.key === "Enter" || e.keyCode == 32) {
            var tagValue = self.val(); // 값 가져오기

            // 값이 없으면 동작 ㄴㄴ
            if (tagValue !== "") {
                // 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
                var result = Object.values(tag).filter(function (word) {
                    return word === tagValue;
                })
            
                // 태그 중복 검사
                if (result.length == 0) { 
                    j$("#tag-list").append("<li class='tag-item'>"+"#"+tagValue+"<span class='del-btn' idx='_"+counter+"'><font color='black'>x</font></span></li>");
                    addTag(tagValue);
                    self.val("");
                } else {
                    alert("태그값이 중복됩니다.");
                }
            }
            e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
        }
    });
    
    // 삭제 버튼 
    // 삭제 버튼은 비동기적 생성이므로 document 최초 생성시가 아닌 검색을 통해 이벤트를 구현시킨다.
    j$(document).on("click", ".del-btn", function (e) {
        var index = j$(this).attr("idx");
        tag[index] = "";
        j$(this).parent().remove();
        j$.ajax({
            url:'/action/HashTagAction.do?mode=deleteExpertHashTag',
            type:'post', 
            data:{ "uuid":index, "expertSsn":'<c:out value="${expertPool.ssn}"/>'},
            success: function(data) {
            },
            error: function(err) {
            	alert(err);
            }
        });
    });
})

function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear)+1;
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear)+1;
	} else {
		var age = "&nbsp;";
	}
	return age;	
}

function Page_OnLoad(){
	FILEUPLOADER_INIT();
	//국적구분 로드 
	//DropDownList_DataLoad(document.getElementById("nationality"), "NATIONALITY", "");
	// 직업구분에 데이타로드
	//DropDownList_DataLoad(document.getElementById("jobClass"), "EMP_WORKING_TYPE", "");
	DropDownList_DataLoad2(document.getElementById("selectDept"), "getGroupCode", "<c:out value="${expertPool.dept}"/>");
	DropDownList_DataLoad2(document.getElementById("selectPosition"), "getRoleCode", "<c:out value="${expertPool.companyPosition}"/>");
}

function FILEUPLOADER_INIT() {
	var button1 =$("MyPic");
	new AjaxUpload(button1,{
		action: '/action/RepositoryAction.do?mode=fileUpload', 
		name: 'userfile',
		onSubmit : function(file, ext){
			button1.src="/images/ajax-loader.gif";
			this.disable();
		},
		onComplete: function(file, response){
			this.enable();
           	var res = eval('(' + response + ')');
           	$("photo").value = res.file.fileId;
           	button1.src="/servlet/PhotoDownLoadServlet?fileId=" + res.file.fileId;
		}
	});
	var button2 =$("resumeB");
	new AjaxUpload(button2,{
		action: '/action/RepositoryAction.do?mode=fileUpload', 
		name: 'userfile',
		onSubmit : function(file, ext){
			this.disable();
		},
		onComplete: function(file, response){
			this.enable();
           	var res = eval('(' + response + ')');
           	
           	$("resumeS").innerHTML = res.file.orginalFileName;
           	$("resume").value = res.file.fileId;
		}
	});
}

function FILEUPLOADER_INIT2() {
	var button1 =$("MyPic2");
	var button =$("MyPic");
	new AjaxUpload(button1,{
		action: '/action/RepositoryAction.do?mode=fileUpload', 
		name: 'userfile',
		onSubmit : function(file, ext){
			button.src="/images/ajax-loader.gif";
			this.disable();
		},
		onComplete: function(file, response){
			this.enable();
           	var res = eval('(' + response + ')');
           	$("photo").value = res.file.fileId;
           	button.src="/servlet/PhotoDownLoadServlet?fileId=" + res.file.fileId;
		}
	});
}

var imgMyPic = "/images/picguide.gif";
function photo_ProptertyChange(obj){
	var temp = obj.value;
	if(temp != ""){
		chekck(temp,obj);
	}
}

function chekck(temp,obj){
	if(!imageFile_Check(temp)){
		alert("이미지 파일만 선택 가능합니다.\n\.jpg , \.gif, \.png 등등.");
		document.getElementById("MyPic").src = imgMyPic;
		obj.value = "";		
	}else{
		document.getElementById("MyPic").src = temp;
	}
}

function expertPool_Save(){
	var checkSsn = '<%=session.getAttribute("userId") %>';
	if(checkSsn != 'eun' && checkSsn != 'yunsh'){
		if(expertForm.name.value == ""){				alert("성명을 입력하시기 바랍니다.");		expertForm.name.focus();					return;	}
		if(expertForm.uid.value == ""){					alert("주민번호를 입력하시기 바랍니다.");		expertForm.uid.focus();						return;	}
		if(expertForm.jobClass.value == "") {			alert("직업구분을 선택하시기 바랍니다.");		expertForm.jobClass.focus();				return;	}
		if(expertForm.company.value == "") {			alert("소속 기관을 입력하시기 바랍니다.");	expertForm.company.focus();					return;	}
		if(expertForm.deptName.value == "") {			alert("소속 부서를 입력하시기 바랍니다.");	expertForm.deptName.focus();				return;	}
		if(expertForm.companyPositionName.value == ""){ alert("직위를 입력하시기 바랍니다.");		expertForm.companyPositionName.focus();		return;	}
		if(expertForm.mobileNo.value == "") {			alert("휴대전화번호를 입력하시기 바랍니다.");	expertForm.mobileNo.focus();				return;	}
		if(expertForm.resume.value == ""){				alert("프로필을 등록하시기 바랍니다.");		expertForm.resume.focus();					return; }
	}
	
	var ActionURL = "/action/ExpertPoolManagerAction.do";
	if(varSsn == "")
		ActionURL += "?mode=createExpertPool";
	else
		ActionURL += "?mode=updateExpertPool";
	var sFrm = document.forms["expertForm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert('저장하였습니다.');
					/* document.location = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn=<c:out value="${expertPool.ssn}"/>"; */
					document.location = "/_new/expertpool/searchList";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("이미 등록되어 있는 인력입니다.");
				}
			}
	); status = null;
}

function Schooling_Add(){
	var tableObj = $('schoolingListTable');
	if($('schoolingListTableBody').childElements().size() > 0){
		if($('schoolingListTableBody').down('tr').childElements().size() == 1){
	   		$('schoolingListTableBody').down('tr').remove();   		
	   	}
	}
	var tdCount = tableObj.down('tr').down('th').nextSiblings().length + 1 ;
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	
	var date_tmp = new Date()
	tmp =
		(date_tmp.getMonth())+
		(date_tmp.getMonth())+
		(date_tmp.getDate())+
		(date_tmp.getHours())+
		(date_tmp.getMinutes())+
		(date_tmp.getSeconds())+
		(date_tmp.getMilliseconds()); 
	
	newCellArr[0].innerHTML = "<ul><li><input type='checkbox' name='chkScholing' id='"+tmp+"' class='btn_check' value='C'/><label for='"+tmp+"'></label></li></ul><input type='hidden' name='schoolSeq' id='schoolSeq' value=''/>";
	newCellArr[1].innerHTML = "<input type='text' id='schoolTerm' name='schoolTerm' class='contentInput_left'/>";
	newCellArr[2].innerHTML = "<input type='text' id='schoolName' name='schoolName' class='contentInput_left'/>";
	newCellArr[3].innerHTML = "<input type='text' id='schoolMajor' name='schoolMajor' class='contentInput_left'/>";
	newCellArr[4].innerHTML = "<input type='text' id='schoolGraduationState' name='schoolGraduationState' class='contentInput_left'/>";
	newCellArr[5].innerHTML = "<input type='text' id='schoolLocation' name='schoolLocation' class='contentInput_left'/>";
	newCellArr[6].innerHTML = "<input type='text' id='schoolRemark' name='schoolRemark' class='contentInput_left'/>";
}

function Schooling_Del(){
	var tableObj = $('schoolingListTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="chkScholing"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().up().up().remove();
		}
	}
}
function Career_Add(){
	var tableObj = $('careerListTable');
	if($('careerListTableBody').childElements().size() > 0){
		if($('careerListTableBody').down('tr').childElements().size() == 1){
	   		$('careerListTableBody').down('tr').remove();   		
	   	}
	}
	var tdCount = tableObj.down('tr').down('th').nextSiblings().length + 1 ;
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	
	var date_tmp = new Date()
	tmp =
		(date_tmp.getMonth())+
		(date_tmp.getMonth())+
		(date_tmp.getDate())+
		(date_tmp.getHours())+
		(date_tmp.getMinutes())+
		(date_tmp.getSeconds())+
		(date_tmp.getMilliseconds()); 
	
	newCellArr[0].innerHTML = "<ul><li><input type='checkbox' name='chkCareer' id='"+tmp+"' class='btn_check' value='C'/><label for='"+tmp+"'></label></li></ul><input type='hidden' id='careerSeq' name='careerSeq' value=''/>";
	newCellArr[1].innerHTML = "<input type='text' id='careerTerm' name='careerTerm' class='contentInput_left'/>";
	newCellArr[2].innerHTML = "<input type='text' id='careercompanyName' name='careercompanyName' class='contentInput_left'/>";
	newCellArr[3].innerHTML = "<input type='text' id='careerWorkingDept' name='careerWorkingDept' class='contentInput_left'/>";
	newCellArr[4].innerHTML = "<input type='text' id='careerPosition' name='careerPosition' class='contentInput_left'/>";
	newCellArr[5].innerHTML = "<input type='text' id='careerWorkingDetail' name='careerWorkingDetail' class='contentInput_left'/>";
	newCellArr[6].innerHTML = "<input type='text' id='careerRemark' name='careerRemark' class='contentInput_left'/>";

}

function Career_Del(){
	var tableObj = $('careerListTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="chkCareer"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().up().up().remove();
		}
	}
}
function fileDownload(){
	var uuid = $('resume').value;
	if(uuid != ""){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    	$("_targetDownload").src = "/servlet/PhotoDownLoadServlet?fileId=" + uuid;
	}
}
function openSpecialField(){
	var companyWin;
	var sURL = "/action/ExpertPoolSpecialFieldAction.do?mode=selectSpecialField";
	var sFeather = "top=100,left=100,width=505,height=410,scrollbars=no";
	companyWin = window.open(sURL, "companyWin", sFeather);
	companyWin.focus();	
}
</script>
</head>
<body onload="Page_OnLoad();">
<%-- 작업영역 --%>
<div style="display:none;">
<input type="hidden" value="" name="hashTag" id="hashTag" />
</div>
<div class="location">
	<p class="menu_title">인력 POOL</p>
	<ul>
		<li class="home">HOME</li>
		<li>경영관리</li>
		<li>인력관리</li>
		<li>인력 POOL</li>
	</ul>
</div>
<span id="guide" style="color:#999"></span>
<c:set var="isAble" value="disabled"/>
<c:set var="isReadonly" value="disabled"/>
<%-- 작업영역 --%>
<form name="expertForm" method="post">		
<div class="contents sub">
	<div class="fixed_box">
		<div class="title">
			<div class="h1_area">
				<p class="h1"><i class="mdi mdi-file-document-outline"></i>인력정보</p>
			</div>
			<div class="btn_area">
				<button type="button" class="btn line btn_blue" onclick="expertPool_Save();"><i class="mdi mdi-square-edit-outline"></i>저장</button>
				<!-- <button type="button" class="btn line btn_pink" onclick="location.href='javascript:;'"><i class="mdi mdi-trash-can-outline"></i>삭제</button> -->
				<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
			</div>
		</div>	
		<div class="fixed_contents sc">
			
			<!-- 인력 정보 -->
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">기본정보</p>
					</div>
				</div>
				<div class="board_contents">
					<div class="info_area">
						<div class="profile_box edit">
							<c:choose>
								<c:when test="${!empty expertPool.photo}">
									<div class="profile"><img id="MyPic" style="cursor:hand;" src="/servlet/PhotoDownLoadServlet?fileId=<c:out value="${expertPool.photo }"/>" ></div>		
								</c:when>
								<c:otherwise>
									<div class="profile"><img id="MyPic" src="../resources/img/photo_none.png" alt="프로필 이미지"></div>			
								</c:otherwise>
							</c:choose>
							<div class="btn_area">
								<button type="button" class="btn line btn_blue" id="MyPic2" onclick="FILEUPLOADER_INIT2();"><i class="mdi mdi-camera"></i>사진첨부</button>
							</div>
							<input type="hidden" name="photo" id="photo" value="<c:out value="${expertPool.photo}"/>">
						</div>
						<div class="profile_detail_box">
							<table class="tbl-edit w-100"><!-- 요소들 100% 할 경우 -->
								<colgroup>
									<col style="width: 20%">
									<col style="width: 30%">
									<col style="width: 20%">
									<col style="width: 30%">
								</colgroup>				
								<tbody>
									<tr>
										<th><span class="require">*</span>성명</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<input type="text" name="name" id="name" value="<c:out value="${expertPool.name}"/>" disabled/>
											</c:when>
											<c:otherwise>
												<input type="text" name="name" id="name" value="<c:out value="${expertPool.name}"/>" />
											</c:otherwise>
										</c:choose>
										</td>
										<th><span class="require">*</span>성별</th>
										<td>
											<ul class="chek_ui">
											<c:choose>
												<c:when test="${isReadonly == 'readonly'}">
													<li>
														<input type="radio" id="choose_1" name="gender" class="btn_radio" value="1" <c:if  test="${expertPool.gender == '1'}">checked</c:if> diabled>
                            										<label for="choose_1"><p>남</p></label>       
													</li>
													<li>
														<input type="radio" id="choose_2" name="gender" class="btn_radio" value="0" <c:if  test="${expertPool.gender == '0'}">checked</c:if> diabled>
                            										<label for="choose_2"><p>여</p></label>       
													</li>
												</c:when>
												<c:otherwise>
													<li>
														<input type="radio" id="choose_1" name="gender" class="btn_radio" value="1" <c:if  test="${expertPool.gender == '1'}">checked</c:if>>
                            										<label for="choose_1"><p>남</p></label>       
													</li>
													<li>
														<input type="radio" id="choose_2" name="gender" class="btn_radio" value="0" <c:if  test="${expertPool.gender == '0'}">checked</c:if>>
                            										<label for="choose_2"><p>여</p></label>       
													</li>
												</c:otherwise>
											</c:choose>
											</ul>
										</td>
									</tr>
									<tr>
										<th><span class="require">*</span>주민번호</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<input type="hidden" name="ssn" id="ssn" value="<c:out value="${expertPool.ssn}"/>" />
												<input type="hidden" name="uid" id="uid" value="<c:out value="${expertPool.uid}"/>" />
											<%ExpertPool e = (ExpertPool)request.getAttribute("expertPool");%>	
											<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>
												<input type="text" name="dispSsn" id="dispSsn" value="<%=e.getUid().substring(0,6) + "-" + e.getUid().substring(6,13) %>" disabled />
											<%} else {%>
												<input type="text" name="dispSsn" id="dispSsn" value="<%=e.getUid().substring(0,2) + "****-*******" %>" disabled />
											<%}%>
											</c:when>
											<c:otherwise>
												<c:choose>
												<c:when test="${empty expertPool.uid}">
													<input type="text" name="uid" id="uid" maxlength="13" placeholder="※ 숫자만 입력" value="<c:out value="${expertPool.uid}"/>" onblur="Jumin_Check();" style="ime-mode:disabled" <c:if test="${!empty expertPool.uid}">disabled</c:if> onchange="Number_Only(this, -1, -1);" >
													<input type="hidden" name="ssn" value="<c:out value="${expertPool.ssn}"/>" >
												</c:when>
												<c:otherwise>
												<input type="hidden" name="ssn" id="ssn" value="<c:out value="${expertPool.ssn}"/>" />
												<input type="hidden" name="uid" id="uid" value="<c:out value="${expertPool.uid}"/>" />
												<%ExpertPool e = (ExpertPool)request.getAttribute("expertPool");%>	
												<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>
													<input type="text" name="dispSsn" id="dispSsn" class="contentInput_left" value="<%=e.getUid().substring(0,6) + "-" + e.getUid().substring(6,13) %>" disabled />
												<%} else {%>
													<input type="text" name="dispSsn" id="dispSsn" class="contentInput_left" value="<%=e.getUid().substring(0,2) + "****-*******" %>" disabled />
												<%}%>
												</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										</td>
										<th><span class="require">*</span>국적</th>
										<td>
											<c:set var="nationality" value="${expertPool.nationality}"/>
											<c:if test="${empty nationality}"><c:set var="nationality" value="${'KOR'}"/></c:if>
											<code:codelist tableName="NATIONALITY" selectedInfo="${nationality}" attribute=" name=\"nationality\" id=\"nationality\" class=\"selectboxPopup\" " isSelectBox="Y"/>
										</td>
									</tr>
									<tr>
										<th><span class="require">*</span>직업구분</th>
										<td>
											<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<code:codelist tableName="EMP_WORKING_TYPE" 
																attribute=" ${isAble} name=\"jobClass\" id=\"jobClass\" class=\"selectboxPopup\" onpropertychange=\"jobClass_Select(this);\" title=\"직업구분 변경 필요 시 관리자에게 연락바랍니다.\" "
																isSelectBox="Y" selectedInfo="${expertPool.jobClass}"/>
											</c:when>
											<c:otherwise>
												<select name="jobClass" id="jobClass" class="select" onpropertychange="jobClass_Select(this);" >
													<option value=''>선택하세요</option>
														<option value="A" <c:if test="${expertPool.jobClass=='A'}">selected</c:if>>상근</option>
														<option value="B" <c:if test="${expertPool.jobClass=='B'}">selected</c:if>>상근(2)</option>
														<option value="J" <c:if test="${expertPool.jobClass=='J'}">selected</c:if>>상임</option>
														<option value="N" <c:if test="${expertPool.jobClass=='N'}">selected</c:if>>RA</option>
														<option value="C" <c:if test="${expertPool.jobClass=='C'}">selected</c:if>>엑스퍼트</option>
														<option value="D" <c:if test="${expertPool.jobClass=='D'}">selected</c:if>>산업계강사</option>
														<option value="E" <c:if test="${expertPool.jobClass=='E'}">selected</c:if>>대학교수</option>
														<option value="R" <c:if test="${expertPool.jobClass=='R'}">selected</c:if>>아르바이트</option>
												</select>
											</c:otherwise>
											</c:choose>
										</td>
										<th><span class="require">*</span>소속기관</th>
										<td>
										<c:choose>
										<c:when test="${isReadonly == 'readonly'}">
											<input type="text"	 name="company"	  id="company"	  value="<c:out value="${expertPool.company}"/>" class="contentInput_left" style="width:100%;" disabled />
											<input type="hidden" name="companyId" id="companyId" value="<c:out value="${expertPool.companyId}"/>" />
										</c:when>
										<c:otherwise>
											<input type="text"	 name="company"	  id="company"	  value="<c:out value="${expertPool.company}"/>" class="contentInput_left" style="width:100%;" />
											<input type="hidden" name="companyId" id="companyId" value="<c:out value="${expertPool.companyId}"/>" />
											<!-- <a href="javascript:search_Company();" id="btnCompany" disabled><img src="/images/btn_search.jpg" border="0" alt="소속기관 찾기"></a> -->
										</c:otherwise>
										</c:choose>
										</td>
									</tr>
									<tr>
										<th><span class="require">*</span>소속부서</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<c:set var="mode1" value="inLine"/>
												<c:set var="mode2" value="none"/>
												<c:set var="isDeptDisabled" value="disabled"/>
											</c:when>
											<c:otherwise>
												<c:set var="mode1" value="none"/>
												<c:set var="mode2" value="inLine"/>
												<c:set var="isDeptDisabled" value=""/>
											</c:otherwise>						
										</c:choose>
											<input type="hidden" name="dept" id="dept" value="<c:out value="${expertPool.dept}"/>">
											<select name="selectDept" id="selectDept" class="select" onpropertychange="companyDept_Set(this);" style="display:<c:out value="${mode1}"/>;" <c:if test="${!empty expertPool.ssn}">disabled</c:if> >
											</select>
											<input type="text"  name="deptName" id="deptName" value="<c:out value="${expertPool.deptName}"/>" style="display:<c:out value="${mode2}"/>;" >
										</td>
										<th><span class="require">*</span>직위</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<c:set var="mode3" value="inLine"/>
												<c:set var="mode4" value="none"/>
											</c:when>
											<c:otherwise>
												<c:set var="mode3" value="none"/>
												<c:set var="mode4" value="inLine"/>
											</c:otherwise>						
										</c:choose>
											<input type="hidden" name="companyPosition" id="companyPosition" value="<c:out value="${expertPool.companyPosition}"/>">
											<select name="selectPosition" id="selectPosition" class="select" onpropertychange="companyPosition_Set(this);"  style="display:<c:out value="${mode3}"/>;" <c:if test="${!empty expertPool.ssn}">disabled</c:if> >
											</select>
										<input type="text" name="companyPositionName" id="companyPositionName" value="<c:out value="${expertPool.companyPositionName}"/>" style="display:<c:out value="${mode4}"/>;" >
										</td>
									</tr>
									<tr>
										<th>E-mail</th>
										<td><input type="text" name="email" id="email" class="contentInput_left" size="77" style="ime-mode:disabled" onblur="check_Mail(this);" value="<c:out value="${expertPool.email}"/>" disabled></td>
										<th><span class="require">*</span>휴대전화</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
											<input type="text" name="mobileNo" id="mobileNo" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.mobileNo}"/>" disabled>
											</c:when>
											<c:otherwise>
											<input type="text" name="mobileNo" id="mobileNo" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.mobileNo}"/>">
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
									<tr>
										<th>직장전화</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<input type="text" name="companyTelNo" id="companyTelNo" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.companyTelNo}"/>" disabled>
											</c:when>
											<c:otherwise>
												<input type="text" name="companyTelNo" id="companyTelNo" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.companyTelNo}"/>">
											</c:otherwise>
										</c:choose>
										</td>
										<th>FAX</th>
										<td>
										<c:choose>
											<c:when test="${isReadonly == 'readonly'}">
												<input type="text" name="companyFaxNo" id="companyFaxNo" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.companyFaxNo}"/>" disabled>
											</c:when>
											<c:otherwise>
												<input type="text" name="companyFaxNo" id="companyFaxNo" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.companyFaxNo}"/>">
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
									<%if(session.getAttribute("dept").equals("2000") || session.getAttribute("dept").equals("2040") || session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){ %>
									<tr>
										<th rowspan="2">주소</th>
										<td colspan="3">
											<div class="add">
												<input type="hidden" name="zipCode" id="zipCode" value="<c:out value="${expertPool.zipCode}"/>">
												<input type="text" name="zipCodeview" id="zipCodeview" class="add_1" value="<c:out value="${expertPool.zipCode}"/>"/>
												<a href="javascript:zipCodeWin_Open('callBackByZipWin1');"><button type="button" class="btn line btn_black"><i class="mdi mdi-mailbox-open"></i>우편번호 검색</button></a>
												<input type="text" name="address1" id="address1" class="add_2" value="<c:out value="${expertPool.address1}"/>" />
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="3">																
											<input type="text" name="address2" id="address2" value="<c:out value="${expertPool.address2}"/>" />																
										</td>
									</tr>
									<%} %>
									<input type="hidden" name="remark" id="remark" class="contentInput_left" size="77"  value="<c:out value="${expertPool.remark}"/>"><input type="hidden" name="rate" value="<c:out value="${expertPool.rate}"/>">
									<input type="hidden" name="salaryMailingYN" id="salaryMailingYN" value="<c:out value="${expertPool.salaryMailingYN}"/>">
								</tbody>
							</table>
						</div>											
					</div>
				</div>
			</div>
			<!-- // 인력 정보 -->
						
			<!-- 프로필 등록 -->
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">컨설턴트 프로필</p>
					</div>
				</div>
				<div class="board_contents">
					<ul class="hashtag_box line">
					<colgroup>
						<col style="width: 100%">
					</colgroup>				
					<tbody>
						<td>
							<a href="#" onclick="fileDownload();"><span id="resumeS"><p><c:out value="${expertPool.resumeName}" /></p></span></a>&nbsp
							<button type="button" class="btn line btn_black" id="resumeB" style="cursor:hand;"><input type="hidden" name="resume" id="resume" value="<c:out value="${expertPool.resume}" />"><i class="mdi mdi-upload"></i>프로필 등록</button>
							<%-- <img src="/images/btn_regist_profile.jpg" border="0" id="resumeB" style="cursor:hand;"><input type="hidden" name="resume" id="resume" value="<c:out value="${expertPool.resume}" />"> --%>															
					 	</td>
					</tbody>
					</ul>
				</div>
			</div>
			<!-- // 프로필 등록 -->
								
			<!-- 전문스킬·분야 -->
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">전문분야·스킬</p>
						<input type="text" id="tag" title="전문분야·스킬 해시태그" placeholder="#는 자동 입력" class="input_tag" />
					</div>
				</div>
				<div class="board_contents">
					<ul class="hashtag_box line" id="tag-list">	
						<c:forEach var="rs" items="${hashTagList}">		
					        <li class="tag-item" title="<c:out value="${rs.createDate}"/>" <c:if test="${rs.isShow ne 'SHOW'}">style="display: none;"</c:if>>#<c:out value="${rs.hashTag}"/>
								<span class="del-btn" idx="<c:out value="${rs.uuid}"/>"><font color="black">x</font></span>													
					        </li>
				        </c:forEach>						        		
					</ul>																	
				</div>
			</div>
			<!-- // 전문스킬·분야 -->
			
			<!-- 학력사항-->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">학력 사항</p>
					</div>
					<div class="text-R">
						<button type="button" class="btn line btn_blue" onclick="Schooling_Add();"><i class="mdi mdi-arrow-up-thin-circle-outline"></i>행 추가</button>
						<button type="button" class="btn line btn_pink" onclick="Schooling_Del();"><i class="mdi mdi-arrow-down-thin-circle-outline"></i>행 삭제</button>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit td-c pd-none" id="schoolingListTable"><!-- 요소들 100% 할 경우 -->
						<colgroup>
							<col style="width: 5%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: *">
						</colgroup>
						<tr>
							<th>선택</th>
							<th>기간</th>
							<th>출신교</th>
							<th>전공</th>
							<th>졸업구분</th>
							<th>학교위치</th>
							<th>비고</th>
						</tr>
						<tbody id="schoolingListTableBody">
							<c:forEach var="rs" items="${expertPool.expertPoolSchoolHst}">
							<tr>
								<td>
									<input type="checkbox" name="chkScholing" id="<c:out value="${rs.seq}"/>"  class="btn_check"/><label for="<c:out value="${rs.seq }" />"/>
									<input type="hidden"   name="schoolSeq" id="schoolSeq" value="<c:out value="${rs.seq}"/>"/>
								</td>
								<td>
									<input type="text" name="schoolTerm" id="schoolTerm" class="contentInput_center" value="<c:out value="${rs.term}"/>"/>
								</td>
								<td>
									<input type="text" name="schoolName" id="schoolName" class="contentInput_left" value="<c:out value="${rs.schoolName}"/>"/>
								</td>
								<td>
									<input type="text" name="schoolMajor" id="schoolMajor" class="contentInput_center" value="<c:out value="${rs.major}"/>"/>
								</td>
								<td>
									<input type="text" name="schoolGraduationState" id="schoolGraduationState" class="contentInput_center" value="<c:out value="${rs.graduationState}"/>"/>
								</td>
								<td>
									<input type="text" name="schoolLocation" id="schoolLocation" class="contentInput_center" value="<c:out value="${rs.location}"/>"/>
								</td>
								<td>
									<input type="text" name="schoolRemark" id="schoolRemark" class="contentInput_left" value="<c:out value="${rs.remark}"/>"/>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty expertPool.expertPoolSchoolHst}">
							<tr>
								<td align="center">
									<input type="checkbox" name="chkScholing" id="chkScholing"/>
									<input type="hidden"   name="schoolSeq" id="schoolSeq" value=""/>
								</td>
								<td>
									<input type="text" name="schoolTerm" id="schoolTerm" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="schoolName" id="schoolName" class="contentInput_left"/>
								</td>
								<td>
									<input type="text" name="schoolMajor" id="schoolMajor" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="schoolGraduationState" id="schoolGraduationState" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="schoolLocation" id="schoolLocation" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="schoolRemark" id="schoolRemark" class="contentInput_left"/>
								</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<!-- // 학력사항 -->
			
			<!-- 경력사항-->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">경력 사항</p>
					</div>
					<div class="text-R">
						<button type="button" class="btn line btn_blue" onclick="Career_Add();"><i class="mdi mdi-arrow-up-thin-circle-outline"></i>행 추가</button>
						<button type="button" class="btn line btn_pink" onclick="Career_Del();"><i class="mdi mdi-arrow-down-thin-circle-outline"></i>행 삭제</button>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit td-c pd-none" id="careerListTable"><!-- 요소들 100% 할 경우 -->
						<colgroup>
							<col style="width: 5%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: 17%">
							<col style="width: *">
						</colgroup>
						<tr>
							<th>선택</th>
							<th>기간</th>
							<th>회사명</th>
							<th>근무부서</th>
							<th>직위</th>
							<th>담당업무</th>
							<th>비고</th>
						</tr>
						<tbody id="careerListTableBody">
							<c:forEach var="rs" items="${expertPool.expertPoolCareerHst}">
							<tr>
								<td align="center">
									<input type="checkbox" name="chkCareer" id="chkCareer"/>
									<input type="hidden"   name="careerSeq" id="careerSeq" value="<c:out value="${rs.seqNo}"/>"/>
								</td>
								<td>
									<input type="text" name="careerTerm" id="careerTerm" class="contentInput_center" value="<c:out value="${rs.term}"/>"/>
								</td>
								<td>
									<input type="text" name="careercompanyName" id="careercompanyName" class="contentInput_left" value="<c:out value="${rs.companyName}"/>"/>
								</td>
								<td>
									<input type="text" name="careerWorkingDept" id="careerWorkingDept" class="contentInput_center" value="<c:out value="${rs.workingDept}"/>"/>
								</td>
								<td>
									<input type="text" name="careerPosition" id="careerPosition" class="contentInput_center" value="<c:out value="${rs.companyPosition}"/>"/>
								</td>
								<td>
									<input type="text" name="careerWorkingDetail" id="careerWorkingDetail" class="contentInput_center" value="<c:out value="${rs.workingDetail}"/>"/>
								</td>
								<td>
									<input type="text" name="careerRemark" id="careerRemark" class="contentInput_left" value="<c:out value="${rs.remark}"/>"/>
								</td>
							</tr>
							</c:forEach>
							<c:if test="${empty expertPool.expertPoolCareerHst}">
							<tr>
								<td align="center">
									<input type="checkbox" name="chkCareer" id="chkCareer"/>
									<input type="hidden"   name="careerSeq" id="careerSeq" value=""/>
								</td>
								<td>
									<input type="text" name="careerTerm" id="careerTerm" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="careercompanyName" id="careercompanyName" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="careerWorkingDept" id="careerWorkingDept" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="careerPosition" id="careerPosition" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="careerWorkingDetail" id="careerWorkingDetail" class="contentInput_center"/>
								</td>
								<td>
									<input type="text" name="careerRemark" id="careerRemark" class="contentInput_left"/>
								</td>
							</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<!-- // 학력사항 -->
			
			
			</div>
		</div>
	</div>
</div>
</form>
<div id="xxxTempDivForTemplete" style="display:none"></div>
</body>
</html>