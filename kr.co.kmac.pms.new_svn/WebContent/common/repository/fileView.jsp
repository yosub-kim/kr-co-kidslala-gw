	<TR height='30'>
		<TD class="titleTd1_center">Ã·ºÎ ÆÄÀÏ</TD>
		<TD >
			<TABLE>
					<c:forEach var="fileList" items="${VIEW_FILE_LIST}">
						<TR>
							<TD>
								<a href="javascript:fileDownLoad('<c:out value="${fileList.fileId}"/>')"/><c:out value="${fileList.orginalFileName}"/></a>
			               </TD>
						</TR>
					</c:forEach>
			</TABLE>
		</TD>
	</TR>