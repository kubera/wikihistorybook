QUnit.test("Year Slider Test: sliders range, min max ", function() {
	var yearSlider = new YearSlider();

	QUnit.equal(yearSlider.min, -2000, "The sliders min year is -2000");
	QUnit.equal(yearSlider.max, 2000, "The sliders max year is 2000");
});

QUnit.test("Year Slider Test: position of slider by year in percentage ", function() {
	var yearSlider = new YearSlider();

	QUnit.equal(yearSlider.positionSliderPercentage(0), 50, "For year 0, slider position is 50%");
	QUnit.equal(yearSlider.positionSliderPercentage(-2000), 0, "For year -2000, slider position is 0%");
	QUnit.equal(yearSlider.positionSliderPercentage(2000), 100, "For year 2000, slider position is 100%");
	QUnit.equal(yearSlider.positionSliderPercentage(1000), 75, "For year 1000, slider position is 75%");
});
