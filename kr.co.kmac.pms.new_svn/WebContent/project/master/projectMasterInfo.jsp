<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.Calendar"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<style>

/* Hide Column */
	.hideme {display:none}

</style>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>


<%
	boolean canSanction = true;
%>
</head>

<script type="text/javascript">
function canSanction(){
	var from = '<c:out value="${canSanction.data1}" />';
	var to = '<c:out value="${canSanction.data2}" />';
	var toDate = '<%= Calendar.getInstance().get(Calendar.DATE)%>';
	if(from > toDate && to < toDate){
		$('gg').innerHTML = '[알림] 해당 월의 성과급 품의는 <c:out value="${canSanction.data1}" />일부터 가능합니다.';		
	} 
} 
</script>

<script type="text/javascript">
j$(document).ready(function () {

    var tag = {};
    var counter = 0;

    // 태그를 추가한다.
    function addTag (value) {
        tag[counter] = value; // 태그를 Object 안에 추가
        counter++; // counter 증가 삭제를 위한 del-btn 의 고유 id 가 된다.
        
        j$.ajax({
            url:'/action/HashTagAction.do?mode=addHashTag',
            type:'post', 
            data:{ "hashTag": value, "projectCode":j$("#projectCode").val(), "tagType": "ByUSER"},
            success: function(data) {
            },
            error: function(err) {
            	alert(err);
            }
        });
    }

    /*/ 최종적으로 서버에 넘길때 tag 안에 있는 값을 array type 으로 만들어서 넘긴다.
    function marginTag () {
        return Object.values(tag).filter(function (word) {
            return word !== "";
        });
    }*/

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
            url:'/action/HashTagAction.do?mode=deleteHashTag',
            type:'post', 
            data:{ "uuid":index, "projectCode":j$("#projectCode").val()},
            success: function(data) {
            },
            error: function(err) {
            	alert(err);
            }
        });
    });
})
</script>

<body>
	<form name="projectMasterInfoForm" method="post">
		<div style="display: none;">
			<input type="hidden" value="" name="hashTag" id="hashTag" />
			<input type="text" name="entNo" id="entNo" value="<c:out value="${project.entNo}"/>" >
			<input type="text" name="projectCode" id="projectCode" value="<c:out value="${project.projectCode}"/>" >
			<input type="text" name="projectState" id="projectState" value="<c:out value="${project.projectState}" default="1" />" >
			<input type="text" name="viewMode" id="viewMode" value="<c:out value="${viewMode}" />" >
			<input type="text" name="businessTypeCode" id="businessTypeCode" value="<c:out value="${project.businessTypeCode}" />" >
			<input type="text" name="projectTypeCode" id="projectTypeCode" value="<c:out value="${project.projectTypeCode}" />" >
			<input type="text" name="isRefresh" id="isRefresh" value="<c:out value="${isRefresh}" />" >
			<input type="text" name="isRegisteredProject" id="isRegisteredProject" value="<c:out value="${isRegisteredProject}" />" >
			<!-- By-Pass -->
			<input type="text" name="projectCodeBp" id="projectCodeBp" value="<c:out value="${project.parentProjectCode}" />" >
			<input type="text" name="func" id="func" value="<c:out value="${project.attach}" />" >		
			<input type="hidden" name="orgCodes" id="orgCodes" value="<c:out value="${orgdbCodes}"/>">
			<input type="text" name="orgNames" id="orgNames" value="<c:out value="${orgdbNames}"/>" class="textInput_left" style="width: 100%" readonly>
		</div>
		
		<!-- 프로젝트 기본 정보 -->
		<div id="tab_menu_content">
		<div class="board_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">프로젝트 기본 정보</p>
				</div>
			</div>
			<div class="board_contents">
				<table class="tbl-edit">
					<colgroup>
						<col style="width: 12%" />
						<col style="width: 38%" />
						<col style="width: 12%" />
						<col style="width: 38%" />
					</colgroup>
					<tbody>
						<tr>
							<th>프로젝트 명</th>
							<td colspan="3">[<c:out value="${project.projectCode}" />] <c:out
									value="${project.projectName}" /></td>
						</tr>
						<tr>
							<th>조직단위</th>
							<td><org:group id="${project.runningDeptCode}" /></td>
							<th>비즈니스 유형</th>
							<td><code:code tableName="BUSINESS_TYPE_CODE"
									code="${project.businessTypeCode}" /></td>
						</tr>
						<tr>
							<th>산업별 구분</th>
							<td><code:code tableName="INDUSTRY_TYPE_CODE"
									code="${project.industryTypeCode}" /></td>
								<th>프로젝트 규모</th>
							<td colspan="3">
								<c:choose>
									<c:when test="${project.projectScale == 'L' || project.projectStateDetail == 'L'}">대</c:when>
									<c:when test="${project.projectScale == 'M' || project.projectStateDetail == 'M'}">중</c:when>
									<c:when test="${project.projectScale == 'S' || project.projectStateDetail == 'S'}">소</c:when>
								</c:choose> 
								<input type="hidden" value="<c:out value="${project.projectScale }"/>" name="projectStateDetail" id="projectStateDetail">
							</td>
						</tr>
						<c:choose>
							<c:when test="${viewMode == 'register' && (project.projectState == '1' || project.projectState == null)}">
								<c:choose>
									<c:when test="${project.businessTypeCode eq 'BTA' }">
										<tr>
											<th>프로젝트 분야</th>
											<td><select name="businessFunctionType"
												id="businessFunctionType" class="selectboxPopup"
												onchange="businessFunctionTypeChange(this.value, '<c:out value="${project.runningDeptCode}"/>', '<c:out value="${project.businessTypeCode}"/>');"
												style="width: 100%"
												<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
													<option value="">프로젝트 분야을 선택하세요</option>
													<c:forEach var="rs" items="${businessFunctionTypeList}">
														<option value="<c:out value="${rs.key1}"/>"
															<c:if test="${rs.key1==project.businessFunctionType}">selected</c:if>><c:out
																value="${rs.data1}" /></option>
													</c:forEach>
											</select></td>
											<th>프로세스 유형</th>
											<td>
												<input type="hidden" name="_processTemplateCode" id="_processTemplateCode" value="" /> 
												<input type="hidden" name="_businessFunctionType" id="_businessFunctionType" value="" /> 
												<select name="projectDetailCode" id="projectDetailCode" class="selectboxPopup" style="width: 100%" onchange="projectDetailCodeChange(this.value);"
												<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
													<option value="">-- 프로세스 유형을 선택하세요--</option>
													<c:forEach var="rs" items="${processCategoryList}">
														<option value="<c:out value="${rs.processCategoryCode}"/>" <c:if test="${rs.processCategoryCode==project.projectDetailCode}">selected</c:if>>
															<c:out value="${rs.processCategoryName}" />
														</option>
													</c:forEach>
												</select>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th>프로세스 유형</th>
											<td colspan="3">
												<input type="hidden" name="_processTemplateCode" id="_processTemplateCode" value="" /> 
												<input type="hidden" name="_businessFunctionType" id="_businessFunctionType" value="" /> 
												<select name="projectDetailCode" id="projectDetailCode" class="selectboxPopup" style="width: 100%" onchange="projectDetailCodeChange(this.value);"
												<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
													<option value="">프로세스 유형을 선택하세요</option>
													<c:forEach var="rs" items="${processCategoryList}">
														<option
															processTemplateCode="<c:out value="${rs.processTemplateCode}"/>"
															businessFunctionType="<c:out value="${rs.businessFunctionType}"/>"
															value="<c:out value="${rs.processCategoryCode}"/>"
															<c:if test="${rs.processCategoryCode==project.projectDetailCode}">selected</c:if>><c:out
																value="${rs.processCategoryName}" /></option>
													</c:forEach>
												</select>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${project.businessTypeCode eq 'BTA' }">
										<tr>
											<th>프로젝트 분야</th>
											<td>
												<code:code tableName="BUSINESS_FUNCTION_TYPE" code="${project.businessFunctionType}" /> 
												<input type="hidden" value="<c:out value="${project.businessFunctionType}"></c:out>" name="businessFunctionType" id="businessFunctionType">
											</td>
											<th>프로세스 유형</th>
											<td>
												<code:code tableName="PROCESS_PRODUCT_CODE" code="${project.projectDetailCode}" /> 
												<input type="hidden" value="<c:out value="${project.projectDetailCode}"></c:out>" name="projectDetailCode" id="projectDetailCode"> 
												<input type="hidden" value="<c:out value="${project.processTypeCode}"></c:out>" name="processTemplateCode" id="processTemplateCode">
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th>프로세스 유형</th>
											<td colspan="3">
												<code:code tableName="PROCESS_PRODUCT_CODE" code="${project.projectDetailCode}" /> 
												<input type="hidden" value="<c:out value="${project.projectDetailCode}"></c:out>" name="projectDetailCode" id="projectDetailCode"> 
												<input type="hidden" value="<c:out value="${project.processTypeCode}"></c:out>" name="processTemplateCode" id="processTemplateCode">
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<tr>
							<th>프로젝트 운영기간</th>
							<fmt:parseDate value="${project.realStartDate}" type="DATE" dateStyle="SHORT" pattern="yyyyMMdd" var="fromdate" />
							<fmt:formatDate value="${fromdate}" pattern="yyyy.MM.dd" var="formattedFrom" />
							<fmt:parseDate value="${project.realEndDate}" type="DATE" dateStyle="SHORT" pattern="yyyyMMdd" var="todate" />
							<fmt:formatDate value="${todate}" pattern="yyyy.MM.dd" var="formattedTo" />
							<td><c:out value="${formattedFrom}" /> ~ <c:out value="${formattedTo}" /></td>
							<th>프로젝트 계약기간</th>
							<fmt:parseDate value="${project.realWorkFromDt}" type="DATE" dateStyle="SHORT" pattern="yyyyMMdd" var="fromdate" />
							<fmt:formatDate value="${fromdate}" pattern="yyyy.MM.dd" var="formattedFrom" />
							<fmt:parseDate value="${project.realWorkToDt}" type="DATE" dateStyle="SHORT" pattern="yyyyMMdd" var="todate" />
							<fmt:formatDate value="${todate}" pattern="yyyy.MM.dd" var="formattedTo" />
							<c:choose><c:when test="${empty project.realWorkFromDt }"><td>계약기간 미 입력</td></c:when>
								<c:when test="${project.realWorkFromDt eq '------' }"><td>계약기간 미 입력</td></c:when>
								<c:when test="${project.realWorkFromDt eq '--' }"><td>계약기간 미 입력</td></c:when>
							<c:otherwise><td><c:out value="${formattedFrom}" /> ~ <c:out value="${formattedTo}" /></td>
							</c:otherwise></c:choose>
						</tr>
						<tr>
							<th>계약 유형</th>
							<td>
								<ul class="chek_ui">
									<li><input type="radio" name="projectContractType" id="projectContractType" class="btn_radio"
										<c:if test="${project.projectContractType == 1}">checked="checked"</c:if>
										<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
										<label for="choose_1"><p>신규</p></label></li>
									<li><input type="radio" name="projectContractType" id="projectContractType" class="btn_radio"
										<c:if test="${project.projectContractType == 2}">checked="checked"</c:if>
										<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
										<label for="choose_2"><p>재계약</p></label></li>
									<li><input type="radio" name="projectContractType" id="projectContractType" class="btn_radio"
										<c:if test="${project.projectContractType == 3}">checked="checked"</c:if>
										<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
										<label for="choose_3"><p>기타</p></label></li>
								</ul>
							</td>
							<th>운영유형</th>
							<td>
								<c:choose>
									<c:when test="${project.attach == 'Y' && (project.projectCode == project.parentProjectCode) }">
										By-Pass
									</c:when>
									<c:otherwise>
										<code:code tableName="PROJECT_TYPE_CODE" code="${project.projectTypeCode}" />
										<input type="hidden" value="<c:out value="${project.projectTypeCode}"></c:out>" name="projectTypeCode" id="projectTypeCode">
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- // 프로젝트 기본 정보 -->

		<!-- 프로젝트 인력 정보 -->
		<div class="board_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">프로젝트 인력 정보</p>
				</div>
				<c:if test="${viewMode == 'register'}">
					<div class="text-R">
						<p>행 추가 후 컨설턴트 명 입력 > 엔터키를 치면 자동으로 인력이 추가 됩니다.&nbsp(동명이인은 팝업창에서 선택)</p>
						<button type="button" class="btn line btn_blue" onclick="javascript:addRowProjectMember();"><i class="mdi mdi-square-edit-outline"></i>행 추가</button>
					</div>
				</c:if>
			</div>
			<div class="board_contents">
				<table class="tbl-edit td-c" id="projectMemberTable">
					<!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 12%" />
						<col style="width: 18%" />
						<col style="width: 18%" />
						<%-- <c:choose><c:when test="${(projectRole != 'MB' and projectRole == 'NOMB')}"><col style="*" /></c:when><c:otherwise></c:otherwise></c:choose> --%>
						<c:choose><c:when test="${viewMode != 'register' && (projectRole == 'MB' || projectRole == 'NOMB')}">  </c:when><c:otherwise> <col style="width: 18%" /></c:otherwise></c:choose>
						<col style="width: 15%" />
						<col style="width: 15%" />
					</colgroup>
					<tbody id="projectMemberTableBody">
						<tr>
							<th>역할</th>
							<th>컨설턴트 명</th>
							<th>직위/직책</th>
							<%--<th <c:choose><c:when test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}"> class="hideme" </c:when><c:otherwise> style="width: 30px;" </c:otherwise></c:choose>>찾기 --%>
							<th <c:choose><c:when test="${viewMode != 'register' && (projectRole == 'MB' || projectRole == 'NOMB')}"> class="hideme" </c:when><c:otherwise>  style="width: *" </c:otherwise></c:choose>>총 성과급</th>
							<th>컨설턴트 정보</th>
							<th>삭제</th>
						</tr>
						<c:if test="${!empty project.projectMemberList }">
							<c:forEach var="rs" items="${project.projectMemberList}" varStatus="status">
								<tr>
									<td>
										<select style = "text-align:center;"  name="memberRole" id="memberRole" class="select" <c:if test="${viewMode != 'register'}"> disabled </c:if> style="width:100%">
											<option value="AR" <c:if test="${rs.role== 'AR'}">selected</c:if>>COO</option>
											<option value="QM" <c:if test="${rs.role== 'QM'}">selected</c:if>>QM</option>
											<option value="PM" <c:if test="${rs.role== 'PM'}">selected</c:if>>PM</option>
											<%--<c:if test="${project.projectState > '2'}">
													<option value="AA" <c:if test="${rs.role== 'AA'}">selected</c:if>>PU장</option>
													<option value="AG" <c:if test="${rs.role== 'AG'}">selected</c:if>>그룹장</option>
													<option value="QM" <c:if test="${rs.role== 'QM'}">selected</c:if>>QM</option>
												</c:if>--%>
											<c:if test="${project.businessTypeCode== 'BTA' or project.businessTypeCode== 'BTJ'}">
												<option value="PL" <c:if test="${rs.role== 'PL'}">selected</c:if>>PL</option>
												<%-- <option value="subPL" <c:if test="${rs.role== 'subPL'}">selected</c:if>>Part Leader</option> --%>
											</c:if>
											<c:if test="${project.businessTypeCode!= 'BTA' and project.businessTypeCode != 'BTJ' and project.projectState > '2'}">
												<option value="PL" <c:if test="${rs.role== 'PL'}">selected</c:if>>PL</option>
												<%-- <option value="partPL" <c:if test="${rs.role== 'partPL'}">selected</c:if>>Part Leader</option> --%>
											</c:if>
											<option value="MB" <c:if test="${rs.role== 'MB'}">selected</c:if>>MB</option>
										</select>
										<input type="hidden" name="memberTrainingYn" id="memberTrainingYn" value="<c:out value="${rs.trainingYn}"/>">
										<input type="hidden" name="projectMemberCheck" id="projectMemberCheck">
									</td>
									
									<td>
										<input type="hidden" name="memberSsn" id="memberSsn" value="<c:out value="${rs.ssn}"/>">
										<c:choose>
											<c:when test="${project.projectState=='2'}">
												<input type="text" style = "text-align:center;" name="memberName" value="<c:out value="${rs.name}"/>" onKeypress="javascript:if(event.keyCode==13) {openExpertPoolPopUpForProjectMaster(this);}"/>
											</c:when>
											<c:otherwise>
												<input type="text" style = "text-align:center;" name="memberName" value="<c:out value="${rs.name}"/>" onKeypress="javascript:if(event.keyCode==13) {openExpertPoolPopUpForProjectMaster(this);}"/>
											</c:otherwise>
										</c:choose>
									</td>
									
									<c:choose>
										<c:when
											test="${!isRegisteredProject && (rs.jobClass == 'A' || rs.jobClass == 'J' || rs.jobClass == 'C' || rs.jobClass == 'H' || rs.jobClass == 'B')}">
											<td>
												<input type="hidden" name="memberPosition" id="memberPosition" value="<c:out value="${rs.position}"/>">
												<input type="text" style = "text-align:center;" name="memberPositionName" value="<code:code tableName="POSITION_KIND" code="${rs.position}"/>" readonly>
											</td>
										</c:when>
										<c:otherwise>
											<td>
												<input type="hidden" name="memberPosition" id="memberPosition" value="<c:out value="${rs.position}"/>">
												<input type="text" style = "text-align:center;" name="memberPositionName" value="<c:out value="${rs.position}"/>" readonly>
											</td>
										</c:otherwise>
									</c:choose>

									<input type="hidden" name="memberJobClass" id="memberJobClass" value="<c:out value="${rs.jobClass}"/>">
									<input type="hidden" name="memberResRate" id="memberResRate" value="<c:out value="${rs.resRate}"/>" style="ime-mode: disabled;" onchange="Money_Only(this, -1, -1);"
										<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>>
									
									<td <c:choose><c:when test="${viewMode != 'register' && (projectRole == 'MB' || projectRole == 'NOMB')}"> class="hideme" </c:when><c:otherwise> style="width: *" </c:otherwise></c:choose>>
									<input class="a-r" type="text" name="memberCost" id="memberCost"
										minAmt="<c:out value="${rs.minAmt}"/>"
										maxAmt="<c:out value="${rs.maxAmt}"/>"
										minEdu="<c:out value="${rs.minEdu}"/>"
										maxEdu="<c:out value="${rs.maxEdu}"/>"
										maxMMAmt="<c:out value="${rs.maxMMAmt}"/>"
										minMMAmt="<c:out value="${rs.minMMAmt}"/>"
										businessTypeCode="<c:out value="${project.businessTypeCode }"/>"
										projectTypeCode="<c:out value="${project.projectTypeCode }"/>" onchange="getNumber(this);" onkeyup="getNumber(this);"
										value="<fmt:formatNumber value="${rs.cost}" type="number" var="restAmount" /><c:out value="${restAmount}" />"
										<c:if test="${((viewMode != 'register' && project.projectState > '2') && viewMode != 'myProject') || (project.projectState > '3' && project.adminOpen == 'N')}">readonly</c:if>>
									</td>
									<!-- 기여금액 주석처리 20190926 -->
									<%-- <input type="hidden" name="memberContributionCost"
										id="memberContributionCost"
										value="<c:out value="${rs.contributionCost}"/>"
										class="textInput_right"
										style="width: 100%; ime-mode: disabled;"
										onchange="Money_Only(this, -1, -1);"
										<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>> --%>

									<!-- 프로젝트 운영기간 주석 -->
									<c:choose>
										<c:when
											test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">
											<c:choose>
												<c:when test="${rs.jobClass == 'C' || rs.jobClass == 'D' || rs.jobClass == 'E'}">
													<script>
													
													</script>
												</c:when>
												<c:otherwise>
													<script>
														$("memberStartDate<c:out value="${status.index}"/>").disabled = true;
														$("memberEndDate<c:out value="${status.index}"/>").disabled = true;
													</script>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when
													test="${rs.jobClass == 'C' || rs.jobClass == 'D' || rs.jobClass == 'E'}">
													<script>
														jQuery(function(){jQuery( "#memberStartDate<c:out value="${status.index}"/>" ).datepicker({showOn: 'focus', 'disabled': true});});
														jQuery(function(){jQuery( "#memberEndDate<c:out value="${status.index}"/>" ).datepicker({showOn: 'focus', 'disabled': true});});
													</script>
												</c:when>
												<c:otherwise>
													<script>
														jQuery(function(){jQuery( "#memberStartDate<c:out value="${status.index}"/>").datepicker({showOn: 'focus', 'disabled': true});});
														jQuery(function(){jQuery( "#memberEndDate<c:out value="${status.index}"/>"  ).datepicker({showOn: 'focus', 'disabled': true});});
													</script>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									<input type="hidden" name="memberStartDate"
										id="memberStartDate<c:out value="${status.index}"/>"
										value="<c:out value="${rs.startDate}"/>"
										<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>>
									<input type="hidden" name="memberEndDate"
										id="memberEndDate<c:out value="${status.index}"/>"
										value="<c:out value="${rs.endDate}"/>"
										<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>>
									<%--
									<td
										<c:choose><c:when test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}"> class="hideme" </c:when><c:otherwise> style="width: 30px;" </c:otherwise></c:choose>><button
											type="button" class="btn line btn_black"
											onclick="location.href='javascript:openExpertPoolPopUpForProjectMaster(this);'">
											<i class="mdi mdi-account-search"></i>검색</td>
									 --%>	
									<td>
										<c:choose><c:when test="${rs.ssn != '' }"><button type="button" class="btn line btn_black" onclick="detailview('<c:out value="${rs.ssn}"/>')"><i class="mdi mdi-account-search"></i>조회</c:when><c:otherwise>-</c:otherwise></c:choose>
									</td>
									<td>
										<div class="btn_area">
											<c:choose><c:when test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}"> - </c:when>
											<c:otherwise>
												<button type="button" class="btn line btn_pink	" onclick="deleteRowProjectMember(this);"><i class="mdi mdi-trash-can-outline"></i>행 삭제</button>
											</c:otherwise></c:choose>
										</div>
									</td>
									<!-- 투입률 등 이전 코드 주석 -->
									<%-- <td <c:choose><c:when test="${viewMode != 'register' && (projectRole == 'MB' || projectRole == 'NOMB')}"> class="hideme" </c:when><c:otherwise> style="width: 70px;" </c:otherwise></c:choose>
												><input type="hidden" name="memberJobClass" id="memberJobClass" value="<c:out value="${rs.jobClass}"/>"><input type="text" name="memberResRate" id="memberResRate" value="<c:out value="${rs.resRate}"/>" class="textInput_center" style="width:100%;ime-mode:disabled;" onchange="Money_Only(this, -1, -1);" readonly></td>
											<td <c:choose><c:when test="${viewMode != 'register' && (projectRole == 'MB' || projectRole == 'NOMB')}"> class="hideme" </c:when><c:otherwise> style="width: 120px;" </c:otherwise></c:choose>
												><input type="text" name="memberCost" id="memberCost" minAmt="<c:out value="${rs.minAmt}"/>" maxAmt="<c:out value="${rs.maxAmt}"/>" minEdu="<c:out value="${rs.minEdu}"/>" maxEdu="<c:out value="${rs.maxEdu}"/>" maxMMAmt="<c:out value="${rs.maxMMAmt}"/>" minMMAmt="<c:out value="${rs.minMMAmt}"/>" businessTypeCode="<c:out value="${project.businessTypeCode }"/>" projectTypeCode="<c:out value="${project.projectTypeCode }"/>" value="<c:out value="${rs.cost}"/>" class="textInput_right" style="width:100%;ime-mode:disabled;" onchange="Money_Only(this, 12000000, -1);Check_MoneyAmt(this);" readonly></td>
											<td <c:choose><c:when test="${viewMode != 'register' && (projectRole == 'MB' || projectRole == 'NOMB')}"> class="hideme" </c:when><c:otherwise> style="width: 120px;" </c:otherwise></c:choose>
												><input type="text" name="memberContributionCost" id="memberContributionCost" value="<c:out value="${rs.contributionCost}"/>" class="textInput_right" style="width:100%;ime-mode:disabled;" onchange="Money_Only(this, -1, -1);" readonly></td>
											<td align="center" <c:choose><c:when test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}"> class="hideme" </c:when><c:otherwise> style="width: 30px;" </c:otherwise></c:choose>
												><img alt="편집" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="openExpertPoolPopUpForProjectMaster(this);"></td> --%>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty project.projectMemberList}">
							<tr>
								<td class="detailTableField_center" colspan="8">검색된 데이터가 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<!-- // 프로젝트 인력 정보 -->

		<!-- 프로젝트 고객사 정보 -->
		<c:if
			test="${project.businessTypeCode == 'BTA' || project.businessTypeCode == 'BTJ' || project.businessTypeCode == 'BTD' || project.businessTypeCode == 'BTF'  
							|| (project.businessTypeCode== 'BTE')}">
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 고객사 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit pd-none">
						<colgroup>
							<col style="width: 12%" />
							<col style="width: 18%" />
							<col style="width: 12%" />
							<col style="width: 18%" />
							<col style="width: 12%" />
							<col style="width: 18%" />
							<col style="width: 12%" />
						</colgroup>
						<tbody>
							<tr>
								<th>고객사 명</th>
								<td colspan="6"><c:out value="${project.customerName}" /></td>
							</tr>
							<tr>
								<th>업무 담당자</th>
								<td><input type="text" id="customerWorkPName"
									name="customerWorkPName"
									value="<c:out value="${project.customerWorkPName}"/>"
									class="contentInput_left"
									<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>></td>
								<th>E-Mail</th>
								<td><input type="text" id="customerWorkPEmail"
									name="customerWorkPEmail" style="ime-mode: disabled;"
									value="<c:out value="${project.customerWorkPEmail}"/>"
									class="contentInput_left"
									<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>></td>
								<th>전화번호</th>
								<td><input type="text" id="customerWorkPPhone"
									name="customerWorkPPhone"
									value="<c:out value="${project.customerWorkPPhone}"/>"
									class="contentInput_left"
									<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != null)}">readonly</c:if>></td>
								<td class=a-c><c:if
										test="${(viewMode=='register' || viewMode=='myProject') && (project.projectState=='1' || project.projectState==null || project.projectState=='3') && (projectRole!='MB')}">
										<button type="button" class="btn line btn_black"
											onclick="openDMListPopUp('setCustomerWork')">
											<i class="mdi mdi-account-search"></i>검색
										</button>
									</c:if></td>
								<!-- 영역의 가운데 정렬 .a-c -->
							</tr>
							<tr>
								<th>계약 담당자</th>
								<td><input type="text" id="customerContPName"
									name="customerContPName"
									value="<c:out value="${project.customerContPName}"/>"
									class="contentInput_left"
									<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != '4' && project.projectState != null)}">readonly</c:if>></td>
								<th>E-Mail</th>
								<td><input type="text" id="customerContPEmail"
									name="customerContPEmail" style="ime-mode: disabled;"
									value="<c:out value="${project.customerContPEmail}"/>"
									class="contentInput_left"
									<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != '4' && project.projectState != null)}">readonly</c:if>></td>
								<th>전화번호</th>
								<td><input type="text" id="customerContPPhone"
									name="customerContPPhone"
									value="<c:out value="${project.customerContPPhone}"/>"
									class="contentInput_left"
									<c:if test="${(viewMode != 'register' && viewMode != 'myProject') || (project.projectState != '1' && project.projectState != '3' && project.projectState != '4' && project.projectState != null)}">readonly</c:if>></td>
								<td class=a-c><c:if
										test="${(viewMode=='register' || viewMode=='myProject') && (project.projectState=='1' || project.projectState==null || project.projectState=='3' || project.projectState=='4') && (projectRole!='MB')}">
										<button type="button" class="btn line btn_black"
											onclick="openDMListPopUp('setCustomerCont')">
											<i class="mdi mdi-account-search"></i>검색
										</button>
									</c:if></td>
								<!-- 영역의 가운데 정렬 .a-c -->
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
		<!-- // 프로젝트 고객사 정보 -->
		
		<!-- 프로젝트 부가 정보 -->
		<c:if test="${project.businessTypeCode=='BTA'|| project.businessTypeCode=='BTJ' || project.businessTypeCode=='BTD'}">
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 부가 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit">
						<colgroup>
							<col style="width: 12%" />
							<col style="width: 38%" />
							<col style="width: 12%" />
							<col style="width: 38%" />
						</colgroup>
						<tbody>
							<tr>
								<c:if test="${project.businessTypeCode== 'BTA'}">
									<th>VOC 실시</th>
									<td>
										<div class="td-L">
											<!-- td 안에 여러 요소가 들어갈 경우 .td-l -->
											<select id="isVoc" name="isVoc" class="select" onchange="vocValueChange()"
												<c:if test="${viewMode != 'register' && viewMode != 'myProject'}">disabled</c:if>>
												<option value="Y" <c:if test="${project.isVoc=='Y' }">selected</c:if>>예</option>
												<option value="N" <c:if test="${project.isVoc == null || project.isVoc=='N' }">selected</c:if>>아니오</option>
											</select>
											<span id="vocType" <c:if test="${(project.isVoc == null || project.isVoc=='N')}">style="display: none;"</c:if>>
												<c:if test="${(viewMode eq 'register' && (project.projectState eq '1' || project.projectState eq null) ) || (viewMode eq 'myProject' && projectRole ne 'MB' && (project.projectState eq '3' || project.projectState eq '4')) }">
													<button type="button" class="btn line btn_blue" onclick="openRegistProjectEvalPopUp('<c:out value="${project.projectCode}"/>','<c:out value="${project.projectState}"/>');">
														<i class="mdi mdi-square-edit-outline"></i>일정등록
														<%-- registProjectVocPopUp('<c:out value="${project.projectCode}"/>') --%>
													</button>
												</c:if>
											</span>
										</div>
									</td>
								</c:if>
								<c:if test="${project.businessTypeCode== 'BTA' || project.businessTypeCode== 'BTD' || project.businessTypeCode== 'BTJ'}">
									<th>고객만족도조사 실시</th>
									<td colspan="3"><select id="isEvaluate" name="isEvaluate" class="select" disabled>
											<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>
											<option value="Y" <c:if test="${project.isEvaluate==null || project.isEvaluate=='Y' }">selected</c:if>>예</option>
											<option value="N" <c:if test="${project.isEvaluate=='N' }">selected</c:if>>아니오</option>
									</select>&nbsp
									<button type="button" class="btn line btn_blue" onclick="registProjectEvalPopUp('<c:out value="${project.projectCode}"/>', '<c:out value="${project.projectState}"/>');">
														<i class="mdi mdi-square-edit-outline"></i>추가등록
									</button> &nbsp(평가 고객사가 다수일 경우 추가)
									</td>
								</c:if>
								<!-- 설문 언어 주석 -->
								<%-- <th>설문 언어</th>
								<td><select class="select" name="lang" id="lang"
									style="width: 100%;"
									onchange="if($('lang').value=='KOR'){$('langTd1').hide();$('langTd2').hide();$('langTd1').innerHTML='';}else{ $('langTd1').show();$('langTd2').show();if($('lang').value=='ENG'){$('langTd1').innerHTML='영어 프로젝트명 ';}else if($('lang').value=='CHN'){$('langTd1').innerHTML='중국어 프로젝트명 ';}else if($('lang').value=='JPN'){$('langTd1').innerHTML='일본어 프로젝트명 ';}}"
									<c:if test="${viewMode != 'register' || (project.projectState != '1' && project.projectState != null)}">disabled</c:if>>
										<option value="KOR"
											<c:if test="${project.lang eq 'KOR' or empty project.lang}">selected</c:if>>한국어</option>
										<option value="ENG"
											<c:if test="${project.lang eq 'ENG'}">selected</c:if>>영어</option>
										<option value="CHN"
											<c:if test="${project.lang eq 'CHN'}">selected</c:if>>중국어</option>
										<option value="JPN"
											<c:if test="${project.lang eq 'JPN'}">selected</c:if>>일본어</option>
								</select></td> --%>
								<input id="langTd2" type="hidden" name="projectSubName" class="textInput_left" value="<c:out value="${project.projectSubName }"/>"  />
								<input type="hidden" id="lang" name="lang" value="KOR" />
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${project.businessTypeCode=='BTE' || project.businessTypeCode=='BTF'}" >
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 부가 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit">
						<colgroup>
							<col style="width: 12%" />
							<col style="width: 18%" />
							<col style="width: *" />
						</colgroup>
						<tr>
							<th>기획사업 연계</th>
							<td>
								<ul class="chek_ui">
									<li><input type="radio" name="isEduConnected" id="Y" onclick="$('eduCourseRel').show();" class="btn_radio" value="Y" <c:if test="${project.isEduConnected == 'Y'}">checked <c:if test="${project.projectState > '2'}">disabled</c:if></c:if>
										<c:if test="${project.projectState > '2'}">disabled</c:if>>
										<label for="Y"><p>예</p></label>
									</li>
									<li>
										<input type="radio" name="isEduConnected" id="N" onclick="$('eduCourseRel').hide();" class="btn_radio"  value="N" <c:if test="${project.isEduConnected == 'N'}">checked <c:if test="${project.projectState > '2'}">disabled</c:if></c:if> 
										<c:if test="${project.projectState > '2'}">disabled</c:if>>
										<label for="N"><p>아니오</p></label>
									</li>
								</ul>
							</td>
								<input type="hidden" name="isEduConnected" id="isEduConnected" value="<c:out value="${project.isEduConnected }"/>" >
							<td>
								<button type="button" class="btn line btn_blue"  id="eduCourseRel" onclick="eduCourseRel_new();">
									<i class="mdi mdi-square-edit-outline"></i>기획사업 연계
								</button> &nbsp 공개교육, 기획성 연수, 세미나, 컨퍼런스 는 반드시 기획사업 연계
							</td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
		<!-- //프로젝트 부가 정보 -->
		<input type="hidden" name="orgCodes" id="orgCodes" value="<c:out value="${orgdbCodes}"/>">
		<!-- 프로젝트 고객 정보 (주석) -->
		 <%-- <div class="board_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">프로젝트 고객 정보</p>
				</div>
			</div>
			<div class="board_contents">
				<table class="tbl-edit">
					<colgroup>
						<col style="width: 12%" />
						<col style="" />
					</colgroup>
					<tbody>
						<tr>
							<th>고객정보 연계</th>
							<td><c:if test="${ project.projectState <= '3' }">
									<div class="td-L">
										<!-- td 안에 여러 요소가 들어갈 경우 .td-l -->
										<button type="button" class="btn line btn_black"
											onclick="location.href='javascript:openCustomerInfoPopUp('<c:out value="${project.projectCode}"/>');'">
											<i class="mdi mdi-account-search"></i>고객정보 연계
										</button>
										<c:if test="${ project.projectState < '3' }">
											<p>※ 이 프로젝트에 연관이 있는 니즈발굴 고객정보를 연결하세요.</p>
										</c:if>
									</div>
								</c:if> <c:if test="${ project.projectState==null }">
									<p>프로젝트 기본 정보를 저장한 뒤 프로젝트 품의 직전에 니즈발굴 고객정보를 연계 하세요.</p>
								</c:if></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div> --%>
		<!-- // 프로젝트 고객 정보 (주석)-->
		
		<!-- 프로젝트 협력사 정보 (주석) -->
		 <%-- <div class="board_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">프로젝트 협력사 정보</p>
				</div>
			</div>
			<div class="board_contents">
				<table class="tbl-edit">
					<colgroup>
						<col style="width: 12%"/>
						<col style="*" />
					</colgroup>
					<tbody>
						<tr>
							<th>협력사명</th>
							<td>
								<div class="td-L"><!-- td 안에 여러 요소가 들어갈 경우 .td-l -->
									<input type="hidden" name="orgCodes" id="orgCodes" value="<c:out value="${orgdbCodes}"/>"><input type="text" name="orgNames" id="orgNames" value="<c:out value="${orgdbNames}"/>" class="textInput_left"  readonly>
									<c:if test="${(viewMode=='register' || viewMode=='myProject') && (project.projectState=='1' || project.projectState == null || project.projectState=='3') && (projectRole=='PM'||projectRole=='AR'||projectRole=='PL')}"><button type="button" class="btn line btn_black" onclick="location.href='javascript:openRelationCompanyPopUp();'"><i class="mdi mdi-account-search"></i>검색</button></c:if>
								</div>																		
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div> --%> 
		<!-- // 프로젝트 협력사 정보 (주석)-->
		
		<!-- 프로젝트 해시태그 -->
		<c:if test="${project.projectState > '1' }">
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 해시태그</p>
						<c:if test="${(viewMode=='myProject') && (project.projectState==null || project.projectState=='1' || project.projectState=='3' || project.projectState=='4' || project.adminOpen=='Y') && (projectRole!='MB')}">
							<input type="text" id="tag" title="프로젝트 해시태그" placeholder="#는 자동 입력" class="input_tag" />
						</c:if>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit">
						<colgroup>
							<col style="width: 100%" />
						</colgroup>
						<tbody>
							<tr>
								<td>
									<ul class="hashtag_box" id="tag-list">	
									<c:forEach var="rs" items="${hashTagList}">		
								        <li class="tag-item" title="<c:out value="${rs.createDate}"/>" <c:if test="${rs.isShow ne 'SHOW'}">style="display: none;"</c:if>>#<c:out value="${rs.hashTag}"/>
											<c:if test="${(viewMode=='myProject') && (project.projectState==null || project.projectState=='1' || project.projectState=='3' || project.projectState=='4' || project.adminOpen=='Y') && (projectRole!='MB')}">											        	
								        		<span class="del-btn" idx="<c:out value="${rs.uuid}"/>"><font color="black">x</font></span>
											</c:if>														
								        </li>
							        </c:forEach>									        		
									</ul>																	
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
		
		<div class="btn_area head"><!-- 타이틀 영역에 위치하게 할때 .head -->
			<c:if test="${(viewMode=='register' || viewMode=='myProject') && (project.projectState==null || project.projectState=='1' || project.projectState=='3' || project.projectState=='4' || project.adminOpen=='Y') && (projectRole!='MB')}">
				<button type="button" class="btn line btn_blue" onclick="storeProjectMasterInfo('<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">reject</c:if>')"><i class="mdi mdi-square-edit-outline"></i>저장</button>
			</c:if>
			<c:if test="${viewMode=='register' && project.projectState== '1' && (sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT')}"> 
				<button type="button" class="btn line btn_pink" onclick="deleteProjectMasterInfo();"><i class="mdi mdi-trash-can-outline"></i>삭제</button>					
			</c:if>
		</div>
		
		<!-- projectinfotab 으로 경로 이동 -->
		<%-- <c:if test="${viewMode=='register' && project.projectState== '1' && (sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT')}">
			<c:choose>
				<c:when test="${project.businessTypeCode== 'BTA' || project.businessTypeCode== 'BTJ' || project.businessTypeCode== 'BTD' || (project.businessTypeCode== 'BTE' && project.processTypeCode== 'N4') || project.processTypeCode=='DE' || project.processTypeCode=='SS'}">
					<a title="프로젝트 실행품의요청" class="btN3fac0c txt2btn" href="#" onclick="doProjectLaunchSanction('A');">실행 품의하기</a>
				</c:when>
				<c:otherwise>
					<a title="프로젝트 기획품의요청" class="btN3fac0c txt2btn"  href="#"  onclick="doProjectLaunchSanction('PA');">기획 품의하기</a>
				</c:otherwise>
			</c:choose>
		</c:if> --%>
		
	</div>
	</form>
</body>
</html>

<!-- // 프로젝트 고객사 정보 -->
		<%--
				<!-- sub 타이틀 영역 시작-->
				
					
					

				<!-- 버튼부분-->
				<tr>
					<td height="7"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" height="34" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0"> 
							<tr>
								<td>
								<jsp:useBean id="now" class="java.util.Date" />
								<fmt:formatDate value="${now}" pattern="dd" var="today" />
								<fmt:formatNumber var="no" minIntegerDigits="2" value="${canSanction.data2}" type="number"/>
								
									<div class="btNbox txtR">
									<!-- Job Date: 2019-09-19 Author: yskim	Description: SALARY_DATE 요율,금액 변경 제어 추가 -->
										<c:choose>
											<c:when test="${(canSanction.data1 le today) && (no ge today) && (viewMode eq 'myProject')}">
											<c:when test="${(canSanction.data1 le today) && (canSanction.data2 ge today) && (viewMode eq 'myProject')}">
												[알림] 성과급 품의가 마감되었으므로 성과 요율 및 기준금액을 변경하실 수 없습니다.
													[알림] 해당 월의 성과급 품의는 <c:out value="${canSanction.data1}" />일부터 가능합니다.
											</c:when>
											<c:otherwise>
												<c:if test="${(viewMode=='register' || viewMode=='myProject') && (project.projectState==null || project.projectState=='1' || project.projectState=='3' || project.projectState=='4' || project.adminOpen=='Y') && (projectRole!='MB')}">
													<a title="프로젝트 저장" class="btNe006bc6 txt2btn" href="#" onclick="storeProjectMasterInfo('<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">reject</c:if>');" >프로젝트 저장</a>	
												</c:if>
											</c:otherwise>
										</c:choose>
									<c:if test="${viewMode=='register' && project.projectState== '1' && (sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT')}"> 
										<a title="프로젝트 삭제" class="btNe14f42 txt2btn" href="#" onclick="deleteProjectMasterInfo();" >프로젝트 삭제</a>						
									</c:if>
									<c:if test="${viewMode=='register' && (project.projectState== '1' || sanctionDoc.state eq 'SANCTION_STATE_REJECT_DRAFT')}">
										<a title="프로젝트 예산 정보 동기화" class="btN006bc6 txt2btn" href="#" onclick="syncBudgetInfo();" >프로젝트 예산 동기화</a>
									</c:if>
									<c:if test="${viewMode=='register' && project.projectState== '1' && (sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT')}">
										TODO 추후 변경 되는 코드에 따른 변경 
										<c:choose>
											<c:when test="${project.businessTypeCode== 'BTA' || project.businessTypeCode== 'BTJ' || project.businessTypeCode== 'BTD' || (project.businessTypeCode== 'BTE' && project.processTypeCode== 'N4') || project.processTypeCode=='DE' || project.processTypeCode=='SS'}">
												<a title="프로젝트 실행품의요청" class="btN3fac0c txt2btn" href="#" onclick="doProjectLaunchSanction('A');">실행 품의하기</a>
											</c:when>
											<c:otherwise>
												<a title="프로젝트 기획품의요청" class="btN3fac0c txt2btn"  href="#"  onclick="doProjectLaunchSanction('PA');">기획 품의하기</a>
											</c:otherwise>
										</c:choose>
										</c:if>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
<!-- 본문종료-->
			
			</table>
		</td>
	</tr>						 --%>
