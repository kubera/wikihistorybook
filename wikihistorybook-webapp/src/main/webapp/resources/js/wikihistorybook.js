function setupSlider($, url) {
	genImgUrl = url + '/gensvg/';
	$("#slider").slider({
		min : -2000,
		max : 2000,
		value : 0,
		change : function(event, ui) {
			$('#svgImg').hide();
			$.blockUI();
			$.ajax({
				type : "POST",
				url : genImgUrl,
				data : {
					year : ui.value
				},
				success : function(imageName) {
			        var i = $('<img />').attr('src', genImgUrl + imageName).load(function() {
			            $('#svgImg').attr('src', i.attr('src'));
			            $('#svgImg').fadeIn();
			            $.unblockUI();
			        });
				},
				error: function() {
					alert("error " + genImgUrl);
				},
				dataType : "text"
			});
		},
		slide : function(event, ui) {
			var v = 50;
			if (ui.value > 0) {
				v = (ui.value / 40) + v;
			}
			if (ui.value < 0) {
				v = v - (Math.abs(ui.value) / 40);
			}
			$('#value').text(ui.value).css('left', (v - 0.5) + '%');
		}
	});
	$('#min').text($('#slider').slider('option', 'min'));
	$('#max').text($('#slider').slider('option', 'max'));
	$('#middle').text(0);
};
