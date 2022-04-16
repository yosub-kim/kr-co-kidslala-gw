<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script src="https://code.jquery.com/jquery-latest.js"></script>

<script type="text/javascript">
jQuery.noConflict();
var j$ = jQuery;

	j$(document).ready(function(){
		var objDragAndDrop = j$(".dragAndDropDiv");
		
		j$(document).on("dragenter",".dragAndDropDiv",function(e){
			e.stopPropagation();
			e.preventDefault();
			j$(this).css('border', '2px solid #0B85A1');
		});
		j$(document).on("dragover",".dragAndDropDiv",function(e){
			e.stopPropagation();
			e.preventDefault();
		});
		j$(document).on("drop",".dragAndDropDiv",function(e){
			j$(this).css('border', '2px dotted #0B85A1');
			e.preventDefault();
			var files = e.originalEvent.dataTransfer.files;
			handleFileUpload(files,objDragAndDrop);
		});
		
		j$(document).on('dragenter', function (e){
			e.stopPropagation();
			e.preventDefault();
		});
		j$(document).on('dragover', function (e){
		  e.stopPropagation();
		  e.preventDefault();
		  objDragAndDrop.css('border', '2px dotted #0B85A1');
		});
		j$(document).on('drop', function (e){
			e.stopPropagation();
			e.preventDefault();
		});
		
		objDragAndDrop.on('click',function (e){
			j$('#fileUpload1').trigger('click');
		});
		j$('#fileUpload1').on('change', function(e) {
			var files = e.originalEvent.target.files;
			handleFileUpload(files,objDragAndDrop);
		});
		
		
		function handleFileUpload(files,obj)
		{
		   for (var i = 0; i < files.length; i++) 
		   {
				var fd = new FormData();
				fd.append('userfile', files[i]);
		 
				var status = new createStatusbar(obj); //Using this we can set progress.
				status.setFileNameSize(files[i].name,files[i].size);
				sendFileToServer(fd,status);
		   }
		}
		function handleFileUpload0(files,obj)
		{
		   for (var i = 0; i < files.length; i++) 
		   {
				var fd = new FormData();
				fd.append('userfile', files[i]);
		 
				var status = new createstatusbar0(obj); //Using this we can set progress.
				status.setFileNameSize(files[i].name,files[i].size);
				sendFileToServer(fd,status);
		   }
		}
		
		function createstatusbar0(obj){
			var appendStr="";
			appendStr += "<tr>";
			appendStr += "  <td>-</td>"
			appendStr += "	<td><input type='text' name='attachFileName' class='textInput_noLine_left' style='width: 100%;' readonly>";
			//appendStr += "		<select name='attachIsEssential' class='hideme'><option value='1'>필수</option><option value='0' <c:out value="${preportEssentialFlag}"/> >비필수</option></select>";
			appendStr += "		<input name='f_seq' type='hidden'/>";
			appendStr += "		<input name='f_taskId' type='hidden'/>";
			appendStr += "	</td>";
			appendStr += "	<td <c:if test="${viewMode != 'SIMPLE'}"> style='width: 100px;' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><select name='attachOutputType' class='selectbox'><option></option><c:forEach var="item" items="${attachOutputType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option>	</c:forEach></select></td>";
			appendStr += "	<td <c:if test="${viewMode != 'SIMPLE'}"> style='width: 100px;' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><input type='text' name='attachOutputName' class='textInput_left' style='width: 100%;'/>";
			appendStr += "		<input type='hidden' name='attachFileId'/>";
			appendStr += "		<input type='hidden' name='attachCreatorId' value='<authz:authentication operation="username" />' />";
			appendStr += "		<input type='hidden' name='attachCreateDate'/>";
			appendStr += "		<input type='hidden' name='attachWorkName' />";
			//appendStr += "		<input type='hidden' name='attachOutputBusType' value='<c:out value="${attachBusType}"/>' />";
			appendStr += "	</td>";
			appendStr += "	<td>"
							+"	<div class='progressBar'><div></div></div>"
							+"	<img alt='파일삭제' src='/images/btn_file_delete.png' align='absmiddle' class='abort'/>"
							+"	<img alt='다운로드' src='/images/btn_download.png' align='absmiddle' width='23px' class='download'/>"
							+"</td>";
			appendStr += "</tr>";
			j$('#attachFileTable0 > tbody:last').append(appendStr);
						
			//this.filename = j$('#attachFileTable0 > tbody > tr:last > td').eq(0).children()[0];
			//this.fileid = j$('#attachFileTable0 > tbody > tr:last > td').eq(2).children()[1];
			//this.size = j$("<div class='filesize'></div>").appendTo(this.statusbar0);
			//this.progressBar = j$('#attachFileTable0 > tbody > tr:last > td[3] > #progressBar');
			//this.abort = j$('#attachFileTable0 > tbody > tr:last > td[3] > #abort');
			
			this.filename = j$(j$('#attachFileTable0 > tbody > tr:last > td').eq(1).children()[0]);
			this.fileid = j$(j$('#attachFileTable0 > tbody > tr:last > td').eq(2).children()[1]);
			this.progressBar = j$(j$('#attachFileTable0 > tbody > tr:last > td').eq(4).children()[0]);
			this.abort = j$(j$('#attachFileTable0 > tbody > tr:last > td').eq(4).children()[1]);
			this.download = j$(j$('#attachFileTable0 > tbody > tr:last > td').eq(4).children()[2]);
			
			
			//obj.after(this.statusbar0);
		 
			this.setFileNameSize = function(name,size){
				var sizeStr="";
				var sizeKB = size/1024;
				if(parseInt(sizeKB) > 1024){
					var sizeMB = sizeKB/1024;
					sizeStr = sizeMB.toFixed(2)+" MB";
				}else{
					sizeStr = sizeKB.toFixed(2)+" KB";
				}
				this.filename.val(name);
				//this.size.html(sizeStr);
			}
			
			this.setFileid = function(fileid){
				this.fileid.val(fileid);
			}
			this.setProgress = function(progress){	   
				var progressBarWidth =progress*this.progressBar.width()/ 100;  
				this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
				if(parseInt(progress) >= 100)
				{
					this.progressBar.hide()
					this.abort.show();
					//this.download.show();
				}
			}

			this.setAbort = function(jqxhr){
				//var sb = this.statusbar0;
				this.abort.click(function()
				{
					deleteRowAttachFile(this, j$(this).parent().parent().children()[2].childElements()[1].value)
				});
			}

			this.setDownload = function(jqxhr){
				//var sb = this.statusbar0;
				this.download.click(function()
				{
					fileDownload(this, j$(this).parent().parent().children()[2].childElements()[1].value)
				});
			}
		}
		
		function createStatusbar(obj){
			var appendStr="";
			appendStr += "<tr>";
			appendStr += "  <td>-</td>"
			appendStr += "	<td><input type='text' name='attachFileName' class='textInput_noLine_left' style='width: 100%;' readonly>";
			appendStr += "		<select name='attachIsEssential' class='hideme'><option value='1'>필수</option><option value='0' <c:out value="${preportEssentialFlag}"/> >비필수</option></select>";
			appendStr += "		<input name='f_seq' type='hidden'/>";
			appendStr += "		<input name='f_taskId' type='hidden'/>";
			appendStr += "	</td>";
			appendStr += "	<td <c:if test="${viewMode != 'SIMPLE'}"> class='hideme' style='width: 100px;' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><select name='attachOutputType' class='selectbox'><option></option><c:forEach var="item" items="${attachOutputType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option>	</c:forEach></select></td>";
			appendStr += "	<td <c:if test="${viewMode != 'SIMPLE'}"> class='hideme' style='width: 100px;' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><input type='text' name='attachOutputName' class='textInput_left' style='width: 100%;'/>";
			appendStr += "		<input type='hidden' name='attachFileId'/>";
			appendStr += "		<input type='hidden' name='attachCreatorId' value='<authz:authentication operation="username" />' />";
			appendStr += "		<input type='hidden' name='attachCreateDate'/>";
			appendStr += "		<input type='hidden' name='attachWorkName' />";
			appendStr += "		<input type='hidden' name='attachOutputBusType' value='<c:out value="${attachBusType}"/>' />";
			appendStr += "	</td>";
			appendStr += "	<td>"
							+"	<div class='progressBar'><div></div></div>"
							+"	<img alt='파일삭제' src='/images/btn_file_delete.png' align='absmiddle' class='abort'/>"
							+"	<img alt='다운로드' src='/images/btn_download.png' align='absmiddle' width='23px' class='download'/>"
							+"</td>";
			appendStr += "</tr>";
			j$('#attachFileTable1 > tbody:last').append(appendStr);
						
			//this.filename = j$('#attachFileTable1 > tbody > tr:last > td').eq(0).children()[0];
			//this.fileid = j$('#attachFileTable1 > tbody > tr:last > td').eq(2).children()[1];
			//this.size = j$("<div class='filesize'></div>").appendTo(this.statusbar);
			//this.progressBar = j$('#attachFileTable1 > tbody > tr:last > td[3] > #progressBar');
			//this.abort = j$('#attachFileTable1 > tbody > tr:last > td[3] > #abort');
			
			this.filename = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(1).children()[0]);
			this.fileid = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(3).children()[1]);
			this.progressBar = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(4).children()[0]);
			this.abort = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(4).children()[1]);
			this.download = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(4).children()[2]);
			
			
			//obj.after(this.statusbar);
		 
			this.setFileNameSize = function(name,size){
				var sizeStr="";
				var sizeKB = size/1024;
				if(parseInt(sizeKB) > 1024){
					var sizeMB = sizeKB/1024;
					sizeStr = sizeMB.toFixed(2)+" MB";
				}else{
					sizeStr = sizeKB.toFixed(2)+" KB";
				}
				this.filename.val(name);
				//this.size.html(sizeStr);
			}
			
			this.setFileid = function(fileid){
				this.fileid.val(fileid);
			}
			this.setProgress = function(progress){	   
				var progressBarWidth =progress*this.progressBar.width()/ 100;  
				this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
				if(parseInt(progress) >= 100)
				{
					this.progressBar.hide()
					this.abort.show();
					//this.download.show();
				}
			}

			this.setAbort = function(jqxhr){
				//var sb = this.statusbar;
				this.abort.click(function()
				{
					deleteRowAttachFile(this, j$(this).parent().parent().children()[3].childElements()[1].value)
				});
			}

			this.setDownload = function(jqxhr){
				//var sb = this.statusbar;
				this.download.click(function()
				{
					fileDownload(this, j$(this).parent().parent().children()[3].childElements()[1].value)
				});
			}
		}
		
		function sendFileToServer(formData,status)
		{
			var uploadURL = "/action/RepositoryAction.do?mode=fileUpload"; //Upload URL
			var extraData ={}; //Extra Data.
			var jqXHR=j$.ajax({
					xhr: function() {
					var xhrobj = j$.ajaxSettings.xhr();
					if (xhrobj.upload) {
							xhrobj.upload.addEventListener('progress', function(event) {
								var percent = 0;
								var position = event.loaded || event.position;
								var total = event.total;
								if (event.lengthComputable) {
									percent = Math.ceil(position / total * 100);
								}
								//Set progress
								status.setProgress(percent);
							}, false);
						}
					return xhrobj;
				},
				url: uploadURL,
				type: "POST",
				contentType:false,
				processData: false,
				cache: false,
				data: formData,
				success: function(data){
					status.setProgress(100);
					status.setFileid(JSON.parse(data).file.fileId);		 
					//j$("#status1").append("File upload Done<br>");		   
					status.setAbort(jqXHR);
					status.setDownload(jqXHR);
				}
			}); 
		 
		}
		
	});
	

	function deleteRowAttachFile3(obj, fileId){	
		if(!confirm("파일을 삭제하시겠습니까?")) return;
		var deleteURL = "/action/RepositoryAction.do?mode=deleteFile"; //Upload URL
		var extraData ={"fileId":fileId}; //Extra Data.
		var jqXHR=j$.ajax({
			url: deleteURL,
			type: "GET",
			cache: false,
			data: extraData,
			success: function(data){
				var tr = j$(obj).parent().parent();
				tr.remove();
				draftRequest3();
			}
		}); 
	}
	function draftRequest3(){
		var ActionURL = "/action/ProjectEndingAction.do";	
		ActionURL += "?mode=insertAditionalOutput3";
		var sFrm = document.forms["endingFrm"];
		
		AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					/* alert("저장하였습니다."); */
					/* document.location = "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode="+$('projectCode').value; */
					location.reload(true);
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
		)
	}
	
	
	//첨부파일 다운로드
	function fileDownLoad3(uuid){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
	    $("_targetDownload").src = "/servlet/RepositoryDownLoad?fileId=" + uuid;
	}
</script>

	<tr>
		<td>		
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
			 		<td><c:out value=""></c:out>
			 		<c:if test="${fileMode == 'WRITE'}">
							<%-- 결재 업무에서 협조1, 대표이사 단계에서 첨부 칸 숨기기 --%>
							<c:if test="${(sanctionDoc.state ne 'SANCTION_STATE_ASSIST1') and (sanctionDoc.state ne 'SANCTION_STATE_CEO') }">
								<div id="fileUpload" class="dragAndDropDiv">Drag & Drop Files Here or Browse Files</div>
								<input type="file" name="fileUpload" id="fileUpload1" style="display:none;" multiple/>
							</c:if>
						</c:if>
						<br>
						<table id="attachFileTable1" width="100%" class='tableStyle05' cellpadding="0" cellspacing="0">
							<thead id="attachFileTableHeader">
								<c:if test="${fileMode == 'WRITE'}">
									<tr> 
										<%--
										<td style="width: 25px; align: center">선택</td>
										<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 65px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
										<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 75px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>구분</td> 
										 --%>
										<td style="width: 30px;">순서</td>
										<td>파일명</td>
										<td <c:if test="${viewMode != 'SIMPLE'}"> class="hideme" style="width: 100px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td> 
										<td <c:if test="${viewMode != 'SIMPLE'}"> class="hideme" style="width: 130px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
										<td style="width: 70px;">삭제/다운</td> 
										<%--
										<td style="width: 30px;">파일찾기</td>
										 --%>
									</tr> 
								</c:if>
								<c:if test="${fileMode == 'READ'}">
									<c:if test="${!empty fileList1}">
										<tr>
											<%--
											<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 70px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
											<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 80px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>구분</td>
											 --%>
											 <td style="width: 30px;">순서</td>
											<td>파일명</td>
											<td <c:if test="${viewMode != 'SIMPLE'}"> class="hideme" style="width: 120px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td>
											<td <c:if test="${viewMode != 'SIMPLE'}"> class="hideme" style="width: 140px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
											<td style="width: 30px;">내려받기</td> 
										</tr>
									</c:if>
								</c:if>
							</thead>
							<tbody id="attachFileTableBody">
								<c:if test="${fileMode == 'WRITE'}">
									<c:if test="${!empty fileList1}">
										<c:forEach var="file" items="${fileList1}" varStatus="status">
											<input name="f_seq" value="<c:out value="${file.seq}"/>" type="hidden"/>
											<tr>
												<input name="f_taskId" value="<c:out value="${file.taskId}"/>"" type="hidden"/>
												<%--
												<td><input type="checkbox" name="fileAttachCheck"/></td>
												<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><select name="attachIsEssential" class='hideme'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select></td>
												<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
													<select name="attachOutputBusType" class='selectbox'>
														<c:forEach var="item" items="${attachOutputBusType}">
															<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputBusType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
														</c:forEach>
													</select>
												</td>
												 --%>
												<td><c:out value="${status.index + 1}" /></td>
												<c:choose>
													<c:when test="${isReview== 'TRUE'}">
														<td><input type="text" name="attachFileName" id="fileName_<c:out value="${file.attachSeq}"/>" 
															value="<c:out value="${file.attachFileName}" escapeXml="false"/>" class="textInput_left"  
															style="width: 100%;"><select name="attachIsEssential" class='hideme'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select></td>
													</c:when>
													<c:otherwise>
														<td><input type="text" name="attachFileName" id="fileName_<c:out value="${file.attachSeq}"/>" 
															value="<c:out value="${file.attachFileName}" escapeXml="false"/>" class="textInput_noLine_left"  
															readonly style="width: 100%;"><select name="attachIsEssential" class='hideme'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select></td>
													</c:otherwise>
												</c:choose>
												<td class="hideme">
													<select name="attachOutputType" class='selectbox'>
														<c:forEach var="item" items="${attachOutputType}">
															<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
														</c:forEach>
													</select>
												</td>
												<td class="hideme"
												><input type="text" name="attachOutputName" value="<c:out value="${file.attachOutputName}"/>" class="textInput_left" style="width: 100%;"
												><input type="hidden" name="attachFileId"  value="<c:out value="${file.attachFileId}"/>" 
												><input type="hidden" name="attachCreatorId" value="<c:out value="${file.attachCreatorId}" />"
												><input type="hidden" name="attachCreateDate" value="<c:out value="${file.attachCreateDate}" />"
												><input type="hidden" name="attachWorkName" value="<c:out value="${file.attachWorkName}"/>" />
												<input type="hidden" name="attachOutputBusType" value="<c:out value="${file.attachOutputBusType}"/>" /> 
												</td>
												<td>
													<img alt='파일삭제' src='/images/btn_file_delete.png' align='absmiddle' onclick="deleteRowAttachFile3(this, '<c:out value="${file.attachFileId}"/>');" class="abort" style="display: inline;"/>
													<img alt="다운로드" src="/images/btn_download.png" width="23px" align="absmiddle" style="cursor: hand;" onclick="fileDownload3('<c:out value="${file.attachFileId}"/>')" />
												</td>
											</tr>
										</c:forEach>  
									</c:if> 
								</c:if>
								<c:if test="${fileMode == 'READ'}">
									<c:if test="${!empty fileList1}">
										<c:forEach var="file" items="${fileList1}">
											<tr>
												<input name="f_seq" value="<c:out value="${file.seq}"/>"" type="hidden"/>
												<input name="f_taskId" value="<c:out value="${file.taskId}"/>"" type="hidden"/>
												<%--
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
												 --%>
												 <td><c:out value="${status.index + 1}" /></td>
												<td style="text-align: left;"><c:out value="${file.attachFileName}" escapeXml="false"/></td>
												<td class="hideme">
													<select name="attachOutputType" disabled="disabled" class='selectbox'>
														<c:forEach var="item" items="${attachOutputType}">
															<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
														</c:forEach>
													</select>
												</td>
												<td class="hideme"><c:out value="${file.attachOutputName}"/></td>
												<td align="center">
													<input type="hidden" name="attachFileId" id="fileId_a1" value="<c:out value="${file.attachFileId}"/>"> 
													<a href="javascript:void(0);" onclick="fileDownload('<c:out value="${file.attachFileId}"/>')"><img alt="다운로드" src="/images/btn_download.png" align="absmiddle" width="23px" /></a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:if>
							</tbody>
						</table>	
						<br>	
					</td>
				</tr>
			</table>
		</td>
	</tr>		