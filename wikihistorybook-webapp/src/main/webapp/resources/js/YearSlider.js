function YearSlider() {

	this.min = -2000;
	this.max = 2000;
	
	this.minNodes = 200;
	this.maxNodes = 1000;

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
	
	this.suggestedNodesByYear = function(year) {
		var diffNodes = this.maxNodes - this.minNodes;
		var maxYear = this.max + Math.abs(this.min);
		var currentYear = year + Math.abs(this.min);
		return Math.round(Math.abs(((diffNodes * currentYear) / maxYear) - this.maxNodes));
	};

}
