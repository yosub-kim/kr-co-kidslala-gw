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
					['Year', '일정지연', '평가지연', '리뷰지연'],
					['컨설팅',	10,    35,      4],
					['진흥',		17,    30,      4],
					['인증',		6,     0,      4],
					['진단',		6,     30,      4],
					['리서치',	3,     30,      4],
					['교육',		2,     30,      4],
					['국제사업',		8,     30,      4]
				]);
				
				var options = {
					//title: 'Company Performance?,
					//hAxis: {title? 'Year', titleTextStyle: {color: 'red'}}
					hAxis: {textStyle: {color: '#333333', fontName: '돋움체', fontSize: '11px'}},
					legend: {position: 'top', textStyle: {color: 'blue', fontName: '돋움체', fontSize: '11px'}},					
					chartArea: {width: '87%', height: '70%'}
				};
				
				var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
				google.visualization.events.addListener(chart, 'select', selectHandler);
				chart.draw(data, options);
				
				function selectHandler() {
					var message;
					var selection = chart.getSelection();
					
					for (var i = 0; i < selection.length; i++) {
						var item = selection[i];
						if (item.row != null && item.column != null) {
							message += '{row:' + item.row + ',column:' + item.column + '}';
						} else if (item.row != null) {
							message += '{row:' + item.row + '}';
						} else if (item.column != null) {
							message += '{column:' + item.column + '}';
						}
					}
					
					if (message == '') {
						message = 'nothing';
					}
					
					alert('You selected ' + message);
				}

      }
    </script>
  </head>
  <body style="margin: 0px;">
    <div id="chart_div" style="width: 220px; height: 203px; border-style: none; "></div>
  </body>
</html>