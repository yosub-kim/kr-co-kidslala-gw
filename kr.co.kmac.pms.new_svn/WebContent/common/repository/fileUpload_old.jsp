<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
	//alert('<c:out value="${param.log}"/>');
	function addRowAttachFile(targetTblObjId){
	    var tableObj;
	    if (targetTblObjId != undefined && targetTblObjId != "")
	    	tableObj = $(targetTblObjId); 
	    else
	    	tableObj = $('attachFileTable');
	
	    var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
	    
	    var newRow=tableObj.insertRow(-1);
		var newCellArr = new Array();
		for ( var i=0;i<tdCount;i++ ){
			newCellArr[i] = newRow.insertCell(-1);
		}
		new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[4], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[5], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[6], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		

		var tbRowId = (tableObj.rows.length-1);

		newCellArr[0].innerHTML = "<input type='checkbox' name='fileAttachCheck' />";
		newCellArr[1].innerHTML = "<select name='attachIsEssential' class='selectbox'><option value='1'>필수</option><option value='0' <c:out value="${preportEssentialFlag}"/> >비필수</option></select>";
		newCellArr[2].innerHTML = "<select name='attachOutputType' class='selectbox'><option></option><c:forEach var="item" items="${attachOutputType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option>	</c:forEach></select>";
		newCellArr[3].innerHTML = "<input type='text' name='attachOutputName' class='textInput_left' style='width: 100%;'/>";
		newCellArr[4].innerHTML = "<input type='text' name='attachFileName' id='fileName_"+tbRowId+"' class='textInput_noLine_left' style='width: 100%;' readonly>";
		newCellArr[5].innerHTML = "<img alt='파일찾기' src='/images/btn_open_folder.png' width='26' height='26' style='cursor: hand;'  id='fileButton_"+tbRowId+"' >";
		newCellArr[6].innerHTML = "<input type='hidden'  name='attachFileId' id='fileId_"+tbRowId+"'>"
										   +"<input type='hidden' name='attachCreatorId' value='<authz:authentication operation="username" />' />"
										   +"<input type='hidden' name='attachWorkName' />"
										   +"<input type='hidden' name='attachCreateDate'/>"
										   +"<input type='hidden' name='attachWorkName' />"
										   +"<input type='hidden' name='attachOutputBusType' value='<c:out value="${attachBusType}"/>' />"
										   +"<img alt='다운로드' src='/images/btn_disk.png' width='26' height='26' align='absmiddle' style='cursor: hand;'>";

		newCellArr[0].className = "BGC";
		newCellArr[1].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[2].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[3].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[4].className = "BGL";
		newCellArr[5].className = "BGC";
		newCellArr[6].className = "BGC";

		dispatchFileEvent("fileButton_"+tbRowId, "fileName_"+tbRowId, "fileId_"+tbRowId);
	}

	//첨부파일 다운로드
	function fileDownload1(element){
		fileDownload($(element).siblings()[0].value, '<c:out value="${param.log}"/>');
	}
	
	
	
	function deleteRowAttachFile(clickObj, targetTblObjId ){
		var deleteOverAllIds = "";
		var chkBoxEls = $$('input[name="fileAttachCheck"]');
		for ( var i=0;i<chkBoxEls.length;i++){ 
			if ( chkBoxEls[i].checked ) {
				$(chkBoxEls[i]).up().up().remove();
			}
		}
	}

	function dispatchFileEvent(_buttonId, _fileName, _fileId) {
		var button = $(_buttonId), fileName = $(_fileName), fileId = $(_fileId);
		new AjaxUpload(button,{
			action: '/action/RepositoryAction.do?mode=fileUpload', 
			name: 'userfile',
			onSubmit : function(file, ext){
				button.src="/images/ajax-loader.gif";
	           	button.style.width = "26";
	           	button.style.height = "26";
				this.disable();
			},
			onComplete: function(file, response){
				this.enable();
	           	var res = eval('(' + response + ')');
	           	fileName.value = res.file.orginalFileName;
	           	fileId.value = res.file.fileId;
	           	button.style.width = "26";
	           	button.style.height = "26";
	           	button.src="/images/btn_open_folder.png";
			}
		});
	}
	
	<c:if test="${fileMode == 'WRITE'}">
		document.observe("dom:loaded", function() {
			<c:if test="${!empty fileList}">
				<c:forEach var="file" items="${fileList}">
					var fileButton_<c:out value="${file.attachSeq}"/> = $('fileButton_<c:out value="${file.attachSeq}"/>'),
						 fileName_<c:out value="${file.attachSeq}"/> = $('fileName_<c:out value="${file.attachSeq}"/>'), 
						 fileId_<c:out value="${file.attachSeq}"/> = $('fileId_<c:out value="${file.attachSeq}"/>');
					new AjaxUpload(fileButton_<c:out value="${file.attachSeq}"/>,{
						action: '/action/RepositoryAction.do?mode=fileUpload', 
						name: 'userfile',
						onSubmit : function(file, ext){
							fileButton_<c:out value="${file.attachSeq}"/>.src="/images/ajax-loader.gif";
							fileButton_<c:out value="${file.attachSeq}"/>.style.width = "26";
							fileButton_<c:out value="${file.attachSeq}"/>.style.height = "26";
							this.disable();
						},
						onComplete: function(file, response){
							this.enable();
				           	var res = eval('(' + response + ')');
				           	fileName_<c:out value="${file.attachSeq}"/>.value = res.file.orginalFileName;
				           	fileId_<c:out value="${file.attachSeq}"/>.value = res.file.fileId;
				           	fileButton_<c:out value="${file.attachSeq}"/>.style.width = "26";
				           	fileButton_<c:out value="${file.attachSeq}"/>.style.height = "26";
				           	fileButton_<c:out value="${file.attachSeq}"/>.src="/images/btn_open_folder.png";
						}
					});
				</c:forEach>
			</c:if>
			/*
			var button = $('fileButton_a1'), fileName = $('fileName_a1'), fileId = $('fileId_a1');
			new AjaxUpload(button,{
				action: '/action/RepositoryAction.do?mode=fileUpload', 
				name: 'userfile',
				onSubmit : function(file, ext){
					button.src="/images/ajax-loader.gif";
		           	button.style.width = "16";
		           	button.style.height = "16";
					this.disable();
				},
				onComplete: function(file, response){
					this.enable();
		           	var res = eval('(' + response + ')');
		           	fileName.value = res.file.orginalFileName;
		           	fileId.value = res.file.fileId;
		           	button.src="/images/btn_open_folder.jpg";
				}
			});
			*/
		});
	</c:if>


	</script>	
		
							<c:choose>
								<c:when test="${fileMode=='READ' && !empty fileList}">
									<!-- sub 타이틀 영역 시작1--> 
									<tr>
										<td>
											<h4 class="subTitle">파일 첨부</h4
										</td>
									</tr>
									<!-- sub 타이틀 영역 종료1--> 			
								</c:when>
								<c:when test="${(fileMode=='READ' || fileMode==null) && empty fileList}">
									<!-- sub 타이틀 영역 종료1--> 		
								</c:when>
								<c:when test="${(fileMode=='WRITE' and  isReview=='TRUE')}">
									<!-- sub 타이틀 영역 종료1--> 		
									<tr>
										<td>
											<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
												<tr><td height='10'></td></tr>
												<tr>
													<td width="70%" align="left" valign="middle"><h4 class="subTitle">필수 산출물 확인(이 프로젝트에 현재까지 등록된 필수 산출물 입니다)</h4></td>
													<td width="30%" align="right" valign="middle"><img src="/images/btn_produc_info.jpg" style="cursor: hand" onclick="open_attachFile()" alt="필수 산출물 안내"></td>
												</tr>
											</table>
										</td>
									</tr>									
								</c:when>
								<c:otherwise>
									<!-- sub 타이틀 영역 시작2--> 
									<tr>
										<td>
											<h4 class="subTitle">파일 첨부</h4>
										</td>
									</tr>
									<!-- sub 타이틀 영역 종료2--> 			
								</c:otherwise>
							</c:choose>
							<tr>
								<td>		
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
									 		<td>
												<table id="attachFileTable" class="tableStyle05">
													<thead id="attachFileTableHeader">
														<c:if test="${fileMode == 'WRITE'}">
															<tr> 
																<td style="width: 25px; padding-left:0px; padding-right: 0px">선택</td>
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 70px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 100px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td> 
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 120px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
																<td>파일명</td>
																<td style="width: 30px;">파일찾기</td>
																<td style="width: 30px;">내려받기</td> 
															</tr> 
														</c:if>
														<c:if test="${fileMode == 'READ'}">
															<c:if test="${!empty fileList}">
																<tr>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 70px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 100px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 120px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
																	<td>파일명</td>
																	<td style="width: 30px; padding-left:0px; padding-right: 0px">내려받기</td> 
																</tr>
															</c:if>
														</c:if>
													</thead>
													<tbody id="attachFileTableBody">
														<c:if test="${fileMode == 'WRITE'}">
															<c:if test="${!empty fileList}">
																<c:forEach var="file" items="${fileList}">
																	<input name="f_seq" value="<c:out value="${file.seq}"/>"" type="hidden"/>
																	<tr>
																		<input name="f_taskId" value="<c:out value="${file.taskId}"/>"" type="hidden"/>
																		<td><input type="checkbox" name="fileAttachCheck"/></td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><select name="attachIsEssential" class='selectbox'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select></td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
																			<select name="attachOutputType" class='selectbox'>
																				<c:forEach var="item" items="${attachOutputType}">
																					<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
																				</c:forEach>
																			</select>
																		</td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>
																		><input type="text" name="attachOutputName" value="<c:out value="${file.attachOutputName}"/>" class="textInput_left" style="width: 100%;"></td>
																		<c:choose>
																			<c:when test="${isReview== 'TRUE'}">
																				<td><input type="text" name="attachFileName" id="fileName_<c:out value="${file.attachSeq}"/>" 
																					value="<c:out value="${file.attachFileName}" escapeXml="false"/>" class="textInput_left"  
																					style="width: 100%;"></td>
																			</c:when>
																			<c:otherwise>
																				<td><input type="text" name="attachFileName" id="fileName_<c:out value="${file.attachSeq}"/>" 
																					value="<c:out value="${file.attachFileName}" escapeXml="false"/>" class="textInput_noLine_left"  
																					readonly style="width: 100%;"></td>
																			</c:otherwise>
																		</c:choose>
																		<td><img alt="파일찾기" src="/images/btn_open_folder.png" width='26' height='26'  style="cursor: hand;"  id="fileButton_<c:out value="${file.attachSeq}"/>" ></td>
																		<td>
																			<input type="hidden" name="attachFileId" id="fileId_<c:out value="${file.attachSeq}"/>" value="<c:out value="${file.attachFileId}"/>" >
																			<input type="hidden" name="attachCreateDate" value="<c:out value="${file.attachCreateDate}" />" />
																			<input type="hidden" name="attachCreatorId" value="<c:out value="${file.attachCreatorId}" />" />
																			<input type="hidden" name="attachWorkName" value="<c:out value="${file.attachWorkName}"/>" />
																			<input type="hidden" name="attachOutputBusType" value="<c:out value="${file.attachOutputBusType}"/>" />
																			<img alt="다운로드" src="/images/btn_disk.png" width='26' height='26' align="absmiddle" style="cursor: hand;" onclick="fileDownload1(this)" />
																		</td>
																	</tr>
																</c:forEach>  
															</c:if> 
														</c:if>
														<c:if test="${fileMode == 'READ'}">
															<c:if test="${!empty fileList}">
																<c:forEach var="file" items="${fileList}">
																	<tr>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><select name="attachIsEssential" disabled="disabled" class='selectbox'>
																				<option value="0" <c:if test="${file.attachIsEssential == '0'}">selected</c:if>>비필수</option>
																				<option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option>
																			</select></td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
																		<select name="attachOutputType" disabled="disabled" class='selectbox'>
																			<c:forEach var="item" items="${attachOutputType}">
																				<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
																			</c:forEach>
																		</select>
																		</td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if><c:if test="${viewMode != 'SIMPLE'}"> class="myoverflowC" </c:if>><c:out value="${file.attachOutputName}"/></td>
																		<td class="myoverflow"><c:out value="${file.attachFileName}" escapeXml="false"/></td>
																		<td align="center">
																			<input type="hidden" name="attachFileId" id="fileId_a1" value="<c:out value="${file.attachFileId}"/>"> 
																			<a href="javascript:void(0);" onclick="fileDownload1(this)"
																			><img alt="다운로드" src="/images/btn_disk.png" align="absmiddle" width='26' height='26' /></a>
																		</td>
																	</tr>
																</c:forEach>
															</c:if>
														</c:if>
													</tbody>
												</table>
									 		</td> 
									 	</tr>
										<c:if test="${fileMode == 'WRITE'}">
											<tr><td height="4"></td></tr>
										 	<tr>
												<td>
												<div class="btNdiv mt10 mb10 txtR">
												<a class="btNa0a0a0 txt4btn" href="javascript:addRowAttachFile('attachFileTable')">행 추가</a>
												<a class="btNe14f42 txt4btn" href="javascript:deleteRowAttachFile()">행 삭제</a></div>
												</td>
											</tr>	
										</c:if>
									</table>
								</td>
							</tr>		