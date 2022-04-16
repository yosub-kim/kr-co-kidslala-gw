<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>

<table class="tbl-ty1 line">
    <colgroup>
        <col width="30%" />
        <col width="30%" />
        <col width="40%" />
    </colgroup>
	<thead>
        <tr>
            <th>단계</th>
            <th>이름</th>
            <th>일자</th>
        </tr>
    </thead>
    <tbody>
	<tr>
		<td>기안</td>
		<td><c:out value="${sanctionDoc.registerName}"/>
			<input type="hidden" sanctionInfo="name" seq="1" name="registerName" id="registerName" value="<c:out value="${sanctionDoc.registerName}"/>" > 
			<input type="hidden" sanctionInfo="ssn" seq="1" name="registerSsn" id="registerSsn" value="<c:out value="${sanctionDoc.registerSsn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="1" name="registerDept" id="registerDept" value="<c:out value="${sanctionDoc.registerDept}"/>">
		</td>
		<td>
			<fmt:formatDate value="${sanctionDoc.registerDate}" pattern="yyyy-MM-dd" />
			<input type="hidden" name="registerDate" id="registerDate" value="<fmt:formatDate value="${sanctionDoc.registerDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
		</td>
	</tr>
	<c:if test="${sanctionTemplate.hasTeamManager}">
		<tr>
			<td>검토</td>
			<td><c:out value="${sanctionDoc.teamManagerName}"/>
				<input type="hidden" sanctionInfo="name" seq="2" name="teamManagerName" id="teamManagerName" value="<c:out value="${sanctionDoc.teamManagerName}"/>">  
				<input type="hidden" sanctionInfo="ssn" seq="2" name="teamManagerSsn" id="teamManagerSsn" value="<c:out value="${sanctionDoc.teamManagerSsn}"/>">
				<input type="hidden" sanctionInfo="dept" seq="2" name="teamManagerDept" id="teamManagerDept" value="<c:out value="${sanctionDoc.teamManagerDept}"/>">
			</td>
			<td>
				<fmt:formatDate value="${sanctionDoc.teamManagerDate}" pattern="yyyy-MM-dd" />
				<input type="hidden" name="teamManagerDate"	id="teamManagerDate" value="<fmt:formatDate value="${sanctionDoc.teamManagerDate}"	pattern="yyyy-MM-dd hh:mm:sss" />"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${sanctionTemplate.hasCfo}">
		<tr>
			<td>확인</td>
			<td><c:out value="${sanctionDoc.cfoName}"/>
				<input type="hidden" sanctionInfo="name" seq="3" id="cfoName" name="cfoName" value="<c:out value="${sanctionDoc.cfoName}"/>">
				<input type="hidden" sanctionInfo="ssn" seq="3" name="cfoSsn" id="cfoSsn" value="<c:out value="${sanctionDoc.cfoSsn}"/>">
				<input type="hidden" sanctionInfo="dept" seq="3" name="cfoDept" id="cfoDept" value="<c:out value="${sanctionDoc.cfoDept}"/>">
			</td>
			<td>
				<fmt:formatDate value="${sanctionDoc.cfoDate}" pattern="yyyy-MM-dd" />
				<input type="hidden" name="cfoDate" id="cfoDate" value="<fmt:formatDate value="${sanctionDoc.cfoDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/> 
			</td>
		</tr>
	</c:if>
	<c:if test="${sanctionTemplate.hasCio}">
		<tr>
			<td>승인</td>
			<td><c:out value="${sanctionDoc.cioName}"/>
				<input type="hidden" sanctionInfo="name" seq="4" name="cioName" id="cioName" value="<c:out value="${sanctionDoc.cioName}"/>">
				<input type="hidden" sanctionInfo="ssn" seq="4" name="cioSsn" id="cioSsn" value="<c:out value="${sanctionDoc.cioSsn}"/>">
				<input type="hidden" sanctionInfo="dept" seq="4" name="cioDept" id="cioDept" value="<c:out value="${sanctionDoc.cioDept}"/>"
			</td>
			<td>
				<fmt:formatDate value="${sanctionDoc.cioDate}" pattern="yyyy-MM-dd" />
				<input type="hidden" name="cioDate" id="cioDate" value="<fmt:formatDate value="${sanctionDoc.cioDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
			</td>
		</tr>
	</c:if>
	<c:choose>
		<c:when test="${sanctionTemplate.hasAssistMember}">
			<c:choose>
				<c:when test="${sanctionTemplate.assistMemberCnt == 1}">
					<tr>
						<td>협의</td>
						<td><c:out value="${sanctionDoc.assistant1Name}"/>
							<input type="hidden" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionDoc.assistant1Name}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionDoc.assistant1Ssn}"/>">
							<input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionDoc.assistant1Dept}"/>">
						</td>
						<td>
							<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>					
						</td>				
					</tr>
				</c:when>
				<c:when test="${sanctionTemplate.assistMemberCnt == 2}">
					<tr>
						<td>협의1</td>
						<td><c:out value="${sanctionDoc.assistant1Name}"/>
							<input type="hidden" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionDoc.assistant1Name}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionDoc.assistant1Ssn}"/>">
							<input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionDoc.assistant1Dept}"/>">
						</td>
						<td>
							<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</td>				
					</tr>
					<tr>
						<td>협의2</td>
						<td><c:out value="${sanctionDoc.assistant2Name}"/>
							<input type="hidden" sanctionInfo="name" seq="7" name="assistant2Name" id="assistant2Name" value="<c:out value="${sanctionDoc.assistant2Name}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="7" name="assistant2Ssn" id="assistant2Ssn" value="<c:out value="${sanctionDoc.assistant2Ssn}"/>">
							<input type="hidden" sanctionInfo="dept" seq="7" name="assistant2Dept" id="assistant2Dept" value="<c:out value="${sanctionDoc.assistant2Dept}"/>">
						</td>
						<td>
							<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant2Date" id="assistant2Date" value="<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</td>				
					</tr>
				</c:when>
				<c:when test="${sanctionTemplate.assistMemberCnt == 3}">
					<tr>
						<td>협의1</td>
						<td><c:out value="${sanctionDoc.assistant1Name}"/>
							<input type="hidden" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionDoc.assistant1Name}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionDoc.assistant1Ssn}"/>">
							<input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionDoc.assistant1Dept}"/>">
						</td>
						<td>
							<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>			
						</td>				
					</tr>
					<tr>
						<td>협의2</td>
						<td><c:out value="${sanctionDoc.assistant2Name}"/>
							<input type="hidden" sanctionInfo="name" seq="7" name="assistant2Name" id="assistant2Name" value="<c:out value="${sanctionDoc.assistant2Name}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="7" name="assistant2Ssn" id="assistant2Ssn" value="<c:out value="${sanctionDoc.assistant2Ssn}"/>">
							<input type="hidden" sanctionInfo="dept" seq="7" name="assistant2Dept" id="assistant2Dept" value="<c:out value="${sanctionDoc.assistant2Dept}"/>">
						</td>
						<td>
							<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant2Date" id="assistant2Date" value="<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>			
						</td>				
					</tr>
					<tr>
						<td>협의3</td>
						<td><c:out value="${sanctionDoc.assistant3Name}"/>
							<input type="hidden" sanctionInfo="name" seq="8" name="assistant3Name" id="assistant3Name" value="<c:out value="${sanctionDoc.assistant3Name}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="8" name="assistant3Ssn" id="assistant3Ssn" value="<c:out value="${sanctionDoc.assistant3Ssn}"/>">
							<input type="hidden" sanctionInfo="dept" seq="8" name="assistant3Dept" id="assistant3Dept" value="<c:out value="${sanctionDoc.assistant3Dept}"/>">
						</td>
						<td>
							<fmt:formatDate value="${sanctionDoc.assistant3Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant3Date" id="assistant3Date" value="<fmt:formatDate value="${sanctionDoc.assistant3Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>			
						</td>				
					</tr>
				</c:when>
			</c:choose>
		</c:when>
		<c:when test="${sanctionTemplate.hasSptTeamMng}"> 
			<tr>
				<td>경영기획실</td>
				<td>
					<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.sptTeamMngName}"/></c:when><c:otherwise><c:out value="${sanctionDoc.assistant1Name}"/></c:otherwise></c:choose>
					<input type="hidden" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.sptTeamMngName}"/></c:when><c:otherwise><c:out value="${sanctionDoc.assistant1Name}"/></c:otherwise></c:choose>">
					<input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn"  id="assistant1Ssn"  style="width: 52px;" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.sptTeamMngSsn}"/></c:when><c:otherwise><c:out value="${sanctionDoc.assistant1Ssn}"/></c:otherwise></c:choose>">
					<input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" style="width: 52px;" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.sptTeamMngDept}"/></c:when><c:otherwise><c:out value="${sanctionDoc.assistant1Dept}"/></c:otherwise></c:choose>">
				</td>
				<td>
					<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" />
					<input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>			
				</td>		
			</tr>		
		</c:when>
	</c:choose>			
	<c:if test="${sanctionTemplate.hasCeo && !empty sanctionTemplate.ceoSsn}"> 
		<tr>
			<td>대표이사</td>
			<td><c:out value="${sanctionTemplate.ceoName}"/>
				<input type="hidden" sanctionInfo="name" seq="5" name="ceoName" id="ceoName" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.ceoName}"/></c:when><c:otherwise><c:out value="${sanctionDoc.ceoName}"/></c:otherwise></c:choose>">
				<input type="hidden" sanctionInfo="ssn" seq="5" name="ceoSsn" id="ceoSsn"  value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.ceoSsn}"/></c:when><c:otherwise><c:out value="${sanctionDoc.ceoSsn}"/></c:otherwise></c:choose>">
				<input type="hidden" sanctionInfo="dept" seq="5" name="ceoDept"  id="ceoDept"  value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.ceoDept}"/></c:when><c:otherwise><c:out value="${sanctionDoc.ceoDept}"/></c:otherwise></c:choose>">
			</td>
			<td>
				<fmt:formatDate value="${sanctionDoc.ceoDate}" pattern="yyyy-MM-dd" />
				<input type="hidden" name="ceoDate" id="ceoDate" value="<fmt:formatDate value="${sanctionDoc.ceoDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
			</td>	
		</tr>			
	</c:if>
	</tbody>
</table>

<c:if test="${sanctionTemplate.hasRefMember == true}">
	<table class="tbl-ty1 line">
 		<colgroup>
        	<col width="30%" />
        	<col width="70%" />
    	</colgroup>
		<tbody>
			<tr>
				<th>참조</th>
				<td>
					<c:choose>
						<c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.refMemberNames}" /></c:when>
						<c:otherwise><c:out value="${sanctionDoc.sanctionDocCCName}" /></c:otherwise>
					</c:choose> 
					<input type="hidden" name="refMemberName" id="refMemberName" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.refMemberNames}"/></c:when><c:otherwise><c:out value="${sanctionDoc.sanctionDocCCName}"/></c:otherwise></c:choose>">
					<input type="hidden" name="refMemberSsn" id="refMemberSsn" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.refMemberSsns}"/></c:when><c:otherwise><c:out value="${sanctionDoc.sanctionDocCCSsn}"/></c:otherwise></c:choose>">
				</td>
			</tr>
		</tbody>
	</table>
</c:if>
			
							
