<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<div style="height: 100px;">
	<table width="200" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
		<c:if test="${!empty prjList}">
			<c:forEach var="prj" items="${prjList}"	varStatus="i">
				<c:if test="${!i.first}"><tr><td height="1" align="left" bgcolor="#dddddd"></td></tr></c:if>
				<tr>
					<td height="20"  style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; ">
						<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">	
						<a title="<c:out value="${prj.projectName }"/>" href="javascript:schedule_Info('<c:out value="${ssn}"/>','<c:out value="${sdate}"/>','');">
							<font color='darkblue'><b>지도일정: </b></font>
							<c:out value="${prj.projectName }"/>
						</a>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${!empty schList}">
			<c:forEach var="sch" items="${schList}">
				<c:if test="${!i.first}"><tr><td height="1" align="left" bgcolor="#dddddd"></td></tr></c:if>
				<tr>
					<td height="20"  style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; ">
						<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">	
						<a title="<c:out value="${sch.content }"/>" href="javascript:schedule_Info('<c:out value="${ssn}"/>','<c:out value="${sdate}"/>','<c:out value="${sch.seq }"/>');">
							<c:if test="${!((empty sch.startHour)||(sch.startHour == '')) }">
								<font color='darkblue'><b><c:out value="${sch.startHour}"/>:<c:out value="${sch.startMin }"/>~<c:out value="${sch.endHour  }"/>:<c:out value="${sch.endMin   }"/></b></font>
							</c:if>
							<c:out value="${sch.content }"/>
						</a>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty schList && empty prjList}">
			<tr>
				<td align="center" height="20">등록된 오늘 일정이 없습니다. </td>
			</tr>
		</c:if>
	</table>
</div> 