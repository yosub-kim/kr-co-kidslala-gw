/*
 * TabbedPane.js v1.0.0 -- Dynamic AJAX Tabs for Prototype
 * Copyright (c) 2007-2008 Jesse Farmer <jesse@20bits.com>
 * Licensed under the MIT license.
 */
Hash.prototype.toObject = Hash.prototype.toObject || function() { return Object.clone(this); }

var CustomerTabbedPane = function(pane, page_urls, args) {
	var args = $H({asynchronous: true, method: 'get', evalScripts: true}).merge(args).toObject();	
	this.ActveTabPage = "";
	this.getURL = function(page_id) {
		var surl = '/action/CustomerPresentReportAction.do?mode=getCustomerProjectPresentReport';
		if(page_id == 'pane2')
			surl += "&reportType=PU";
		else
			surl += "&reportType=BU";
		surl += "&from=" + frm.from.value;
		surl += "&to=" + frm.to.value;
		//alert(surl);
		
		return surl;
	}
	
	this.loadPage = function(page_id) {
		this.ActveTabPage = page_id;
		new Ajax.Updater(pane, this.getURL(page_id), args);
	}
	this.changeTab = function(page_id,changeUrl){
		for (_page_id in page_urls) {
			$(_page_id).removeClassName('active');
		}
		$(page_id).addClassName('active').id;
		this.ActveTabPage = page_id;
		if(changeUrl) {
			//alert("¿©±â");
			new Ajax.Updater(pane,changeUrl, args);
		} else {
			
			new Ajax.Updater(pane, this.getURL(page_id), args);
		}
	} 
	
	for (page_id in page_urls) {
		Event.observe(page_id, 'click', function(e) {
			if ('function' == typeof(args.onClick))
				args.onClick(e);

			for (page_id in page_urls) $(page_id).removeClassName('active');
			this.loadPage(Event.element(e).addClassName('active').id);
			Event.stop(e);
		}.bindAsEventListener(this));

		if ($(page_id).hasClassName('active')) { this.loadPage(page_id); }
	}
}