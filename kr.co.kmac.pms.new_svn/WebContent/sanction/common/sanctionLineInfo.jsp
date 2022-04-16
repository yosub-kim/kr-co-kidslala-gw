<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<script type="text/javascript">

</script>
<!-- sanctionline title -->
<div class="sign_area writer">
	<div>
		<ul class="sign_box">
			<li class="draft">
				<p class="subject">기안</p>
				<div class="name_group">
					<input type="text" sanctionInfo="name" seq="1" name="registerName" id="registerName" value="<c:out value="${sanctionDoc.registerName}"/>" style="border:none; width: 40%;" readonly >
					<input type="hidden" sanctionInfo="ssn" seq="1" name="registerSsn" id="registerSsn" value="<c:out value="${sanctionDoc.registerSsn}"/>">
					<input type="hidden" sanctionInfo="dept" seq="1" name="registerDept" id="registerDept" value="<c:out value="${sanctionDoc.registerDept}"/>"> 
					<c:if test="${!readonly}">
						<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
							<div class="btn_area">
								<button type="button" class="btn plus" onclick="openExpertPoolPopUp('1')"><i>+</i></button>
								<button type="button" class="btn minus" onclick="$('registerName').value='';$('registerSsn').value='';$('registerDept').value='';$('registerDate').value='';"><i>-</i></button>
							</div>
						</c:if>
					</c:if>
				</div>
				<p class="date">
					<fmt:formatDate value="${sanctionDoc.registerDate}" pattern="yyyy-MM-dd" />
					<input type="hidden" name="registerDate" id="registerDate" 	value="<fmt:formatDate value="${sanctionDoc.registerDate}"	pattern="yyyy-MM-dd hh:mm:sss" />"/>						
				</p>
			</li>
		
			<c:if test="${sanctionTemplate.hasTeamManager}"> 
				<li class="review">
					<p class="subject">검토</p>
					<div class="name_group">
						<input type="text" sanctionInfo="name" seq="2" name="teamManagerName" id="teamManagerName" value="<c:out value="${sanctionDoc.teamManagerName}"/>" style="border:none; width: 40%;" readonly
							<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when>
							<c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>>
						<input type="hidden" sanctionInfo="ssn" seq="2" name="teamManagerSsn" id="teamManagerSsn" value="<c:out value="${sanctionDoc.teamManagerSsn}"/>">
						<input type="hidden" sanctionInfo="dept" seq="2" name="teamManagerDept" id="teamManagerDept" value="<c:out value="${sanctionDoc.teamManagerDept}"/>"> 
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('2')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('teamManagerName').value='';$('teamManagerSsn').value='';$('teamManagerDept').value='';$('teamManagerDate').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
					</div>
					<p class="date">
						<c:if test="${sanctionTemplate.hasTeamManager}"> 
								<fmt:formatDate value="${sanctionDoc.teamManagerDate}" 	pattern="yyyy-MM-dd" />
								<input type="hidden" name="teamManagerDate"	id="teamManagerDate"	value="<fmt:formatDate value="${sanctionDoc.teamManagerDate}"	pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</c:if>
					</p>
				</li>
			</c:if>
		
			<c:if test="${sanctionTemplate.hasCfo}">
				<li class="check">
					<p class="subject">확인</p>
					<div class="name_group">
						<input type="text" sanctionInfo="name" seq="3" id="cfoName" name="cfoName" value="<c:out value="${sanctionDoc.cfoName}"/>" style="border:none; width: 40%;" readonly
							<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when>
							<c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>>
						<input type="hidden" sanctionInfo="ssn" seq="3" name="cfoSsn" id="cfoSsn" value="<c:out value="${sanctionDoc.cfoSsn}"/>">
						<input type="hidden" sanctionInfo="dept" seq="3" name="cfoDept" id="cfoDept" value="<c:out value="${sanctionDoc.cfoDept}"/>"> 
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('3')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('cfoName').value='';$('cfoSsn').value='';$('cfoDept').value='';$('cfoDate').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
					</div>
					<p class="date">
						<c:if test="${sanctionTemplate.hasCfo}"> 
							<fmt:formatDate value="${sanctionDoc.cfoDate}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="cfoDate" id="cfoDate" value="<fmt:formatDate value="${sanctionDoc.cfoDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</c:if>
					</p>
				</li>
			</c:if>
		
			<c:if test="${sanctionTemplate.hasCio}"> 
				<li class="approved">
					<p class="subject">승인</p>
					<div class="name_group">
						<input type="text" sanctionInfo="name" seq="4" name="cioName" id="cioName" value="<c:out value="${sanctionDoc.cioName}"/>" style="border:none; width: 40%;" readonly
							<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when>
							<c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>>
						<input type="hidden" sanctionInfo="ssn" seq="4" name="cioSsn" id="cioSsn" value="<c:out value="${sanctionDoc.cioSsn}"/>">
						<input type="hidden" sanctionInfo="dept" seq="4" name="cioDept" id="cioDept" value="<c:out value="${sanctionDoc.cioDept}"/>"> 
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('4')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('cioName').value='';$('cioSsn').value='';$('cioDept').value='';$('cioDate').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
					</div>
					<p class="date">
						<c:if test="${sanctionTemplate.hasCio}"> 
							<fmt:formatDate value="${sanctionDoc.cioDate}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="cioDate" id="cioDate" value="<fmt:formatDate value="${sanctionDoc.cioDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</c:if>
					</p>
				</li>
			</c:if>
		
			<c:if test="${sanctionTemplate.hasCeo}"> 
				<li class="ceo">
					<p class="subject">대표이사</p>
					<div class="name_group">
						<input type="text" sanctionInfo="name" seq="5" name="ceoName" id="ceoName" style="border:none; width: 40%;" readonly 
						<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when><c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>
							value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.ceoName}"/></c:when><c:otherwise><c:out value="${sanctionDoc.ceoName}"/></c:otherwise></c:choose>"
							><input type="hidden" sanctionInfo="ssn" seq="5" name="ceoSsn"  id="ceoSsn"  style="width: 52px;"
							value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.ceoSsn}"/></c:when><c:otherwise><c:out value="${sanctionDoc.ceoSsn}"/></c:otherwise></c:choose>"
							><input type="hidden" sanctionInfo="dept" seq="5" name="ceoDept"  id="ceoDept"  style="width: 52px;"
							value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.ceoDept}"/></c:when><c:otherwise><c:out value="${sanctionDoc.ceoDept}"/></c:otherwise></c:choose>"
							> 
							<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
								<button type="button" class="btn plus" onclick="openExpertPoolPopUp('5')"><i>+</i></button>
								<button type="button" class="btn minus" onclick="$('ceoName').value='';$('ceoSsn').value='';$('ceoDept').value='';$('ceoDate').value='';"><i>-</i></button>
								</div>
							</c:if></c:if>
					</div>
					<p class="date">
						<fmt:formatDate value="${sanctionDoc.ceoDate}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="ceoDate" id="ceoDate" value="<fmt:formatDate value="${sanctionDoc.ceoDate}" pattern="yyyy-MM-dd hh:mm:sss" />"/>			
					</p>
				</li>
			</c:if>
		</ul>
		
		<div class="row_group">
			<c:if test="${sanctionTemplate.hasRefMember == true }">
				<ul class="sign_box row">
				<li>
					<p class="subject">참조</p>
					<div class="name_group edit">
						<p class="name"><input type="text" style="border:none; width: 100%;" name="refMemberName" id="refMemberName"  readonly="readonly"
							value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.refMemberNames}"/></c:when><c:otherwise><c:out value="${sanctionDoc.sanctionDocCCName}"/></c:otherwise></c:choose>" 
							<c:choose><c:when test="${readonly}"> class="textInput_noLine_center" </c:when><c:otherwise> class="contentInput_center" </c:otherwise></c:choose> /></p>
						<input type="hidden" name="refMemberSsn" id="refMemberSsn" value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.refMemberSsns}"/></c:when><c:otherwise><c:out value="${sanctionDoc.sanctionDocCCSsn}"/></c:otherwise></c:choose>" />
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('refMember')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('refMemberSsn').value='';$('refMemberName').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
					</div>
				</li>
				</ul>
			</c:if>
		
			<c:choose>
				<c:when test="${sanctionTemplate.assistMemberCnt == 1}">
					<div style="margin: 0 0 -10 0;">
					<ul class="sign_box">
						<li class="discuss">
						<p class="subject">협의 1</p>
						<div class="name_group">
						<input type="text" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionDoc.assistant1Name}"/>" style="border:none; width: 40%;" readonly
							<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when>
							<c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>>
						<input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionDoc.assistant1Ssn}"/>">
						<input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionDoc.assistant1Dept}"/>"> 
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('6')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('assistant1Name').value='';$('assistant1Ssn').value='';$('assistant1Dept').value='';$('cioDate').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
						</div>
						<p class="date">
							<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>				
						</p>
					</li>
					</ul>
					</div>
				</c:when>
						
				<c:when test="${sanctionTemplate.assistMemberCnt == 2}">
					<div style="margin: 0 0 -10 0;">
					<ul class="sign_box">
					<li class="discuss">
						<p class="subject">협의 1</p>
						<div class="name_group">
						<input type="text" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionDoc.assistant1Name}"/>" style="border:none; width: 40%;" readonly
							<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when>
							<c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>>
						<input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionDoc.assistant1Ssn}"/>">
						<input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionDoc.assistant1Dept}"/>"> 
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('6')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('assistant1Name').value='';$('assistant1Ssn').value='';$('assistant1Dept').value='';$('assistant1Date').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
						</div>
						<p class="date">
							<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>				
						</p>
					</li>
					<li class="discuss">
						<p class="subject">협의 2</p>
						<div class="name_group">
						<input type="text" sanctionInfo="name" seq="7" name="assistant2Name" id="assistant2Name" value="<c:out value="${sanctionDoc.assistant2Name}"/>" style="border:none; width: 40%;" readonly
							<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when>
							<c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>>
						<input type="hidden" sanctionInfo="ssn" seq="7" name="assistant2Ssn" id="assistant2Ssn" value="<c:out value="${sanctionDoc.assistant2Ssn}"/>">
						<input type="hidden" sanctionInfo="dept" seq="7" name="assistant2Dept" id="assistant2Dept" value="<c:out value="${sanctionDoc.assistant2Dept}"/>"> 
						<c:if test="${!readonly}">
							<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
								<div class="btn_area">
									<button type="button" class="btn plus" onclick="openExpertPoolPopUp('7')"><i>+</i></button>
									<button type="button" class="btn minus" onclick="$('assistant2Name').value='';$('assistant2Ssn').value='';$('assistant2Dept').value='';$('assistant2Date').value='';"><i>-</i></button>
								</div>
							</c:if>
						</c:if>
						</div>
						<p class="date">
							<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="assistant2Date" id="assistant2Date" value="<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</p>
					</li>
					</ul>
					</div>
				</c:when>
					
				<c:when test="${sanctionTemplate.assistMemberCnt == 3}">
					<div style="margin: 0 0 -10 0;">
					<ul class="sign_box">
					<li class="discuss">
						<p class="subject">협의 3</p>
						<div class="name_group">
						<input type="text" sanctionInfo="name" seq="6" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionDoc.assistant1Name}"/>" style="border:none; width: 40%;" readonly 
												<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when><c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>
												><input type="hidden" sanctionInfo="ssn" seq="6" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionDoc.assistant1Ssn}"/>"
												><input type="hidden" sanctionInfo="dept" seq="6" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionDoc.assistant1Dept}"/>"
												> <c:if test="${!readonly}"><c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}"
													><img alt="조직도검색" src="/images/btn_plus.gif" align="absmiddle" onclick="openExpertPoolPopUp('6')" style="cursor: hand;"
													> <img alt="지우기" src="/images/btn_minus.gif" 
														onclick="$('assistant1Name').value='';$('assistant1Ssn').value='';$('assistant1Dept').value='';$('assistant1Date').value='';" 
														style="cursor: hand;"></c:if></c:if>
						</div>
						<div class="name_group">
						<input type="text" sanctionInfo="name" seq="7" name="assistant2Name" id="assistant2Name" value="<c:out value="${sanctionDoc.assistant2Name}"/>" style="border:none; width: 40%;" readonly 
												<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when><c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>
												><input type="hidden" sanctionInfo="ssn" seq="7" name="assistant2Ssn" id="assistant2Ssn" value="<c:out value="${sanctionDoc.assistant2Ssn}"/>"
												><input type="hidden" sanctionInfo="dept" seq="7" name="assistant2Dept" id="assistant2Dept" value="<c:out value="${sanctionDoc.assistant2Dept}"/>"
												> <c:if test="${!readonly}"><c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}"
													><img alt="조직도검색" src="/images/btn_plus.gif" align="absmiddle" onclick="openExpertPoolPopUp('7')" style="cursor: hand;"
													> <img alt="지우기" src="/images/btn_minus.gif" 
														onclick="$('assistant2Name').value='';$('assistant2Ssn').value='';$('assistant2Dept').value='';$('assistant2Date').value='';" 
														style="cursor: hand;"></c:if></c:if>
						</div>
						<div class="name_group">
							<input type="text" sanctionInfo="name" seq="7" name="assistant2Name" id="assistant2Name" value="<c:out value="${sanctionDoc.assistant2Name}"/>" style="border:none; width: 40%;" readonly 
												<c:choose><c:when test="${!readonly && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT')}"> class="contentInput_center" </c:when><c:otherwise> class="textInput_noLine_center" </c:otherwise></c:choose>
												><input type="hidden" sanctionInfo="ssn" seq="7" name="assistant2Ssn" id="assistant2Ssn" value="<c:out value="${sanctionDoc.assistant2Ssn}"/>"
												><input type="hidden" sanctionInfo="dept" seq="7" name="assistant2Dept" id="assistant2Dept" value="<c:out value="${sanctionDoc.assistant2Dept}"/>"
												> <c:if test="${!readonly}"><c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}"
													><img alt="조직도검색" src="/images/btn_plus.gif" align="absmiddle" onclick="openExpertPoolPopUp('7')" style="cursor: hand;"
													> <img alt="지우기" src="/images/btn_minus.gif" 
														onclick="$('assistant2Name').value='';$('assistant2Ssn').value='';$('assistant2Dept').value='';$('assistant2Date').value='';" 
														style="cursor: hand;"></c:if></c:if>
						</div>
						<p class="date">
							<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd" /><input type="hidden" name="assistant1Date" id="assistant1Date" value="<fmt:formatDate value="${sanctionDoc.assistant1Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</p><p class="date">
							<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd" /><input type="hidden" name="assistant2Date" id="assistant2Date" value="<fmt:formatDate value="${sanctionDoc.assistant2Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</p><p class="date">
							<fmt:formatDate value="${sanctionDoc.assistant3Date}" pattern="yyyy-MM-dd" /><input type="hidden" name="assistant3Date" id="assistant3Date" value="<fmt:formatDate value="${sanctionDoc.assistant3Date}" pattern="yyyy-MM-dd hh:mm:sss" />"/>
						</p>
					</li>
					</ul>
					</div>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>
				