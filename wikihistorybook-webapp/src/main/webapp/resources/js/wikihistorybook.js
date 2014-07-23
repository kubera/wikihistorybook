function setup(url, sliderValue) {
	$("#spinner").spinner();
	$("#slider").slider({
		min : -2000,
		max : 2000,
		value : sliderValue,
		change : changeActionSlider(url),
		slide : slideAction,
		create : svgPan
	});
	$('#min').text($('#slider').slider('option', 'min'));
	$('#max').text($('#slider').slider('option', 'max'));
	$('#middle').text(0);
}

function resetImage() {
	$slider = $('#slider');
	$slider.slider('option', 'change').call($slider);
}

function svgPan() {
	$('svg').svgPan('root', true, true, false, 0.5);
}

function changeActionSlider(url) {
	var genImgUrl = url + '/gensvg/';
	return function() {
		$('#imageWrap').hide();
		$.blockUI({ message:  blockUiMessage});
		$.ajax({
			type : "POST",
			url : genImgUrl,
			data : {
				year : $("#slider").slider('value'),
				maxNodes : $("#spinner").spinner('value')
			},
			success : changeActionSliderSuccess(genImgUrl),
			error : changeActionSliderError,
			dataType : "text"
		});
	};
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

function changeActionSliderSuccess(genImgUrl) {
	return function(imageName) {
		$('#imageWrap').empty();
		$('#imageWrap').load(genImgUrl + imageName,
				function(response, status, xhr) {
					if (status == "error") {
						alert("could not reload svg");
					}
					if (status == "success") {
						$('#imageWrap').fadeIn();
					}
					$.unblockUI();
					svgPan();
				});
	};
}

function changeActionSliderError() {
	return function(jqXHR, textStatus, errorThrown) {
		$.unblockUI();
		alert("error status " + textStatus);
	};
}

function slideAction(event, ui) {
	var v = 50;
	if (ui.value > 0) {
		v = (ui.value / 40) + v;
	}
	if (ui.value < 0) {
		v = v - (Math.abs(ui.value) / 40);
	}
	$('#value').text(ui.value).css('left', (v - 0.5) + '%');
}
