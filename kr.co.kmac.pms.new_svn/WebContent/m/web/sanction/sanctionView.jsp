<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>Index</title>
	<meta name="description" content="" />
	<meta name="author" content="KMAC_KMcenter" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />	
	<link rel="shortcut icon" href="" />
	<link rel="apple-touch-icon" href="" />
	<link rel="stylesheet"  href="/m/css/themes/default/jquery.mobile-1.2.0.css" />
	<%--
	<link rel="stylesheet"  href="/m/css/kmac_mobile.css" />
	 --%>
	<script src="/m/js/jquery.js"></script>
	<script src="/m/js/jquery.mobile-1.2.0.js"></script>
</head> 
<body> 
<div data-role="page">

	<div data-role="header" data-theme="b">
		<h1>결재내용 보기</h1>
		<a href="/m/web/board/boardList.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
		<a href="/m/web/board/boardWrite.jsp" data-icon="check" data-direction="reverse">수정</a>
	</div><!-- /header -->

	<div data-role="content" style="margin: 0px; padding: 0px;" >
		<ul style="margin: 0px; padding: 0px;">
			<li style="list-style: none; padding: .6em; border-bottom: 1px solid #E3E3E3;">안녕하세요 모바일 PMS 오픈을 축하합니다.</li>
			<li style="list-style: none; padding: .6em; border-bottom: 1px solid #E3E3E3; font-size: 10px;">2012-10-10 | 조회: 45 | 작성자: 이지웅</li>
		</ul>
		<div style="padding: 10px; vertical-align: top;">
			<p>
				To create a collapsible block of content, create a container and add
				the
				<code> data-role="collapsible"</code>
				attribute. Using
				<code>data-content-theme</code>
				attribute allows you to set a theme for the content of the
				collapsible. View the <a href="../api/data-attributes.html"
					class="ui-link">data- attribute reference</a> to see all the
				possible attributes you can add to collapsibles.
			</p>
		</div>
		<ul style="margin: 0px; padding: 0px;">
			<li style="list-style: none; padding: .6em; border-top: 1px solid #E3E3E3; font-size: 10px;">
				aaaa_목록_ㅁㅁㄴwewe.docZ &nbsp;&nbsp;&nbsp;
				aaaa_목록_ㅁㅁerㄴ.docZ &nbsp;&nbsp;&nbsp;
				aaaa_목록_ㅁㅁreㄴ.docZ &nbsp;&nbsp;&nbsp;   aaaa_목록_ㅁㅁㄴ.docZ 
			</li>
		</ul>
	</div><!-- /content -->
	
	<div data-role="footer" class="footer-docs" data-theme="c" style="margin-top: 20px;">
		<div class="ui-grid-b">
			<div class="ui-block-a"><a href="index.html" data-role="button" data-icon="check">로그아웃</a></div>
			<div class="ui-block-b"><a href="index.html" data-role="button" data-icon="gear">정보수정</a></div>
			<div class="ui-block-c"><a href="index.html" data-role="button" data-icon="check">PC버전</a></div>
		</div>
		<p>&copy; 2012 KMAC All Rights Reserved.</p>
	</div><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
