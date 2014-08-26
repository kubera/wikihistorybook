function setupSvg(sliderValue, zoomEnabled) {
	setup(sliderValue, zoomEnabled);
	setActualNodesEdges();
	if (zoomEnabled) {
		svgPan();
	}
}

function svgPan() {
	svgZoom('#Wiki');
}

function changeActionSliderSuccess() {
	return function(imageName) {
		$('#imageWrap').empty();
		$('#imageWrap').load(baseUrl + imageName,
				function(response, status, xhr) {
					if (status == "error") {
						alert("could not reload svg");
					}
					if (status == "success") {
						setActualNodesEdges();
						$('#imageWrap').fadeIn();
					}
					$.unblockUI();
					if ($("#enableZoomBtn").hasClass('btn-warning')) {
						svgPan();
					}
				});
	};
}
