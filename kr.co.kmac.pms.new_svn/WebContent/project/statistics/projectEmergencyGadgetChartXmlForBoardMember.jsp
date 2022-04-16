<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%--
<?xml version="1.0" encoding="utf-8"?>
<chart> 
	<c:if test="${!empty list}">
		<c:forEach var="rs" items="${list.list}">
			<item	bizType="<c:out value="${rs.data_1}"/>"
						bizTypeCode="<c:out value="${rs.key_1}"/>"
						일정지연="<c:out value="${rs.scheduleDelayCnt}"/>"
						평가지연="<c:out value="${rs.evalDelayCnt}"/>"
						리뷰지연="<c:out value="${rs.reviewDelayCnt}"/>"    
			/> 
		</c:forEach>
	</c:if>
</chart>
--%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
	<head>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(drawChart);
			function drawChart() {
				var data = google.visualization.arrayToDataTable([
					['Year', '일정지연', '평가지연', '리뷰지연']
					<c:if test="${!empty list}">
						<c:forEach var="rs" items="${list.list}">		 
							,['<c:out value="${rs.data_1}"/>', <c:out value="${rs.scheduleDelayCnt}"/>, <c:out value="${rs.evalDelayCnt}"/>, <c:out value="${rs.reviewDelayCnt}"/>]
           				</c:forEach>
           			</c:if>		 				                                                  
				]);
				var options = {
					//title: 'Company Performance?,
					//hAxis: {title? 'Year', titleTextStyle: {color: 'red'}}
					hAxis: {textStyle: {color: '#000000', fontName: '돋움체', fontSize: '11'}, allowContainerBoundaryTextCufoff: '1'},
					vAxis: {textStyle: {color: '#000000', fontName: '돋움체', fontSize: '11'}, gridlines: {color: '#ffffff'}, viewWindowMode: 'maximized', maxValue:10, format:'#'},
					legend: {position: 'top', textStyle: {color: 'blue', fontName: '돋움체', fontSize: '10'}, alignment: 'center'},
					chartArea: {width: '99%', height: '70%'},
					tooltip:  {textStyle: {color: '#000000', fontName: '돋움체', fontSize: '14'}, showColorCode: true}
				};
				
				var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
				google.visualization.events.addListener(chart, 'select', selectHandler);
				chart.draw(data, options);
				
				function selectHandler() {
					var bizTypeCode;
					var delayState;
					var selection = chart.getSelection();
					
					for (var i = 0; i < selection.length; i++) {
						var item = selection[i];
						if (item.row != null && item.column != null) {
							if(item.row == 0){bizTypeCode = 'BGA';}
							else if(item.row == 1){bizTypeCode = 'BGB';}
							else if(item.row == 2){bizTypeCode = 'BGC';}
							else if(item.row == 3){bizTypeCode = 'BGD';}
							else if(item.row == 4){bizTypeCode = 'BGE';}
							
							if(item.column == 1){delayState = 'R'; projectState='3';}
							else if(item.column == 2){delayState = 'ER'; projectState='4';}
							else if(item.column == 3){delayState = 'RR'; projectState='5';}
						}
					}
					//alert(emergencyType);
					parent.emergencyListFromColumnChart(bizTypeCode, delayState, projectState);
				}

      }
    </script>
  </head>
  <body style="margin: 0px;">
    <div id="chart_div" style="width: 210px; height: 203px; border-style: none; "></div>
  </body>
</html>