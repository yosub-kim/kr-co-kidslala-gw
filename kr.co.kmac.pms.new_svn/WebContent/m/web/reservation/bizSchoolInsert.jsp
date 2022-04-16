<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%
	String year = StringUtil.getCurr("yyyy");	
	String month = StringUtil.getCurr("MM");	
	String day = StringUtil.getCurr("dd");	
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head>  
<body>  
<div data-role="page">
	<form id="bizSchoolInsertForm" action="">
		<input type="hidden" name="fId" value="<c:out value="${bizSchoolReservation.fId }"/>">
		<div data-role="header" data-theme="a"> 
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
		</div><!-- /header -->
		<div data-role="content">
			<div class="ui-grid-solo" style="text-align: center; margin-bottom: 30px;">
				<ul data-role="listview" id="expertpoolListView">
					<li data-role="list-divider">강의실 예약</li>
				</ul>
			</div>	
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">연수실:</label>
				<select name="fUserid">
					<c:forEach var="rs" items="${bizSchoolList}">
						<option value="<c:out value="${rs.fUserid}"/>"
							<c:if test="${rs.fUserid eq bizSchoolReservation.fUserid }">selected</c:if>
						><c:out value="${rs.fUsername}"/></option>
					</c:forEach>
				</select>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">일자:</label>
				<table style="width: 100%;">
					<tr>
						<td>
							<select name="fYyyy" id="fYyyy">
								<option value='<%= (Integer.parseInt(year)) %>' selected><%= (Integer.parseInt(year)) %></option>
								<option value='<%= (Integer.parseInt(year))+1 %>' ><%= (Integer.parseInt(year))+1 %></option>
							</select> 
						</td> 
						<td>
							<select name="fMm" id="fMm">
								<option value='01' <c:if test="${'01' eq bizSchoolReservation.fMm }">selected</c:if>>1월</option>
								<option value='02' <c:if test="${'02' eq bizSchoolReservation.fMm }">selected</c:if>>2월</option>
								<option value='03' <c:if test="${'03' eq bizSchoolReservation.fMm }">selected</c:if>>3월</option>
								<option value='04' <c:if test="${'04' eq bizSchoolReservation.fMm }">selected</c:if>>4월</option>
								<option value='05' <c:if test="${'05' eq bizSchoolReservation.fMm }">selected</c:if>>5월</option>
								<option value='06' <c:if test="${'06' eq bizSchoolReservation.fMm }">selected</c:if>>6월</option>
								<option value='07' <c:if test="${'07' eq bizSchoolReservation.fMm }">selected</c:if>>7월</option>
								<option value='08' <c:if test="${'08' eq bizSchoolReservation.fMm }">selected</c:if>>8월</option>
								<option value='09' <c:if test="${'09' eq bizSchoolReservation.fMm }">selected</c:if>>9월</option>
								<option value='10' <c:if test="${'10' eq bizSchoolReservation.fMm }">selected</c:if>>10월</option>
								<option value='11' <c:if test="${'11' eq bizSchoolReservation.fMm }">selected</c:if>>11월</option>
								<option value='12' <c:if test="${'12' eq bizSchoolReservation.fMm }">selected</c:if>>12월</option>
							</select>					
						</td>
						<td>
							<select name="fDd" id="fDd">
								<option value='01' <c:if test="${'01' eq bizSchoolReservation.fDd }">selected</c:if>>1일</option>   
								<option value='02' <c:if test="${'02' eq bizSchoolReservation.fDd }">selected</c:if>>2일</option>   
								<option value='03' <c:if test="${'03' eq bizSchoolReservation.fDd }">selected</c:if>>3일</option>   
								<option value='04' <c:if test="${'04' eq bizSchoolReservation.fDd }">selected</c:if>>4일</option>   
								<option value='05' <c:if test="${'05' eq bizSchoolReservation.fDd }">selected</c:if>>5일</option>   
								<option value='06' <c:if test="${'06' eq bizSchoolReservation.fDd }">selected</c:if>>6일</option>   
								<option value='07' <c:if test="${'07' eq bizSchoolReservation.fDd }">selected</c:if>>7일</option>   
								<option value='08' <c:if test="${'08' eq bizSchoolReservation.fDd }">selected</c:if>>8일</option>   
								<option value='09' <c:if test="${'09' eq bizSchoolReservation.fDd }">selected</c:if>>9일</option>   
								<option value='10' <c:if test="${'10' eq bizSchoolReservation.fDd }">selected</c:if>>10일</option>   
								<option value='11' <c:if test="${'11' eq bizSchoolReservation.fDd }">selected</c:if>>11일</option>   
								<option value='12' <c:if test="${'12' eq bizSchoolReservation.fDd }">selected</c:if>>12일</option>   
								<option value='13' <c:if test="${'13' eq bizSchoolReservation.fDd }">selected</c:if>>13일</option>   
								<option value='14' <c:if test="${'14' eq bizSchoolReservation.fDd }">selected</c:if>>14일</option>   
								<option value='15' <c:if test="${'15' eq bizSchoolReservation.fDd }">selected</c:if>>15일</option>   
								<option value='16' <c:if test="${'16' eq bizSchoolReservation.fDd }">selected</c:if>>16일</option>   
								<option value='17' <c:if test="${'17' eq bizSchoolReservation.fDd }">selected</c:if>>17일</option>   
								<option value='18' <c:if test="${'18' eq bizSchoolReservation.fDd }">selected</c:if>>18일</option>   
								<option value='19' <c:if test="${'19' eq bizSchoolReservation.fDd }">selected</c:if>>19일</option>   
								<option value='20' <c:if test="${'20' eq bizSchoolReservation.fDd }">selected</c:if>>20일</option>   
								<option value='21' <c:if test="${'21' eq bizSchoolReservation.fDd }">selected</c:if>>21일</option>   
								<option value='22' <c:if test="${'22' eq bizSchoolReservation.fDd }">selected</c:if>>22일</option>   
								<option value='23' <c:if test="${'23' eq bizSchoolReservation.fDd }">selected</c:if>>23일</option>   
								<option value='24' <c:if test="${'24' eq bizSchoolReservation.fDd }">selected</c:if>>24일</option>   
								<option value='25' <c:if test="${'25' eq bizSchoolReservation.fDd }">selected</c:if>>25일</option>   
								<option value='26' <c:if test="${'26' eq bizSchoolReservation.fDd }">selected</c:if>>26일</option>   
								<option value='27' <c:if test="${'27' eq bizSchoolReservation.fDd }">selected</c:if>>27일</option>   
								<option value='28' <c:if test="${'28' eq bizSchoolReservation.fDd }">selected</c:if>>28일</option>   
								<option value='29' <c:if test="${'29' eq bizSchoolReservation.fDd }">selected</c:if>>29일</option>   
								<option value='30' <c:if test="${'30' eq bizSchoolReservation.fDd }">selected</c:if>>30일</option>   
								<option value='31' <c:if test="${'31' eq bizSchoolReservation.fDd }">selected</c:if>>31일</option>
							</select>					
						</td>
					</tr>
				</table>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<table style="width: 100%" >
					<tr>
						<td style="text-align: left;">시작시간: </td>
						<td>
							<select name="fHh" id="fHh">							
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.fHh }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.fHh }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.fHh }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.fHh }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.fHh }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.fHh }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.fHh }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.fHh }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.fHh }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.fHh }">selected</c:if>>17</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.fHh }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.fHh }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.fHh }">selected</c:if>>20</option>
							</select>
						</td>
						<td style="text-align: left;">시</td>
						<td>
							<select name="fSs" id="fSs">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.fSs }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.fSs }">selected</c:if>>30</option>
							</select>
						</td>
						<td style="text-align: left;">분</td>
					</tr>
					<tr>
						<td style="text-align: left;">종료시간: </td>
						<td>
							<select name="fEhh" id="fEhh">
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.fEhh }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.fEhh }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.fEhh }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.fEhh }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.fEhh }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.fEhh }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.fEhh }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.fEhh }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.fEhh }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.fEhh }">selected</c:if>>17</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.fEhh }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.fEhh }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.fEhh }">selected</c:if>>20</option>
							</select>
						</td>
						<td style="text-align: left;">시</td>
						<td>
							<select name="fEss" id="fEss">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.fEss }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.fEss }">selected</c:if>>30</option>
							</select>
						</td>
						<td style="text-align: left;">분</td>
					</tr>
				</table>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">강의 기간:</label>
				<select name="term" id="term">
					<option value="1">1일</option>
					<option value="2">2일</option>
					<option value="3">3일</option>
					<option value="4">4일</option>
					<option value="5">5일</option>
				</select>				
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">주/야간:</label>
				<fieldset data-role="controlgroup" data-mini="true">
					<input type="radio" name="fJuya" id="radio-mini-1" value="1" <c:if test="${'1' eq bizSchoolReservation.fJuya }">checked="checked" </c:if>/>
					<label for="radio-mini-1">주간</label>
					
					<input type="radio" name="fJuya" id="radio-mini-2" value="2" <c:if test="${'2' eq bizSchoolReservation.fJuya }">checked="checked" </c:if>/>
					<label for="radio-mini-2">야간</label>
				</fieldset>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">업무 유형:</label>
				<select name="fJobtype" id="fJobtype">
					<c:forEach var="rs" items="${bizTypeList}">
						<option value="<c:out value="${rs.fJobtype}"/>"
							<c:if test="${rs.fJobtype eq bizSchoolReservation.fJobtype }">selected</c:if>
						><c:out value="${rs.fJobtypeDesc}"/></option>
					</c:forEach>				
				</select>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">과정명:</label>
				<input type="text" name="fJobContent" id="fJobContent" value="<c:out value="${bizSchoolReservation.fJobContent}"/>" data-mini="true" />	
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">노동부환급과정:</label>
				<fieldset data-role="controlgroup" data-mini="true">
					<input type="radio" name="fJido" id="fJido1" value="유" <c:if test="${'유' eq bizSchoolReservation.fJido }">checked="checked" </c:if>/>
					<label for="fJido1">예</label>
					
					<input type="radio" name="fJido" id="fJido2" value="무"  <c:if test="${'무' eq bizSchoolReservation.fJido }">checked="checked" </c:if>/>
					<label for="fJido2">아니오</label>
				</fieldset>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">회사명:</label>
				<table style="width: 99%">
					<tr>
						<td>
							<select name="company" id="biz_companyGubun" onchange="javascript:bizCompanySelect();">								
								<option value="kmac" <c:if test="${'kmac' eq bizSchoolReservation.company }">selected</c:if>>KMAC</option>
								<option value="etc" <c:if test="${'etc' eq bizSchoolReservation.company }">selected</c:if>>기타</option>
							</select>
						</td>
						<td>
							<div id="biz_dept_sel" <c:if test="${'etc' eq bizSchoolReservation.company }">style="display: none;"</c:if>>
								<select name="dept_sel">
									<c:forEach var="rs" items="${deptList}">
										<option value="<c:out value="${rs.name}"/>"
											<c:if test="${rs.name eq bizSchoolReservation.dept }">selected</c:if>
										><c:out value="${rs.name}"/></option>
									</c:forEach>	
								</select>
							</div>
							<input type="text" name="dept_txt" id="biz_dept_txt" value="<c:out value="${bizSchoolReservation.dept}"/>" data-mini="true" <c:if test="${('kmac' eq bizSchoolReservation.company) or (empty bizSchoolReservation.company)}">style="display: none;"</c:if>/>
						</td>
					</tr>
				</table>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">담당자:</label>
				<input type="text" name="fCustomer" id="fCustomer" value="<c:out value="${bizSchoolReservation.fCustomer}"/>" data-mini="true" />	
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">담당자 연락처:</label>
				<input type="text" name="fCustomerTel" id="fCustomerTel" value="<c:out value="${bizSchoolReservation.fCustomerTel}"/>" data-mini="true" />	
			</div>						
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">교육인원:</label>
				<input type="text" name="fPlace" id="fPlace" value="<c:out value="${bizSchoolReservation.fPlace}"/>" data-mini="true" />	
			</div>						
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">강사1:</label>
				<table>
					<colgroup>
						<col width="60%">
						<col width="40%">
					</colgroup>
					<tr>
						<td>
							<fieldset data-role="controlgroup" data-mini="true" data-type="horizontal">
								<input type="radio" name="instructor1Diff" id="radio-mini-1" value="1" <c:if test="${'1' eq bizSchoolReservation.instructor1Diff }">checked="checked" </c:if>/>
								<label for="radio-mini-1">상임</label>
								
								<input type="radio" name="instructor1Diff" id="radio-mini-2" value="2" <c:if test="${'2' eq bizSchoolReservation.instructor1Diff }">checked="checked" </c:if>/>
								<label for="radio-mini-2">엑스퍼트</label>
							</fieldset>				
						</td>
						<td>
							<input type="text" name="instructor1" id="instructor1" value="<c:out value="${bizSchoolReservation.instructor1}"/>" data-mini="true" placeholder="강사명"/>
						</td>
					</tr>
				</table>
				<table style="width: 99%">
					<tr>
						<td>시작</td>
						<td>
							<select name="startTime1_1">								
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>17</option>
								<option value="18" <c:if test="${'18' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.startTime1_1 }">selected</c:if>>20</option>
							</select>
						</td>
						<td>
							<select name="startTime1_2">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.startTime1_2 }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.startTime1_2 }">selected</c:if>>30</option>
							</select>
						</td>
					</tr>
				</table>
				<table style="width: 99%">
					<tr>
						<td>종료</td>
						<td>
							<select name="endTime1_1">
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>17</option>
								<option value="18" <c:if test="${'18' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.endTime1_1 }">selected</c:if>>20</option>
							</select>
						</td>
						<td>
							<select name="endTime1_2">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.endTime1_2 }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.endTime1_2 }">selected</c:if>>30</option>
							</select>
						</td>
					</tr>
				</table>				
			</div>						
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">강사2:</label>
				<table>
					<colgroup>
						<col width="60%">
						<col width="40%">
					</colgroup>
					<tr>
						<td>
							<fieldset data-role="controlgroup" data-mini="true" data-type="horizontal">
								<input type="radio" name="instructor2Diff" id="radio-mini-1" value="1" <c:if test="${'1' eq bizSchoolReservation.instructor2Diff }">checked="checked" </c:if>/>
								<label for="radio-mini-1">상임</label>
								
								<input type="radio" name="instructor2Diff" id="radio-mini-2" value="2" <c:if test="${'2' eq bizSchoolReservation.instructor2Diff }">checked="checked" </c:if>/>
								<label for="radio-mini-2">엑스퍼트</label>
							</fieldset>				
						</td>
						<td>
							<input type="text" name="instructor2" id="instructor2" value="<c:out value="${bizSchoolReservation.instructor1}"/>" data-mini="true" placeholder="강사명"/>
						</td>
					</tr>
				</table>
				<table style="width: 99%">
					<tr>
						<td>시작</td>
						<td>
							<select name="startTime2_1">								
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>17</option>
								<option value="18" <c:if test="${'18' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.startTime2_1 }">selected</c:if>>20</option>
							</select>
						</td>
						<td>
							<select name="startTime2_2">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.startTime2_2 }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.startTime2_2 }">selected</c:if>>30</option>
							</select>
						</td>
					</tr>
				</table>
				<table style="width: 99%">
					<tr>
						<td>종료</td>
						<td>
							<select name="endTime2_1">
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>17</option>
								<option value="18" <c:if test="${'18' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.endTime2_1 }">selected</c:if>>20</option>
							</select>
						</td>
						<td>
							<select name="endTime2_2">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.endTime2_2 }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.endTime2_2 }">selected</c:if>>30</option>
							</select>
						</td>
					</tr>
				</table>				
			</div>						
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">강사3:</label>
				<table>
					<colgroup>
						<col width="60%">
						<col width="40%">
					</colgroup>
					<tr>
						<td>
							<fieldset data-role="controlgroup" data-mini="true" data-type="horizontal">
								<input type="radio" name="instructor3Diff" id="radio-mini-1" value="1" <c:if test="${'1' eq bizSchoolReservation.instructor3Diff }">checked="checked" </c:if>/>
								<label for="radio-mini-1">상임</label>
								
								<input type="radio" name="instructor3Diff" id="radio-mini-2" value="2" <c:if test="${'2' eq bizSchoolReservation.instructor3Diff }">checked="checked" </c:if>/>
								<label for="radio-mini-2">엑스퍼트</label>
							</fieldset>				
						</td>
						<td>
							<input type="text" name="instructor3" id="instructor3" value="<c:out value="${bizSchoolReservation.instructor1}"/>" data-mini="true" placeholder="강사명"/>
						</td>
					</tr>
				</table>
				<table style="width: 99%">
					<tr>
						<td>시작</td>
						<td>
							<select name="startTime3_1">								
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>17</option>
								<option value="18" <c:if test="${'18' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.startTime3_1 }">selected</c:if>>20</option>
							</select>
						</td>
						<td>
							<select name="startTime3_2">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.startTime3_2 }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.startTime3_2 }">selected</c:if>>30</option>
							</select>
						</td>
					</tr>
				</table>
				<table style="width: 99%">
					<tr>
						<td>종료</td>
						<td>
							<select name="endTime3_1">
								<option value="08" <c:if test="${'08' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>08</option>
								<option value="09" <c:if test="${'09' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>09</option>
								<option value="10" <c:if test="${'10' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>10</option>
								<option value="11" <c:if test="${'11' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>11</option>
								<option value="12" <c:if test="${'12' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>12</option>
								<option value="13" <c:if test="${'13' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>13</option>
								<option value="14" <c:if test="${'14' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>14</option>
								<option value="15" <c:if test="${'15' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>15</option>
								<option value="16" <c:if test="${'16' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>16</option>
								<option value="17" <c:if test="${'17' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>17</option>
								<option value="18" <c:if test="${'18' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>18</option>
								<option value="19" <c:if test="${'19' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>19</option>
								<option value="20" <c:if test="${'20' eq bizSchoolReservation.endTime3_1 }">selected</c:if>>20</option>
							</select>
						</td>
						<td>
							<select name="endTime3_2">
								<option value="00" <c:if test="${'00' eq bizSchoolReservation.endTime3_2 }">selected</c:if>>00</option>
								<option value="30" <c:if test="${'30' eq bizSchoolReservation.endTime3_2 }">selected</c:if>>30</option>
							</select>
						</td>
					</tr>
				</table>				
			</div>						
			
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">책상배치:</label>
				<textarea name="deskArrange" id="deskArrange" style="min-height: 80px;"><c:out value="${bizSchoolReservation.deskArrange}" escapeXml="false"/></textarea>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">교육준비물:</label>
				<textarea name="courseReady" id="courseReady" style="min-height: 80px;"><c:out value="${bizSchoolReservation.courseReady}" escapeXml="false"/></textarea>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">기타요구사항:</label>
				<input type="text" name="fBigo" id="fBigo" value="<c:out value="${bizSchoolReservation.fBigo}"/>" data-mini="true" />	
			</div>
			<c:choose>
				<c:when test="${not empty bizSchoolReservation.fId}">
					<a href="javascript:updateBizSchool();" data-role="button" data-corners="false" data-icon="star" data-theme="e">수정</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:insertBizSchool();" data-role="button" data-corners="false" data-icon="star" data-theme="e">등록</a>
				</c:otherwise>
			</c:choose>
		</div><!-- /content -->
	</form>
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
