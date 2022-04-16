<%@ taglib prefix="c" 			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="expertPool"	uri="/WEB-INF/expertPool.tld" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head>
<body> 
<div data-role="page">
	<form action="">
		<input type="hidden" name="workId" value="<c:out value="${projectReportContent.workId}" />" />
		<input type="hidden" name="projectCode" value="<c:out value="${projectReportContent.projectCode}" />" />
		<input type="hidden" name="projectName" value="<c:out value="${projectReportContent.projectName}" />" />
		<input type="hidden" name="taskFormTypeId" value="<c:out value="${projectReportContent.taskFormTypeId}" />" />
		<input type="hidden" name="taskFormTypeName" value="<c:out value="${projectReportContent.taskFormTypeName}" />" />
		<input type="hidden" name="seq" value="<c:out value="${projectReportContent.seq}" />" />
		<input type="hidden" name="workStartDate" value="<c:out value="${projectReportContent.workStartDate}" />" />
		<input type="hidden" name="workEndDate" value="<c:out value="${projectReportContent.workEndDate}" />" />
		<input type="hidden" name="assignDate" value="<c:out value="${projectReportContent.assignDate}" />" />
		<input type="hidden" name="dueDate" value="<c:out value="${projectReportContent.dueDate}" />" />
		<input type="hidden" name="attach" value="<c:out value="${projectReportContent.attach}" />" />
		<input type="hidden" name="state" value="<c:out value="${projectReportContent.state}" />" />
		<input type="hidden" name="payYN" value="<c:out value="${projectReportContent.payYN}" />" />
		<input type="hidden" name="isExceed" value="<c:out value="${projectReportContent.isExceed}" />" />
		<input type="hidden"" sanctionInfo="ssn" seq="1" name="writerSsn" value="<c:out value="${projectReportContent.writerSsn}"/>">
		<input type="hidden" sanctionInfo="ssn" seq="2" name="reviewerSsn" value="<c:out value="${projectReportContent.reviewerSsn}"/>">
		<input type="hidden" sanctionInfo="ssn" seq="3" name="approverSsn" value="<c:out value="${projectReportContent.approverSsn}"/>">
		
		<div data-role="header" data-theme="a">
			<h1>지도일지</h1>
			<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
			<a href="/m/index.jsp" data-icon="check" >등록</a>
		</div><!-- /header -->
	
		<div data-role="content">
			<h4 style="height: 0px; padding: 0em 0em 0.2em 0.3em;"> [결재정보]</h4>
			<table style="width: 100%; font-size: 12px; border-collapse: collapse; ">
				<colgroup> 
					<col width="33%">
					<col width="34%"> 
					<col width="33%"> 
				</colgroup>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">작성</th>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">검토</th>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">승인</th>
				</tr>
				<tr>
					<td style="text-align: center; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><expertPool:exportname ssn="${projectReportContent.writerSsn}"/></td>
					<td style="text-align: center; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><expertPool:exportname ssn="${projectReportContent.reviewerSsn}"/></td>
					<td style="text-align: center; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><expertPool:exportname ssn="${projectReportContent.approverSsn}"/></td>
				</tr>
				<tr>
					<td style="text-align: center; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;">
						<fmt:parseDate value="${projectReportContent.writeDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v1"/>
						<fmt:formatDate value="${v1}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="writeDate" value="<c:out value="${projectReportContent.writeDate}"/>"/></td>
					<td style="text-align: center; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;">
						<fmt:parseDate value="${projectReportContent.revieweDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v2"/>
						<fmt:formatDate value="${v2}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="revieweDate" value="<c:out value="${projectReportContent.revieweDate}"/>"/></td>
					<td style="text-align: center; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;">
						<fmt:parseDate value="${projectReportContent.approveDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v3"/>
						<fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="approveDate" value="<c:out value="${projectReportContent.approveDate}"/>"/></td>
				</tr>
			</table>

			<h4 style="height: 0px; padding-left: 0.3em;"> [지도일지 내용]</h4> 
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="aa" class="ui-input-text">프로젝트명:</label>
				<input type="text" name="aa" id="aa" data-mini="true" placeholder="제목" <c:if test="${readonly}">readonly</c:if> 
					value="[<c:out value="${projectReportContent.projectName}"/>] <c:out value="${projectReportContent.taskFormTypeName}" />" class="ui-input-text ui-body-c ui-corner-all ui-shadow-inset"/>	
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="date" class="ui-input-text">지도일:</label>
				<fmt:parseDate value="${projectReportContent.assignDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="ass"/>
							<fmt:formatDate value="${ass}" pattern="yyyy-MM-dd" />
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="title" class="ui-input-text">제목:</label>
				<input type="text" name="title" id="title" data-mini="true" placeholder="제목" <c:if test="${readonly}">readonly</c:if> 
					value="<c:out value="${projectReportContent.title}"/>" />	
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="workContent" class="ui-input-text">금번진행사항:</label>
				<textarea name="workContent" id="workContent" placeholder="금번진행사항" style="min-height: 150px;" <c:if test="${readonly}">readonly</c:if>>
					<c:out value="${projectReportContent.workContent}" />
				</textarea>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="impoInstContent" class="ui-input-text">중요조치사항:</label>
				<textarea name="impoInstContent" id="impoInstContent" placeholder="중요조치사항" style="min-height: 50px;" <c:if test="${readonly}">readonly</c:if>>
					<c:out value="${projectReportContent.impoInstContent}" />
				</textarea>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="nextPlan" class="ui-input-text">향후일정:</label>
				<textarea name="nextPlan" id="nextPlan" placeholder="향후일정" style="min-height: 50px;" <c:if test="${readonly}">readonly</c:if>>
					<c:out value="${projectReportContent.nextPlan}" />
				</textarea>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="consultantOpinion" class="ui-input-text">컨설턴트의견:</label>
				<textarea name="consultantOpinion" id="consultantOpinion" placeholder="컨설턴트의견" style="min-height: 50px;" <c:if test="${readonly}">readonly</c:if>>
					<c:out value="${projectReportContent.consultantOpinion}" />
				</textarea>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="requestContent" class="ui-input-text">지원요구사항:</label>
				<textarea name="requestContent" id="requestContent" placeholder="지원요구사항" style="min-height: 50px;" <c:if test="${readonly}">readonly</c:if>>
					<c:out value="${projectReportContent.requestContent}" />
				</textarea>
			</div>
					
					
			<c:if test="${!empty fileList}">
				<h4 style="height: 0px; padding-left: 0.3em;"> [첨부파일]</h4>
				<ul style="margin: 0px; padding: 0px; border-bottom: 1px solid #E3E3E3; ">
					<c:forEach var="file" items="${fileList}">
						<li style="list-style: none; padding: .6em; border-top: 1px solid #E3E3E3; font-size: 12px;">
							<a href="javascript:fileDownload('<c:out value="${file.attachFileId}"/>', 'Y')">
								<img alt="다운로드" src="/images/btn_disk.jpg" align="absmiddle"/>
								<c:out value="${file.attachFileName}" escapeXml="false"/>
							</a>
						</li>
					</c:forEach>
				</ul>											
			</c:if>
																
			
		</div><!-- /content -->
		
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include><!-- /footerx	 -->	
	
	</form>
</div><!-- /page -->
</body>
</html>
