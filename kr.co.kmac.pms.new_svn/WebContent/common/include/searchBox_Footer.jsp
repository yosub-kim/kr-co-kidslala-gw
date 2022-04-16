<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="org.springframework.web.bind.ServletRequestUtils"%>
								</td>
								<%
									String hasbutton = ServletRequestUtils.getStringParameter(request, "hasButton", "Y");
									if(hasbutton.equals("Y")){
								%>
								<td width="70px" class="searchField_center" style="border-left:0px"><a href="javascript:doSearch();"><img src="/images/sub/btnSrch.gif" height="27" width="27"></a></td>
								<%
									}
								%> 
							</tr>
						</table>
			  		</div>  