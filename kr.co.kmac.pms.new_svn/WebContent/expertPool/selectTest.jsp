<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/common/include/taglib.jsp" %>
<script type="text/javascript" src="<c:url value="/js/prototype-1.6.0.3.js"/>"></script>
<%-- <code:codelist tableName="EMP_WORKING_TYPE" /> --%>

<script>
	var url = 'http://localhost:8080/action/CommonCodeRetrieveAction.do?mode=getCodeEntityList&tableName=BUSINESS_TYPE_CODE';
    new Ajax.Request(url, {
		           	method: 'post',
	    	       	parameters: {},
		      	 	onSuccess: function(transport) {
		                       	var res = eval('(' + transport.responseText + ')');
		                       	var resultObj = res.returnValue;
		                        var innerSelect = '';
		                        for(var i=0; i < resultObj.length; i++){
		                        	innerSelect = innerSelect + "<option value='"+resultObj[i].key1+"'>"+resultObj[i].data1+"</option>"
			                    }
		                        resSelect.innerHTML = "<select name='rep' id='rep' >"+innerSelect+"</select>";
	    	       	}
   	});		
</script>
<div id="resSelect">
</div>