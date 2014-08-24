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

QUnit.test("Suggested number of nodes", function() {
	var yearSlider = new YearSlider();

	QUnit.equal(yearSlider.minNodes, 200, "Suggested number of min nodes are 200");
	QUnit.equal(yearSlider.maxNodes, 1000, "Suggested number of max nodes are 1000");
	QUnit.equal(yearSlider.suggestedNodesByYear(yearSlider.min), yearSlider.maxNodes, "Suggested number of nodes for year -2000 is 1000");
	QUnit.equal(yearSlider.suggestedNodesByYear(yearSlider.max), yearSlider.minNodes, "Suggested number of nodes for year 2000 is 200");
	QUnit.equal(yearSlider.suggestedNodesByYear(0), 600, "Suggested number of nodes for year 0 is 600");
	QUnit.equal(yearSlider.suggestedNodesByYear(1000), 400, "Suggested number of nodes for year 1000 is 400");
	QUnit.equal(yearSlider.suggestedNodesByYear(-1000), 800, "Suggested number of nodes for year -1000 is 800");
	QUnit.equal(yearSlider.suggestedNodesByYear(1971), 206, "Suggested number of nodes for year 1971 is 206");	
});
