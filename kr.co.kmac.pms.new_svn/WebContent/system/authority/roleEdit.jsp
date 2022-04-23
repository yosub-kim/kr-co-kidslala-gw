<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=5" />
<title>Role 생성 및 수정</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp" %>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<%-- treeview 를 표현 하기 위한 참조 시작 --%>
	<link rel="stylesheet" type="text/css" href="/js/ext/treeview/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="/js/ext/treeview/examples.css" />
	<script type="text/javascript" src="/js/ext/treeview/yui-utilities.js"></script>
	<script type="text/javascript" src="/js/ext/treeview/ext-yui-adapter.js"></script>
	<script type="text/javascript" src="/js/ext/treeview/ext-all.js"></script>

	<script type="text/javascript" src="/js/ext/treeview/TreeCheckNode.js"></script>
	<link rel="stylesheet" type="text/css" href="/js/ext/treeview/TreeCheckNode.css" />
	<%@ include file="/common/include/includeCss.jsp" %>
<%-- treeview 끝 --%>
<script type="text/javascript">

<%-- 개별 스크립트 영역 --%>
var myDate = new Date();
var nowYear = myDate.getYear();
var vRoleNum = "<c:out value="${roleNum}"/>";  //treeDataRetrieve
var TreeURL = "/action/AuthorityAction.do";	
TreeURL += "?mode=treeDataRetrieve&roleNum=" + vRoleNum;
Ext.BLANK_IMAGE_URL = "/js/ext/treeview/s.gif";
Ext.EventManager.onDocumentReady(function() {
	tree = new Ext.tree.TreePanel('tree-div', {
		animate:true,
		loader: new Ext.tree.CustomUITreeLoader({
			dataUrl:TreeURL,
			baseAttr: {
				uiProvider: Ext.tree.CheckboxNodeUI
			}
		}),
		enableDD:false,
		containerScroll: true,
		rootUIProvider: Ext.tree.LableUI,
		selModel:new Ext.tree.CheckNodeMultiSelectionModel(),
		rootVisible:true
	});
	
	tree.on('check', function() {
		//Ext.get('cn').dom.value = this.getChecked().join(',');
	}, tree);

	// set the root node
	root = new Ext.tree.AsyncTreeNode({
		text: '<B style="font-style: normal;	text-decoration: underline;">PMS 메뉴</B>',
		draggable:false,
		id:'ROOT',
		uiProvider: Ext.tree.LabelUI
	});
	tree.setRootNode(root);

	// render the tree
	tree.render();
	root.expand(false, false, function() {
		root.firstChild.expand(false);

	});

	//this.selectedNode;
});

var nodePath;
var pathPs = 0;
function Node_Append(){  // Node_Append
	var sFrm = document.frmMenuList;
	if(tree.selModel.selNodes.length == 0) {
		alert("추가할 위치를 선택하세요.");
		return false;
	}
	sFrm.pNodeKey.value = tree.selModel.selNodes[0].id;
	var checkedCount = 0;
	for(var i = 0; i < sFrm.elements.length; i++){
		var ele = sFrm.elements[i];
		if((ele.type == "checkbox")&&(ele.name=="chkMenu")){
			if(ele.checked){
				checkedCount ++;
			}
		} 
	}
	if(checkedCount == 0) {
		alert("추가할 메뉴를 선택해주세요.");
		return false;
	}
	nodePath = new Array();
	SaveNodePath(tree.selModel.selNodes[0]);
	sFrm.treeSaveMode.value = "CREATE";
	var ActionURL = "/action/AuthorityAction.do";	
	ActionURL += "?mode=saveTree";
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					//alert(nodePath.length);
					if(nodePath == 0)
						location.href = document.location;
					else 
						tree.root.reload();
					//alert(tree.toString()/dd);
					pathPs = nodePath.length-1;
					xNode = tree.root;
					expandByNode();

				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("노드 추가하는데 실패 하셨습니다.");
				}
			}
	); status = null;
}
function SaveNodePath(node){
	if(node.id == "ROOT")
		return false;
	else {
		nodePath[nodePath.length] = node.id;
		SaveNodePath(node.parentNode);
	}		
}
var tid;
var xNode;
function expandByNode(){
	var node = xNode;
	if(node.loading){
		tid = setTimeout("expandByNode()",100); //1초후 expandById함수 실행 - 재귀호출
	}else {
		if(node.childNodes.length > 0){
			for(var i = 0; i < node.childNodes.length; i++){
				
				if(node.childNodes[i].id == nodePath[pathPs]) {
					//alert("탔다..");
					node.childNodes[i].expand(false);
					pathPs--;
					xNode = node.childNodes[i];
					expandByNode();
					break;
				}
			}
		}
		clearTimeout(tid);
	}
}


function Node_Delete(){
	var sfrm = document.frmTree;
	var checkedCount = 0;
	var vMsg = "";
	var vVal = "";
	var arr_Season = document.getElementsByName("checkTree");
    for(var i=0;i<arr_Season.length;i++){
        if(arr_Season[i].checked == true) {
        	vMsg += ((checkedCount == 0) ? "" : ",") + arr_Season[i].text;
			vVal += ((checkedCount == 0) ? "" : ",") + arr_Season[i].value;
			checkedCount ++;
        }
    }
	if(checkedCount > 0){
		var sMsg = "삭제 하시겠습니까?";
		if(confirm(sMsg)) {
			var sFrm = document.frmMenuList;
			sFrm.treeSaveMode.value = "REMOVE";
			sFrm.chkNodes.value = vVal;
			var ActionURL = "/action/AuthorityAction.do";	
			ActionURL += "?mode=saveTree";
			var status = AjaxRequest.submit(
					sFrm
					,{ 'url':ActionURL
						,'onSuccess':function(obj){
							alert("삭제완료");
							tree.root.reload();
						}
						,'onLoading':function(obj){}
						,'onError':function(obj){
							alert("실패 하셨습니다.");
						}
					}
			); status = null;
		}
	}
}

</script>

<style type="text/css">
<!--
#c {
	display:none;
}

.out_Line {
	margin: 1px;padding: 1px; height: 320px; width: 100%; border-width:1px; border-color:#B2BEC1; border-style: solid; overflow: auto;
}
-->
</style>
</head>
<body style="margin-top: 0px; padding-left: 10px; margin-left: 0px; padding-top: 10px; position: static;">
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div id="mainTitle">
				<span class="mainTitle">Role 편집</span>
			</div>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td>
			<table width="100%">
				<tr>
					<form name="frmTree" method="post">
						<td width="42%">
							<div>
					    		<div id="c"><pre class="code"></pre></div>
					    		<div id="tree-div" class="out_Line" style="text-align:left" ></div>
					    	</div>
						</td>
					</form>
				    <td width="8%" valign="middle" align="center">
				    	<a onclick="Node_Append();"><img src="/images/btn_left_arrow.gif"  class="IMGBTN"></a>
				    	<br><br>
				    	<a onclick="Node_Delete();"><img src="/images/btn_right_arrow.gif" class="IMGBTN"></a>
				    </td>
				    <td width="50%" style="overfolw:visible;">
			    		<div class="out_Line" style="width: 98%">
							<table cellSpacing="0" cellpadding="0" width="95%" class="listTable">
								<thead>
									<tr height="25px">
										<td>선택</td>
										<td>분류</td>
										<td>메뉴명</td>
										<td>레벨</td>
									</tr>
								</thead>
								<form name="frmMenuList" method="post">
									<input type="hidden" name="roleNum" value="<c:out value="${ roleNum }"/>">
									<input type="hidden" name="treeSaveMode" value="">
									<input type="hidden" name="pNodeKey" value="">
									<input type="hidden" name="chkNodes" value="">
								<tbody id="ListData">
									<c:forEach var="menu" items="${menuList}"> 
										<tr>
											<td align='center'>
												<input type="checkbox" name="chkMenu" value="<c:out value="${menu.menuNum}" />" menuName="<c:out value="${menu.menuName}" />">
											</td>
											<td align='center'><c:out value="${menu.menuSort}"/></td>
											<td class="detailTableField_left"><c:out value="${menu.menuName}" escapeXml="false"/></td>
											<td align='center'><c:out value = "${menu.menuType}" /> 레벨</td>
										</tr>
									</c:forEach>
								</tbody>
								</form>
							</table>
			      		
			    		</div>
			    	</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>