function setup(url, sliderValue) {
	$("#spinner").spinner();
	$("#slider").slider({
		min : -2000,
		max : 2000,
		value : sliderValue,
		change : changeActionSlider(url),
		slide : slideAction,
		create : sliderCreated
	});
	$('#min').text($('#slider').slider('option', 'min'));
	$('#max').text($('#slider').slider('option', 'max'));
	$('#middle').text(0);
}

function resetImage() {
	$slider = $('#slider');
	$slider.slider('option', 'change').call($slider);
}

function sliderCreated() {
	svgPan();
	var year = $("#slider").slider('value');
	var p = positionSliderPercentage(year);
	setSliderUiValue(year, p);
}

function svgPan() {
	$('svg').svgPan('root', true, true, false, 0.5);
}

function changeActionSlider(url) {
	var genImgUrl = url + '/gensvg/';
	return function() {
		$('#imageWrap').hide();
		$.blockUI({
			message : blockUiMessage
		});
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
	var p = positionSliderPercentage(ui.value);
	setSliderUiValue(ui.value, p);
}

function setSliderUiValue(year, percentage) {
	$('#value').text(year).css('left', (percentage - 0.5) + '%');
}

function positionSliderPercentage(year) {
	var v = 50;
	if (year > 0) {
		v = (year / 40) + v;
	}
	if (year < 0) {
		v = v - (Math.abs(year) / 40);
	}
	return v;
}
