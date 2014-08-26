function setupJson(sliderValue, zoomEnabled, jsonFileName) {
	setup(sliderValue, zoomEnabled);
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
		setActualNodesEdges();
		if ($("#enableZoomBtn").hasClass('btn-warning')) {
			svgPan();
		}
	});
}

function svgPan() {
	svgZoom('#d3svg');
}

function changeActionSliderSuccess() {
	return function(imageName) {
		$('#imageWrap').empty();
		d3Init(imageName);
		$('#imageWrap').fadeIn();
		$.unblockUI();
	};
}
