<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<div id="orgTree" style='overflow:auto;width:100%;height:320; vertical-align:top;'>
<div style="margin:15px 15px 0 15px;">
<table class="tbl-edit td-c pd-none" width="100%">
	<thead>
		<colgroup> 	
			<col width="10%">
			<col width="30%">
			<col width="30%"> 
			<col width="30"> 
		</colgroup>
		<tr>
			<th>&nbsp;</th>
			<th>이름</th>
			<th>구분</th>
			<th>소속</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="expert" items="${list}">
		<tr>
			<td>
				<ul><li>
				<input type="checkbox" name="chkExpertPool" value=""
					exprt_ssn                 = "<c:out value="${expert.ssn}"/>"                  
					exprt_uid                 = "<c:out value="${expert.uid}"/>"                  
					exprt_name                = "<c:out value="${expert.name}"/>"                 
					exprt_enable              = "<c:out value="${expert.enable}"/>"               
					exprt_absence             = "<c:out value="${expert.absence}"/>"              
					exprt_gender              = "<c:out value="${expert.gender}"/>"               
					exprt_nationality         = "<c:out value="${expert.nationality}"/>"          
					exprt_telNo               = "<c:out value="${expert.telNo}"/>"                
					exprt_mobileNo            = "<c:out value="${expert.mobileNo}"/>"             
					exprt_zipCode             = "<c:out value="${expert.zipCode}"/>"              
					exprt_address1            = "<c:out value="${expert.address1}"/>"             
					exprt_address2            = "<c:out value="${expert.address2}"/>"             
					exprt_company             = "<c:out value="${expert.company}"/>"              
					exprt_dept                = "<c:out value="${expert.dept}"/>"             
					exprt_deptName            = "<c:out value="${expert.deptName}"/>"                 
					exprt_companyPosition     = "<c:out value="${expert.companyPosition}"/>"      
					exprt_companyPositionName = "<c:out value="${expert.companyPositionName}"/>"  
					exprt_jobClass            = "<c:out value="${expert.jobClass}"/>"             
					exprt_companyZipcode      = "<c:out value="${expert.companyZipcode}"/>"       
					exprt_companyAddress1     = "<c:out value="${expert.companyAddress1}"/>"      
					exprt_companyAddress2     = "<c:out value="${expert.companyAddress2}"/>"      
					exprt_companyTelNo        = "<c:out value="${expert.companyTelNo}"/>"         
					exprt_companyFaxNo        = "<c:out value="${expert.companyFaxNo}"/>"         
					exprt_email               = "<c:out value="${expert.email}"/>"                
					exprt_indField            = "<c:out value="${expert.indField}"/>"             
					exprt_funcField           = "<c:out value="${expert.funcField}"/>"            
					exprt_consultingMajor     = "<c:out value="${expert.consultingMajor}"/>"      
					exprt_consultingMinor     = "<c:out value="${expert.consultingMinor}"/>"      
					exprt_consultingDetail    = "<c:out value="${expert.consultingDetail}"/>"
					exprt_rate                = "<c:out value="${expert.rate}"/>"                
					exprt_role                = "<c:out value="${expert.role}"/>"                 
					exprt_extRole             = "<c:out value="${expert.extRole}"/>"              
					exprt_resume              = "<c:out value="${expert.resume}"/>"               
					exprt_companyId           = "<c:out value="${expert.companyId}"/>" 
					exprt_minAmt         	  = "<c:out value="${expert.minAmt}"/>" 
					exprt_maxAmt        	  = "<c:out value="${expert.maxAmt}"/>" 
					exprt_minEdu			  = "<c:out value="${expert.minEdu}"/>" 
					exprt_maxEdu              = "<c:out value="${expert.maxEdu}"/>"
					exprt_minMMAmt         	  = "<c:out value="${expert.minMMAmt}"/>" 
					exprt_maxMMAmt        	  = "<c:out value="${expert.maxMMAmt}"/>" 
					id="<c:out value="${expert.ssn }"/>" class="btn_check">
					<label for="<c:out value="${expert.ssn }"/>"></label>
			</li></ul></td>
			<td align="center" nowrap="nowrap"><c:out value="${expert.name}"/></td>
			<td align="left" nowrap="nowrap">
			<c:choose>
				<c:when test="${expert.jobClass eq 'A' || expert.jobClass eq 'B' }" >
					상근
				</c:when>
				<c:when test="${expert.jobClass eq 'J' }" >
					상임
				</c:when>
				<c:when test="${expert.jobClass eq 'N'}" >
					RA
				</c:when>
				<c:when test="${expert.jobClass eq 'C' }" >
					엑스퍼트
				</c:when>
				<c:when test="${expert.jobClass eq 'D' }" >
					산업계강사
				</c:when>
				<c:when test="${expert.jobClass eq 'E' }" >
					대학교수
				</c:when>
				<c:otherwise>
					기타
				</c:otherwise>
			</c:choose>
			</td>
			<td align="left"  nowrap="nowrap"><c:out value="${expert.company}"/></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
