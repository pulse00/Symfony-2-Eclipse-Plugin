function initLightbox() {
	$('a.lightbox').lightBox();
	$('div.multiShot a').lightBox({fixedNavigation:true});
}

$(document).ready(function() {	
	$("#tabs").tabs();	
	$('#featurestab').tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
	
	if($("#featurestab") && document.location.hash){
	  $.scrollTo("#featurestab");
	}
	
	$("#featurestab ul").localScroll({ 
	  target:"#featurestab",
	  duration:0,
	  hash:true
	});	
	
	$('#featurestab li').removeClass('ui-corner-top').addClass('ui-corner-left');			
	$('div.multiShotContainer').tabs();			
	initLightbox();
});