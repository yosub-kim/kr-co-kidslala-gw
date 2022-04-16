<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/common/include/taglib.jsp" %>
<script type="text/javascript" src="<c:url value="/js/prototype-1.6.0.3.js"/>"></script>
<%-- <code:codelist tableName="EMP_WORKING_TYPE" /> --%>

<script>
	var url = 'http://localhost:8080/action/ExpertPoolCodeRetrieveAction.do?mode=getBuGroupCode';
    new Ajax.Request(url, {
		           	method: 'post',
	    	       	parameters: {},
		      	 	onSuccess: function(transport) {
		                       	var res = eval('(' + transport.responseText + ')');
		                       	var resultObj = res.returnValue;
		                       	t
		                        var innerSelect = '';
		                        for(var i=0; i < resultObj.length; i++){
		                        	innerSelect = innerSelect + "<option value='"+resultObj[i].id+"'>"+resultObj[i].name+"</option>"
			                    }
		                        resSelect.innerHTML = "<select name='rep' id='rep' >"+innerSelect+"</select>";
	    	       	}
   	});		 
</script>
<div id="resSelect">
</div>