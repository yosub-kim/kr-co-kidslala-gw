<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%--
<?xml version="1.0" encoding="utf-8"?>
<chart> 
	<c:if test="${!empty list}">
		<c:forEach var="item" items="${list.list}">
			<item label="일정지연" cnt="<c:out value="${item.scheduleDelayCnt}"/>" type="scheduleDelay"></item> 
			<item label="평가지연" cnt="<c:out value="${item.evalDelayCnt}"/>" type="evalDelay"></item> 
			<item label="리뷰지연" cnt="<c:out value="${item.reviewDelayCnt}"/>" type="reviewDelay"></item> 
		</c:forEach>
	</c:if>
</chart>
--%>
<html>
<head>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load("visualization", "1", {packages:["corechart"]});
	google.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = google.visualization.arrayToDataTable([
			['유형',	'건수'],
			<c:if test="${!empty list}">
				<c:forEach var="item" items="${list.list}">		 
					['일정지연',	<c:out value="${item.scheduleDelayCnt}"/>],
					['평가지연',	<c:out value="${item.evalDelayCnt}"/>],
					['리뷰지연',	<c:out value="${item.reviewDelayCnt}"/>]
				</c:forEach>
			</c:if>		                                        		
		]);
		
		var options = {
			//title: 'Company Performance?,
			//hAxis: {title? 'Year', titleTextStyle: {color: 'red'}}
			hAxis: {textStyle: {color: '#000000', fontName: '돋움체', fontSize: '12'}},
			legend: {position: 'top', textStyle: {color: 'blue', fontName: '돋움체', fontSize: '9.5'}},
			//legend: {position: 'top', textStyle: {color: 'blue', fontName: '돋움체', fontSize: '11'}, alignment: 'left'},
			chartArea: {width: '87%', height: '70%'},
			fontSize: '11',
			pieSliceText: 'value',
			pieSliceTextStyle: {color: '#ffffff', fontName: '돋움체', fontSize: '13'},
			tooltip:  {textStyle: {color: '#000000', fontName: '돋움체', fontSize: '14'}, showColorCode: true}
		};
		
		var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
		google.visualization.events.addListener(chart, 'select', selectHandler);
		chart.draw(data, options);
		
		function selectHandler() {
			var emergencyType;
			var selection = chart.getSelection();
			
			for (var i = 0; i < selection.length; i++) {
				var item = selection[i];
				if (item.row != null) {
					if(item.row == 0){emergencyType = 'scheduleDelay';}
					else if(item.row == 1){emergencyType = 'evalDelay';}
					else if(item.row == 2){emergencyType = 'reviewDelay';}					
				} 
			}
			//alert(emergencyType);
			parent.emergencyListFromPieChart(emergencyType);
		}

     }
</script>
</head>
<body style="margin: 0px;">
	<div id="chart_div" style="width: 220px; height: 203px; border-style: none; "></div>
</body>
</html>