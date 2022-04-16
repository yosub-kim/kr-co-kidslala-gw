<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<style>
/* Hide Column */
	.hideme {display:none}
	
	.dragAndDropDiv {
		border: 2px dashed #92AAB0;
		width: 40%;
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
		cursor:pointer;
		width: 24px;
		margin-right: 10px;
		vertical-align:middle;
	}
	.download {
		cursor: hand; 
		display: none;
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
			appendStr += "<div class='file_text_area' style='padding:0 0 2 23'>";
			appendStr += "	<div class='file_text'><p><a href='#'><input type='text' name='attachFileName' style='width:100%;' readonly></a></p> ";
			appendStr += "		<select name='attachIsEssential' class='hideme'><option value='1'>필수</option><option value='0' <c:out value="${preportEssentialFlag}"/> >비필수</option></select>";
			appendStr += "		<input name='f_seq' type='hidden'/>";
			appendStr += "		<input name='f_taskId' type='hidden'/>";
			appendStr += "	<span class='btn_del'><a href='#' class='abort'><i class='mdi mdi-close'></i></a></span></div>";
			appendStr += "	<div <c:if test="${viewMode != 'SIMPLE'}"> class='select_area' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><select name='attachOutputType' class='select'><option value=''>첨부타입</option><c:forEach var="item" items="${attachOutputType}"><option value='<c:out value="${item.key}"/>'><c:out value="${item.detail1}"/></option></c:forEach></select></div>";
			appendStr += "	<div <c:if test="${viewMode != 'SIMPLE'}"> class='select_area' </c:if><c:if test="${viewMode == 'SIMPLE'}"> class='hideme' </c:if>><select name='attachOutputName' class='select'><option value='공개' <c:if test="${attachOutputName == '공개'}">selected</c:if>>공개</option><option value='비공개' <c:if test="${attachOutputName=='비공개'}">selected</c:if>>비공개</option></select>";
			appendStr += "		<input type='hidden' name='attachFileId' />";
			appendStr += "		<input type='hidden' name='attachCreatorId' value='<authz:authentication operation="username" />' />";
			appendStr += "		<input type='hidden' name='attachCreateDate' />";
			appendStr += "		<input type='hidden' name='attachWorkName' />";
			appendStr += "		<input type='hidden' name='attachOutputBusType' value='<c:out value="${attachBusType}"/>' />";
			appendStr += "	</div>";
			appendStr += "	<div>"
		        +" <div class='progressBar'><div>"
				+" </div>";
appendStr += "</div>";
			j$('#attachFileTable1 > tbody:last').append(appendStr);
						
			//this.filename = j$('#attachFileTable1 > tbody > tr:last > td').eq(0).children()[0];
			//this.fileid = j$('#attachFileTable1 > tbody > tr:last > td').eq(2).children()[1];
			//this.size = j$("<div class='filesize'></div>").appendTo(this.statusbar);
			//this.progressBar = j$('#attachFileTable1 > tbody > tr:last > td[3] > #progressBar');
			//this.abort = j$('#attachFileTable1 > tbody > tr:last > td[3] > #abort');
			
			this.filename = j$(j$('#attachFileTable1 > tbody > div:last > div > p > a').eq(0).children()[0]);
			this.fileid = j$(j$('#attachFileTable1 > tbody > div:last > div').eq(2).children()[1]);
			this.progressBar = j$(j$('#attachFileTable1 > tbody > div:last > div').eq(3).children()[0]);
			this.abort = j$(j$('#attachFileTable1 > tbody > div:last > div > span').eq(0).children()[0]);
			this.download = j$(j$('#attachFileTable1 > tbody > tr:last > td').eq(3).children()[2]);
			
			
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
					deleteRowAttachFile(this, this.fileid)
				});
			}

			this.setDownload = function(jqxhr){
				//var sb = this.statusbar;
				this.download.click(function()
				{
					fileDownload1(this, j$(this).parent().parent().children()[2].childElements()[1].value)
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
	
	//첨부파일 다운로드
	function fileDownload1(uuid){
		fileDownload(uuid, 'Y');
	}
	
	function deleteRowAttachFile(obj, fileId){	
		if(!confirm("파일을 삭제하시겠습니까?")) return;
		var deleteURL = "/action/RepositoryAction.do?mode=deleteFile"; //Upload URL
		var extraData ={"fileId":fileId}; //Extra Data.
		var jqXHR=j$.ajax({
			url: deleteURL,
			type: "GET",
			cache: false,
			data: extraData,
			success: function(data){
				var tr = j$(obj).parent().parent().parent();
				tr.remove();
			}
		}); 
		
	}

</script>

	
<c:if test="${fileMode == 'WRITE'}">
		</br>
		<%-- 결재 업무에서 협조1, 대표이사 단계에서 첨부 칸 숨기기 --%>
		<c:if test="${(sanctionDoc.state ne 'SANCTION_STATE_ASSIST1') and (sanctionDoc.state ne 'SANCTION_STATE_CEO') }">
			<div style="padding: 0 30 0 30">
				<div id="fileUpload" class="dragAndDropDiv">Drag & Drop Files Here or Browse Files</div>
				<input type="file" name="fileUpload" id="fileUpload" style="display:none;" multiple/>
			</div>
		</c:if>
		</br>
	</c:if>
	
	<table id="attachFileTable1" width="100%" cellpadding="0" cellspacing="0">
		<thead id="attachFileTableHead"></thead>
		<tbody id="attachFileTableBody">		
			<c:if test="${fileMode == 'WRITE'}">
				<c:if test="${!empty fileList}">
					<c:forEach var="file" items="${fileList}">
						<div class="file_text_area" style="padding: 0 0 0 30">
							<input name="f_seq" value="<c:out value="${file.seq}"/>" type="hidden"/>
							<input name="f_taskId" value="<c:out value="${file.taskId}"/>" type="hidden"/>
							<c:choose>
								<c:when test="${isReview== 'TRUE'}">
									<!-- file tag add -->
									<div class="file_text">
										<p>[<select name="attachOutputType" disabled="disabled" class='selectbox'><c:forEach var="item" items="${attachOutputType}"><option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option></c:forEach></select>]</p>
									</div>
									<!-- file info add -->
									<div class="file_text">
										<p><a href="#" style="cursor:pointer;" onclick="fileDownload1('<c:out value="${file.attachFileId}"/>')">
										<input type="text" title="<c:out value="${file.attachFileName}" />" style="width: 100%; cursor:pointer;" name="attachFileName" id="fileName_<c:out value="${file.attachSeq}"/>" value="<c:out value="${file.attachFileName}" escapeXml="false"/>"></a></p>
										<select name="attachIsEssential" class='hideme'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select>
										<span class="btn_del"><a href="#" style="display: inline;" class="abort" onclick="deleteRowAttachFile(this, '<c:out value="${file.attachFileId}"/>');"><i class="mdi mdi-close"></i></a></span>
									</div>
								</c:when>
								<c:otherwise>
									<!-- file tag add -->	
									<%-- <div class="file_text">
										<p>[<select name="attachOutputType" disabled="disabled" class='selectbox'><c:forEach var="item" items="${attachOutputType}"><option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option></c:forEach></select>]</p>
									</div> --%>
									<!-- file info add -->
									<div class="file_text">
										<p><a href="#" onclick="fileDownload1('<c:out value="${file.attachFileId}"/>')">
										<input type="text" title="<c:out value="${file.attachFileName}" />" style="width: 100%; cursor:pointer;" readonly name="attachFileName" id="fileName_<c:out value="${file.attachSeq}"/>" value="<c:out value="${file.attachFileName}" escapeXml="false"/>"></a></p>
										<select name="attachIsEssential" class='hideme'><option value="0" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>비필수</option><option value="1" <c:if test="${file.attachIsEssential == '1'}">selected</c:if>>필수</option></select>
										<span class="btn_del"><a href="#" style="display: inline;" class="abort" onclick="deleteRowAttachFile(this, '<c:out value="${file.attachFileId}"/>');"><i class="mdi mdi-close"></i></a></span>
									</div>
								</c:otherwise>
							</c:choose>
							
								<div class="select_area" <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
									<select class="select" name="attachOutputType">
										<c:forEach var="item" items="${attachOutputType}">
											<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
										</c:forEach>
									</select>
								</div>
							
								<div class="select_area" <c:if test="${viewMode == 'SIMPLE'}"> class="hideme" </c:if>>
									<select class="select" name="attachOutputName">
										<option value='공개' <c:if test="${file.attachOutputName=='공개'}">selected</c:if>>공개</option><option value='비공개' <c:if test="${file.attachOutputName=='비공개'}">selected</c:if>>비공개</option>
									</select>
								</div>
								<input type="hidden" name="attachFileId" value="<c:out value="${file.attachFileId}" />" />
								<input type="hidden" name="attachCreatorId" value="<c:out value="${file.attachCreatorId}" />" />
								<input type="hidden" name="attachCreateDate" value="<c:out value="${file.attachCreateDate}" />" />
								<input type="hidden" name="attachWorkName" value="<c:out value="${file.attachWorkName}"/>" />
								<input type="hidden" name="attachOutputBusType" value="<c:out value="${file.attachOutputBusType}"/>" /> 
						</div>					
					</c:forEach>  
				</c:if> 
			</c:if>
			
			<c:if test="${fileMode == 'READ'}">
				<c:if test="${!empty fileList}">
				<div class="file_link">
					<c:forEach var="file" items="${fileList}">
							<p>
								<a href="javascript:void(0);" onclick="fileDownload1('<c:out value="${file.attachFileId}"/>')">
										<b>[<select name="attachOutputType" disabled="disabled" class='selectbox'>
											<c:forEach var="item" items="${attachOutputType}">
												<option value="<c:out value="${item.key}"/>" <c:if test="${item.key == file.attachOutputType}">selected</c:if>><c:out value="${item.detail1}"/></option>																		
											</c:forEach></select>]&nbsp&nbsp<c:out value="${file.attachFileName}"/></b>
									<input type="hidden" name="attachFileId" id="fileId_a1" value="<c:out value="${file.attachFileId}"/>"><i class="mdi mdi-link-variant"></i>
								</a>
							</p>
					</c:forEach>
					</div>
				</c:if>
			</c:if>
			
		</tbody>
	</table>		