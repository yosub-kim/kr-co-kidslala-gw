var NS4;
var IE4;
var mouse_top;
var mouse_left;
var parent_menu_name = "class_menuInfo";
var status_over = false;	
var event_id = "none";
var column_height = 18;
var mainmenu_top_indent = 0;	
var menuover_bgcolor = "#C9CDD1";
var menuover_fgcolor = "#ffffff";
var default_menuover_bgcolor = "#ffffff";
var default_menuover_fgcolor = "#000000";

NS4 = (document.layers);
IE4 = (document.all);
isWin = (navigator.appVersion.indexOf("Win") != -1);
//if (NS4) { 
//  document.captureEvents(Event.CLICK);
//  document.onclick = MouseDown;
//}
//else if (IE4) {
  document.onclick = MouseDown;
//}

var selectedProjectCode;
var selectedBusinessTypeCode;
function MouseDown(e) {
  event_target = event.srcElement.outerHTML;
  //alert(event_target);
  //event_target = event_target.toString();
  event_check  = event_target.indexOf("javascript:ProjectMenuView(");
  //alert(event_check);
  if (!status_over) hideAll();
  if (event_check == 35) {	// ie8, ȣȯ�� ���� ���/ȣȯ�� ���� ����
	viewMenu(event, parent_menu_name);
	selectedProjectCode = event_target.substring(63, 70);
	selectedBusinessTypeCode = event_target.substring(74, 77);
  } else if (event_check == 14) {	// ie10, ȣȯ�� ���� ���
	  viewMenu(event, parent_menu_name);
	  selectedProjectCode = event_target.substring(42, 49);	  
	  selectedBusinessTypeCode = event_target.substring(53, 56);
  } else if (event_check == 36) {	// ie10, ȣȯ������ ����
	  viewMenu(event, parent_menu_name);
	  selectedProjectCode = event_target.substring(64, 71);
	  selectedBusinessTypeCode = event_target.substring(75, 78);
  } else if (event_check == 80) {	// Chrome, Safari
	  viewMenu(event, parent_menu_name);
	  selectedProjectCode = event_target.substring(108, 115);
	  selectedBusinessTypeCode = event_target.substring(119, 122);
  } else {
    hideAll(); 
  }
  //alert(event_target);
  //alert("projectCode: " + selectedProjectCode);
  //alert("businessTypeCode: " + selectedBusinessTypeCode);
}

function hideAll() {
  eval(parent_menu_name + ".style.display = \"none\"");
  if (event_id == "none") { return; }
}

function viewMenu (e, ar_id) {
  if (ar_id == "none") return;
  menuLocBod = window.document.body;
  xPos = menuLocBod.scrollLeft + event.clientX - 150;
  yPos = event.clientY + menuLocBod.scrollTop;
  screen_height = window.document.body.offsetHeight;
  screen_width = window.document.body.offsetWidth;
  mouse_top = e.y;
  mouse_left = e.x;
  eval("mainmenu_top_indent = " + parent_menu_name + ".children[0].children[0].children.length");
  mainmenu_top_indent = mainmenu_top_indent * column_height;

  if (screen_height > mouse_top + mainmenu_top_indent) 
    yPos = event.clientY + menuLocBod.scrollTop;
  else
    yPos = (event.clientY + menuLocBod.scrollTop) - mainmenu_top_indent;

  if (mouse_top - mainmenu_top_indent < 0) 
  yPos = event.clientY + menuLocBod.scrollTop;

  eval(ar_id + ".style.top =" + yPos); 
  eval(ar_id + ".style.left =" + xPos);		
  eval(ar_id + ".style.display = \"\"");
}

function onMouseOver(ar_obj, ar_id) {
  status_over = true;
  changeColor(ar_obj);
}

function onMouseOut(ar_obj) {
  status_over = false;
  changeColor(ar_obj);
}

function changeColor(ar_obj) {
  if (ar_obj.children[0].style.color == menuover_fgcolor || ar_obj.children[0].style.color == "rgb(255, 255, 255)") {
    ar_obj.children[0].style.color = default_menuover_fgcolor;
    ar_obj.children[0].style.backgroundColor = default_menuover_bgcolor;
  } else {
    ar_obj.children[0].style.color = menuover_fgcolor;
    ar_obj.children[0].style.backgroundColor = menuover_bgcolor;
  }
}

function ProjectMenuView() {
	
}
