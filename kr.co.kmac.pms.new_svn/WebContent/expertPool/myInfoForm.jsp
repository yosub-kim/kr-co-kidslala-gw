<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript" src='<c:url value="/js/expertPool_Form2.js"/>'></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

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

function Mypic_Click(){
	alert("ddd");
	document.getElementById("btnUPLOAD").click();
	kkkk._rerouteClicks();
	alert(dd/dd);
}
var imgMyPic = "/images/picguide.gif";
function photo_ProptertyChange(obj){
	var temp = obj.value;
	if(temp != ""){
		chekck(temp,obj);
	}
}

function FILEUPLOADER_INIT() {
	var button =$("MyPic");
	new AjaxUpload(button,{
		action: '/action/RepositoryAction.do?mode=fileUpload', 
		name: 'userfile',
		onSubmit : function(file, ext){
			//button.src="/images/ajax-loader.gif";
           	//button.style.width = "16";
           	//button.style.height = "16";
			this.disable();
		},
		onComplete: function(file, response){
			this.enable();
           	var res = eval('(' + response + ')');
           	
           	//fileName.value = res.file.orginalFileName;
           	frm.photoId.value = res.file.fileId;
           	button.src="/servlet/PhotoDownLoadServlet?fileId=" + res.file.fileId;
		}
	});
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
function fnCheckPassword(pw){
	var pw_passed = true;
	var pattern1 = /[0-9]/;
	var pattern2 = /[a-zA-Z]/;
	//var pattern3 = /[~!@\#$%<>^&*]/;     // 원하는 특수문자 추가
	var pw_msg = "";
	
	if(!pattern1.test(pw)||!pattern2.test(pw)||pw.length<8||pw.length>50){
		alert("비밀번호를 알파벳+숫자 8자리 이상으로 구성하여야 합니다.");
		return false;
	}
	
	var SamePass_0 = 0; //동일문자 카운트
	var SamePass_1 = 0; //연속성(+) 카운드
	var SamePass_2 = 0; //연속성(-) 카운드
	
	for(var i=0; i < pw.length; i++) {
		var chr_pass_0;
		var chr_pass_1;
		var chr_pass_2;
		
		if(i >= 2) {
			chr_pass_0 = pw.charCodeAt(i-2);
			chr_pass_1 = pw.charCodeAt(i-1);
			chr_pass_2 = pw.charCodeAt(i);
			
			// 동일 문자 카운트
			if((chr_pass_0 == chr_pass_1) && (chr_pass_1 == chr_pass_2)) {
				SamePass_0++;
			} else {
				SamePass_0 = 0;
			}
			
			// 연속성(+) 카운트
			if(chr_pass_0 - chr_pass_1 == 1 && chr_pass_1 - chr_pass_2 == 1) {
				SamePass_1++;
			} else {
				SamePass_1 = 0;	
			}
			
			// 연속성(-) 카운트
			if(chr_pass_0 - chr_pass_1 == -1 && chr_pass_1 - chr_pass_2 == -1) {
				SamePass_2++;
			} else {
				SamePass_2 = 0;
			}
		}
		
		if(SamePass_0 > 0) {
			alert("비밀번호에 동일 문자를 3자 이상 연속 입력할 수 없습니다.");
			pw_passed=false;
		}
		
		if(SamePass_1 > 0 || SamePass_2 > 0 ) {
			alert("비밀번호에 알파벳이나 숫자를 3자 이상 연속 입력할 수 없습니다.");
			pw_passed=false;
		}
		
		if(!pw_passed) {
			return false;
			break;
		}
	}

    return true;
} 


function expertPool_Save(){
	var checkSsn = '<%=session.getAttribute("userId") %>';
	var sFrm = document.forms["frm"];
	if(sFrm.chkPassword.checked) {
		if(sFrm.password.value == ""){
			alert("현 비밀번호를 입력하세요.");
			return;
		}
		if(sFrm.password2.value == ""){
			alert("새 비밀번호를 입력하세요.");
			return;
		}
		if(sFrm.password3.value == ""){
			alert("비밀번호 확인을 입력하세요.");
			return;
		}
		if(sFrm.password2.value != sFrm.password3.value){
			alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return;
		}
		if(sFrm.password.value == sFrm.password2.value){
			alert("변경할 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
			return;
		}
		if(!fnCheckPassword(sFrm.password2.value)){
			return;			
		}
	}

	AjaxRequest.submit(
			sFrm
			,{ 'url':"/action/ExpertPoolManagerAction.do?mode=saveMyInfo"
				,'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if (res.chkPassword == 'Y') {
						webmailForm.user_account.value = res.emailAddr;
	                	webmailForm.new_pass.value = res.encPwd;
	                	webmailForm.is_encrypt.value = 1;
	                	webmailForm.action="https://webmail.kmac.co.kr/a_biz/api/change_pass.nvd";
						webmailForm.target = "hiddenifr"
	                	webmailForm.submit();
					}
					alert(res.resultMsg);
					/* parent.window.close(); */
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	);
}
function passwordSection(obj) {
	var secPassword = document.getElementById("trPassword");
	var secPassword2 = document.getElementById("trWord");
	
	if(obj.checked){
		secPassword.style.display = '';
		secPassword2.style.display = 'none';
	}else{
		secPassword.style.display = 'none';
		secPassword2.style.display = '';
	}
}

function FILEUPLOADER_INIT2() {
	var button =$("MyPic2");
	new AjaxUpload(button,{
		action: '/action/RepositoryAction.do?mode=fileUpload', 
		name: 'userfile',
		onSubmit : function(file, ext){
			//button.src="/images/ajax-loader.gif";
           	//button.style.width = "16";
           	//button.style.height = "16";
			this.disable();
		},
		onComplete: function(file, response){
			this.enable();
           	var res = eval('(' + response + ')');
           	
           	//fileName.value = res.file.orginalFileName;
           	frm.photoId.value = res.file.fileId;
           	button.src="/servlet/PhotoDownLoadServlet?fileId=" + res.file.fileId;
		}
	});
}

</script>
</head>
<body onload="FILEUPLOADER_INIT();">
	<form name="frm" method="post">	
	
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">개인정보 수정</p>
				</div>
				<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="expertPool_Save()"><i class="mdi mdi-square-edit-outline"></i>저장</button>
				</div>
			</div>
			<div class="board_contents">
				<div class="info_area">
					<div class="profile_box edit">
					<c:choose>
						<c:when test="${!empty expertPool.photo }">
							<div class="profile"><img id="MyPic" style="cursor:hand;" src="/servlet/PhotoDownLoadServlet?fileId=<c:out value="${expertPool.photo }"/>" ></div>
						</c:when>
						<c:otherwise>
							<div class="profile"><img id="MyPic" src="/images/im_noimage.jpg" ></div>
						</c:otherwise>
					</c:choose>
					<div class="btn_area">
						<button type="button" class="btn line btn_blue" id="MyPic2" onclick="FILEUPLOADER_INIT2();"><i class="mdi mdi-camera"></i>사진첨부</button>
					</div>
					<input type="hidden" name="photoId">
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
									<th>성명</th>
									<td><c:out value="${expertPool.name}"/><input type="hidden" name="name" class="contentInput_left" value="<c:out value="${expertPool.name}"/>" disabled></td>
									<th>주민번호</th>
									<td>
										<input type="hidden" name="ssn" value="<c:out value="${expertPool.ssn}"/>">
										<c:set var="uuid" value="${expertPool.uid }" />
										<%
											String test=(String)pageContext.getAttribute("uuid");
										
											String uid_1 = test.substring(0,6);
											String uid_2 = test.substring(6,7);
											String uid = uid_1 + " - " + uid_2 + "******";
											pageContext.setAttribute("uid_result", uid);
										%>
										<input type="hidden" name="temp_ssn" class="contentInput_left" value="<c:out value="${uid_result}" />
										<c:if test="${empty expertPool.uid}"> onblur="Jumin_Check();"</c:if> style="ime-mode:disabled" onchange="Number_Only(this, -1, -1);" disabled>
										<c:out value="${uid_result}" />	
									</td>
								</tr>
								<tr>
									<th>성명(영문)</th>
									<td><input type="text" name="engName" class="contentInput_left" style="ime-mode:inactive" value="<c:out value="${expertPool.engName}"/>" ></td>
									<th>성명(한문)</th>
									<td><input type="text" name="chnName" class="contentInput_left" value="<c:out value="${expertPool.chnName}"/>" ></td>
								</tr>
								<tr>
									<th>국적</th>
									<td>
										<input type="text" name="nationality" class="contentInput_left" value="<code:code tableName="NATIONALITY" code="${expertPool.nationality}"/>">
									</td>
									<th>사번</th>
									<td><c:out value="${expertPool.emplNo}"/><input type="hidden"	name="emplNo" class="contentInput_left" value="<c:out value="${expertPool.emplNo}"/>"></td>
								</tr>
								<tr>
									<th>직업구분</th>
									<td>
									<code:code tableName="EMP_WORKING_TYPE" code="${expertPool.jobClass}"/><input type="hidden" name="jobClass" class="contentInput_left" value="<code:code tableName="EMP_WORKING_TYPE" code="${expertPool.jobClass}"/>" disabled/>
									</td>
									<th>소속기관</th>
									<td>
										<c:out value="${expertPool.company}"/>
										<input type="hidden"		name="company"		value="<c:out value="${expertPool.company}"/>" class="contentInput_left" readOnly disabled>
										<input type="hidden"	name="companyId"	value="<c:out value="${expertPool.companyId}"/>" disabled>
									</td>
								</tr>
								<tr>
									<th>소속부서</th>
									<td>
										<c:out value="${expertPool.deptName}"/>
										<input type="hidden" name="Dept" id="Dept" value="<c:out value="${expertPool.dept}"/>" disabled>
										<input type="hidden"  name="deptName" id="deptName" value="<c:out value="${expertPool.deptName}"/>" class="contentInput_left" disabled>
									</td>
									<th>직위</th>
									<td>
										<c:out value="${expertPool.companyPositionName}"/>
										<input type="hidden" name="companyPosition" id="companyPosition" value="<c:out value="${expertPool.companyPosition}"/>" disabled>
										<input type="hidden" name="companyPositionName" id="companyPositionName" value="<c:out value="${expertPool.companyPositionName}"/>" class="contentInput_left" disabled>
									</td>
								</tr>
								<tr>
									<th>E-mail</th>
									<td>
										<c:out value="${expertPool.email}"/>
										<input type="hidden" name="email" class="contentInput_left" value="<c:out value="${expertPool.email}"/>" disabled>
									</td>
									<th>휴대전화</th>
									<td>
										<input type="text" name="mobileNo" class="contentInput_left" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.mobileNo}"/>">
										<input type="hidden" name="telNo" class="contentInput_left" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.telNo}"/>">					
									</td>
								</tr>
								<tr>
									<th>직장전화</th>
									<td>
										<input type="text" name="companyTelNo" class="contentInput_left" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.companyTelNo}"/>">
									</td>
									<th>FAX</th>
									<td>
										<input type="text" name="companyFaxNo" class="contentInput_left" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" value="<c:out value="${expertPool.companyFaxNo}"/>">
									</td>
								</tr>
								<tr>
									<th rowspan="2">주소</th>
									<td colspan="3">
										<div class="add">
											<input type="hidden"	id="zipCode"		name="zipCode" value="<c:out value="${expertPool.zipCode}"/>">
											<input type="text"		id="zipCodeview"	name="zipCodeview" class="smallInput_center" readonly style="width: 100px" value="<c:out value="${expertPool.zipCode}"/>">
											<a href="javascript:zipCodeWin_Open('callBackByZipWin1');"><button type="button" class="btn line btn_black"><i class="mdi mdi-mailbox-open"></i>우편번호 검색</button></a>
											<input type="text"		id="address1"	name="address1" class="smallInput_left" value="<c:out value="${expertPool.address1}"/>">
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<input type="text"		id="address2"	name="address2" class="smallInput_left" value="<c:out value="${expertPool.address2}"/>">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<div style="display:none;">
			<input type="text"	name="remark" class="contentInput_left" value="<c:out value="${expertPool.remark}"/>">
			<input type="text"	name="emplNo" class="contentInput_left" value="<c:out value="${expertPool.emplNo}"/>" disabled>
			<input type="hidden"	id="companyZipcode" 	name="companyZipcode"  value="<c:out value="${expertPool.companyZipcode}"/>">
			<input type="hidden" name="gender" id="gender" value="<c:out value="${expertPool.gender }" />" >
		</div>
		
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">비밀번호 변경</p>
				</div>
				<div class="text-R">
					<ul><li><input type="checkbox" name="chkPassword" id="chkPassword" class="btn_check"  value="Y" onclick="passwordSection(this);" /><label for="chkPassword"></label></li></ul>&nbsp
					<p>비밀번호 변경 시 체크 해주세요.</p>
				</div>
			</div>
			<div class="board_contents">
				
				<div class="box" id="trWord">
					<div class="info_box" style="margin: -15 0 -15 0 ">
						<p class="info_title"><span class="pink">안전한 비밀번호로 내 정보를 보호하세요.</span></p>
						<p class="info_text">· 사용 순서대로의 3자 이상 연속 사용을 금지합니다.</p>
						<p class="info_text">· 이전에 사용한 적 없는 비밀번호가 안전합니다.</p>
					</div>
				</div>
				
				<table id="trPassword" style="display:none;" class="tbl-edit td-c" ><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 15%"/>
						<col style="width: 15%"/>
						<col style="width: 15%" />
						<col style="width: 15%" />
						<col style="width: 15%" />
						<col style="width: 15%" />
					</colgroup>
					<tr>
						<th>현재 비밀번호</th>
						<td><input type="password" name="password" style="width: 100%"></td>
						<th>새 비밀번호</th>
						<td><input type="password" name="password2" style="width: 100%"></td>
						<th>새 비밀번호 확인</th>	
						<td><input type="password" name="password3" style="width: 100%"></td>										
					</tr>
					</form>
					<form name="webmailForm" method="post">
						<input type="hidden" name="user_account" id="user_account" />
						<input type="hidden" name="is_encrypt" id="is_encrypt" />
						<input type="hidden" name="new_pass" id="new_pass" />
					</form>
				</table>
			</div>
		</div>
</body>
</html>