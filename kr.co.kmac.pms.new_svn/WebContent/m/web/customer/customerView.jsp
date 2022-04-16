<%@ include file="/m/web/common/includeAuth.jsp"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<div data-role="page" id="customerViewPage">

	<input type="hidden" id="customerIdx" value="<c:out value="${param.idx }"/>" /> 
	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
		<!-- 
		<a href="/m/index.jsp" data-icon="check" >등록</a>
		 -->
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="customerListView">
				<li data-role="list-divider">고객정보 상세보기</li>
			</ul>
		</div>	
		<div style="margin-top: 20px;">
			<table style="width: 100%; font-size: 12px; border-collapse: collapse; ">
				<colgroup> 
					<col width="30%">
					<col width="70%"> 
				</colgroup>			
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보유형</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_customerInfoName"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">제목</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_subject"></span></td>
				</tr>
				<!-- 
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">입찰금액</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_"></span></td>
				</tr>
				 -->
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">고객사</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_customerName"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">산업별구분</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_industryTypeName"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">비즈니스타입</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_businessTypeName"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">주요상담영역</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_refer_dept"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">작성자</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_writer"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">Function</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_writerDeptName"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보수집자</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsGather"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보수집일</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_info_date"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보제공자</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsName"></span></td>
				</tr>
				<!-- 
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보제공부서</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsDept"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">제공자직위</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsStatus"></span></td>
				</tr>
				 -->
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">제공자연락처</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsPhone"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">제공자Email</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsEmail"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보수집방법</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_embbsMethod"></span></td>
				</tr>
				<tr>
					<!-- 
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">정보내용</th>	
					 -->
					<td colspan="2" style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_content" style="font-size: 14px;"></span></td>
				</tr>
				<tr>
					<th style="text-align: center; padding: 3px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">제출자의견</th>	
					<td style="text-align: left; padding: 3px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><span id="customerView_opinion"></span></td>
				</tr>
			</table>
		</div>
				
	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
</div><!-- /page -->
</body>
</html>
