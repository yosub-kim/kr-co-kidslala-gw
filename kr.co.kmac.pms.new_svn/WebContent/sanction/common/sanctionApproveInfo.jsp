<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

		<!-- 품의 내용 -->
					<c:if test="${!readonly }">
						<table class="tbl-edit">
							<colgroup>
								<col style="width: 13%"/>
								<col style="width: *%" />
								<c:choose>
									<c:when test="${sanctionTemplate.hasWholeApprove}">
										<col style="width: 13%"/>
										<col style="width: 37%" />
									</c:when>
									<c:when test="${sanctionTemplate.hasSptTeamMng and sanctionTemplate.hasSptTeamMngWholeApprove and (sanctionDoc.state eq 'SANCTION_STATE_ASSIST1')}">
										<col style="width: 13%"/>
										<col style="width: 37%" />
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</colgroup>
							<tr>
								<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT' }">
									<input type="hidden" name="isApproved" id="isApproved" value="Y"/>
								</c:if>
								<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
									<th>승인 여부</th>
									<td>
										<ul class="chek_ui">
											<li>
												<input type="radio" class="btn_radio" name="isApproved" id="isApproved_Y" value="Y" checked="">
												<label for="isApproved_Y"><p>승인</p></label>
											</li>
											<li>
												<input type="radio" class="btn_radio" name="isApproved" id="isApproved_N" value="N" >
												<label for="isApproved_N"><p>반려</p></label>
											</li>
										</ul>
								</c:if>
									
								<c:choose>
									<c:when test="${sanctionTemplate.hasWholeApprove}">
										<th>전결 여부</th>
										<td>
											<ul class="chek_ui">
												<li>
													<input type="radio" class="btn_radio" name="isWholeApproval" id="isWholeApproval_Y" value="Y" <c:if test="${readonly}">disabled</c:if>>
													<label for="isWholeApproval_Y"><p>예</p></label>
												</li>
												<li>
													<input type="radio" class="btn_radio" name="isWholeApproval" id="isWholeApproval_N" value="N" <c:if test="${readonly}">disabled</c:if> checked>
													<label for="isWholeApproval_N"><p>아니오</p></label>
												</li>
											</ul>
										</td>
									</c:when>
									<c:otherwise>
										<c:if test="${sanctionTemplate.hasSptTeamMng and sanctionTemplate.hasSptTeamMngWholeApprove and (sanctionDoc.state eq 'SANCTION_STATE_ASSIST1')}">
											<th>전결 여부</th>
											<td>
												<ul class="chek_ui">
													<li>
														<input type="radio" class="btn_radio" name="isWholeApproval" id="isWholeApproval_Y" value="Y" <c:if test="${readonly}">disabled</c:if>>
														<label for="isWholeApproval_Y"><p>예</p></label>
													</li>
													<li>
														<input type="radio" class="btn_radio" name="isWholeApproval" id="isWholeApproval_N" value="N" <c:if test="${readonly}">disabled</c:if> checked>
														<label for="isWholeApproval_N"><p>아니오</p></label>
													</li>
												</ul>
											</td>
										</c:if>
									</c:otherwise>
								</c:choose>
							</tr>	
									
							<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">	
								<c:if test="${!readonly }">
									<c:choose>
										<c:when test="${sanctionTemplate.hasWholeApprove}">
											<th>의견 작성</th>
											<td colspan="3"><div style="padding: 25 0 25 0"><textarea name="rejectReason" id="rejectReason" class="textArea" style="width: 100%; height: 98%;" <c:if test="${readonly}">readonly</c:if>></textarea></div></td>
										</c:when>
										<c:when test="${sanctionTemplate.hasSptTeamMng and sanctionTemplate.hasSptTeamMngWholeApprove and (sanctionDoc.state eq 'SANCTION_STATE_ASSIST1')}">
											<th>의견 작성</th>
											<td colspan="3"><div style="padding: 25 0 25 0"><textarea name="rejectReason" id="rejectReason" class="textArea" style="width: 100%; height: 98%;" <c:if test="${readonly}">readonly</c:if>></textarea></div></td>
										</c:when>
										<c:otherwise>
											<th>의견 작성</th>
											<td><div style="padding: 25 0 25 0"><textarea name="rejectReason" id="rejectReason" class="textArea" style="width: 100%; height: 98%;" <c:if test="${readonly}">readonly</c:if>></textarea></div></td>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:if>
						</table>
						<br>
					</c:if>
							
					<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
						<table class="tbl-edit">
							<colgroup>
								<col style="width: 13%"/>
								<col style="width: *%" />
								<c:choose>
									<c:when test="${sanctionTemplate.hasWholeApprove}">
										<col style="width: 13%"/>
										<col style="width: 37%" />
									</c:when>
									<c:when test="${sanctionTemplate.hasSptTeamMng and sanctionTemplate.hasSptTeamMngWholeApprove and (sanctionDoc.state eq 'SANCTION_STATE_ASSIST1')}">
										<col style="width: 13%"/>
										<col style="width: 37%" />
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</colgroup>
							<tr>
								<c:choose>
									<c:when test="${sanctionTemplate.hasWholeApprove}">
										<th>승인/반려 내용</th>
										<td colspan="3"><div style="padding: 25 0 25 0"><textarea name="rejectReasonView" id="rejectReasonView" class="textArea" style="width: 100%; height: 100px;" readonly><c:out value="${sanctionDoc.rejectReason}" /></textarea></div></td>	
									</c:when>
									<c:when test="${sanctionTemplate.hasSptTeamMng and sanctionTemplate.hasSptTeamMngWholeApprove and (sanctionDoc.state eq 'SANCTION_STATE_ASSIST1')}">
										<th>승인/반려 내용</th>
										<td colspan="3"><div style="padding: 25 0 25 0"><textarea name="rejectReasonView" id="rejectReasonView" class="textArea" style="width: 100%; height: 100px;" readonly><c:out value="${sanctionDoc.rejectReason}" /></textarea></div></td>	
									</c:when>
									<c:otherwise>
										<th>승인/반려 내용</th>
										<td><div style="padding: 25 0 25 0"><textarea name="rejectReasonView" id="rejectReasonView" class="textArea" style="width: 100%; height: 100px;" readonly><c:out value="${sanctionDoc.rejectReason}" /></textarea></div></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</table>
					</c:if>