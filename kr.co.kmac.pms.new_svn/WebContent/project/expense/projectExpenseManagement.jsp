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
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">

<%-- 개별 스크립트 영역 --%>
</script>
</head>

<body>
<form name="projectExpenseForm" method="post">
		<div style="display: none;">
			<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>"> 
			<input type="text" name="year" id="year" value="<c:out value="${year}"/>"> 
			<input type="text" name="month" id="month" value="<c:out value="${month}"/>"> 
			<input type="hidden" name="projectTypeCode" id="projectTypeCode" value="<c:out value="${projectTypeCode}" />">
			<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}" />">
		</div>
		<div id="tab_menu_content">
			<!-- 프로젝트 예산정보 -->
			<c:if test="${projectTypeCode eq 'MM' }">
				<div class="board_box">
					<div class="title">
						<div class="h1_area">
							<p class="h1">프로젝트 예산정보</p>
						</div>
						<div class="text-R">
							<p>지급완료 성과급은 성과급이 지급되는 매월 6일 이후 갱신됩니다.</p>
						</div>
					</div>
					<div class="board_contents">
						<table id="buExpenseTable" class="tbl-edit">
							<colgroup>
								<col style="width: 15%" />
								<col style="width: 30%" />
								<col style="width: 15%" />
								<col style="width: 30%" />
							</colgroup>
							<tbody id="budgetTableBody">
								<c:forEach var="rs" items="${restSalary}">
									<tr>
										<th>성과급 예산</th>
										<td style="text-align: right"><fmt:formatNumber value="${rs.planAmount}" type="number" var="planAmount" />
											<c:out value="${planAmount}" />원</td>
										<th>지급완료 성과급</th>
										<td style="text-align: right"><fmt:formatNumber value="${rs.exeAmount}" type="number" var="exeAmount" />
											<c:out value="${exeAmount}" />원</td>
									</tr>
									<tr>
										<th>월 성과급 총액</th>
										<td style="text-align: right"><fmt:formatNumber value="${rs.monthlyAmount}" type="number" var="realAmount" /> 
											<c:out value="${realAmount}" />원</td>
										<th>남은 성과급 예산</th>
										<td style="text-align: right"><fmt:formatNumber value="${rs.diffAmount}" type="number" var="restAmount" />
											<c:out value="${restAmount}" />원</td>
									</tr>
								</c:forEach>
								<c:if test="${empty restSalary }">
									<td colspan="4">예산 정보가 없습니다.</td>
								</c:if>
								<c:forEach var="rs" items="${incentive}">
									<c:if test="${project.projectStateDetail eq 'L'}" >
										<tr>
											<th>인센티브 예산</th>
											<td style="text-align: right"><fmt:formatNumber value="${rs.planAmount}" type="number" var="planAmount" />
													<c:out value="${planAmount}" />원</td>
											<th>지급완료 인센티브</th>
											<td style="text-align: right"><fmt:formatNumber value="${rs.exeAmount}" type="number" var="exeAmount" />
													<c:out value="${exeAmount}" />원</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
			<!-- // 프로젝트 예산정보 -->
			
			<!-- 월별 성과급 입력 -->
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">성과급 정보</p>
					</div>
					<div class="text-R">
						<c:choose>
							<c:when test="${projectTypeCode == 'MM' }">
								<c:choose>
									<c:when test="${viewMode == 'myProject' && (project.projectState == '3' || project.adminOpen == 'Y' )}">
										<button type="button" class="btn line btn_blue" onclick="storePUSalary();"><i class="mdi mdi-square-edit-outline"></i>저장</button>
										<button type="button" class="btn line btn_pink" onclick="deleteRowPUSalary()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
									</c:when>
										<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
											<button type="button" class="btn line btn_blue" onclick="storeRestSalary2();"><i class="mdi mdi-square-edit-outline"></i>저장</button>
											<button type="button" class="btn line btn_pink" onclick="deleteRowPUSalary2()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
										</c:when>
										<c:otherwise></c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
										<button type="button" class="btn line btn_blue" onclick="storeRestSalary2();"><i class="mdi mdi-square-edit-outline"></i>저장</button>
										<button type="button" class="btn line btn_pink" onclick="deleteRowPUSalary2()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn line btn_blue" onclick="storeBUSalary();"><i class="mdi mdi-square-edit-outline"></i>저장</button>
										<button type="button" class="btn line btn_pink" onclick="deleteRowBUSalary()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="board_contents">
					<c:choose>
						<c:when test="${projectTypeCode == 'MM' }" >
							<table id="puExpenseTable" class="tbl-edit">
								<colgroup>
									<col style="width: 10%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
										<col style="width: 15%" />
										<col style="width: 15%" />
									</c:if>
								</colgroup>
								<tbody id="puExpenseTableBody">
									<tr>
										<th rowspan="2">선택</th>
										<th rowspan="2">구분</th>
										<th rowspan="2">성명</th>
										<th colspan="2">인건비 예산</th>
										<th colspan="2">성과급 입력</th>
										<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
											<th colspan="2">인센티브 입력</th>
										</c:if>
									</tr>
									<tr>
										<th><c:out value="${month}" />월 금액 예산</th>
										<th>총 금액</th>
										<th><c:out value="${month}" />월 금액</th>
										<th>진행 상태</th>
										<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
											<th><c:out value="${month}" />월 인센티브</th>
											<th>진행 상태</th>
										</c:if>
									</tr>
									<c:forEach var="ex" items="${puSalary}" varStatus="status">
										<tr>
											<td style="text-align: center">
												<c:choose>
												<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4}">-</c:when>
												<c:when test="${ex.approvalYn == 'Y'}">
													<input type="checkbox" class="btn_check" value="<c:out value="${ex.ssn}" />" ssn="<c:out value="${ex.ssn}" />" id="<c:out value="${ex.ssn}" />" />
													<label for="<c:out value="${ex.ssn}" />"></label>
												</c:when>
												<c:otherwise>
													<input type="checkbox" class="btn_check" name="expenseCheck" id="<c:out value="${ex.ssn}" />" value="<c:out value="${ex.seq}" />" ssn="<c:out value="${ex.ssn}" />">
													<label for="<c:out value="${ex.ssn}" />"></label>
												</c:otherwise>
												</c:choose>
											</td>
											<td style="text-align: center"><c:out value="${ex.jobClassName}" /></td>
											<td>
												<c:choose>
												<c:when test="${ex.approvalYn == 'Y'}">
													<select class="selectboxPopup" style="width: 100%">
														<c:forEach var="m" items="${puMember}">
															<option value="<c:out value="${m.ssn}" />"
																<c:if test="${m.ssn == ex.ssn}">selected</c:if>><c:out value="${m.name}" /></option>
														</c:forEach>
													</select>
												</c:when>
												<c:otherwise>
													<select name="ssn" id="ssn" class="selectboxPopup" style="width: 100%">
														<c:forEach var="m" items="${puMember}">
															<option value="<c:out value="${m.ssn}" />"
																<c:if test="${m.ssn == ex.ssn}">selected</c:if>><c:out value="${m.name}" /></option>
														</c:forEach>
													</select>
												</c:otherwise>
												</c:choose>
											</td>	
											<input type="hidden" name="memresRate" id="memresRate" value="<c:out value="${ex.resRate}"/>"></td>
											<!-- 투입률 주석 -->
											<%-- <td><c:out value="${ex.monthlyMM}" /></td> --%>
											
											<!-- 프로젝트 예산 -->
											<td style="text-align: right"><fmt:formatNumber value="${ex.monthlyBudget}" type="number" var="monthlyBudget" /><c:out value="${monthlyBudget}" /></td>
											<td style="text-align: right"><fmt:formatNumber value="${ex.totalBudget}" type="number" var="totalBudget" /><c:out value="${totalBudget}" /></td>
											<!-- // 프로젝트 예산 -->
											
											<!-- 성과급 입력 부분 -->
											<c:choose>
												<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4 && project.adminOpen != 'Y'}">
												<td style="text-align: center">지급 완료</td>
												</c:when>
												<c:when test="${ex.approvalYn == 'Y'}">
													<td style="text-align: right">
														<fmt:formatNumber value="${ex.amount}" type="number" var="amount" /><c:out value="${amount }" />
													</td>
												</c:when>
												<c:otherwise>
													<td style="text-align: right"><input type="text"   style="text-align: right" id="puMMRate" name="puMMRate" class="textInput_right" value="<fmt:formatNumber value="${ex.amount}" type="number" var="amount" /><c:out value="${amount}" />" 
													style="width: 100%; ime-mode: disabled" onchange="getNumber(this);" onkeyup="getNumber(this);" >
													<input type="hidden" id="puSeq" name="puSeq" value="<c:out value="${ex.seq}" />" />
													<input type="hidden" id="puSsn" name="puSsn" value="<c:out value="${ex.ssn}" />" /></td>
												</c:otherwise>
											</c:choose>
											<input type="hidden" value="${ex.amount}"  /> 
											<input type="hidden" value="${ex.mmRate}"  />
											<input type="hidden" id="restAmount" name="restAmount" value="<fmt:formatNumber value="${ex.restAmount}" type="number" var="restAmount" /><c:out value="${restAmount}" />"  /> 
											<!-- // 성과급 입력 부분 -->		
												
											<!-- 성과급 상태 -->	
											<c:choose>
												<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4 && project.adminOpen != 'Y'}">
													<td style="text-align: center">-</td>
												</c:when>
												<c:when test="${ex.amount == 0}">
													<td style="text-align: center">-</td>
												</c:when>
												<c:when test="${ex.approvalYn != 'Y'}">
													<td style="text-align: center">품의 전</td>
												</c:when>
												<c:otherwise>
													<td style="text-align: center">품의 완료</td>
												</c:otherwise>
											</c:choose>
											<!-- // 성과급 상태 -->
														
											<!-- 인센티브 영역 -->
											<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}" >
												<!-- 인센티브 금액 입력 -->
												<c:choose>
													<c:when test="${ex.restApprovalYN == 'Y'}">
													<td style="text-align: right">
														<fmt:formatNumber value="${ex.restAmount}" type="number" var="restAmount" /><c:out value="${restAmount }" />
													</td>
													</c:when>
													<c:otherwise>
														<td><input type="text"  style="text-align: right" id="restAmount" name="restAmount" class="textInput_right" value="<fmt:formatNumber value="${ex.restAmount}" type="number" var="restAmount" /><c:out value="${restAmount}" />"
															style="width: 100%; ime-mode: disabled" onchange="getNumber(this);" onkeyup="getNumber(this);" >
															<input type="hidden" id="puSeq" name="puSeq" value="<c:out value="${ex.seq}" />" />
															<input type="hidden" id="puSsn" name="puSsn" value="<c:out value="${ex.ssn}" />" />
														</td>
													</c:otherwise>
												</c:choose>
												
												<!-- 인센티브 상태 -->
												<c:choose>
													<c:when test="${ex.restAmount == 0}">
														<td style="text-align: center">-</td>
													</c:when>
													<c:when test="${ex.restApprovalYN != 'Y' }" >
														<td style="text-align: center">품의 전</td>
													</c:when>
													<c:otherwise>
														<td style="text-align: center">품의 완료</td>
													</c:otherwise>
												</c:choose>
											</c:if>
											<!-- // 인센티브 영역 -->
										</tr>																																							
										<input type="hidden" name="menpowercnt" id="menpowercnt" value="<c:out value="${puSchedule[status.index].cnt}"/> ">
										<input type="hidden" value="<c:out value="${puSchedule[status.index].ssn}"/> ">
										<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${ex.projectCode }" /> ">
									</c:forEach>
									<c:if test="${empty puSalary}">
										<tr>
											<td align="center" colspan="7">해당 인력이 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<table id="buExpenseTable" class="tbl-edit">
								<colgroup>
									<col style="width: 10%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<col style="width: 15%" />
									<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
										<col style="width: 15%" />
										<col style="width: 15%" />
									</c:if>
								</colgroup>
								<tbody id="buExpenseTableBody">
									<tr>
										<th rowspan="2">선택</th>
										<th rowspan="2">구분</th>
										<th rowspan="2">성명</th>
										<th colspan="2">인건비 예산</th>
										<th colspan="2">성과급 입력</th>
										<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
											<th colspan="2">인센티브 입력</th>
										</c:if>
									</tr>
									<tr>
										<th><c:out value="${month}" />월 금액 예산</th>
										<th>총 금액</th>
										<th><c:out value="${month}" />월 금액</th>
										<th>진행 상태</th>
										<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}">
											<th><c:out value="${month}" />월 인센티브</th>
											<th>진행 상태</th>
										</c:if>
									</tr>
									<c:forEach var="ex" items="${puSalary}">
										<tr>
											<td style="text-align: center">
												<c:choose>
													<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4}">-</c:when>
														<c:when test="${ex.approvalYn == 'Y'}">
															<input type="checkbox" class="btn_check" id="<c:out value="${ex.ssn}" />" value="<c:out value="${ex.seq}" />" ssn="<c:out value="${ex.ssn}" />">
															<label for="<c:out value="${ex.ssn}" />"></label>
														</c:when>
														<c:otherwise>
															<input type="checkbox" class="btn_check" name="expenseCheck" id="<c:out value="${ex.ssn}" />" value="<c:out value="${ex.seq}" />" ssn="<c:out value="${ex.ssn}" />">
															<label for="<c:out value="${ex.ssn}" />"></label>
														</c:otherwise>
												</c:choose>
											</td>
											<td><c:out value="${ex.jobClassName}" /></td>
											<td style="text-alian:center">
												<c:choose>
													<c:when test="${ex.approvalYn == 'Y'}">
															<select class="selectbox" style="width: 100%">
																<c:forEach var="m" items="${buMember}">
																	<option value="<c:out value="${m.ssn}" />" <c:if test="${m.ssn == ex.ssn}">selected</c:if>>
																	<c:out value="${m.name}" /></option>
																</c:forEach>
															</select>
													</c:when>
													<c:otherwise>
															<select name="ssn" id="ssn" class="selectbox" style="width: 100%">
																<c:forEach var="m" items="${buMember}">
																	<option value="<c:out value="${m.ssn}" />"
																		<c:if test="${m.ssn == ex.ssn}">selected</c:if>><c:out value="${m.name}" /></option>
																</c:forEach>
															</select>
													</c:otherwise>
												</c:choose>
											</td>
											<td style="text-align: right"><fmt:formatNumber value="${ex.monthlyBudget}" type="number" var="monthlyBudget" /> 
												<c:out value="${monthlyBudget}" /></td>
											<td style="text-align: right"><fmt:formatNumber value="${ex.totalBudget}" type="number" var="totalBudget" /> 
												<c:out value="${totalBudget}" /></td>	
											<td><c:choose>
													<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4 && project.adminOpen != 'Y'}">
														완료
													</c:when>
													<c:when test="${ex.approvalYn == 'Y'}">
														<div style="text-align: right">
															<fmt:formatNumber value="${ex.amount}" type="number" var="amount" />
															<c:out value="${amount}" />
														</div>
													</c:when>
													<c:otherwise>
														<input type="text" name="amount" id="amount" class="textInput_right" value="<fmt:formatNumber value="${ex.amount}" type="number" var="amount" /><c:out value="${amount}" />"
															style="width: 100%; ime-mode: disabled" onchange="getNumber(this);" onkeyup="getNumber(this);">
														<input type="hidden" name="seq" id="seq" value="<c:out value="${ex.seq}" />">
													</c:otherwise>
											</c:choose></td>
											<input type="hidden" id="payYn" name="payYn" value="" />
											<c:choose>
												<c:when test="${project.projectStateDetail eq 'L' && project.projectState > 4 && project.adminOpen != 'Y'}">
													<td style="text-align: center">-</td>
												</c:when>
												<c:when test="${ex.amount == 0}">
													<td style="text-align: center">-</td>
												</c:when>
												<c:when test="${ex.approvalYn != 'Y'}">
													<td style="text-align: center">품의 전</td>
												</c:when>
												<c:otherwise>
													<td style="text-align: center">품의 완료</td>
												</c:otherwise>
											</c:choose>
											
											<c:if test="${project.projectStateDetail eq 'L' && project.projectState > 4}" >
												<c:choose>
													<c:when test="${ex.restApprovalYN == 'Y'}">
													<td style="text-align: right">
														<fmt:formatNumber value="${ex.restAmount}" type="number" var="restAmount" />
														<c:out value="${restAmount }" />
													</td>
													</c:when>
													<c:otherwise>
														<td><input type="text" id="restAmount" name="restAmount" class="textInput_right" value="<fmt:formatNumber value="${ex.restAmount}" type="number" var="restAmount" /><c:out value="${restAmount}" />"
															style="width: 80px; ime-mode: disabled" onchange="getNumber(this);" onkeyup="getNumber(this);" >
															<input type="hidden" id="puSeq" name="puSeq" value="<c:out value="${ex.seq}" />" />
															<input type="hidden" id="puSsn" name="puSsn" value="<c:out value="${ex.ssn}" />" /></td>
													</c:otherwise>
												</c:choose>
													<!-- 인센티브 상태 -->
												<c:choose>
													<c:when test="${ex.restAmount == 0}">
														<td style="text-align: center">-</td>
													</c:when>
													<c:when test="${ex.restApprovalYN != 'Y' }" >
														<td style="text-align: center">품의 전</td>
													</c:when>
													<c:otherwise>
														<td style="text-align: center">품의 완료</td>
													</c:otherwise>
												</c:choose>
											</c:if>
										</tr>
									</c:forEach>
									<c:if test="${empty buSalary}">
										<tr>
											<td align="center" colspan="7">해당 인력이 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!-- // 월별 성과급 입력 -->
			
			<!-- 지급 내역 -->
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">성과급 지급내역</p>
					</div>
					<div class="text-R">
						<p>금액 단위는 만원 입니다.</p>
					</div>
				</div>
				<div class="board_contents">
				<table id="buExpenseTable" class="tbl-edit">
					<c:choose>
					<c:when test="${! empty salaryHistory}">
						<c:forEach var="outer" items="${salaryHistory}" varStatus="vS">
							<c:choose>
								<c:when test="${vS.count == 1}">
									<thead id="expenseTableHeader">
										<c:forEach var='inner' items="${outer}" begin="1">
											<col style="width: *" />
										</c:forEach>
										<tr>
											<c:forEach var='inner' items="${outer}" begin="1">
												<th><c:out value="${inner}" /></th>
											</c:forEach>
										</tr>
									</thead>
								</c:when>
								<c:otherwise>
									<tbody id="expenseTableBody">
										<tr>
											<c:forEach var='inner' items="${outer}" varStatus="idx" begin="1">
												<td style="text-align: center">
													<c:choose>
														<c:when test="${inner == 'null'}"></c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${idx.count > 1}">
																	<c:out value="${inner}" />
																</c:when>
																<c:otherwise>
																	<c:out value="${inner}" />
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</td>
											</c:forEach>
										</tr>
									</tbody>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tbody id="expenseTableBody">
							<tr>
								<td align="center">지급 내역이 없습니다.</td>
							</tr>
						</tbody>
					</c:otherwise>
				</c:choose>
				</table>
				</div>
			</div>
			<!-- // 지급 내역 -->
		</div>
	</form>
</body>

</html>
