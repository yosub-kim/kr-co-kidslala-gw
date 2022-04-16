<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
	function addRowAttachFile1(targetTblObjId){
	    var tableObj;
	    if (targetTblObjId != undefined && targetTblObjId != "")
	    	tableObj = $(targetTblObjId); 
	    else
	    	tableObj = $('attachFileTable1');
	
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
		new Effect.Highlight(newCellArr[7], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 

		var tbRowId = (tableObj.rows.length-1);

		newCellArr[0].innerHTML = "<input type='checkbox' name='fileAttachCheck' /><input name='f_seq' type='hidden'/><input name='f_taskId' type='hidden'/>";			
//		newCellArr[1].innerHTML = "<select name='attachIsEssential' class='selectbox'><option value='1'>필수</option><option value='0' <c:out value="${preportEssentialFlag}"/> >비필수</option></select>";
//		newCellArr[2].innerHTML = "<select name='attachOutputBusType' class='selectbox'><option></option><c:forEach var="item" items="${attachOutputBusType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option>	</c:forEach></select>";
		newCellArr[5].innerHTML = "<input type='text' name='attachFileName' id='fileName_"+tbRowId+"' class='textInput_noLine_left' style='width: 100%;' readonly>";
		newCellArr[3].innerHTML = "<select name='attachOutputType' class='selectbox'><option></option><c:forEach var="item" items="${attachOutputType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option>	</c:forEach></select>";
		newCellArr[4].innerHTML = "<input type='text' name='attachOutputName' class='textInput_left' style='width: 100%;'/>";
		newCellArr[7].innerHTML = "<input type='hidden'  name='attachFileId' id='fileId_"+tbRowId+"'>"
										   +"<input type='hidden' name='attachCreatorId' value='<c:out value="${projectSummaryData.pmName}" />' />"
										   +"<input type='hidden' name='attachWorkName' />"
										   +"<input type='hidden' name='attachCreateDate'/>"
										   +"<input type='hidden' name='attachWorkName' />"
										   +"<img alt='다운로드' src='/images/btn_disk.png' align='absmiddle' style='cursor: hand;'>";

		
		
		
		newCellArr[6].innerHTML = "<img alt='파일찾기' src='/images/btn_open_folder.png' width='26' height=26' style='cursor: hand;'  id='fileButton_"+tbRowId+"' >";

		newCellArr[0].className = "BGC";
		newCellArr[1].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[2].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[3].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[4].className = "<c:if test="${viewMode == 'SIMPLE'}">hideme</c:if><c:if test="${viewMode != 'SIMPLE'}">BGC</c:if>";
		newCellArr[5].className = "BGL";
		newCellArr[6].className = "BGC";
		newCellArr[7].className = "BGC";

		dispatchFileEvent("fileButton_"+tbRowId, "fileName_"+tbRowId, "fileId_"+tbRowId);
	}

	//첨부파일 다운로드
	function fileDownload1(element){
		fileDownload($(element).siblings()[0].value, 'Y');
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
	           	button.style.width = "16";
	           	button.style.height = "16";
				this.disable();
			},
			onComplete: function(file, response){
				this.enable();
	           	var res = eval('(' + response + ')');
	           	fileName.value = res.file.orginalFileName;
	           	fileId.value = res.file.fileId;
	           	button.src="/images/btn_open_folder.png";
			}
		});
	}
	
	<c:if test="${fileMode == 'WRITE'}">
		document.observe("dom:loaded", function() {
			<c:if test="${!empty fileList1}">
				<c:forEach var="file" items="${fileList1}">
					var fileButton_<c:out value="${file.attachSeq}"/> = $('fileButton_<c:out value="${file.attachSeq}"/>'),
						 fileName_<c:out value="${file.attachSeq}"/> = $('fileName_<c:out value="${file.attachSeq}"/>'), 
						 fileId_<c:out value="${file.attachSeq}"/> = $('fileId_<c:out value="${file.attachSeq}"/>');
					new AjaxUpload(fileButton_<c:out value="${file.attachSeq}"/>,{
						action: '/action/RepositoryAction.do?mode=fileUpload', 
						name: 'userfile',
						onSubmit : function(file, ext){
							fileButton_<c:out value="${file.attachSeq}"/>.src="/images/ajax-loader.gif";
							fileButton_<c:out value="${file.attachSeq}"/>.style.width = "16";
							fileButton_<c:out value="${file.attachSeq}"/>.style.height = "16";
							this.disable();
						},
						onComplete: function(file, response){
							this.enable();
				           	var res = eval('(' + response + ')');
				           	fileName_<c:out value="${file.attachSeq}"/>.value = res.file.orginalFileName;
				           	fileId_<c:out value="${file.attachSeq}"/>.value = res.file.fileId;
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
		
							<tr>
								<td>		
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
									 		<td>
												<table id="attachFileTable1" width="100%" class='tableStyle05' cellpadding="0" cellspacing="0">
													<thead id="attachFileTableHeader">
														<c:if test="${fileMode == 'WRITE'}">
															<tr> 
																<td style="width: 25px; align: center">선택</td>
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 65px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 75px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>구분</td> 
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 100px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td> 
																<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 130px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
																<td>파일명</td>
																<td style="width: 30px;">파일찾기</td>
																<td style="width: 30px;">내려받기</td> 
															</tr> 
														</c:if>
														<c:if test="${fileMode == 'READ'}">
															<c:if test="${!empty fileList1}">
																<tr>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 70px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 80px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>구분</td>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 120px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td>
																	<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 140px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
																	<td>파일명</td>
																	<td style="width: 30px;">내려받기</td> 
																</tr>
															</c:if>
														</c:if>
													</thead>
													<tbody id="attachFileTableBody">
														<c:if test="${fileMode == 'WRITE'}">
															<c:if test="${!empty fileList1}">
																<c:forEach var="file" items="${fileList1}">
																	<input name="f_seq" value="<c:out value="${file.seq}"/>"" type="hidden"/>
																	<tr>
																		<input name="f_taskId" value="<c:out value="${file.taskId}"/>"" type="hidden"/>
																		<td><input type="checkbox" name="fileAttachCheck"/></td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><select name="attachIsEssential" class='selectbox'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select></td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
																			<select name="attachOutputBusType" class='selectbox'>
																				<c:forEach var="item" items="${attachOutputBusType}">
																					<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputBusType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
																				</c:forEach>
																			</select>
																		</td>
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
																		<td align="center"><img alt="파일찾기" src="/images/btn_open_folder.png" style="cursor: hand;"  id="fileButton_<c:out value="${file.attachSeq}"/>" ></td>
																		<td align="center">
																			<img alt="다운로드" src="/images/btn_disk.png" align="absmiddle" style="cursor: hand;" onclick="fileDownload1(this)" />
																			<input type="hidden" name="attachFileId" id="fileId_<c:out value="${file.attachSeq}"/>" value="<c:out value="${file.attachFileId}"/>" >
																			<input type="hidden" name="attachCreateDate" value="<c:out value="${file.attachCreateDate}" />" />
																			<input type="hidden" name="attachCreatorId" value="<c:out value="${file.attachCreatorId}" />" />
																			<input type="hidden" name="attachWorkName" value="<c:out value="${file.attachWorkName}"/>" />
																		</td>
																	</tr>
																</c:forEach>  
															</c:if> 
														</c:if>
														<c:if test="${fileMode == 'READ'}">
															<c:if test="${!empty fileList1}">
																<c:forEach var="file" items="${fileList1}">
																	<tr>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><select name="attachIsEssential" disabled="disabled" class='selectbox'>
																				<option value="0" <c:if test="${file.attachIsEssential == '0'}">selected</c:if>>비필수</option>
																				<option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option>
																			</select></td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
																			<select name="attachOutputBusType" disabled="disabled" class='selectbox'>
																				<c:forEach var="item" items="${attachOutputBusType}">
																					<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputBusType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
																				</c:forEach>
																			</select>
																		</td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
																			<select name="attachOutputType" disabled="disabled" class='selectbox'>
																				<c:forEach var="item" items="${attachOutputType}">
																					<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
																				</c:forEach>
																			</select>
																		</td>
																		<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><c:out value="${file.attachOutputName}"/></td>
																		<td><c:out value="${file.attachFileName}" escapeXml="false"/></td>
																		<td align="center">
																			<input type="hidden" name="attachFileId" id="fileId_a1" value="<c:out value="${file.attachFileId}"/>"> 
																			<a href="javascript:void(0);" onclick="fileDownload1(this)"><img alt="다운로드" src="/images/btn_disk.png" align="absmiddle"/></a>
																		</td>
																	</tr>
																</c:forEach>
															</c:if>
														</c:if>
													</tbody>
												</table>
									 		</td> 
									 	</tr>
									</table>
								</td>
							</tr>		