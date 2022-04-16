<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Test</title>

</head>

<body>
The window with rubyonrails.org in it has a close callback to prevent closing. <br>That's why you cannot close it. Check out the code to see how to do it.
<br>
Open  

<a href="javascript:openConfirmDialog()">confirm dialog</a><br> 
<a href="javascript:openAlertDialog()">alert dialog</a><br>
<a href="javascript:openInfoDialog()">info dialog</a>(with alert_lite.css)<br>
<a href="javascript:openModalDialog()">Click here to open a modal window</a>
<br>
<a href="javascript:openContentWindow()">Click here to open a window with the div below at the exact same size and position</a><br>
<a href="javascript:Windows.closeAll()">Close all windows</a>(if it's possible)<br>





<div id="select" style="display: none;">Test select for IE <SELECT NAME="partnumber">
	<OPTION VALUE="1">One</OPTION>
	<OPTION VALUE="2">Two</OPTION>
	<OPTION VALUE="3">Three</OPTION>
	<OPTION VALUE="5">Five</OPTION>
	<OPTION VALUE="4">Oooopppppppps I forgot four</OPTION> 
</SELECT> <br />
</div>


<div style="clear: both"></div> 

<div id="container">

	<div style="float: left; width: 150px">&nbsp;</div>

	<!-- margin -->
	<div id="test_content" style="display: none; float: left; width: 100px; height: 150px; background: #DFA; color: #000; font-size: 12px;">
		Lorem ipsum dolor sit amet, consectetur
		adipiscing elit
	</div>
</div>
<script>
	var index = 0;
	var contentWin = null;

	// Debug window
	//showDebug();

	// Window with scrollable text
	win = new Window('dialog1', {
		className : "alphacube",
		width : 300,
		height : 200,
		zIndex : 100,
		resizable : true,
		title : "Not draggable!!",
		showEffect : Element.show,
		hideEffect : Effect.SwitchOff,
		draggable : false
	})
	win.getContent().innerHTML = "<div style='padding:10px'> Lorem ipsum dolor sit amet, consectetur adipiscing elit, ..</div>"
	win.showCenter();

	// Windows with an URL as content
	win2 = new Window('dialog2', {
		title : "Ruby on Rails",
		bottom : 70,
		left : 0,
		width : 300,
		height : 200,
		resizable : true,
		url : "http://www.rubyonrails.com/",
		showEffectOptions : {
			duration : 3
		}
	})
	win2.show();
	win2.setDestroyOnClose();

	win3 = new Window('dialog3', {
		className : "spread",
		title : "Not Closable",
		top : 10,
		right : 20,
		width : 300,
		height : 200,
		closable : false,
		url : "http://www.google.com/",
		showEffectOptions : {
			duration : 3
		}
	})
	win3.show();

	function openConfirmDialog() {
		Dialog
				.confirm(
						"Test of confirm panel, check out debug window after closing it<br>Test select for IE <SELECT NAME='partnumber'><OPTION VALUE='1'> One<OPTION VALUE='2'> Two<OPTION VALUE='3'> Three<OPTION VALUE='5'> Five<OPTION VALUE='4'> Oooopppppppps I forgot four</SELECT>",
						{
							windowParameters : {
								width : 300,
								height : 100
							},
							okLabel : "close",
							cancel : function(win) {
								//debug("cancel confirm panel")
							},
							ok : function(win) {
								//debug("validate confirm panel");
								return true
							}
						});
	}

	function openAlertDialog() {
		Dialog.alert(
				"Test of alert panel, check out debug window after closing it",
				{
					windowParameters : {
						width : 300,
						height : 100
					},
					okLabel : "close",
					ok : function(win) {
						//debug("validate alert panel");
						return true
					}
				});
	}

	var timeout;
	function openInfoDialog() {
		Dialog.info("Test of info panel, it will close <br>in 3s ...", {
			windowParameters : {
				className : "alert_lite",
				width : 250,
				height : 100
			},
			showProgress : true
		});
		// timeout=3;
		// setTimeout("infoTimeout()", 1000)
		Dialog.closeInfo()

	}

	function infoTimeout() {
		timeout--;
		if (timeout > 0) {
			Dialog.setInfoMessage("Test of info panel, it will close <br>in "
					+ timeout + "s ...")
			setTimeout("infoTimeout()", 1000)
		} else
			Dialog.closeInfo()
	}

	function openModalDialog() {
		//debug($('modal_window_content'))
		var win = new Window('modal_window', {
			className : "dialog",
			title : "Ruby on Rails",
			top : 100,
			left : 100,
			width : 300,
			height : 200,
			zIndex : 150,
			opacity : 1,
			resizable : true
		})
		//win.getContent().innerHTML = "Hi"
		win.setContent("select")
		win.setDestroyOnClose();
		win.show(true);
	}

	function openContentWindow() {
		if (contentWin != null) {
			Dialog.alert("Close the window 'Test' before opening it again!", {
				windowParameters : {
					width : 200,
					height : 130
				}
			});
		} else {
			contentWin = new Window('content_win', {
				className : "darkX",
				resizable : false,
				hideEffect : Element.hide,
				showEffect : Element.show
			})
			contentWin.setContent('test_content', true, true)
			contentWin.toFront();
			contentWin.setDestroyOnClose();
			contentWin.show();
		}
	}

	// Sample code to see how to implement a closeCallback
	function canClose(win) {
		//debug("You cannot close " + win.getId());
		// return false, the window cannot be closed
		return false;
	}

	// Set up a deleagte for win2 (the one with rubyonrails.org in it)
	win2.setCloseCallback(canClose);
	date = new Date();
	date.setMonth(date.getMonth() + 3);
	win2.setCookie("test", date);

	// Set up a windows observer, check ou debug window to get messages
	var myObserver = {
		onStartMove : function(eventName, win) {
		//debug(eventName + " on " + win.getId())
		},
		onEndMove : function(eventName, win) {
			//debug(eventName + " on " + win.getId())
		},
		onStartResize : function(eventName, win) {
			//debug(eventName + " on " + win.getId())
		},
		onEndResize : function(eventName, win) {
			//debug(eventName + " on " + win.getId())
		},
		onClose : function(eventName, win) {
			//debug(eventName + " on " + win.getId())
		},
		onDestroy : function(eventName, win) {
			if (win == contentWin) {
				$('container').appendChild($('test_content'));
				contentWin = null;
			}

			//debug(eventName + " on " + win.getId())
		}
	}
	Windows.addObserver(myObserver);
</script>
</body>
</html>
