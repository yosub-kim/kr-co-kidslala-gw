/*
 * TabbedPane.js v1.0.0 -- Dynamic AJAX Tabs for Prototype
 * Copyright (c) 2007-2008 Jesse Farmer <jesse@20bits.com>
 * Licensed under the MIT license.
 */
Hash.prototype.toObject = Hash.prototype.toObject || function() { return Object.clone(this); }
function movePage() {
	Tab.changeTab(movePageId);
}
function storeProjectInfo() {
	var sFrm = document.forms["projectMasterInfoForm"];
	var status = AjaxRequest.submit( 
			sFrm,
			{	'url':"/action/ProjectMasterInfoAction.do?mode=createProjectMasterInfo",
				'onSuccess':function(obj){
					movePage();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장 할 수 없습니다.");
				}
			}
	);
}
var movePageId;
var ProjectInfoTabbedPane = function(pane, page_urls, args) {
	var args = $H({asynchronous: true, method: 'get', evalScripts: true}).merge(args).toObject();	
	this.loadPage = function(page_id) {
		new Ajax.Updater(pane, page_urls[page_id], args);
	}
	this.changeTab = function(page_id,changeUrl){
		for (_page_id in page_urls) {$(_page_id).removeClassName('current');}
		$(page_id).addClassName('current').id;
		if(changeUrl) {
			new Ajax.Updater(pane,changeUrl, args);
		} else {
			new Ajax.Updater(pane, page_urls[page_id], args);
		}
	}  
	for (page_id in page_urls) {
		Event.observe(page_id, 'click', function(e) {
			movePageId = Event.element(e).addClassName('current').id;
			AjaxRequest.post (
					{	'url':  "/action/RegisterProjectAction.do?mode=registerProjectCheck",
						'parameters' : { "projectCode": $('projectCode').value },
						'onSuccess':function(obj){
				           	var res = eval('(' + obj.responseText + ')');
				           	var rsObj = res.isRegistered;
				           	if(rsObj == false){
				           		alert("다음 단계를 진행하시려면 프로젝트 기본정보를 저장하셔야 합니다.");
				           		/*Dialog.confirm("다음 단계를 진행하시려면 프로젝트 기본정보를 저장하셔야 합니다. 저장하시겠습니까?.", 
									{
										width:300, 
							        	okLabel: "예", 
							        	cancelLabel: "아니오",
							        	id: "myDialogId",
							    		showEffectOptions : {duration : 0.1},
							    		hideEffectOptions : {duration : 0.1},	
							            cancel:function(win) {},
							            ok:function(win) {
							            	storeProjectInfo();
							            	return true;
							            }
							    	}
								);*/
				           		Tab.changeTab('pane1');
				           	}else{
				           		movePage();
				           	}
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("저장 할 수 없습니다.");
						}
					}
			);			
		}.bindAsEventListener(this));

		if ($(page_id).hasClassName('current')) { this.loadPage(page_id); }
	}
}