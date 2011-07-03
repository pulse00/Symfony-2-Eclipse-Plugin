$(document).ready(function() {
	
			$("#tabs").tabs();
			$('#featurestab').tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
			$('#featurestab li').removeClass('ui-corner-top').addClass('ui-corner-left');
			
			$('div.multiShotContainer').tabs();
			
			$('a.lightbox').lightBox();
			$('div.multiShot a').lightBox({fixedNavigation:true});
	
});