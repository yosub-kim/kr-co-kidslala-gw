<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<?xml version="1.0" encoding="utf-8"?>
<ganttChart projectCode="<c:out value="${project.projectCode}"/>" maxWorkSeq="<c:out value="${maxWorkSeq}"/>" 
				projectStartDate="<c:out value="${project.realStartDate}"/>" projectEndDate="<c:out value="${project.realEndDate}"/>"> 
	<c:if test="${!empty list}">
		<c:forEach var="item" items="${list}">
			<item workName="<c:out value="${item.workName}"/>"
				workSeq="<c:out value="${item.workSeq}"/>"
				parentWorkSeq="<c:out value="${item.parentWorkSeq}"/>"
				startDate="<c:out value="${item.startDate}"/>"
				endDate="<c:out value="${item.endDate}"/>"
				realEndDate="<c:out value="${item.realEndDate}"/>"
				projectCode="<c:out value="${project.projectCode}"/>" 
				contentId="<c:out value="${item.contentId}"/>">
				<c:if test="${!empty item.projectProgressEntityList}">
					<c:forEach var="item" items="${item.projectProgressEntityList}">
						<item workName="<c:out value="${item.workName}"/>" 
							workSeq="<c:out value="${item.workSeq}"/>"
							startDate="<c:out value="${item.startDate}"/>"
							endDate="<c:out value="${item.endDate}"/>"
							realEndDate="<c:out value="${item.realEndDate}"/>"
							projectCode="<c:out value="${project.projectCode}"/>"  
							contentId="<c:out value="${item.contentId}"/>">
						</item>
					</c:forEach>
				</c:if>
			</item> 
		</c:forEach>
	</c:if>
</ganttChart>
