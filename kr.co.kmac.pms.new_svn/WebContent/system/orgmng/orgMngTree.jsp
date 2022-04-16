<div id="orgTree" style='overflow:auto;width:98%;height:450px; border-width:1; border-color: #c7c7c7; border-style: solid;'></div>
<script type="text/javascript">
	YAHOO.namespace("hynix");
	YAHOO.hynix.hynixOrgTree = function() {

		var tree, currentIconMode;
		var selectedNode;
		var onLabelClick = function(oNode) {
			//document.getElementById("report").innerHTML = oNode.id + " / " + oNode.name + " / " + oNode.objType;
			//alert(oNode.id + " / " + oNode.name + " / " + oNode.objType);
			//alert(oNode.name)
			//treeDataToRight(oNode);
			//selectedNode = oNode;
			//alert("dddd");
			//selectedNode = oNode;
			//selectedNode = oNode;
			if(oNode.objType == "GROUP"){
				//SelectNode(oNode);
				//alert(dd/dd);
				nodeSelected(oNode);
			}
		}
		var nodeSelected = function(oNode) {
			var Label;
			if(selectedNode != null){
				Label = document.getElementById(selectedNode.labelElId);
				Label.style.backgroundColor = '';
			}
			Label = document.getElementById(oNode.labelElId);
			//Label.style.backgroundColor = '#3B10E7';//change background color double click event fired
			selectedNode = oNode;
			groupNodeSelect(oNode);
		}
		
		var onLabelDblClick = function(oNode){
			//alert("[DBL]"+oNode.name)
			LoadData(oNode);
		}

		var treeDataToRight=function(oNode){

			var tempEl =  document.createElement("INPUT");
			tempEl.setAttribute("USER_ID",oNode.id );
			tempEl.setAttribute("USER_NAME",oNode.name );
			tempEl.setAttribute("DEPT_ID",oNode.deptId );
			tempEl.setAttribute("DEPT_NAME",oNode.deptName );
			tempEl.setAttribute("POSITION",oNode.position );
			
			moveDataToRight(tempEl);


		}
		
		function changeIconMode() {
			var newVal = parseInt(this.value);
			if (newVal != currentIconMode) {
				currentIconMode = newVal;
			}
			buildTree();
		}

		function loadNodeData(node, fnLoadComplete) {
			var reqValue = encodeURI(node.id);
			
			var sUrl = "/action/OrgTreeAction.do?mode=getOrgNodeList&groupId=" + reqValue;
			if($('isAll').checked){
				sUrl = sUrl  + "&isAll=true";
			}
			
			var callback = {
				success : function(oResponse) {

					var oResults = eval("(" + oResponse.responseText + ")");
					if (YAHOO.lang.isArray(oResults.orgNodeList)) {
						for ( var i = 0, j = oResults.orgNodeList.length; i < j; i++) {
							var newNode = new YAHOO.widget.TextNode(oResults.orgNodeList[i].name,node, false);
							newNode.id = oResults.orgNodeList[i].id;
							newNode.name = oResults.orgNodeList[i].extendedName;
							newNode.objType = oResults.orgNodeList[i].objType;
							if (oResults.orgNodeList[i].objType == "USER") {
								newNode.deptId = oResults.orgNodeList[i].deptId;
								newNode.deptName = oResults.orgNodeList[i].deptName;
								newNode.position = oResults.orgNodeList[i].position;
								newNode.isLeaf = true;
								//newNode.onLabelDblClick = onLabelDblClick;
							}
							newNode.onLabelDblClick = onLabelDblClick;
							newNode.onLabelClick = onLabelClick;
							//newNode.attachEvent('onclick',onLabelClick);
						}
					} else {
						if(oResults.orgNodeList != null){
							var newNode = new YAHOO.widget.TextNode(oResults.orgNodeList.name,node, false)
							newNode.id = oResults.orgNodeList.id;
							newNode.name = oResults.orgNodeList.extendedName;
							newNode.objType = oResults.orgNodeList.objType;
							if (oResults.orgNodeList.objType == "USER") {
								newNode.deptId = oResults.orgNodeList.deptId;
								newNode.deptName = oResults.orgNodeList.deptName;
								newNode.position = oResults.orgNodeList.position;
								newNode.isLeaf = true;
								//newNode.onLabelDblClick = onLabelDblClick;
							}
							newNode.onLabelDblClick = onLabelDblClick;
							newNode.onLabelClick = onLabelClick;
							newNode.attachEvent('onclick',onLabelClick);
						}
					}
					//alert("id: " + node.id +"/ name: "+ node.name);
					oResponse.argument.fnLoadComplete();
				},

				failure : function(oResponse) {
					oResponse.argument.fnLoadComplete();
				},

				argument : {
					"node" :node,
					"fnLoadComplete" :fnLoadComplete
				},

				timeout :7000
			};

			YAHOO.util.Connect.asyncRequest('POST', sUrl, callback);
		}

		function buildTree() {
			tree = new YAHOO.widget.TreeView("orgTree");

			tree.setDynamicLoad(loadNodeData, currentIconMode);

			var root = tree.getRoot();
 
			var aStates = [ {
				id :"2000",
				name :"KMAC"
			} ];

			for ( var i = 0, j = aStates.length; i < j; i++) {
				var rootNode = new YAHOO.widget.TextNode(aStates[i].name, root,
						true);
				rootNode.id = aStates[i].id;
				rootNode.name = aStates[i].name;
				rootNode.objType = "GROUP";
				//rootNode.position = aStates[i].position;
				//rootNode.deptName = aStates[i].deptName;
				rootNode.onLabelDblClick = onLabelDblClick;
				rootNode.onLabelClick = onLabelClick;
				
			}
			tree.draw();
		}

		return {
			init : function() {
				YAHOO.util.Event.on( [ "mode0", "mode1" ], "click",
						changeIconMode);
				var el = document.getElementById("mode1");
				if (el && el.checked) {
					currentIconMode = parseInt(el.value);
				} else {
					currentIconMode = 0;
				}

				buildTree();
			}

		}
	}();

	//once the DOM has loaded, we can go ahead and set up our tree:
	var orgTree = YAHOO.util.Event.onDOMReady(YAHOO.hynix.hynixOrgTree.init,
			YAHOO.hynix.hynixOrgTree, true)
</script>

