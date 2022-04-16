<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<style>
	.dragAndDropDiv {
		border: 2px dashed #92AAB0;
		width: 751px;
		height: 80px;
		color: #92AAB0;
		text-align: center;
		vertical-align: middle;
		padding: 10px 4px 10px 10px;
		font-size: 200%;
		display: table-cell;
	}
	.statusbar{
		border-top:1px solid #A9CCD1;
		min-height:25px;
		width:99%;
		padding:5px 5px 0px 5px;
		vertical-align: middle;
	}
	.statusbar:nth-child(odd){
		background:#EBEFF0;
	}
	.attachOutputType{
		display:inline-block;
		vertical-align: middle;
		color:#30693D;
		width:100px;
		margin-left:5px;
		margin-right:5px;
	}
	.attachOutputName{
		display:inline-block;
		vertical-align: middle;
		color:#30693D;
		width:128px;
		margin-left:5px;
		margin-right:5px;
	}
	.filename{
		display:inline-block;
		vertical-align: middle;
		width:265px;
		margin-left:5px;
		margin-right:5px;
	}
	.filesize{
		display:inline-block;
		vertical-align: middle;
		color:#30693D;
		width:70px;
		margin-left:5px;
		margin-right:5px;
	}
	.progressBar {
		width: 77px;
		height: 22px;
		border: 1px solid #ddd;
		border-radius: 5px; 
		overflow: hidden;
		display:inline-block;
		margin-left:5px;
		margin-right:5px;
		vertical-align:middle;
	}	 
	.progressBar div {
		height: 100%;
		color: #fff;
		text-align: right;
		width: 0;
		padding:4px 0px;
		background-color: #0ba1b5; border-radius: 3px; 
	}
	.abort{
		display: none;
		width: 45px;
		background-color:#A8352F;
		-moz-border-radius:4px;
		-webkit-border-radius:4px;
		border-radius:4px;
		color:#fff;
		font-family:arial;font-size:13px;font-weight:normal;
		padding:4px 5px;
		cursor:pointer;
		vertical-align:middle;
	}
</style>
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
		//drag 영역 클릭시 파일 선택창
		objDragAndDrop.on('click',function (e){
			j$('input[type=file]').trigger('click');
		});
		j$('input[type=file]').on('change', function(e) {
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
		
		var rowCount=0;
		function createStatusbar(obj){
			var appendStr="";
			appendStr += "<tr>";
			appendStr += "	<td><input type='text' name='attachFileName' class='textInput_noLine_left' style='width: 100%;' readonly>";
			appendStr += "		<select name='attachIsEssential' class='hideme'><option value='1'>필수</option><option value='0' <c:out value="${preportEssentialFlag}"/> >비필수</option></select></td>";
			appendStr += "	<td <c:if test="${viewMode != 'SIMPLE'}"> style='width: 100px;' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><select name='attachOutputType' class='selectbox'><option></option><c:forEach var="item" items="${attachOutputType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option>	</c:forEach></select></td>";
			appendStr += "	<td <c:if test="${viewMode != 'SIMPLE'}"> style='width: 100px;' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><input type='text' name='attachOutputName' class='textInput_left' style='width: 100%;'/>";
			appendStr += "		<input type='hidden' name='attachFileId'/>";
			appendStr += "		<input type='hidden' name='attachCreatorId' value='<authz:authentication operation="username" />' />";
			appendStr += "		<input type='hidden' name='attachCreateDate'/>";
			appendStr += "		<input type='hidden' name='attachWorkName' />";
			appendStr += "		<input type='hidden' name='attachOutputBusType' value='<c:out value="${attachBusType}"/>' />";
			appendStr += "	</td>";
			appendStr += "	<td><div class='progressBar'><div></div></div><div class='abort'>삭제</div></td>";
			appendStr += "</tr>";
			j$('#attachFileTable1 > tbody:last').append(appendStr);
						
			//this.filename = j$('#attachFileTable1 > tbody > tr:last > td').eq(0).children()[0];
			//this.fileid = j$('#attachFileTable1 > tbody > tr:last > td').eq(2).children()[1];
			//this.size = j$("<div class='filesize'></div>").appendTo(this.statusbar);
			//this.progressBar = j$('#attachFileTable1 > tbody > tr:last > td[3] > #progressBar');
			//this.abort = j$('#attachFileTable1 > tbody > tr:last > td[3] > #abort');
			
			this.filename = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(0).children()[0]);
			this.fileid = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(2).children()[1]);
			this.progressBar = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(3).children()[0]);
			this.abort = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(3).children()[1]);
			
			
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
				}
			}
			
			this.setAbort = function(jqxhr){
				//var sb = this.statusbar;
				this.abort.click(function()
				{
					//jqxhr.abort();
					//sb.hide();
					deleteRowAttachFile(this, j$(this).parent().parent().children()[2].childElements()[1].value)
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
				}
			}); 
		 
			status.setAbort(jqXHR);
		}
		
	});
	
	//첨부파일 다운로드
	function fileDownload1(uuid){
		fileDownload(uuid, '<c:out value="${param.log}"/>');
	}
	
	function deleteRowAttachFile(obj, fileId){	
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
			}
		}); 
		
	}

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
		
						<div id="fileUpload" class="dragAndDropDiv">Drag & Drop Files Here or Browse Files</div>
						<input type="file" name="fileUpload" id="fileUpload" style="display:none;" multiple/>
						<table id="attachFileTable1" width="100%" class='tableStyle05' cellpadding="0" cellspacing="0">
							<thead id="attachFileTableHeader">
								<c:if test="${fileMode == 'WRITE'}">
									<tr> 
										<%--
										<td style="width: 25px; align: center">선택</td>
										<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 65px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
										<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 75px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>구분</td> 
										 --%>
										<td>파일명</td>
										<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 100px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td> 
										<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 130px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
										<td style="width: 70px;">상태</td> 
										<%--
										<td style="width: 30px;">파일찾기</td>
										 --%>
									</tr> 
								</c:if>
								<c:if test="${fileMode == 'READ'}">
									<c:if test="${!empty fileList}">
										<tr>
											<%--
											<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 70px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>필수여부</td>
											<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 80px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>구분</td>
											 --%>
											<td>파일명</td>
											<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 120px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부타입</td>
											<td <c:if test="${viewMode != 'SIMPLE'}"> style="width: 140px;" </c:if><c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>첨부문서명</td>
											<td style="width: 30px;">내려받기</td> 
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
												<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
													<select name="attachOutputType" class='selectbox'>
														<c:forEach var="item" items="${attachOutputType}">
															<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
														</c:forEach>
													</select>
												</td>
												<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>
												><input type="text" name="attachOutputName" value="<c:out value="${file.attachOutputName}"/>" class="textInput_left" style="width: 100%;"
												><input type="hidden" name="attachFileId"  value="<c:out value="${file.attachFileId}"/>" 
												><input type="hidden" name="attachCreatorId" value="<c:out value="${file.attachCreatorId}" />"
												><input type="hidden" name="attachCreateDate" value="<c:out value="${file.attachCreateDate}" />"
												><input type="hidden" name="attachWorkName" value="<c:out value="${file.attachWorkName}"/>" />
												<input type="hidden" name="attachOutputBusType" value="<c:out value="${file.attachOutputBusType}"/>" /> 
												</td>
												<td>
													<div class='abort' style="display: inline;" onclick="deleteRowAttachFile(this, '<c:out value="${file.attachFileId}"/>');">삭제</div>
													<%--
													<img alt="다운로드" src="/images/btn_disk.png" align="absmiddle" style="cursor: hand;" onclick="fileDownload1('<c:out value="${file.attachFileId}"/>')" />
													 --%>
												</td>
											</tr>
										</c:forEach>  
									</c:if> 
								</c:if>
								<c:if test="${fileMode == 'READ'}">
									<c:if test="${!empty fileList}">
										<c:forEach var="file" items="${fileList}">
											<tr>
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
												<td style="text-align: left;"><c:out value="${file.attachFileName}" escapeXml="false"/></td>
												<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
													<select name="attachOutputType" disabled="disabled" class='selectbox'>
														<c:forEach var="item" items="${attachOutputType}">
															<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
														</c:forEach>
													</select>
												</td>asdf
												<td <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>><c:out value="${file.attachOutputName}"/></td>
												<td align="center">
													<input type="hidden" name="attachFileId" id="fileId_a1" value="<c:out value="${file.attachFileId}"/>"> 
													<a href="javascript:void(0);" onclick="fileDownload1('<c:out value="${file.attachFileId}"/>')"><img alt="다운로드" src="/images/btn_download.png" align="absmiddle" width="20px" /></a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:if>
							</tbody>
						</table>						
		</td>
	</tr>		