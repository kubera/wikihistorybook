function SvgSlider() {

	this.min = -2000;
	this.max = 2000;

	this.positionSliderPercentage = function(year) {
		var v = 50;
		if (year > 0) {
			v = (year / 40) + v;
		}
		if (year < 0) {
			v = v - (Math.abs(year) / 40);
		}
		return v;
	};

}
