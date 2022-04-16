<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>

<%@page import="kr.co.kmac.pms.project.master.data.Project"%>

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
<%--<script type="text/javascript" src='<c:url value="/js/project.js"/>'></script>
include 해서 사용 할 경우 ie6에서 에러발생하므로 주석처리
--%>
<% 
	String projectRole = (String)request.getAttribute("projectRole");
	String position = (String)request.getAttribute("position");
	String jobclass = (String)request.getAttribute("jobclass");
	String isRefresh = (String)request.getAttribute("isRefresh");
	String divRole = (String)request.getAttribute("divRole");
	String reqStr = "&projectRole="+projectRole+"&position="+position+"&jobclass="+jobclass;
%>
<script type="text/javascript">
// 첨부파일 삭제
function deleteRowAttachFile(obj, fileId){	
	if(!confirm("파일을 삭제하시겠습니까?")) return;
	var deleteURL = "/action/RepositoryAction.do?mode=deleteFile"; //Upload URL
	var extraData ={"fileId":fileId}; //Extra Data.
	var jqXHR=j$.ajax({
		url: deleteURL,
		type: "GET",
		cache: false,
		data: extraData,
		success: function(data){
			var tr = j$(obj).parent().parent().parent();
			/* alert(j$(tr).attr("id")); */
			tr.remove();
			if(j$("#attachFileTableBody div").length == 0){					
				j$("#attachFileTableHeader").hide();
			}
		}
	}); 
}
// 과거 성과급, 투입률 내역 화면 (200318)
function viewProjectSalary(){
	var url = "/action/ProjectExpenseAction.do?mode=getViewProjectSalary"
			+ "&projectCode=" + $('projectCode').value;
	var sFeatures = "top=50,left=50,width=540,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "viewProjectSalaryDetail", sFeatures);
	detailWin.focus();
}
// 상태 별 성과급 화면(200102)
function viewProjectReportDetail(){
	var url = "/action/ProjectExpenseAction.do?mode=getExpertPoolWorkPeriodList_repodetail"
			+"&projectCode="+$('projectCode').value;
	var sFeatures = "top=50,left=50,width=840,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_repodetail", sFeatures);
	detailWin.focus();
}
//프로젝트 버튼 이벤트 관련 ---------------------------------------------------시작//
function syncBudgetInfo(){
	document.location.href = "/action/ProjectSearchAction.do"
		+ "?mode=getProjectInfoTab&viewMode=<%=request.getAttribute("viewMode")%>"
		+ "&projectCode="+$('projectCode').value
		+ "&isRefresh=true";
}

function storeProjectMasterInfo(isReject) {
	var businessTypeCode = $('businessTypeCode').value;
	var processTemplateCode;
	if($('processTemplateCode') != null){
		processTemplateCode = $('processTemplateCode').value;
	}else{
		processTemplateCode = $('_processTemplateCode').value;
		//processTemplateCode = $('projectDetailCode').options[$('projectDetailCode').selectedIndex].attributes.processTemplateCode.value;
		//processTemplateCode = $('projectDetailCode').options[$('projectDetailCode').selectedIndex].readAttribute("processTemplateCode");
	}
	
	 
	//validation check 시작
	var memberSsnList = $$('input[name="memberSsn"]');
	var memberRoleList = $$('select[name="memberRole"]');
	/* var memberResRateList = $$('input[name="memberResRate"]'); */
	var memberCostList = $$('input[name="memberCost"]');
	memberContributionCostList = $$('input[name="memberContributionCost"]');	

	var flag=false;
	
	if(businessTypeCode=='BTA' || businessTypeCode=='BTJ'){
		var plFlag = false;
		memberRoleList.each( function(memberRole){
			if(memberRole.value == "PL"){flag=true;}
		});	if(flag==false){alert("PL을 설정하세요.");return;}
	}flag=false;

	memberRoleList.each( function(memberRole){
		if(memberRole.value == "PM"){flag=true;}
	});	if(flag==false){alert("PM을 설정하세요.");return;}flag=false;
		
	memberRoleList.each( function(memberRole){
		if(memberRole.value == "AR"){flag=true;}
	});	if(flag==false){alert("COO를 설정하세요.");return;}flag=false;
	
	
	/* 20200401 */
	<%-- <% if(!request.getAttribute("viewMode").equals("myProject")) {%>
		memberRoleList.each( function(memberRole){
			if(memberRole.value == "QM"){flag=true;}
		});	if(flag==false){alert("QM을 설정하세요.");return;}flag=false;
	<%}%> --%>
	
	
	if(businessTypeCode=='BTA' && $('businessFunctionType').value == ""){alert("프로젝트 분야를 선택하세요. "); return;};
	if($('projectDetailCode') == null || $('projectDetailCode').value == ""){alert("프로세스 유형을 선택하세요. "); return;};
	
	memberSsnList.each( function(memberSsn){
		if(memberSsn.value == ""){alert("프로젝트 인력을 등록하지 않았습니다. ");flag=true;}
	});	if(flag==true){return;}
	memberRoleList.each( function(memberRole){
		if(memberRole.value == "" || memberRole.value == "-선택-"){alert("프로젝트 인력정보의 역할정보가 없습니다. ");flag=true;}
	});	if(flag==true){return;}
	/* memberResRateList.each( function(memberResRate){
		if(memberResRate.value == ""){alert("프로젝트 인력정보의 요율정보가 없습니다. ");flag=true;}
	});	if(flag==true){return;} */
	memberCostList.each( function(memberCost){
		if(memberCost.value == ""){alert("프로젝트 인력정보의 기준금액정보가 없습니다. ");flag=true;}
	});	if(flag==true){return;}
	/* memberContributionCostList.each( function(memberContributionCost){
		if(memberContributionCost.value == ""){alert("프로젝트 인력정보의 개인별수주단가/기여매출단가정보가 없습니다. ");flag=true;}
	});	if(flag==true){return;} */

	if(!isAvailablePL()){return;}		//PL 가능 여부 체크
	if(!memberRoleDualCheck()){return;}	//member role 중복 체크
	
	/* if(!isExpertDateCheck()){return;} */		// 전문가 투일 일정 여부 체크
	
	//validation check 종료
	
	if(businessTypeCode=='BTE' || businessTypeCode=='BTF'){
		if(!document.projectMasterInfoForm.isEduConnected[0].checked && !document.projectMasterInfoForm.isEduConnected[1].checked){
			alert("기획사업 연계 여부를 선택하세요.");return;
		}
		if($F('projectState') != '3'){	// JobDate: 2010-09-29	Author: yhyim	Description: 프로젝트 진행 중일 때 단가 조정하기 위해 임시로 조치한 사항
			if((processTemplateCode=='N1' || processTemplateCode=='N2' || processTemplateCode=='EE' || processTemplateCode=='DD') || $F('isEduConnected') == 'Y'){
				var isConnected = "true";
				var status = AjaxRequest.get(
					{	'url':"/action/RegisterProjectAction.do?mode=isEduProjectConnected&projectCode="+$('projectCode').value,
						'async' : false,
						'anotherParameter':'false',
						'onSuccess':function(obj){  // 
				           	var res = eval('(' + obj.responseText + ')');
				           	if(res.isConnected !="true"){
				           		isConnected =  "false";			           	
				           	}
						}, 
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("데이터를 가져오지 못했습니다.");
						}
					});
				if($F('projectState') != '3'){
					if(isConnected == "false"){
						if($F('isEduConnected') == 'Y') {
							alert("기획사업 연계를 '예'로 선택한 경우 기획사업 연계를 해야 합니다.\n'기획사업 연계' 버튼을 눌러서 해당 기획사업과 연결하세요.");
						} else {
							alert("공개교육/기획성연수/세미나/컨퍼런스는 기획사업 연계를 해야 합니다.\n'기획사업 연계' 버튼을 눌러서 해당 기획사업과 연결하세요.");
						} 
						return;
					}
				}
			}
		}
		
	}

	if(businessTypeCode=='BTA' || businessTypeCode=='BTJ' || (businessTypeCode=='BTD' && ($('isEvaluate').value == 'Y')) || (businessTypeCode=='BTE' && (processTemplateCode=='N1'|| processTemplateCode=='N2' || processTemplateCode=='N4' || processTemplateCode=='SS')) || processTemplateCode=='DE'){
		if($('customerWorkPEmail').value == ''){
			alert('컨설팅, 진단, 리서치, 공개(장기/단기)교육, 사내교육, 특강, 수주성 연수는 고객사 담당자 정보(e-Mail)를 입력해야 합니다.');
			return;
		}
	}  

	/* if($('lang') != null && ($('lang').value =='CHN' || $('lang').value =='ENG' || $('lang').value =='JPN') && $('langTd2').value ==''){
		alert('사용언어가 외국어일 경우 외국어 프로젝트명을 입력하세요.');
		return;
	} */ 
	var ActionURL = "/action/ProjectMasterInfoAction.do?mode=createProjectMasterInfo&isRefresh=<%=(String)request.getAttribute("isRefresh")%>";
	var sFrm = document.forms["projectMasterInfoForm"];
	var sanctionType = "기획";
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){ 
					if($F('projectState') == '3'){//예산 초과정보 업데이트
						AjaxRequest.post (
								{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
									'parameters' : {projectCode: $("projectCode").value },
									'onSuccess':function(obj){},
									'onLoading':function(obj){},
									'onError':function(obj){
										alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
									}
								}
						); 						
					}				
					if($F('projectState') != '3' && $F('projectState') != '4'){
						if(businessTypeCode=='BTA' || (businessTypeCode=='BTD' && ($('isEvaluate').value == 'Y')) || (businessTypeCode=='BTE' && (processTemplateCode=='N4')) || processTemplateCode=='DE'){
							sanctionType="실행";
						}
						if(isReject == 'reject'){
							alert("저장하였습니다.");
						}else{
							alert('프로젝트 기본정보를 저장하였습니다.\n\n일정관리로 이동하여 일정을 입력하시고 기본정보로 돌아온 뒤 ' + sanctionType + '품의를 진행하십시오.\n\n'
								+ '※ 이후 예산서 내 인력이 변경된 경우 프로젝트 인력 정보를 직접 수정하셔야 합니다.');
							document.location.href = "/action/ProjectSearchAction.do?mode=getProjectInfoTab&viewMode=register&projectCode="+$("projectCode").value;
						}
					}else{
						alert("저장하였습니다.");
						//return;			
					}
					if($F('projectState') == '1' && isReject == 'reject'){
						document.location.href = "/action/ProjectSearchAction.do"
							+"?mode=getProjectInfoTab&viewMode=register&projectCode="+$("projectCode").value;
					}
			},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function doProjectLaunchSanction(approvalType) {
	
	var memberRoleList = $$('select[name="memberRole"]');
	
	/* memberSsnList.each( function(memberSsn){
		if(memberSsn.value == ""){alert("프로젝트 인력을 등록하지 않았습니다. ");flag=true;}
	});	if(flag==true){return;} */
	
	memberRoleList.each( function(memberRole){
		if(memberRole.value == "PM"){flag=true;}
	});	if(flag==false){alert("PM을 설정하세요.");return;}flag=false;
	
	if($('businessTypeCode').value == 'BTA'){
		AjaxRequest.post (
				{	'url': "/action/ProjectVocAction.do?mode=hasProjectVoc",
					'parameters' : { "projectCode": $("projectCode").value},
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.hasProjectVoc == true){
							document.projectMasterInfoForm.target = "";
							document.projectMasterInfoForm.action = "/action/ProjectLaunchSanctionAction.do?mode=loadFormProjectLaunchSanction&approvalType="+approvalType+"&viewMode=lSanction";
							document.projectMasterInfoForm.submit();
						}else{
							alert("컨설팅은 VOC 진행 일정을 등록하셔야 합니다.\nVOC 실시를 '예'로 선택하신 후 발송 일정을 등록하세요.\n\n※발송 기준\n - 6개월 미만 프로젝트 매월 1회 \n - 6개월 이상 프로젝트 격월 1회");
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); 		
	}else{
		// 교육연계 여부 체크 시작
		//var processTemplateCode = $('projectDetailCode').options[$('projectDetailCode').selectedIndex].processTemplateCode; 웹 호환성 관련 주석
		var processTemplateCode = $('_processTemplateCode').value;
		if((processTemplateCode=='N1' || processTemplateCode=='N2' || processTemplateCode=='EE' || processTemplateCode=='DD') || $('isEduConnected')=='Y'){
			var isConnected = "true";
			var status = AjaxRequest.get(
				{	'url':"/action/RegisterProjectAction.do?mode=isEduProjectConnected&projectCode="+$('projectCode').value,
					'async' : false,
					'anotherParameter':'false',
					'onSuccess':function(obj){  // 
			           	var res = eval('(' + obj.responseText + ')');
			           	if(res.isConnected !="true"){
			           		isConnected =  "false";			           	
			           	}
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("데이터를 가져오지 못했습니다.");
					}
				});
			if($F('projectState') != '3'){
				if(isConnected == "false"){
					if($F('isEduConnected') == 'Y') {
						alert("기획사업 연계 여부를 '예'로 선택 시 기획사업 연계를 해야 합니다.\n'기획사업 연계' 버튼을 눌러서 해당 기획사업과 연결하세요.");
					} else {
						alert("공개교육/기획성연수/세미나/컨퍼런스는 기획사업 연계를 해야 합니다.\n'기획사업 연계' 버튼을 눌러서 해당 기획사업과 연결하세요.");
					}
					return;
				}
			}
		}		
		// 교육연계 여부 체크 완료
		document.projectMasterInfoForm.target = "";
		document.projectMasterInfoForm.action = "/action/ProjectLaunchSanctionAction.do?mode=loadFormProjectLaunchSanction&approvalType="+approvalType+"&viewMode=lSanction";
		document.projectMasterInfoForm.submit();	
	}
}

function completeProjectProcess() {
	var ActionURL = "/action/ProjectProgressManagementAction.do?mode=canDoProjectEval";
	var sFrm = document.forms["projectProgressForm"];
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.canFinish == true){
						if(confirm("평가/리뷰를 진행하시겠습니까?\n변경품의,멤버일정 등록,강사료 입력을 할 수 없게 됩니다.")) {
							AjaxRequest.post (
									{	'url': "/action/ProjectEndProcessAction.do?mode=assignEndProcessTask&projectCode="+$('projectCode').value,
										'parameters' : { "projectCode": $("projectCode").value},
										'onSuccess':function(obj){
											var res = eval('(' + obj.responseText + ')');
											if(res.result == 'SUCCESS'){
												document.projectProgressForm.target = "";
												document.projectProgressForm.projectCode.value = "";
												document.projectProgressForm.action = "/action/MyProjectAction.do?mode=getMyProjectList";
												document.projectProgressForm.submit();	
											}
										},
										'onLoading':function(obj){},
										'onError':function(obj){
											alert("저장할 수 없습니다.2");
										}
									}
							); 
						}
					}else{
						alert('모든 일정을 완료해야 프로젝트 평가/리뷰를 하실 수 있습니다.\n실행하지 않은 액티비티 우측의 연필을 누른 뒤 수행내용을 입력하십시오.');			
					}
			},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.3");
				}
			}
	); 

}

function deleteProjectMasterInfo(){
	if(confirm("삭제 하시겠습니까?")) {
		var ActionURL = "/action/ProjectMasterInfoAction.do?mode=deleteProjectMasterInfo&projectCode="+$('projectCode').value;
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $("projectCode").value,
									 "projectCodeBp": $("projectCodeBp").value,
									 "func": $("func").value
									 },
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						alert("삭제하였습니다.");
						if(res.result == 'SUCCESS'){
							document.projectMasterInfoForm.target = "";
							document.projectMasterInfoForm.action = "/action/RegisterProjectAction.do?mode=getRegisterProjectList";
							document.projectMasterInfoForm.submit();	
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert($('projectCodeBp').value);
						alert("삭제할 수 없습니다.");
					}
				}
		); status = null;		
	}
} 

function businessFunctionTypeChange(businessFunctionType, runningDivCode, businessTypeCode){
	var ActionURL = "/action/ProcessCategoryAction.do?mode=searchProcessCategoryList"
			+"&businessFunctionType="+businessFunctionType
			+"&runningDivCode="+runningDivCode
			+"&businessTypeCode="+businessTypeCode;
	AjaxRequest.post (
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.result == 'SUCCESS'){
						$('projectDetailCode').innerHTML = "";
						$('projectDetailCode').add(new Option("-- 프로세스 유형을 선택하세요--", ""));
						for(var n=0; res.processCategoryList.length>n; n++){
							$('projectDetailCode').add(new Option(res.processCategoryList[n].processCategoryName, res.processCategoryList[n].processCategoryCode));
						}
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("삭제할 수 없습니다.");
				}
			}
	);			
}
//프로젝트 버튼 이벤트 관련 ---------------------------------------------------끝//


//프로젝트 멤버 추가삭제 ---------------------------------------------------시작// 
function addRowProjectMember() {
	var date_tmp = new Date()
	var tmp = (date_tmp.getMonth())+ (date_tmp.getMonth())+ (date_tmp.getDate())+ (date_tmp.getHours())+ (date_tmp.getMinutes())+ (date_tmp.getSeconds())+ (date_tmp.getMilliseconds()); 
	var tableObj = $('projectMemberTable');
	//alert($('businessTypeCode').value);
	if($('projectMemberTableBody').childElements().size() > 0){
		if($('projectMemberTableBody').down('tr').childElements().size() == 1){
	   		$('projectMemberTableBody').down('tr').remove();   		
	   	}
	}
	var tdCount = tableObj.down('tr').down('th').nextSiblings().length + 1 ;
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){ 
		newCellArr[i] = newRow.insertCell(-1);
	}

	new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[4], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[5], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	
	var text = "<select name='memberRole' id='memberRole' class='select' style='width:100%'><option>-선택-</option><option value='AR'>COO</option><option value='QM'>QM</option><option value='PM'>PM</option>";	
	if($('businessTypeCode').value == 'BTA' || $('businessTypeCode').value == 'BTJ'){
		text = text + "<option value='PL'>PL</option><option value='MB'>멤버</option></select>";
	} else {
		text = text + "<option value='MB'>멤버</option></select>";
	}
	
	newCellArr[0].innerHTML = text+"<input type='hidden' name='memberTrainingYn' id='memberTrainingYn' value='Y'><input type='checkbox' name='projectMemberCheck' />";;
	newCellArr[1].innerHTML = "<input type='hidden' name='memberSsn' id='memberSsn'>"
							 +"<input type='text' name='memberName' id='memberName' onKeypress='javascript:if(event.keyCode==13) {openExpertPoolPopUpForProjectMaster(this);}'/>";
	newCellArr[2].innerHTML = "<input type='hidden' name='memberPosition' id='memberPosition'>"
							 +"<input type='text' name='memberPositionName' readonly='readonly'>"
							 +"<input type='hidden' name='memberJobClass' id='memberJobClass'>"
							 +"<input type='hidden' name='memberResRate' id='memberResRate' value='1'>";
	newCellArr[3].innerHTML = "<input type='text' name='memberCost' id='memberCost' maxAmt='' minAmt='' maxEdu='' minEdu='' maxMMAmt='' minMMAmt='' businessTypeCode='<c:out value="${project.businessTypeCode }"/>' projectTypeCode='<c:out value="${project.projectTypeCode }"/>' style='ime-mode:disabled;'  value='0'>";
	newCellArr[4].innerHTML = "-";
	//예전 스타일
	//newCellArr[4].innerHTML = "<button type='button' class='btn line btn_black' onclick='location.href='javascript:openExpertPoolPopUpForProjectMaster(this);''><i class='mdi mdi-account-search'></i>조회</td>";
	newCellArr[5].innerHTML = "<div class='btn_area'><button type='button' class='btn line btn_pink' onclick='deleteRowProjectMember(this);'><i class='mdi mdi-trash-can-outline'></i>행 삭제</button></div>";
	
	
	newCellArr[0].className = "BGC";
	newCellArr[1].className = "BGC";
	newCellArr[2].className = "BGC"; 
	newCellArr[3].className = "BGL";
	newCellArr[4].className = "BGC";
	newCellArr[5].className = "BGC";	
}

function deleteRowProjectMember(obj) {
	$(obj).up().up().up().remove();
}

var selectedElmt;
function openExpertPoolPopUpForProjectMaster(projectMemberElmt) {
	if(projectMemberElmt != 'all'){
		selectedElmt = $(projectMemberElmt);
	}
	if(projectMemberElmt.value == ""){alert("이름을 입력 후 엔터를 누르세요"); return false;}
	//orgFinder_Open('setProjectMember');	
	orgFinder_Open2('setProjectMember', projectMemberElmt.value);	
}

function openExpertPoolPopUpForProjectMember(projectMemberElmt) {
	if(projectMemberElmt != 'all'){
		selectedElmt = $(projectMemberElmt);
	}
	orgFinder_OpenForProjectMember($('projectCode').value, 'setProjectMember');	
}

function setProjectMember(expertPoolList) {
	var memberTrainingYnList = $$('input[name="memberTrainingYn"]');
	var memberSsnList = $$('input[name="memberSsn"]');
	var memberNameList = $$('input[name="memberName"]');
	var memberPositionList = $$('input[name="memberPosition"]');
	var memberPositionNameList = $$('input[name="memberPositionName"]'); 
	var memberCostList = $$('input[name="memberCost"]');
	var memberRole = $$('select[name="memberRole"]');
	var memberJobClassList = $$('input[name="memberJobClass"]');
	var memberStartDateList = $$('input[name="memberStartDate"]');
	var memberEndDateList = $$('input[name="memberEndDate"]');
	if(selectedElmt == null){
		var n=0;
		for (var i = 0; i < memberSsnList.length; ++i) {
			if(memberSsnList[i].value == ""){
				if(n < expertPoolList.length){
					if(expertPoolList[0].jobClass == 'C' 
						&& (expertPoolList[0].companyPosition != "41EF" 
							&& expertPoolList[0].companyPosition != "42EG" 
							&& expertPoolList[0].companyPosition != "43EH"
							&& expertPoolList[0].companyPosition != "44EI")){
						alert("등급 지정이 안된 엑스퍼트는 선택할 수 없습니다. ");
					}else if(expertPoolList[0].jobClass == 'H' 
						&& (expertPoolList[0].companyPosition != "61DT" 
							&& expertPoolList[0].companyPosition != "62ET" 
							&& expertPoolList[0].companyPosition != "63FT" 
							&& expertPoolList[0].companyPosition != "64GT")){
						alert("(구)RA는 선택할 수 없습니다. ");
					}else if(expertPoolList[0].jobClass == 'C' 
						&& memberRole[selectedElmt.up().up().rowIndex -1].options[memberRole[selectedElmt.up().up().rowIndex -1].selectedIndex].value == 'PL' ){
						alert("PL에 엑스퍼트를 지정할 수 없습니다.\n상근 또는 상임을 지정하십시오.");
					}else{
						memberTrainingYnList[i].value = 'Y';
						memberSsnList[i].value = expertPoolList[n].ssn;
						memberNameList[i].value = expertPoolList[n].name;
						memberPositionList[i].value = expertPoolList[n].companyPosition;
						memberPositionNameList[i].value = expertPoolList[n].companyPositionName; 
						memberCostList[i].maxAmt = expertPoolList[n].maxAmt;
						memberCostList[i].minAmt = expertPoolList[n].minAmt;
						memberCostList[i].maxEdu = expertPoolList[n].maxEdu;
						memberCostList[i].minEdu = expertPoolList[n].minEdu;
						memberCostList[i].maxMMAmt = expertPoolList[n].maxMMAmt;
						memberCostList[i].minMMAmt = expertPoolList[n].minMMAmt;
						memberCostList[i].jobClass = expertPoolList[n].jobClass;
						memberJobClassList[i].value = expertPoolList[n].jobClass;
						
						//if(expertPoolList[0].jobClass == "C" || expertPoolList[0].jobClass == "D" || expertPoolList[0].jobClass == "E") {
						//	jQuery(function(){jQuery( "#"+memberStartDateList[i].id).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd', 'disabled': false});});
						//	jQuery(function(){jQuery( "#"+memberEndDateList[i].id).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd', 'disabled': false});});
						//} else {
						//	jQuery(function(){jQuery( "#"+memberStartDateList[i].id).datepicker({showOn: 'focus', 'disabled': true});});
						//	jQuery(function(){jQuery( "#"+memberEndDateList[i].id).datepicker({showOn: 'focus', 'disabled': true});});							
						//}
					}
					n++;
				}
			}
		}
	}else{
		if(expertPoolList[0].jobClass == 'C' 
			&& (expertPoolList[0].companyPosition != "41EF" 
				&& expertPoolList[0].companyPosition != "42EG" 
				&& expertPoolList[0].companyPosition != "43EH"
				&& expertPoolList[0].companyPosition != "44EI")){
			alert("등급 지정이 안된 엑스퍼트는 선택할 수 없습니다. ");
		}else if(expertPoolList[0].jobClass == 'C' 
			&& memberRole[selectedElmt.up().up().rowIndex -1].options[memberRole[selectedElmt.up().up().rowIndex -1].selectedIndex].value == 'PL' ){
			alert("PL에 엑스퍼트를 지정할 수 없습니다.\n상근 또는 상임을 지정하십시오.");
		}else if(expertPoolList[0].jobClass == 'H' 
			&& (expertPoolList[0].companyPosition != "61DT" 
				&& expertPoolList[0].companyPosition != "62ET" 
				&& expertPoolList[0].companyPosition != "63FT" 
				&& expertPoolList[0].companyPosition != "64GT")){
			alert("(구)RA는 선택할 수 없습니다. ");
		}else{
			var i = selectedElmt.up().up().rowIndex -1;		
			memberSsnList[i].value = expertPoolList[0].ssn;
			memberNameList[i].value = expertPoolList[0].name;
			memberPositionList[i].value = expertPoolList[0].companyPosition;
			memberPositionNameList[i].value = expertPoolList[0].companyPositionName; 
			memberCostList[i].maxAmt = expertPoolList[0].maxAmt;
			memberCostList[i].minAmt = expertPoolList[0].minAmt;
			memberCostList[i].maxEdu = expertPoolList[0].maxEdu;
			memberCostList[i].minEdu = expertPoolList[0].minEdu;
			memberCostList[i].maxMMAmt = expertPoolList[0].maxMMAmt;
			memberCostList[i].minMMAmt = expertPoolList[0].minMMAmt;
			memberCostList[i].jobClass = expertPoolList[0].jobClass;
			memberJobClassList[i].value = expertPoolList[0].jobClass;
			
			//if(expertPoolList[0].jobClass == "C" || expertPoolList[0].jobClass == "D" || expertPoolList[0].jobClass == "E") {
			//	jQuery(function(){jQuery( "#"+memberStartDateList[i].id).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd', 'disabled': false});});
			//	jQuery(function(){jQuery( "#"+memberEndDateList[i].id).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd', 'disabled': false});});
			//} else {
			//	jQuery(function(){jQuery( "#"+memberStartDateList[i].id).datepicker({showOn: 'focus', 'disabled': true});});
			//	jQuery(function(){jQuery( "#"+memberEndDateList[i].id).datepicker({showOn: 'focus', 'disabled': true});});							
			//}
		}
	}
}
//프로젝트 멤버 추가삭제 ---------------------------------------------------끝//

//프로젝트 지도일정 ---------------------------------------------------시작//
function createProjectReport(){
	var ActionURL = "/action/ProjectReportContentAction.do?mode=createProjectReportContent";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "projectCode": $("projectCode").value },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					alert('지도일지를 생성했습니다.');
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.5");
				}
			}
	); status = null;	
}
function searchProjectReportInfo() {
	var ActionURL = "/action/ProjectReportInfoAction.do?mode=searchProjectReportInfoList";
	var sFrm = document.forms["projectReportInfoForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectReportPlanList;
					var project = res.project;		 
					//var table = $('projectReportInfoTable');
					var tbody = $('projectReportInfoTableBody');
					//var tbodyElement = $('projectReportInfoTableBody').childElements();
		           	var tdCount = $('projectReportInfoTableBody').down('tr').childElements().size();

		           	tbody.childElements().each( function(preportInfo){
		           		preportInfo.remove();
		    		});
		    		
 		    		rsObj.each(function(preportInfo){
			    	    var newRow=tbody.insertRow(-1);
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}		
						
		    			newCellArr[0].innerHTML = "<span id='day'>"+preportInfo.day+"</span>";
		    			newCellArr[1].innerHTML = "<span id='week'>"+preportInfo.week+"</span>";

		    			// 지도일정등록 버튼 이미지 생성(프로젝트가 평가, 리뷰가 아닐 때 My Project 일 때만 표시)
		    			var tempElement = "<span id='workName'> "+preportInfo.workName+"</span>";
		    			if (project.projectState < '4' || project.adminOpen == 'Y') {
			    			if('<%=request.getAttribute("viewMode")%>' != 'projectSearch' &&  !res.readonly){
			    				tempElement = "<img src='/images/btn_schedule_regist_mini.jpg' alt='지도일정등록' style='cursor:hand;' align='absmiddle' border='0' "
									+ " onclick='openProjecReportScheduleRegistPopup(\""+ preportInfo.year +"-"+ preportInfo.month +"-"+ preportInfo.day +"\")' > "
									+ "<span id='workName'> "+preportInfo.workName+"</span>";
			    			}
			    		}
		    			newCellArr[2].innerHTML = tempElement;
		    			newCellArr[2].style.textAlign = "left";
		    			var tempHtml = "";
		    			for(var i=0; preportInfo.ssnArray.length >i; i++){
			    			if(preportInfo.nameArray[i] != ''){
				    			var bte="";
				    			if('<%=request.getAttribute("viewMode")%>' != 'projectSearch' &&  !res.readonly){
									if(project.businessTypeCode == 'BTE' || project.businessTypeCode == 'BTI' || project.businessTypeCode == 'BTG' || project.businessTypeCode == 'BTK'){
										 bte = "onclick=changeEduWorkTime('"+ preportInfo.year +"','"+ preportInfo.month +"','"+ preportInfo.day +"','"+
										 	preportInfo.ssnArray[i] +"') ";   
									}
				    			}
								var delStr='';
								if('<%=request.getAttribute("viewMode")%>' == 'projectSearch' ||  res.readonly == true){
									delStr=', ';
								}else{
									delStr=" <img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectReportSchedule('"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"')\">" 
								}
			    				tempHtml += "<span id='"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"' year='"+preportInfo.year+"' month='"+preportInfo.month+"' day='"+preportInfo.day+"' ssn='"+preportInfo.ssnArray[i]+"'>"
			    								+ "<a href='#'"+  bte + ">"+ preportInfo.nameArray[i] +"</a>"
												+ delStr + "&nbsp;</span>";
			    			}
		    			}
		    			newCellArr[3].innerHTML = tempHtml;
		    			newCellArr[3].style.textAlign = "left";
		    			newCellArr[4].innerHTML = "<span id='outputName'>"+preportInfo.outputName+"</span>";
		    			newCellArr[4].style.textAlign = "left";

		    			if(preportInfo.week == 'SAT' ){
		    				newCellArr[0].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[1].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[2].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[3].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[4].style.backgroundColor = "#D9ECFF";
		    			} else if(preportInfo.week == 'SUN'){
		    				newCellArr[0].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[1].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[2].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[3].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[4].style.backgroundColor = "#FFDFFF";
		    			} else {
		    				newCellArr[0].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[1].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[2].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[3].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[4].style.backgroundColor = "#FFFFFF";
		    			}
		    		});	
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;			
}
var tchangeYear;
var tchangeMonth;
var tchangeDay;
var tchangeSsn;
function changeEduWorkTimeSave(){
	var ActionURL = "/action/ProjectReportInfoAction.do?mode=updateTeachingTime";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "projectCode": $("projectCode").value, 
								 "year": tchangeYear, 
								 "month": tchangeMonth, 
								 "day": tchangeDay, 
								 "ssn":tchangeSsn, 
								 "teachingTime":$('teachingTime').value 
								},
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					searchProjectReportInfo();
					$('teachingTime').value = '';
					$('tchange').hide();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.7");
				}
			}
	);
}
function changeEduWorkTime(year,month,day,ssn) {
	//alert(year + "-" + month + "-" + day + "-" + ssn);
	tchangeYear = year;
	tchangeMonth = month;
	tchangeDay = day;
	tchangeSsn = ssn;

	//alert(event.x);
	$('tchange').style.left = event.clientX;
	$('tchange').style.top = event.y + 5;
	
	$('tchange').show();
}
function openProjecReportScheduleRegistPopup(selectedDay) { 
	var preportPop = 
	window.open("/action/ProjectReportInfoAction.do"
			+ "?mode=loadProjectSchedulePopup"
			+ "&projectCode="+$('projectCode').value
			+ "&selectedDay=" + selectedDay,
			"acct", "top=0,left=0,width=490,height=350,scrollbars=no");
}
function deleteProjectReportSchedule(delKey){
	var scheduleElement = $(delKey);
	if(confirm("삭제하시겠습니까?")) {
		var ActionURL = "/action/ProjectReportInfoAction.do?mode=deleteProjectReportInfo";
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $("projectCode").value, "year": scheduleElement.readAttribute("year"), "month": scheduleElement.readAttribute("month"), "day": scheduleElement.readAttribute("day"), "ssn":scheduleElement.readAttribute("ssn") },
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						alert(res.message);
						if(res.canDelete == true){//담당자가 없을경우 업무명 산출물 삭제
							var siblingsCnt = scheduleElement.siblings().length;
							var targetTd = scheduleElement.up('td');
							scheduleElement.remove();	
							if(siblingsCnt==0){
								targetTd.previous(0).down(1).update();
								targetTd.next(0).update();
							}
							AjaxRequest.post (
									{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
										'parameters' : {projectCode: $("projectCode").value },
										'onSuccess':function(obj){},
										'onLoading':function(obj){},
										'onError':function(obj){
											alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
										}
									}
							);
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.8");
					}
				}
		); status = null;
	}
}

//프로젝트 지도일정 ---------------------------------------------------끝//

//프로젝트 투입일정 ---------------------------------------------------시작//
function searchProjectManpower() {
	var ActionURL = "/action/ProjectManpowerAction.do?mode=searchProjectManpowerList";
	var sFrm = document.forms["projectManpowerForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectManpowerList;
					var project = res.project;
					var tbody = $('projectManpowerInfoTableBody');
		           	var tdCount = $('projectManpowerInfoTableBody').down('tr').childElements().size();

		           	tbody.childElements().each( function(manpowerInfo){
		           		manpowerInfo.remove();
		    		});
		    		
 		    		rsObj.each(function(manpowerInfo){
			    	    var newRow=tbody.insertRow(-1);
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}		
						
		    			newCellArr[0].innerHTML = "<span id='day'>"+manpowerInfo.day+"</span>";
		    			newCellArr[1].innerHTML = "<span id='week'>"+manpowerInfo.week+"</span>";
		    			
		    			// 투입일정등록 버튼 이미지 생성(프로젝트가 평가, 리뷰가 아닐 때 My Project 일 때만 표시)
		    			var tempElement = "<span id='workName'> "+manpowerInfo.workName+"</span>";
		    			if (project.projectState < '4' || project.adminOpen == 'Y') {
			    			if('<%=request.getAttribute("viewMode")%>' != 'projectSearch' &&  !res.readonly){
			    				tempElement = "<img src='/images/btn_schedule_regist_mini.jpg' alt='투입일정등록' style='cursor:hand;' align='absmiddle' border='0' "
									+ " onclick='openProjecManpowerScheduleRegistPopup(\""+ manpowerInfo.year +"-"+ manpowerInfo.month +"-"+ manpowerInfo.day +"\")' > "
									+ "<span id='workName'> "+manpowerInfo.workName+"</span>";
			    			}
			    		}
		    			newCellArr[2].innerHTML = tempElement;
		    			newCellArr[2].style.textAlign = "left";
		    			var tempHtml = "";
		    			for(var i=0; manpowerInfo.ssnArray.length >i; i++){
			    			if(manpowerInfo.nameArray[i] != ''){
				    			var bte="";
								var delStr='';
								if('<%=request.getAttribute("viewMode")%>' == 'projectSearch' ||  res.readonly == true){
									delStr=', ';
								}else{
									delStr=" <img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectManpowerSchedule('"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"')\">" 
								}
			    				tempHtml += "<span id='"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"' year='"+manpowerInfo.year+"' month='"+manpowerInfo.month+"' day='"+manpowerInfo.day+"' ssn='"+manpowerInfo.ssnArray[i]+"'>"
			    								+ "<a href='#'"+  bte + ">"+ manpowerInfo.nameArray[i] +"</a>"
												+ delStr + "&nbsp;</span>";
			    			}
		    			}
		    			newCellArr[3].innerHTML = tempHtml;
		    			newCellArr[3].style.textAlign = "left";
		    			
		    			if(manpowerInfo.week == 'SAT' ){
		    				newCellArr[0].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[1].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[2].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[3].style.backgroundColor = "#D9ECFF"; 
		    			} else if(manpowerInfo.week == 'SUN'){
		    				newCellArr[0].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[1].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[2].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[3].style.backgroundColor = "#FFDFFF"; 
		    			} else {
		    				newCellArr[0].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[1].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[2].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[3].style.backgroundColor = "#FFFFFF"; 
		    			}
		    		});	
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("화면 새로고침을 실패하였습니다.");
				}
			}
	); status = null;			
}
function openProjecManpowerScheduleRegistPopup(selectedDay) { 
	var preportPop = 
	window.open("/action/ProjectManpowerAction.do"
			+ "?mode=loadProjectManpowerSchedulePopup"
			+ "&projectCode="+$('projectCode').value
			+ "&selectedDay=" + selectedDay,
			"acct", "top=0,left=0,width=490,height=350,scrollbars=no");
}
function deleteProjectManpowerSchedule(delKey){
	var scheduleElement = $(delKey);
	if(confirm("삭제하시겠습니까?")) {
		var ActionURL = "/action/ProjectManpowerAction.do?mode=deleteProjectManpower";
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $("projectCode").value, "year": scheduleElement.readAttribute("year"), "month": scheduleElement.readAttribute("month"), "day": scheduleElement.readAttribute("day"), "ssn":scheduleElement.readAttribute("ssn") },
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						// 삭제 이후 리스트 화면 생성
						var rsObj = res.projectManpowerList;
						var project = res.project;
						var tbody = $('projectManpowerInfoTableBody');
			           	var tdCount = $('projectManpowerInfoTableBody').down('tr').childElements().size();

			           	tbody.childElements().each( function(manpowerInfo){
			           		manpowerInfo.remove();
			    		});
			    		
	 		    		rsObj.each(function(manpowerInfo){
				    	    var newRow=tbody.insertRow(-1);
				    		var newCellArr = new Array();
				    		for ( var i=0;i<tdCount;i++ ){
				    			newCellArr[i] = newRow.insertCell(-1);
				    		}		
							
			    			newCellArr[0].innerHTML = "<span id='day'>"+manpowerInfo.day+"</span>";
			    			newCellArr[1].innerHTML = "<span id='week'>"+manpowerInfo.week+"</span>";
			    			
			    			// 투입일정등록 버튼 이미지 생성(프로젝트가 평가, 리뷰가 아닐 때 My Project 일 때만 표시)
			    			var tempElement = "<span id='workName'> "+manpowerInfo.workName+"</span>";
			    			if (project.projectState < '4' || project.adminOpen == 'Y') {
				    			if('<%=request.getAttribute("viewMode")%>' != 'projectSearch' &&  !res.readonly){
				    				tempElement = "<img src='/images/btn_schedule_regist_mini.jpg' alt='투입일정등록' style='cursor:hand;' align='absmiddle' border='0' "
										+ " onclick='openProjecManpowerScheduleRegistPopup(\""+ manpowerInfo.year +"-"+ manpowerInfo.month +"-"+ manpowerInfo.day +"\")' > "
										+ "<span id='workName'> "+manpowerInfo.workName+"</span>";
				    			}
				    		}
			    			newCellArr[2].innerHTML = tempElement;
			    			newCellArr[2].style.textAlign = "left";
			    			var tempHtml = "";
			    			for(var i=0; manpowerInfo.ssnArray.length >i; i++){
				    			if(manpowerInfo.nameArray[i] != ''){
					    			var bte="";
									var delStr='';
									if('<%=request.getAttribute("viewMode")%>' == 'projectSearch' ||  res.readonly == true){
										delStr=', ';
									}else{
										delStr=" <img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectManpowerSchedule('"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"')\">" 
									}
				    				tempHtml += "<span id='"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"' year='"+manpowerInfo.year+"' month='"+manpowerInfo.month+"' day='"+manpowerInfo.day+"' ssn='"+manpowerInfo.ssnArray[i]+"'>"
				    								+ "<a href='#'"+  bte + ">"+ manpowerInfo.nameArray[i] +"</a>"
													+ delStr + "&nbsp;</span>";
				    			}
			    			}
			    			newCellArr[3].innerHTML = tempHtml;
			    			newCellArr[3].style.textAlign = "left";
			    			
			    			if(manpowerInfo.week == 'SAT' ){
			    				newCellArr[0].style.backgroundColor = "#D9ECFF"; 
				    			newCellArr[1].style.backgroundColor = "#D9ECFF"; 
				    			newCellArr[2].style.backgroundColor = "#D9ECFF"; 
				    			newCellArr[3].style.backgroundColor = "#D9ECFF"; 
			    			} else if(manpowerInfo.week == 'SUN'){
			    				newCellArr[0].style.backgroundColor = "#FFDFFF"; 
			    				newCellArr[1].style.backgroundColor = "#FFDFFF"; 
			    				newCellArr[2].style.backgroundColor = "#FFDFFF"; 
			    				newCellArr[3].style.backgroundColor = "#FFDFFF"; 
			    			} else {
			    				newCellArr[0].style.backgroundColor = "#FFFFFF"; 
			    				newCellArr[1].style.backgroundColor = "#FFFFFF"; 
			    				newCellArr[2].style.backgroundColor = "#FFFFFF"; 
			    				newCellArr[3].style.backgroundColor = "#FFFFFF"; 
			    			}
			    		});	
						alert(res.message);
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("삭제할 수 없습니다.");
					}
				}
		); status = null;
	}
}
//프로젝트 투입일정 ---------------------------------------------------끝//

//협력사 팝업----------------------------------------------------------시작//
function openRelationCompanyPopUp(){
	relationCompanyWin_Open('setRelationCompany')
}
function setRelationCompany(relationCompanyList){
	relationCompanyList.each(function(relationCompany){
		 $('orgCodes').value = ($('orgCodes').value=="" ? "" : $('orgCodes').value+", ") + relationCompany.OrgCODE;
		 $('orgNames').value = ($('orgNames').value=="" ? "" : $('orgNames').value+", ") + relationCompany.name;
	});
	
}
//협력사 팝업------------------------------------------------------------끝//


//DMList 팝업----------------------------------------------------------시작//
function openDMListPopUp(type){
	dmlist_Open(type);
	//relationCompanyWin_Open('setDM');
}
function setDM(dm){
	 $('customerWorkPName').value = dm.name;
	 $('customerWorkPEmail').value = dm.email;
	 $('customerWorkPPhone').value = dm.tel;
}
function setCustomerWork(dm){
	 $('customerWorkPName').value = dm.name;
	 $('customerWorkPEmail').value = dm.email;
	 $('customerWorkPPhone').value = dm.tel;
}
function setCustomerCont(dm){
	 $('customerContPName').value = dm.name;
	 $('customerContPEmail').value = dm.email;
	 $('customerContPPhone').value = dm.tel;
}
//DMList 팝업------------------------------------------------------------끝//

//교육과정 연계------------------------------------------------------------시작//
function eduCourseRel_new() {
	if($('projectDetailCode').value == ''){
		alert('프로세스 유형을 선택하세요');
		return;
	}
	
	AjaxRequest.post(
			{	'url': "/action/ProcessCategoryAction.do?mode=getProcessCategoryObj",
				'parameters' : { "processCategoryCode": $('projectDetailCode').value },
				'async' : true,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					window.open('', 'eduCourseRel', 'toolbar=no,location=no,status=yes,menubar=no,scrollbars=no,resizable=yes,width=810,height=530,left=0,top=0');
					eduCourseRelForm.action="https://intranet.kmac.co.kr/kmac/Emergency/courseprojectcodeSearch.asp";
					eduCourseRelForm.target = "eduCourseRel";
					eduCourseRelForm.p_code.value = $('projectCode').value;
					eduCourseRelForm.processCode.value = res.processCategory.processTemplateCode;
					eduCourseRelForm.bizCode.value = res.processCategory.businessTypeCode;
					eduCourseRelForm._id.value = "<%=session.getAttribute("userId")%>";
					eduCourseRelForm.submit();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.9");
				}
			}
	);
}
//교육과정 연계------------------------------------------------------------끝//

//VOC 팝업------------------------------------------------------------끝//
function vocValueChange(){
	if($('isVoc').value == 'Y'){
		$('vocType').show();
	}else{
		$('vocType').hide();
	}
}
function registProjectVocPopUp(projectCode) {
	AjaxRequest.post(
			{	'url': "/action/ErpProjectAction.do?mode=erpProjectCheck",
				'parameters' : { "projectCode": projectCode },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.isRegistered){
						var win = new Window('modal_window', {
							className : "dialog",
							title : "VOC 일정 등록",
							top : 50,
							left : 50,
							width : 800,
							height : 800,
							zIndex : 150,
							opacity : 1, 
							resizable : true,	
							url : "/action/ProjectVocAction.do?mode=registProjectVoc&projectCode="+$('projectCode').value
						});
						win.show(true);
						win.setDestroyOnClose();
					}else{
						alert("프로젝트 정보를 저장 후 입력이 가능합니다.");
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.10");
				}
			}
	); 
}
function openVocContent(requestDate, projectCode){
	var url = 'http://intranet.kmac.co.kr/kmac/vocinfo/VocFeedbackView.asp?projectCode='+projectCode+'&RequestDate='+requestDate;
	 window.open(url,'VOC','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=700,height=850,directories=no,menubar=no');
}
function openRegistProjectEvalPopUp(projectCode){
	AjaxRequest.post(
			{	'url': "/action/ErpProjectAction.do?mode=erpProjectCheck",
				'parameters' : { "projectCode": projectCode },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.isRegistered){
						var url = '/action/ProjectVocAction.do?mode=registProjectVoc&projectCode='+projectCode;
						 window.open(url,'VOC','top=350,left=600,status=no,toolbar=no,scrollbars=yes,width=640,height=380,directories=no,menubar=no');
					}else{
						alert("프로젝트 기본정보를 저장 후 입력이 가능합니다.");
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.10");
				}
			}
	); 
}
//VOC 팝업------------------------------------------------------------끝//

//만족도 평가 ------------------------------------------------------------끝//
function projectDetailCodeChange(value){
	var ActionURL = "/action/ProcessCategoryAction.do?mode=getProcessCategoryObj";
	
	var status = AjaxRequest.post(
			{	'url':ActionURL,
				'parameters' : { "processCategoryCode": value },
				'onSuccess':function(obj){
					
					var res = eval('(' + obj.responseText + ')');
					if(res.processCategory != ""){
						$('_processTemplateCode').value = res.processCategory.processTemplateCode;
						$('_businessFunctionType').value = res.processCategory.businessFunctionType;
					}else{
						$('_processTemplateCode').value = "";
						$('_businessFunctionType').value = "";
					}
					/*
					if(res.processCategory.businessTypeCode == 'BTF' 
						&& (res.processCategory.processTemplateCode == 'DD')){
						$('eduCourseRel1').show();
						$('eduCourseRel2').show();
					}else if(res.processCategory.businessTypeCode == 'BTE' 
						&& (res.processCategory.processTemplateCode == 'N1'
							|| res.processCategory.processTemplateCode == 'N2'
								||res.processCategory.processTemplateCode == 'EE'
									||res.processCategory.processTemplateCode == 'DD'
								)){
						$('eduCourseRel1').show();
						$('eduCourseRel2').show();
					} else{
						try{$('eduCourseRel1').hide();$('eduCourseRel2').hide();}catch(e){}
					}
					
					if(res.processCategory.businessTypeCode == 'BTD' || res.processCategory.processTemplateCode == 'N4'){
						$('evaluate1').show();
						$('evaluate2').show();
					}else{
						try{$('evaluate1').hide();
							$('evaluate2').hide();
						}catch(e){}
					}
					*/
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.11");
				}
			}
	); status = null;
}

function isAvailablePL(){
	var pos = 0;
	var memberJobClassList = $$('input[name="memberJobClass"]');
	var memberRoleList = $$('select[name="memberRole"]');
	memberJobClassList.each( function(memberJobClass){
		if(memberJobClassList[pos].value == 'C' && memberRoleList[pos].value == 'PL') {
			alert("PL에 엑스퍼트를 지정할 수 없습니다.\n상근 또는 상임을 지정하십시오.");
			pos = 100;
		}
		pos = pos + 1;
	});
	 
	if(pos>100){ return false; } else{ return true; }	
}

function isExpertDateCheck(){
	var memberJobClassList = $$('input[name="memberJobClass"]');
	var memberStartDateList = $$('input[name="memberStartDate"]');
	var memberEndDateList = $$('input[name="memberEndDate"]');
	for(var i=0; i < memberJobClassList.length; i++){
		if(memberJobClassList[i].value == "C" || memberJobClassList[i].value == "D" || memberJobClassList[i].value == "E" ) {
			if(memberStartDateList[i].value == ""){
				alert("엑스퍼트/산업계강사/대학교수 의 실제 투입기간은 필수 입력 사항 입니다. \n인력정보 "+(i+1)+"번째의 투입 시작 일자를 입력 하세요");	return false; 
			}
			if(memberEndDateList[i].value == ""){
				alert("엑스퍼트/산업계강사/대학교수 의 실제 투입기간은 필수 입력 사항 입니다. \n인력정보 "+(i+1)+"번째의 투입  종료 일자를 입력 하세요");	return false;
			}
			if(memberStartDateList[i].value > memberEndDateList[i].value ){
				alert("인력정보 "+(i+1)+"번째의 투입 인력의 시작일과 종요일을 확인하세요.");	return false;
			}
		}
	}
	return true;	
}

function memberRoleDualCheck(){
	var arCnt = 0;
	var pmCnt = 0;
	var plCnt = 0;
	var memberRole = $$('select[name="memberRole"]');
	for(var i=0; i < memberRole.length; i++){
		if (memberRole[i].value == "AR") { arCnt = arCnt + 1; }
		if (memberRole[i].value == "PM") { pmCnt = pmCnt + 1; }
		if (memberRole[i].value == "PL") { plCnt = plCnt + 1; }
	}
	if(arCnt > 1){ alert("COO가 두 명 이상으로 지정되었습니다.");		return false; }
	if(pmCnt > 1){ alert("PM이 두 명 이상으로 지정되었습니다.");		return false; }
	if(plCnt > 1){ alert("PL이 두 명 이상으로 지정되었습니다.");		return false; }
	
	return true;
}

function registProjectEvalPopUp(projectCode, projectState){
	var url = '/action/ProjectETCInfoAction.do?mode=selectProjectCsrInfoList&projectCode='+projectCode+'&projectState='+projectState;
	 window.open(url,'VOC','top=140,left=140,status=no,toolbar=no,scrollbars=yes,width=1130,height=530,directories=no,menubar=no');
}
//만족도 평가------------------------------------------------------------끝//

//My Project------------------------------------------------------------끝//
function searchMyProject(){
	document.projectMasterInfoForm.target = "";
	document.projectMasterInfoForm.action = "/action/MyProjectAction.do?mode=getMyProjectList";
	document.projectMasterInfoForm.submit();	
}
//My Project------------------------------------------------------------끝//

//성과급  ------------------------------------------------------------끝//
function deleteRowBUSalary(){
	var tableObj = $('buExpenseTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="expenseCheck"]');
	var ssnEls = $$('select[name="ssn"]');
	for ( var i=0;i<ssnEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			var ssn = ssnEls[i].value;
			var seq = chkBoxEls[i].value;
			$(chkBoxEls[i]).up().up().remove();//성공 시 행삭제
			if(seq != -1){
				var status = AjaxRequest.post (
						{	'url':"/action/ProjectExpenseAction.do?mode=deleteProjectExpense",
							'parameters' : { "projectCode": $("projectCode").value, "year": $('year').value, "month": $('month').value, "ssn": ssn, "seq": seq },
							'onSuccess':function(obj){
								var res = eval('(' + obj.responseText + ')');
							}, 
							'onLoading':function(obj){}, 
							'onError':function(obj){ 
								alert("삭제 오류가 발생했습니다.");
							}
						}
				); 
			}
		}
	}
	alert("삭제하였습니다.");
	Tab.changeTab("pane5");
	AjaxRequest.post (
			{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
				'parameters' : {projectCode: $("projectCode").value },
				'onSuccess':function(obj){
					//alert("인건비 예산 초과 정보 업데이트 완료");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
				}
			}
	);
}

function deleteRowPUSalary(){
	var tableObj = $('puExpenseTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="expenseCheck"]');
	var ssnEls = $$('select[name="ssn"]');
	for ( var i=0;i<ssnEls.length;i++){
		if ( chkBoxEls[i].checked ) {
			var ssn = ssnEls[i].value;
			var seq = chkBoxEls[i].value;
			$(chkBoxEls[i]).up().up().remove();//성공 시 행삭제
			if(seq != -1){
				var status = AjaxRequest.post (
						{	'url':"/action/ProjectExpenseAction.do?mode=deleteProjectExpense",
							'parameters' : { "projectCode": $("projectCode").value, "year": $('year').value, "month": $('month').value, "ssn": ssn, "seq": seq },
							'onSuccess':function(obj){
								var res = eval('(' + obj.responseText + ')');
							}, 
							'onLoading':function(obj){}, 
							'onError':function(obj){ 
								alert("삭제 오류가 발생했습니다.");
							}
						}
				); 
			}
		}
	}
	alert("삭제하였습니다.");
	Tab.changeTab("pane5");
	AjaxRequest.post (
			{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
				'parameters' : {projectCode: $("projectCode").value },
				'onSuccess':function(obj){
					//alert("인건비 예산 초과 정보 업데이트 완료");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
				}
			}
	);
}
function deleteRowPUSalary2(){
	var tableObj = $('puExpenseTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="expenseCheck"]');
	var ssnEls = $$('select[name="ssn"]');
	for ( var i=0;i<chkBoxEls.length;i++){
		if ( chkBoxEls[i].checked ) {
			var ssn = ssnEls[i].value;
			var seq = chkBoxEls[i].value;
			$(chkBoxEls[i]).up().up().remove();//성공 시 행삭제
			if(seq != -1){
				var status = AjaxRequest.post (
						{	'url':"/action/ProjectExpenseAction.do?mode=deleteProjectExpense2",
							'parameters' : { "projectCode": $("projectCode").value, "year": $('year').value, "month": $('month').value, "ssn": ssn, "seq": seq },
							'onSuccess':function(obj){
								var res = eval('(' + obj.responseText + ')');
							}, 
							'onLoading':function(obj){}, 
							'onError':function(obj){ 
								alert("삭제 오류가 발생했습니다.");
							}
						}
				); 
			}
		}
	}
	alert("삭제하였습니다.");
	Tab.changeTab("pane5");
	AjaxRequest.post (
			{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
				'parameters' : {projectCode: $("projectCode").value },
				'onSuccess':function(obj){
					//alert("인건비 예산 초과 정보 업데이트 완료");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
				}
			}
	);
}
function deleteRowBUSalary2(){
	var tableObj = $('buExpenseTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="expenseCheck"]');
	var ssnEls = $$('select[name="ssn"]');
	for ( var i=0;i<chkBoxEls.length;i++){
		alert(ssnEls[i].value);
		alert(chkBoxEls[i].value);
		if ( chkBoxEls[i].checked ) {
			var ssn = ssnEls[i].value;
			var seq = chkBoxEls[i].value;
			$(chkBoxEls[i]).up().up().remove();//성공 시 행삭제
			if(seq != -1){
				var status = AjaxRequest.post (
						{	'url':"/action/ProjectExpenseAction.do?mode=deleteProjectExpense2",
							'parameters' : { "projectCode": $("projectCode").value, "year": $('year').value, "month": $('month').value, "ssn": ssn, "seq": seq },
							'onSuccess':function(obj){
								var res = eval('(' + obj.responseText + ')');
							}, 
							'onLoading':function(obj){}, 
							'onError':function(obj){ 
								alert("삭제 오류가 발생했습니다.");
							}
						}
				); 
			}
		}
	}
	alert("삭제하였습니다.");
	Tab.changeTab("pane5");
	AjaxRequest.post (
			{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
				'parameters' : {projectCode: $("projectCode").value },
				'onSuccess':function(obj){
					//alert("인건비 예산 초과 정보 업데이트 완료");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
				}
			}
	);
}

function addRowBUSalary(){
	var tableObj = $('buExpenseTable');
	if($('buExpenseTableBody').childElements().size() > 0){
		if($('buExpenseTableBody').down('tr').childElements().size() == 1){
	   		$('buExpenseTableBody').down('tr').remove();   		
	   	}
	}

	var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	
	newCellArr[0].innerHTML = "<input type='checkbox' name='expenseCheck' />";
	newCellArr[1].innerHTML = $('buMemberValue').value;
	newCellArr[2].innerHTML = "<input type='text' name='amount' style='width: 100%' class='textInput_right' onchange='getNumber(this);' onkeyup='getNumber(this);' ><input type='hidden' name='seq' value='-1'>";
	newCellArr[3].innerHTML = "<select name='payYn' class='selectbox' style='width: 100%'><option value='N'>지급예정</option><option value='Y'>가지급됨</option>";

	newCellArr[0].className = "BGC";
	newCellArr[1].className = "BGC";
	newCellArr[2].className = "BGC";
	newCellArr[3].className = "BGL";
}
function storeBUSalary(){
	var ActionURL = "/action/ProjectExpenseAction.do?mode=insertProjectExpense";
	var sFrm = document.forms["projectExpenseForm"];
 	var monthlyCount = $$('input[name="monthlyReport"]');
	var flag = false;
	
	// 프로젝트레포트 미 작성 시 성과급 차단
	/* By-Pass 추가 */
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
	 	monthlyCount.each( function(buSchedule){
			if(buSchedule.value > 0){
				alert("미 작성된 프로젝트레포트가 있습니다. \n(작성 후 성과급을 신청해주시기 바랍니다.)"); flag=true;
			}
		}); if(flag==true){return;}
	</c:if>
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.buSalary;		 
					var rsMember = res.buMember;
					var table = $('buExpenseTable');
					var tbody = $('buExpenseTableBody').childElements();
					var tdCount = $('buExpenseTableHeader').down('tr').childElements().size();
					tbody.each( function(buSalary){
						buSalary.remove();
					});
	
					rsObj.each(function(buSalary){
					    var newRow=table.insertRow(-1);
						var newCellArr = new Array();
						for ( var i=0;i<tdCount;i++ ){
							newCellArr[i] = newRow.insertCell(-1);
						}		
						if(buSalary.approvalYn == 'Y'){
							newCellArr[0].innerHTML = "";
							newCellArr[1].innerHTML = buSalary.name;
							newCellArr[2].innerHTML = (buSalary.amount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[2].className = "detailTableField_right";
							newCellArr[3].innerHTML = "지급됨";
						}else{
							newCellArr[0].innerHTML = "<input type='checkbox' name='expenseCheck' seq='"+buSalary.ssn+"'  ssn='"+buSalary.ssn+"'>";
							var temp = "";
							rsMember.each( function(mm){
								temp = temp + "<option value='"+mm.ssn+"' "+(mm.ssn == buSalary.ssn ?  "selected" : "")+" >"+buSalary.name+"</option>";
							});
							newCellArr[1].innerHTML = "<select name='ssn' class='selectbox' style='width: 100%' >"+temp+"</select>";
							newCellArr[2].innerHTML = "<input type='text' name='amount' style='width: 100%' class='textInput_right' value='"+(buSalary.amount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"'><input type='hidden' name='seq' value='"+buSalary.seq+"'>";
							newCellArr[3].innerHTML = "<select name='payYn' class='selectbox' style='width: 100%'><option value='N' "+(buSalary.payYn == 'N' ?  "selected" : "")+">지급예정</option><option value='Y' "+(buSalary.payYn == 'Y' ?  "selected" : "")+">가지급됨</option>";
						}

					});	
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: $("projectCode").value },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					); 			
					alert('저장하였습니다.');
					Tab.changeTab("pane5");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("성과급을 저장할 수 없습니다.");
				}
			}
	); status = null;
}

function storePUSalary(){
	var ActionURL = "/action/ProjectExpenseAction.do?mode=insertProjectExpensePU";
	var sFrm = document.forms["projectExpenseForm"];
	var memberResRateList = $$('input[name="memresRate"]');
	var memberMMRateList = $$('input[name="puMMRate"]');
	var menpowercntList = $$('input[name="menpowercnt"]');
	var realAmount = $$('input[name="realAmount"]');
	var scheduleCount = $$('input[name="scheduleReport"]');
	var flag = false;
	var cnt = menpowercntList.length;
	var cnt_t = 0;
	
	
	// 주간진척관리 작성 카운트
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		scheduleCount.each( function(puSchedule){
			if(puSchedule.value > 0){
				alert("미 작성된 주간진척관리가 있습니다. \n(작성 후 성과급을 신청해주시기 바랍니다.)"); flag=true;
			}
		}); if(flag==true){return;}
	</c:if>

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.puSalary;		 
					var rsMember = res.puMember;
					var table = $('puExpenseTable');
					var tbody = $('puExpenseTableBody').childElements();
					var tdCount = $('puExpenseTableBody').down('tr').childElements().size();
					tbody.each( function(puSalary){
						puSalary.remove();
					});
					
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: $("projectCode").value },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					); 						
					alert('저장하였습니다.');
					Tab.changeTab("pane5");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("성과급 정보를 저장할 수 없습니다.");
				}
			}
	); status = null;
}

function puRestAmountStore(index) {
	var chargeSsn = $("ssn"+index).value;
	var restAmount = $("restAmount"+index).value;
	var amount = $("amount"+index).value;
	var approvalYn = $("approvalYn"+index).value;
	
	
	storeRestSalary($("projectCode").value, $("year").value, $("month").value, chargeSsn, restAmount, amount, approvalYn);
}

function puRestAmountDelete(index) {
	var chargeSsn = $("ssn"+index).value;
	
	deleteRestSalary($("projectCode").value, $("year").value, $("month").value, chargeSsn);
}

function storeRestSalary(projectCode, year, month, chargeSsn, restAmount, amount, approvalYN){

	// 20% 지급금액 alert창 띄우기
	var result = restAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	if(confirm("현재 20% 금액은 "+ result +"원 입니다. \n20% 성과급을 신청하시겠습니까?"))
	{} else {return;}
	
	AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { 
					"projectCode": projectCode, 
					"year": year,
					"month": month,
					"chargeSsn": chargeSsn, 
					"restAmount": restAmount
				},
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.puSalary;		 
					var rsMember = res.puMember;
					var table = $('puExpenseTable');
					var tbody = $('puExpenseTableBody').childElements();
					var tdCount = $('puExpenseTableBody').down('tr').childElements().size();
					tbody.each( function(puSalary){
						puSalary.remove();
					});
					
					/* 신청 시 깨지는 화면 주석 */
					/* for(var index=0; index<rsObj.length; index++){
						puSalary = rsObj[index];
						
					    var newRow=table.insertRow(-1);
						var newCellArr = new Array();
						for ( var i=0;i<tdCount;i++ ){
							newCellArr[i] = newRow.insertCell(-1);
						}
							newCellArr[0].innerHTML = puSalary.jobClassName;
							newCellArr[1].innerHTML = puSalary.name;
							newCellArr[2].innerHTML = puSalary.monthlyMM;
							newCellArr[3].innerHTML = (puSalary.monthlyBudget).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[3].className = "detailTableField_right";
							newCellArr[4].innerHTML = (puSalary.totalBudget).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[4].className = "detailTableField_right";
						if(puSalary.approvalYn == 'Y'){
							newCellArr[5].innerHTML = puSalary.mmRate;
						}else{
							newCellArr[5].innerHTML = "<input type='text' id='puMMRate' name='puMMRate' class='textInput_center' value='" + puSalary.mmRate + "' style='width:40px;ime-mode:disabled' onchange='Money_Only(this,1,0);' onkeyup='Money_Only(this,1,0)' /><input type='hidden' id='puSsn' name='puSsn' value='" + puSalary.ssn + "' /><input type='hidden' id='puSeq' name='puSeq' value='" + puSalary.seq + "' />";
						}
							newCellArr[6].innerHTML = (puSalary.amount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[6].className = "detailTableField_right";
						if(puSalary.restAmount == -1) {
							newCellArr[7].innerHTML = "지급완료";
						}else{
							newCellArr[7].innerHTML = (puSalary.restAmount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[7].className = "detailTableField_right";
						}
							newCellArr[8].innerHTML = (puSalary.restBudget).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[8].className = "detailTableField_right";
						if(puSalary.restAmount > 0.0 && puSalary.restCtmCheckYN == '') {
							newCellArr[9].innerHTML = 
								"<input type='hidden' id='ssn"+index+"' value='"+puSalary.ssn+"'/>" +
								"<input type='hidden' id='restAmount"+index+"' value='"+puSalary.restAmount+"'/>" +
								"<input type='hidden' id='amount"+index+"' value='"+puSalary.amount+"'/>" +
								"<input type='hidden' id='approvalYn"+index+"' value='"+puSalary.approvalYn+"'/>" +							
								"<a class='btN3fac0c txt0btn' href='#' onclick='puRestAmountStore("+index+");'>신청</a>";
						}
						if(puSalary.restApprovalYN == 'N' && puSalary.restAmount != -1) {
							newCellArr[9].innerHTML = 
								"<input type='hidden' id='ssn"+index+"' value='"+puSalary.ssn+"'/>" +
								"<a class='btNe14f42 txt0btn' href='#' onclick='puRestAmountDelete("+index+");'>취소</a>";
						}
						if(puSalary.restApprovalYN == 'Y' && puSalary.restCtmCheckYN == 'Y') {
							newCellArr[9].innerHTML = "품의완료";
						}
					}; */
					
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: projectCode },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					);
					alert("20% 성과급을 신청하였습니다.");
					Tab.changeTab("pane5");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("20% 성과급을 신청할 수 없습니다.");
				}
			}
	);
}

function storeRestSalary2(){
	var ActionURL = "/action/ProjectExpenseAction.do?mode=insertProjectExpensePU2";
	var sFrm = document.forms["projectExpenseForm"];
	var memberResRateList = $$('input[name="memresRate"]');
	var memberMMRateList = $$('input[name="puMMRate"]');
	var menpowercntList = $$('input[name="menpowercnt"]');
	var restAmount = $$('input[name="restAmount"]');
	var scheduleCount = $$('input[name="scheduleReport"]');
	var flag = false;
	var cnt = menpowercntList.length;
	var cnt_t = 0;

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.puSalary;		 
					var rsMember = res.puMember;
					var table = $('puExpenseTable');
					var tbody = $('puExpenseTableBody').childElements();
					var tdCount = $('puExpenseTableBody').down('tr').childElements().size();
					tbody.each( function(puSalary){
						puSalary.remove();
					});
					
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: $("projectCode").value },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					); 						
					alert('저장하였습니다.');
					Tab.changeTab("pane5");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("성과급 정보를 저장할 수 없습니다.");
				}
			}
	); status = null;
}

function deleteSalary(projectCode, year, month, chargeSsn){

	// 20% 지급금액 alert창 띄우기
	if(confirm("금액을 삭제하시겠습니까?"))
	{} else {return;}
	
	var ActionURL = "/action/ProjectExpenseAction.do?mode=deleteProjectSalary";
	AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { 
					"projectCode": projectCode, 
					"year": year,
					"month": month,
					"chargeSsn": chargeSsn
				},
				'onSuccess':function(obj){
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: projectCode },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					);
					alert("삭제하였습니다.");
					Tab.changeTab("pane5");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("80% 금액을 삭제할 수 없습니다.");
				}
			}
	);
}

function deleteRestSalary(projectCode, year, month, chargeSsn){
	var ActionURL = "/action/ProjectExpenseAction.do?mode=deleteProjectRestExpense";
	
	AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { 
					"projectCode": projectCode, 
					"year": year,
					"month": month,
					"chargeSsn": chargeSsn
				},
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.puSalary;		 
					var rsMember = res.puMember;
					var table = $('puExpenseTable');
					var tbody = $('puExpenseTableBody').childElements();
					var tdCount = $('puExpenseTableBody').down('tr').childElements().size();
					tbody.each( function(puSalary){
						puSalary.remove();
					});
					
					for(var index=0; index<rsObj.length; index++){
						puSalary = rsObj[index];
						
					    var newRow=table.insertRow(-1);
						var newCellArr = new Array();
						for ( var i=0;i<tdCount;i++ ){
							newCellArr[i] = newRow.insertCell(-1);
						}
							newCellArr[0].innerHTML = puSalary.jobClassName;
							newCellArr[1].innerHTML = puSalary.name;
							newCellArr[2].innerHTML = puSalary.monthlyMM;
							newCellArr[3].innerHTML = (puSalary.monthlyBudget).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[3].className = "detailTableField_right";
							newCellArr[4].innerHTML = (puSalary.totalBudget).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[4].className = "detailTableField_right";
						if(puSalary.approvalYn == 'Y'){
							newCellArr[5].innerHTML = puSalary.mmRate;
						}else{
							newCellArr[5].innerHTML = "<input type='text' id='puMMRate' name='puMMRate' class='textInput_center' value='" + puSalary.mmRate + "' style='width:40px;ime-mode:disabled' onchange='Money_Only(this,1,0);' onkeyup='Money_Only(this,1,0)' /><input type='hidden' id='puSsn' name='puSsn' value='" + puSalary.ssn + "' /><input type='hidden' id='puSeq' name='puSeq' value='" + puSalary.seq + "' />";
						}
							newCellArr[6].innerHTML = (puSalary.amount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[6].className = "detailTableField_right";
						if(puSalary.restAmount == -1) {
							newCellArr[7].innerHTML = "지급완료";
						}else{
							newCellArr[7].innerHTML = (puSalary.restAmount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[7].className = "detailTableField_right";
						}
							newCellArr[8].innerHTML = (puSalary.restBudget).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							newCellArr[8].className = "detailTableField_right";
						if(puSalary.restAmount > 0.0 && puSalary.restCtmCheckYN == '') {
							newCellArr[9].innerHTML = 
								"<input type='hidden' id='ssn"+index+"' value='"+puSalary.ssn+"'/>" +
								"<input type='hidden' id='restAmount"+index+"' value='"+puSalary.restAmount+"'/>" +
								"<input type='hidden' id='amount"+index+"' value='"+puSalary.amount+"'/>" +
								"<input type='hidden' id='approvalYn"+index+"' value='"+puSalary.approvalYn+"'/>" +							
								"<a class='btN3fac0c txt0btn' href='#' onclick='puRestAmountStore("+index+");'>신청</a>";
						}
						if(puSalary.restApprovalYN == 'N' && puSalary.restAmount != -1) {
							newCellArr[9].innerHTML = 
								"<input type='hidden' id='ssn"+index+"' value='"+puSalary.ssn+"'/>" +
								"<a class='btNe14f42 txt0btn' href='#' onclick='puRestAmountDelete("+index+");'>취소</a>";
						}
						if(puSalary.restApprovalYN == 'Y' && puSalary.restCtmCheckYN == 'Y') {
							newCellArr[9].innerHTML = "품의완료";
						}
					};
					
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: projectCode },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					);
					alert("20% 성과급 신청을 취소하였습니다.");
					Tab.changeTab("pane5");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("20% 성과급 신청을 취소할 수 없습니다.");
				}
			}
	);
}

//성과급  ------------------------------------------------------------끝//

 
//프로세스 ------------------------------------------------------------시작//
function openProjectProgressContent(projectCode, contentId, workSeq, readOnly) {
	//alert(projectCode+":"+contentId+":"+workSeq+":"+readOnly);
	var url = "/action/ProjectProgressManagementAction.do?mode=loadProjectProgessContent&contentId="+contentId+"&projectCode="+projectCode+"&workSeq="+workSeq+"&readOnly="+readOnly;
	window.open(url, 'ProgressContent',
			'top=200,left=330,status=no,toolbar=no,scrollbars=yes,width=880,height=680,directories=no,menubar=no');
}	

function thisMovie(movieName) {
    //if (navigator.appName.indexOf("Microsoft") != -1) {
    //    return window[movieName];
    //} else {
        return document[movieName];
    //}
}

function refreshGanttChart() {
	//thisMovie("projectProgress").refreshGanttChart();
	Tab.changeTab("pane2");
}

function registerPrgressAdd() {
	var tableObj = $('projectProgressTable');
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<5;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	var date_tmp = new Date()
	tmp = (date_tmp.getMonth())+ (date_tmp.getMonth())+ (date_tmp.getDate())+ (date_tmp.getHours())+ (date_tmp.getMinutes())+ (date_tmp.getSeconds())+ (date_tmp.getMilliseconds()); 
	
	newCellArr[0].innerHTML = "<input name='projectProgressCheck' type='checkbox' /><input type='hidden' name='workSeq' value='"+tmp+"'/><input type='hidden' name='contentId' value=''/>";
	newCellArr[1].innerHTML = "<input name='orderSeq' value='' style='width: 97%;' readonly='readonly'/>";
	newCellArr[2].innerHTML = "<input name='workName' value=''  style='width: 97%;'/><input type='hidden'/>";
	newCellArr[3].innerHTML = "<input name='startDate' id='startDate_"+tmp+"' value='' style='width: 80%;' readonly='readonly'/>";
	newCellArr[4].innerHTML = "<input name='endDate' id='endDate_"+tmp+"' value='' style='width: 80%;' readonly='readonly'/>";

	newCellArr[0].className = "detailTableField_center";
	newCellArr[1].className = "detailTableField_center";
	newCellArr[2].className = "detailTableField_center";
	newCellArr[3].className = "detailTableField_center";
	newCellArr[4].className = "detailTableField_center";
	
	jQuery(function(){jQuery( "#startDate_"+tmp ).datepicker({});});
	jQuery(function(){jQuery( "#endDate_"+tmp ).datepicker({});});

}
function prgressAdd() {
	var tableObj = $('projectProgressTable');
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<3;i++ ){
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
	
 	newCellArr[0].innerHTML = "<ul><li><input type='checkbox' name='projectProgressCheck' id='"+tmp+"' class='btn_check' value='C' /> <label for='"+tmp+"'></label></li></ul>"
							 +"<input type='hidden' id='workSeq' name='workSeq' value='"+tmp+"'/> "
							 +"<input type='hidden' id='contentId' name='contentId' value='"+tmp+"'/> "
	newCellArr[1].innerHTML = "<input type='text' name='orderSeq' value='' readonly='readonly'/>";
	newCellArr[2].innerHTML = "<input type='text' name='workName' value='' />";
	/* newCellArr[3].innerHTML = "<input type='text' name='startDate' id='startDate_"+tmp+"' value='' style='width: 80%;' readonly='readonly'/>";
	newCellArr[4].innerHTML = "<input type='text' name='endDate' id='endDate_"+tmp+"' value='' readonly='readonly'/>"; */

	newCellArr[0].className = "detailTableField_center";
	newCellArr[1].className = "detailTableField_center";
	newCellArr[2].className = "detailTableField_center";
	/* newCellArr[3].className = "detailTableField_center"; */
	/* newCellArr[5].className = "detailTableField_center"; */
	/* newCellArr[6].className = "detailTableField_center"; */
	
	/* jQuery(function(){jQuery( "#startDate_"+tmp ).datepicker({});});
	jQuery(function(){jQuery( "#endDate_"+tmp ).datepicker({});}); */

}
function prgressDelete() {
	var tableObj = $('projectProgressTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="projectProgressCheck"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().up().up().remove();//성공 시 행삭제
		}
	}	
}		
function prgressMoveUp() {
	var chkEls = j$("input[name=projectProgressCheck]:checkbox:checked");
	if(chkEls.length > 1) {
		alert("이동은 한 행만  선택 하세요.");return;
	}
	if(j$(chkEls[0]).parent().parent().get(0).rowIndex <= 1){
		return;
	}
	
	var j$tr = j$(chkEls[0]).parent().parent(); // 클릭한 버튼이 속한 tr 요소
	j$tr.prev().before(j$tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기
}	
function prgressMoveDown() {
	var chkEls = j$("input[name=projectProgressCheck]:checkbox:checked");
	if(chkEls.length > 1) {
		alert("이동은 한행만  선택 하세요.");return;
	}	
	
	var j$tr = j$(chkEls[0]).parent().parent(); // 클릭한 버튼이 속한 tr 요소
	j$tr.next().after(j$tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기
}

function moveUp(el){
	var $tr = $(el).parent().parent(); // 클릭한 버튼이 속한 tr 요소
	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기
}

function moveDown(el){
	var $tr = $(el).parent().parent(); // 클릭한 버튼이 속한 tr 요소
	$tr.next().after($tr); // 현재 tr 의 다음 tr 뒤에 선택한 tr 넣기
}

function prgressSave(){
	var ActionURL = "/action/ProjectProgressManagementAction.do?mode=storeProjectProgess";
	var sFrm = document.forms["projectProgressForm"];
	var realEndDate = document.getElementById("realEndDate").value;

	/* var startDateList = $$('input[name="startDate"]');
	var endDateList = $$('input[name="endDate"]'); */
	var realendDateList;
	
	/* for(var ii=0; ii < startDateList.length; ii++){
		realendDateList = endDateList[ii].value.replace(/\-/g,'');
		 
		if(realEndDate < realendDateList){
			alert("완료일을 프로젝트 기간보다 길게 설정하실 수 없습니다.");return;
		 }
		if(startDateList[ii].value == ""){
			alert("시작 일자를 입력하세요.  ");return;
		}
		if(endDateList[ii].value == ""){
			alert("종료 일자를 입력하세요.  ");return;
		}
		
		if(startDateList[ii].value>endDateList[ii].value){
			alert((ii+1) + "번 행의 시작일과 종요일을 확인하세요");return;	
			
		}
	} */
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					//location.reload();
					Tab.changeTab("pane2");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
	
}

function draftRequest2(){
	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertAditionalOutput2";
	var sFrm = document.forms["endingFrm"];
	
	// delay time
	fnSleep = function (delay){
        
        var start = new Date().getTime();
        while (start + delay > new Date().getTime());

    };
	
	AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				/* alert("저장하였습니다."); */
				/* document.location = "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode="+$('projectCode').value; */
				/* location.reload(true); */
				/* fnSleep(2000); */
				Tab.changeTab("pane12");
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){
				alert("저장할 수 없습니다.");
			}
		}
	)
}

function draftRequest3(){
	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertAditionalOutput3";
	var sFrm = document.forms["endingFrm"];
	
	AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				/* alert("저장하였습니다."); */
				/* document.location = "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode="+$('projectCode').value; */
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){
				alert("저장할 수 없습니다.");
			}
		}
	)
}

function deleteRowAttachFile(obj, fileId){	
	if(!confirm("파일을 삭제하시겠습니까?")) return;
	var deleteURL = "/action/RepositoryAction.do?mode=deleteFile"; //Upload URL
	var extraData ={"fileId":fileId}; //Extra Data.
	var jqXHR=j$.ajax({
		url: deleteURL,
		type: "GET",
		cache: false,
		data: extraData,
		success: function(data){
			var tr = j$(obj).parent().parent();
			tr.remove();
			draftRequest2();
		}
	}); 
}

function fileDownloadrr(size){
	
	var sFrm = document.forms["endingFrm"];
	var arr = new Array();
	
	// delay time
	fnSleep = function (delay){
        
        var start = new Date().getTime();
        while (start + delay > new Date().getTime());

    };
	
	for( var i = 0; i < size; i++){
		arr[i] = eval('document.endingFrm.fileList_'+i+'.value');
	}
	for(var i = 0; i < size; i++)
	{
		if(arr[i] != undefined){
			if($$("#_targetDownload").length == 0)
				document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
	    	$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + arr[i];
		}else{
			Tab.changeTab("pane12");
		}
		fnSleep(1000);
	}
}


function openErpPopUp() { 
	var win = new Window('modal_window', {
		className : "dialog",
		title : "누락 계산서 체크",
		top : 50,
		left : 50,
		width : 694,
		height : 400,
		zIndex : 150,
		opacity : 1, 
		resizable : true,
		showEffectOptions : {duration : 0.1},
		hideEffectOptions : {duration : 0.1},		
		url : "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload3&projectCode=<%= request.getAttribute("projectCode") %>"
	});
	win.show(true);
	win.setDestroyOnClose();
}

function viewBudget() {
	var url="/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=register<%=reqStr%>";
	window.open(url,'budget','top=0,left=50,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=850,height=700,directories=no,menubar=no');
}

function viewBudget_result(){
	var url="https://newbudget.kmac.co.kr:8080/com/login_chk_pms.jsp?param=<authz:authentication operation="username" />|DWPM30600_LINK|?projid=<c:out value="${project.projectCode}"/>";
	<%-- var url="/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>"; --%>
	window.open(url,'budget_result','top=50,left=50,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=850,height=600,directories=no,menubar=no');
}

//프로세스 ------------------------------------------------------------끝//


//CustomerInfo ------------------------------------------------------------시작//
function openCustomerInfoPopUp(projectCode){
	var url = "/action/ProjectMasterInfoAction.do?mode=getProjectCustomerInfo&projectCode="+projectCode;
	window.open(url, 'CustomerInfo',
			'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
}
//CustomerInfo ------------------------------------------------------------끝//

function goProjectEndDetail(projectCode, businessTypeCode, projectName, viewMode) {
	document.location.href = "/project/endProcess/projectEndInfoTab.jsp?viewMode="+viewMode+"&projectCode="+projectCode+"&businessTypeCode="+businessTypeCode+"&projectName="+projectName;
}

function detailview(ssn) {
		var url = "/action/EmergencyConnectionAction.do?mode=detailView&ssn="+ssn;
		window.open(url, 'expertPoolDetailView',
				'top=140,left=140,status=no,toolbar=no,scrollbars=yes,width=1440,height=980,directories=no,menubar=no');
}
</script>
</head>

<body>
	<form name="eduCourseRelForm" id="eduCourseRelForm" method="post">
		<input name="p_code" id="p_code" value="" type="hidden"> <input
			name="processCode" id="processCode" value="" type="hidden">
		<input name="bizCode" id="bizCode" value="" type="hidden"> <input
			name="_id" id="_id" value="" type="hidden">
	</form>
	<input type="hidden" id="projectTypeCode" name="projectTypeCode" value=<c:out value="${project.projectTypeCode }"/>/>
	<input type="hidden" id="realEndDate" name="realEndDate" value = <c:out value="${project.realEndDate}"/> /> 

		<!-- 타이틀 영역 -->
	<%
		String title = "";
		if (request.getAttribute("viewMode").equals("register")) {
			title = "프로젝트 등록";
		} else if (request.getAttribute("viewMode").equals("myProject")) {
			title = "MY PROJECT";
		} else if (request.getAttribute("viewMode").equals("projectSearch")) {
			title = "프로젝트 전체현황";
		}
	%>
	<div class="location">
		<p class="menu_title">PMS</p>
		<ul>
			<li class="home">HOME</li>
			<li>PMS</li>
			<li>프로젝트 관리</li>
			<li><%=title %></li>
		</ul>
	</div>
	<!-- // location -->

	<div class="contents sub">
		<!-- 서브 페이지 .sub -->
		<!-- 본문시작-->
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">
						<i class="mdi mdi-file-document-outline"></i>MY PROJECT [<c:out value="${project.projectName }"/>]
					</p>
					<c:if test="${project.projectState > '1'}">
					<%if((request.getAttribute("viewMode").equals("myProject") && ((projectRole.equals("PM") || projectRole.equals("PL") || projectRole.equals("AR") || projectRole.equals("COO") || position.equals("08CF") || position.equals("09CJ") || position.equals("10TM"))&&!jobclass.equals("C")))){ %>
						<div class="btn_area">
							<button type="button" class="btn line btn_black"><a title="예결산정보" href="#" onclick="viewBudget_result();"><i class="mdi mdi-currency-krw"></i>예결산정보</a></button>
						</div>
					<%}else if((request.getAttribute("viewMode").equals("projectSearch") && (projectRole.equals("PM") || !divRole.equals("") || position.startsWith("0")))){%>
						<div class="btn_area">
							<button type="button" class="btn line btn_black"><a title="예결산정보" href="#" onclick="viewBudget_result();"><i class="mdi mdi-currency-krw"></i>예결산정보</a></button>
						</div>
					<%} %>
					</c:if>
					<!-- project end button -->
					<c:if test="${!(project.attach == 'Y' && (project.projectCode == project.parentProjectCode)) }" ><c:if test="${project.projectState == '6'}">
						<div class="btn_area">
							<button type="button" class="btn line btn_black"><a title="종료정보" href="#" onclick="goProjectEndDetail('<c:out value="${project.projectCode}" />', '<c:out value="${project.businessTypeCode}" />','<c:out value="${project.projectName }"/>','<%=request.getAttribute("viewMode")%>');"><i class="mdi mdi-calendar-clock"></i>종료정보</a></button>
						</div>
					</c:if></c:if> 
					<div class="btn_area">
						<c:if test="${viewMode=='register' && project.projectState == '1' && (sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT')}">
							<c:choose>
								<c:when test="${project.businessTypeCode== 'BTA' || project.businessTypeCode== 'BTJ' || project.businessTypeCode== 'BTD' || (project.businessTypeCode== 'BTE' && project.processTypeCode== 'N4') || project.processTypeCode=='DE' || project.processTypeCode=='SS'}">
									<button type="button" class="btn line btn_aqua"><a title="프로젝트 실행품의요청" href="#" onclick="doProjectLaunchSanction('A');"><i class="mdi mdi-check-decagram"></i>실행품의</a></button>
									</c:when>
								<c:otherwise>
									<button type="button" class="btn line btn_aqua"><a title="프로젝트 기획품의요청" href="#" onclick="doProjectLaunchSanction('PA');"><i class="mdi mdi-check-decagram"></i>기획품의</a></button>
									</c:otherwise>
							</c:choose>
							<button type="button" class="btn line btn_black"><a title="예산서" href="#" onclick="viewBudget();"><i class="mdi mdi-currency-krw"></i>예산정보</a></button>
						</c:if>
					</div>
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
			<!-- <div class="tabbed-pane"> -->
			<div class="fixed_contents sc">
				<div class="tab_menu_area">
					<ul id="tab_menu" class="tab_ui">
							<% if(request.getAttribute("viewMode").equals("register")){ %>
								<li class="current"><a href="#" class="current"	id="pane1">기본정보</a></li>
								<li><a href="#" 				id="pane2">일정정보</a></li>
							<c:if test="${project.projectTypeCode != 'MM'}">
								<li><a href="#" 				id="pane3">참여정보</a></li>
							</c:if>
							<c:if test="${project.businessTypeCode == 'BTA'}">
								<li><a href="#"					id="pane5">VOC</a></li>
							</c:if>
							<% } else if(request.getAttribute("viewMode").equals("myProject")){ %>
								<li class="current"><a href="#" class="current"	id="pane1">기본정보</a></li>
								<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
									<li><a href="#"					id="pane2">일정관리</a></li>
								</c:if>
							<%if(!projectRole.equals("NOMB")){%>
							<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
								<c:if test="${project.projectTypeCode != 'MM'}">
									<li><a href="#" 				id="pane3">참여정보</a></li>
									<li><a href="#" 				id="pane11">프로젝트레포트</a></li>
								</c:if>
								<c:if test="${project.projectTypeCode == 'MM'}">
									<li><a href="#" 				id="pane10">참여정보</a></li>
									<li><a href="#" 				id="pane3">주간진척관리</a></li>
								</c:if>
							</c:if>
							<%}%>
							<%if((projectRole.equals("PM") || projectRole.equals("PL") || projectRole.equals("AR") || projectRole.equals("QM") || position.equals("08CF") || position.equals("09CJ") || position.equals("10TM"))&&!jobclass.equals("C")){%>
								<!-- <li><a href="#"					id="pane4">예결산정보</a></li> -->
								<li><a href="#"					id="pane9">결산세부정보</a></li>
							<%}%>
							<%if((projectRole.equals("PM") || projectRole.equals("PL") || projectRole.equals("AR") || projectRole.equals("QM"))&&!jobclass.equals("C")){%>
								<li><a href="#"					id="pane5">성과급관리</a></li>
							<%}%>
							<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
								<%if(!jobclass.equals("C")){%>
									<li><a href="#"					id="pane7">산출물</a></li>
								<%}%>
								<c:if test="${project.businessTypeCode == 'BTA'}">
									<li><a href="#"					id="pane8">VOC</a></li>
								</c:if>
									<li><a href="#"					id="pane12">세금계산서</a></li>
							</c:if>
							<% } else if(request.getAttribute("viewMode").equals("projectSearch")){ %>
								<li class="current"><a href="#" class="current"	 id="pane1">기본정보</a></li>
								<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
									<li><a href="#" 				id="pane2">일정정보</a></li>
								</c:if>
							<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
								<c:if test="${project.projectTypeCode != 'MM'}">
									<li><a href="#" 				id="pane3">참여정보</a></li>
									<li><a href="#" 				id="pane11">프로젝트레포트</a></li>
								</c:if>
								<c:if test="${project.projectTypeCode == 'MM'}">
									<li><a href="#" 				id="pane10">참여정보</a></li>
									<li><a href="#" 				id="pane3">주간진척정보</a></li>
								</c:if>		
							</c:if>					
							<%if(projectRole.equals("PM") || !divRole.equals("") || position.startsWith("0")){%>
								<!-- <li><a href="#"					id="pane4">예결산정보</a></li> -->
								<li><a href="#"					id="pane9">결산세부정보</a></li>
							<%}%>
							<%if(projectRole.equals("PM") || position.startsWith("0")){%>
								<li><a href="#"					id="pane5">성과급정보</a></li>
							<%}%>
							<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
									<li><a href="#"					id="pane7">산출물</a></li>
								<c:if test="${project.businessTypeCode == 'BTA'}">
									<li><a href="#"					id="pane8">VOC</a></li>
								</c:if>
									<li><a href="#"					id="pane12">세금계산서</a></li>
							</c:if>
							<% } %> 
					</ul>
					<div id="Process_container">
						<div id="Process_overlay" class="overlay" style="display: none">
							<img alt="" src="/images/loading.gif" align="middle">
						</div>
						<div id="Process" class="pane"></div>
					</div>
					<p></p>
							<script type="text/javascript">
							var Tab = new ProjectInfoTabbedPane('Process', 
								{
<% if(request.getAttribute("viewMode").equals("register")){ %>
		 'pane1': '/action/ProjectMasterInfoAction.do?mode=getProjectMasterInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=register<%=reqStr%>&isRefresh=<%=isRefresh%>'
		,'pane2': '/action/ProjectProgressInfoAction.do?mode=getProjectProgressInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=register<%=reqStr%>'
	<c:if test="${project.projectTypeCode != 'MM'}">
		,'pane3': '/action/ProjectReportInfoAction.do?mode=getProjectReportInfoList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=register<%=reqStr%>'
	</c:if>
	<c:if test="${project.businessTypeCode == 'BTA'}">
		,'pane5': '/action/ProjectVocAction.do?mode=selectVocInfoList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=register<%=reqStr%>'
	</c:if>
<% } else if(request.getAttribute("viewMode").equals("myProject")){ %>
		 'pane1': '/action/ProjectMasterInfoAction.do?mode=getProjectMasterInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>&projectRole=<%=projectRole%>'
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		<%if(projectRole.equals("PM") || projectRole.equals("PL") || projectRole.equals("AR")){%>
			,'pane2': '/action/ProjectProgressInfoAction.do?mode=getProjectProgressInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
		<%}else{%>
			,'pane2': '/action/ProjectProgressInfoAction.do?mode=getProjectProgressInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>&readonly=true'
		<%}%>
	</c:if>
	<%if(!projectRole.equals("NOMB")){%>
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		<c:if test="${project.projectTypeCode != 'MM'}">
			,'pane3': '/project/preport/projectReportListFrame.jsp?mode=getProjectReportInfoList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
			,'pane11': '/action/MonthlyReportAction.do?mode=getMonthlyReportList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=myProject<%=reqStr%>'
		</c:if>
		<c:if test="${project.projectTypeCode == 'MM'}">
			,'pane10': '/project/manpower/projectManpowerListFrame.jsp?mode=getProjectManpowerList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
			,'pane3': '/action/WeeklyReportAction.do?mode=getWeeklyReportList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=myProject<%=reqStr%>'
		</c:if>
	</c:if>
	<%}%>
	<%if((projectRole.equals("PM") || projectRole.equals("PL") || projectRole.equals("AR") || projectRole.equals("QM") || position.equals("08CF") || position.equals("09CJ") || position.equals("10TM"))&&!jobclass.equals("C")){%>
		<%-- ,'pane4': '/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>' --%>
		,'pane9': '/project/expense/projectExpenseHistoryDetailFrame.jsp?mode=projectExpenseHistoryDetail&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
	<%}%>
	<%if((projectRole.equals("PM") || projectRole.equals("PL") || projectRole.equals("AR") || projectRole.equals("QM"))&&!jobclass.equals("C")){%>
		,'pane5': '/action/ProjectExpenseAction.do?mode=projectExpenseManagement&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>&projectTypeCode=<c:out value="${project.projectTypeCode}" />'
	<%}%>
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		<%-- <c:if test="${project.realStartDate < '20200401'}">
			<c:if test="${project.projectTypeCode != 'MM'}">
				,'pane6': '/project/preport/projectReportListFrame.jsp?mode=getProjectReportList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
			</c:if>
		</c:if> --%>
		<%if(!jobclass.equals("C")){%>
			,'pane7': '/project/attach/projectAttachListFrame.jsp?mode=selectList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
		<%}%>
		<c:if test="${project.businessTypeCode == 'BTA'}">
			,'pane8': '/action/ProjectVocAction.do?mode=selectVocInfoList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=myProject<%=reqStr%>'
		</c:if>
			//,'pane12': '/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode=<%= request.getAttribute("projectCode") %>'
			,'pane12': '/project/endProcess/endingForOutputUpload2Frame.jsp?mode=getProjectReportList&projectCode=<%= request.getAttribute("projectCode") %>'					
	</c:if>
<% } else if(request.getAttribute("viewMode").equals("projectSearch")){ %>
	<%if(position.startsWith("0")){%>
		'pane1': '/action/ProjectMasterInfoAction.do?mode=getProjectMasterInfo&projectCode=<%= request.getAttribute("projectCode") %>&projectRole=PM&viewMode=projectSearch<%=reqStr%>'
	<%} else {%>
		'pane1': '/action/ProjectMasterInfoAction.do?mode=getProjectMasterInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
	<%}%>
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		,'pane2': '/action/ProjectProgressInfoAction.do?mode=getProjectProgressInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
	</c:if>
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		<c:if test="${project.projectTypeCode != 'MM'}">
			,'pane3': '/project/preport/projectReportListFrame.jsp?mode=getProjectReportInfoList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
			,'pane11': '/action/MonthlyReportAction.do?mode=getMonthlyReportList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
		</c:if>
		<c:if test="${project.projectTypeCode == 'MM'}">
		,'pane10': '/project/manpower/projectManpowerListFrame.jsp?mode=getProjectManpowerList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
		,'pane3': '/action/WeeklyReportAction.do?mode=getWeeklyReportList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
		</c:if>
	</c:if>
	<%if(projectRole.equals("PM") || !divRole.equals("") || position.startsWith("0")){%>
		<%-- ,'pane4': '/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>' --%>
		,'pane9': '/project/expense/projectExpenseHistoryDetailFrame.jsp?mode=projectExpenseHistoryDetail&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
	<%}%>
	<%if(projectRole.equals("PM") || position.startsWith("0")){%>
		,'pane5': '/action/ProjectExpenseAction.do?mode=projectExpenseHistory&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>&projectTypeCode=<c:out value="${project.projectTypeCode}" />'
	<%}%>
	<c:if test="${!(project.attach == 'Y'  && project.projectCode == project.parentProjectCode )}" >
		<%-- <c:if test="${project.realStartDate < '20200401'}">
			<c:if test="${project.projectTypeCode != 'MM'}">
			,'pane6': '/project/preport/projectReportListFrame.jsp?mode=getProjectReportList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
			</c:if>
		</c:if> --%>
			,'pane7': '/project/attach/projectAttachListFrame.jsp?mode=selectList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
		<c:if test="${project.businessTypeCode == 'BTA'}">
			,'pane8': '/action/ProjectVocAction.do?mode=selectVocInfoList&projectCode=<%= request.getAttribute("projectCode") %>&viewMode=projectSearch<%=reqStr%>'
		</c:if>
			//,'pane12': '/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode=<%= request.getAttribute("projectCode") %>'
			,'pane12': '/project/endProcess/endingForOutputUpload2Frame.jsp?mode=getProjectReportList&projectCode=<%= request.getAttribute("projectCode") %>'					
	</c:if>
<% } %>
								},
								{
									onClick: function(e) {
										$('Process_overlay').show();
									},
									onSuccess: function(e) {
										$('Process_overlay').hide();
									}
								});
						</script>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
