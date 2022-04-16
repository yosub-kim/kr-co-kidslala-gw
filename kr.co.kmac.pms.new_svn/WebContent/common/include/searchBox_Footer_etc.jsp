<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="org.springframework.web.bind.ServletRequestUtils"%>
								</td>
								<%
									String hasbutton = ServletRequestUtils.getStringParameter(request, "hasButton", "Y");
									if(hasbutton.equals("Y")){
								%>
								<td align="center" width="70" class="searchField_center" 
								 	style="border-left-width: 0px;"><a href="javascript:doSearch();"><img src="/images/btn_exec.jpg"></a></td>
								<%
									}
								%> 
							</tr>
						</table>
			  		</div>  