var yearSlider = new YearSlider();

function setup(sliderValue, zoomEnabled, jsonFileName) {
	$("#spinner").spinner({
		stop : maxNodesSpinnerSpinAction,
		change : maxNodesSpinnerSpinAction
	});
	$("#zoomScaleSpinner").spinner({
		step : 0.1,
		numberFormat : "n",
		stop : zoomScaleSpinnerSpinAction,
		change : zoomScaleSpinnerSpinAction
	});
	$("#slider").slider({
		min : yearSlider.min,
		max : yearSlider.max,
		value : sliderValue,
		change : changeActionSlider,
		slide : slideAction,
		create : sliderCreated
	});
	$('#min').text($('#slider').slider('option', 'min'));
	$('#max').text($('#slider').slider('option', 'max'));
	$('#middle').text(0);

	if (zoomEnabled) {
		enableZoom();
	} else {
		disableUiZoom();
	}
	d3Init(jsonFileName);
}

function d3Init(jsonFileName) {
	var imageSize = $("#imageWrap").width();
	var width = imageSize, height = (imageSize / 3) * 2;
	var color = d3.scale.category20();

	var force = d3.layout.force().charge(-40).linkDistance(60).size(
			[ width, height ]);
	var svg = d3.select("#imageWrap").append("svg").attr("width", width).attr(
			"height", height).attr("id", "d3svg");

	d3.json(baseUrl + jsonFileName, function(error, graph) {
		force.nodes(graph.nodes).links(graph.links).start();

		var link = svg.selectAll(".link").data(graph.links).enter().append(
				"line").attr("class", "link").style("stroke-width",
				function(d) {
					return Math.sqrt(d.value);
				});

		var node = svg.selectAll(".node").data(graph.nodes).enter().append("a")
				.attr("xlink:href", function(d) {
					return "http://en.wikipedia.org/wiki/" + d.name;
				}).attr('target', '_blank').append("circle").attr("class",
						"node").attr("r", function(d) {
					return 3 * parseInt(d.group);
				}).style("fill", function(d) {
					return color(d.group);
				}).call(force.drag);

		node.append("title").text(function(d) {
			return d.name;
		});

		force.on("tick", function() {
			link.attr("x1", function(d) {
				return d.source.x;
			}).attr("y1", function(d) {
				return d.source.y;
			}).attr("x2", function(d) {
				return d.target.x;
			}).attr("y2", function(d) {
				return d.target.y;
			});

			node.attr("cx", function(d) {
				return d.x;
			}).attr("cy", function(d) {
				return d.y;
			});
		});
	});
}

function maxNodesSpinnerSpinAction(event, ui) {
	var maxNodes = ui.value;
	if (!$.isNumeric(maxNodes)) {
		maxNodes = $("#spinner").spinner('value');
	}
	var url = $(location).attr('href') + 'maxNodes';
	var data = {
		maxNodes : maxNodes
	};
	var response = function(data, textStatus, jqXHR) {
		if (window.console) {
			console.log('send value to server: ' + maxNodes);
		}
	};
	$.post(url, data, response, "text");
}

function zoomScaleSpinnerSpinAction(event, ui) {
	var zoomScale = ui.value;
	if (!$.isNumeric(zoomScale)) {
		zoomScale = $("#zoomScaleSpinner").spinner('value');
	}
	var url = $(location).attr('href') + 'zoomScale';
	var data = {
		zoomScale : zoomScale
	};
	var response = function(data, textStatus, jqXHR) {
		if (window.console) {
			console.log('send value to server: ' + zoomScale);
		}
	};
	$.post(url, data, response, "text");
}

function resetImage() {
	panZoomSvg.resetZoom();
}

function zoomIn() {
	panZoomSvg.zoomIn();
}

function zoomOut() {
	panZoomSvg.zoomOut();
}

function sliderCreated() {
	// set the current slider value to the ui
	var year = $("#slider").slider('value');
	var p = yearSlider.positionSliderPercentage(year);
	setSliderUiValue(year, p);
}

function svgPan() {
	var width = $("#imageWrap").width();
	$("#imageWrap").css('min-height', width);

	var zoomscale = $("#zoomScaleSpinner").spinner('value');

	panZoomSvg = svgPanZoom('#d3svg', {
		zoomEnabled : true,
		controlIconsEnabled : false,
		fit : 0,
		center : 0,
		zoomScaleSensitivity : zoomscale
	});
}

function changeActionSlider() {
	var currentYear = $("#slider").slider('value');
	var suggestedNodes = yearSlider.suggestedNodesByYear(currentYear);
	$("#spinner").spinner('value', suggestedNodes);
	reloadGraph();
}

function reloadGraph() {
	$('#imageWrap').hide();
	$.blockUI({
		message : blockUiMessage
	});
	$.ajax({
		type : "POST",
		url : baseUrl,
		data : {
			year : $("#slider").slider('value'),
			maxNodes : $("#spinner").spinner('value')
		},
		success : changeActionSliderSuccess(),
		error : changeActionSliderError,
		dataType : "text"
	});
}

function blockUiMessage() {
	return function() {
		var msg = '<h4>Create graph for year ';
		msg += $("#slider").slider('value');
		msg += ' with max. nodes ';
		msg += $("#spinner").spinner('value');
		msg += '</h4>';
		return msg;
	};
}

function changeActionSliderSuccess() {
	return function(imageName) {
		$('#imageWrap').empty();
		d3Init(imageName);
		$('#imageWrap').fadeIn();
		$.unblockUI();
		if ($("#enableZoomBtn").hasClass('btn-warning')) {
			svgPan();
		}
	};
}

function changeActionSliderError() {
	return function(jqXHR, textStatus, errorThrown) {
		$.unblockUI();
		alert("error status " + textStatus);
	};
}

function slideAction(event, ui) {
	var p = yearSlider.positionSliderPercentage(ui.value);
	setSliderUiValue(ui.value, p);
}

function setSliderUiValue(year, percentage) {
	$('#value').text(year).css('left', (percentage - 0.5) + '%');
}

function enableZoom() {
	svgPan();
	$("#enableZoomBtn").removeClass("btn-success").addClass("btn-warning");
	$("#enableZoomBtn").html("Disable zoom");
	$("#enableZoomBtn").attr('onclick', 'postZoomEnabled(false)');
	$("#zoomScaleSpinner").spinner("option", "disabled", true);
	$("#zoomInBtn").removeAttr('disabled');
	$("#zoomOutBtn").removeAttr('disabled');
	$("#resetZoomBtn").removeAttr('disabled');
	postZoomEnabled(true);
}

function disableUiZoom() {
	$("#enableZoomBtn").removeClass("btn-warning").addClass("btn-success");
	$("#enableZoomBtn").html("Enable zoom");
	$("#enableZoomBtn").attr('onclick', 'enableZoom()');
	$("#zoomScaleSpinner").spinner("option", "disabled", false);
	$("#zoomInBtn").attr('disabled', 'disabled');
	$("#zoomOutBtn").attr('disabled', 'disabled');
	$("#resetZoomBtn").attr('disabled', 'disabled');
}

function postZoomEnabled(zoomEnabled) {
	var url = $(location).attr('href') + 'zoomEnabled';
	var data = {
		zoomEnabled : zoomEnabled
	};
	var response = function(data, textStatus, jqXHR) {
		if (window.console) {
			console.log('send value to server: ' + zoomEnabled);
		}
		if (!zoomEnabled) {
			location.reload();
		}
	};
	$.post(url, data, response, "text");
}
