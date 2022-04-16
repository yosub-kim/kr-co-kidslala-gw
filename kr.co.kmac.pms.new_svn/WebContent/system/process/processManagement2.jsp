<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function goProcessTemplateDetail(processTemplateCode){
	document.location = "/action/ProcessTemplateAction.do?mode=getProcessTemplateDetail&processTemplateCode="+processTemplateCode;
}

function goProcessCategoryDetail(obj) {
	var win = new Window('modal_window', {
		className : "dialog",
		title : "프로세스 등록",
		top : event.y,
		left : 50,
		width : 664,
		height : 350,
		zIndex : 150,
		opacity : 1,
		resizable : true,
		showEffectOptions : {duration : 0.1},
		hideEffectOptions : {duration : 0.1},		
		url : "/action/ProcessCategoryAction.do?mode=getProcessCategory&processCategoryCode="+obj
	});
	win.show(true);
	win.setDestroyOnClose();
}

function openProcessTempalte(){
	Dialog.confirm(
		$('processTemplateCreateDiv').innerHTML,
		{
			windowParameters : {
				width : 370,
				height : 100
			},
			okLabel : "Save",
			cancel : function(win) {
			},
			ok : function(win) {
				var ActionURL = "/action/ProcessTemplateAction.do?mode=createProcessTemplate";
				var sFrm = document.forms["processTemplateForm"];
				var status = AjaxRequest.post (
						{	'url':ActionURL,
							'parameters' : { "processTemplateCode": $("processTemplateCode").value, "processTemplateName" : $("processTemplateName").value },
							'onSuccess':function(obj){
								alert('성공하였습니다.');
								document.location = "/system/process/processManagement.jsp?pane=template#";
							},
							'onLoading':function(obj){},
							'onError':function(obj){
								alert("저장할 수 없습니다.");
							}
						}
				); status = null;					
				return true
			}
		}
	);
}

function searchTemplateList() {
	var ActionURL = "/action/ProcessTemplateAction.do?mode=searchProcessTemplateList";
	var sFrm = document.forms["searchTemplateForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.processTemplateList;		
					var table = $('templateTable');
					var tbody = $('templateTableBody').childElements();
		           	var tdCount = $('templateTableHeader').down('tr').childElements().size();

		           	tbody.each( function(template){
		           		template.remove();
		    		});
		    			
 		    		rsObj.each(function(template){
			    	    var newRow=table.insertRow(-1);
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}		

		    			newCellArr[0].innerHTML = template.processTemplateCode;
		    			newCellArr[1].innerHTML = template.processTemplateName;
		    			newCellArr[2].innerHTML = "<a title='수정' class='btn006bc6 pb5 pt5 txt2btn' href='#' onclick=\"goProcessTemplateDetail('"+template.processTemplateCode+"')\">수정</a>";
		    			
		    			newCellArr[1].className = "myoverflow";
		    		});
 		    		$("processTemplateTotCnt").innerHTML = res.processTemplateList.length;
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;			
}
function doSearch() {
	var ActionURL = "/action/ProcessCategoryAction.do?mode=searchProcessCategoryList";
	var sFrm = document.forms["searchCategoryForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.processCategoryList;		
					var table = $('categoryTable');
					var tbody = $('categoryTableBody').childElements();
		           	var tdCount = $('categoryTableHeader').down('tr').childElements().size();

		           	tbody.each( function(category){
		    			category.remove();
		    		});
		    			
		    		rsObj.each(function(category){
			    	    var newRow=table.insertRow(-1);			    	    
			    	    newRow.style.height="30";
			    	    
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}
			    		

		    			newCellArr[0].innerHTML = category.runningDivName;
		    			newCellArr[1].innerHTML = category.businessTypeName;
		    			newCellArr[2].innerHTML = category.businessFunctionName.replace('&', '&amp;');
		    			newCellArr[3].innerHTML = category.processCategoryName;
		    			newCellArr[4].innerHTML = category.processTemplateName;
		    			newCellArr[5].innerHTML = "<a title='수정' class='btN006bc6' style='padding: 5px 10px' href='#' onclick=\"goProcessCategoryDetail('"+category.processCategoryCode+"')\" />수정</a>";
		    			
		    			//newCellArr[0].className = "BGC";
		    			//newCellArr[1].className = "BGC";
		    			//newCellArr[2].className = "BGC"; 
		    			newCellArr[3].className = "myoverflow";
		    			newCellArr[4].className = "myoverflow";
		    			//newCellArr[5].className = "BGC";
		    			
		    			
		    		});	
		    		$("processCategoryTotCnt").innerHTML = res.processCategoryList.length;
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;			
}
</script>
</head>

<body>
	<div id="processTemplateCreateDiv" style="display: none;">
		<table width="370" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
			<tr>
				<td width="370" height="32" colspan="2"><h4 class="subTitle">새 프로세스 템플릿의 코드와 이름을 입력하세요.</h4></td>
			</tr>
			<tr>
				<td class="detailTableTitle_center" width="100">템플릿 코드</td>
				<td class="detailTableField_left" width="270"><input name="processTemplateCode" id="processTemplateCode" value="" class="contentInput_left" style="width: 99%;ime-mode:inactive;"/></td>
			</tr>
			<tr>
				<td class="detailTableTitle_center">템플릿 명</td>
				<td class="detailTableField_left"><input name="processTemplateName" id="processTemplateName" value="" class="contentInput_left" style="width: 99%;"/></td>
			</tr>
		</table>
	</div>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td height="12">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="공공혁신부문 PJT 공유방" />
				</jsp:include>
			</td>
		</tr>
<!-- 본문시작-->

		<tr>
			<td>
				<div class="tabbed-pane">
					<ol class="tabs">
						<li><a href="#" class="active" 	id="pane1">프로젝트 공유 게시판</a></li>
						<li><a href="#"					id="pane2">디렉터 투입현황</a></li>
					</ol>
					<div id="Process_container" class="tabbed-container">
						<div id="Process_overlay" class="overlay" style="display: none">
							<img alt="" src="/images/loading.gif" align="middle" >	 
						</div>
						<div id="Process" class="pane">
						</div>
					</div>
			
					<p></p>
				
					<script type="text/javascript">
						new TabbedPane('Process', 
							{
								'pane1': '/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_project2',
								'pane2': '/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_project'
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
			</td>
		</tr>
<!-- 본문종료-->
	</table>

</body>
</html>