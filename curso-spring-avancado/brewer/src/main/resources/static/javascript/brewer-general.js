this.Brewer = Brewer || {};

Brewer.General = (function() {
	function General() {
		this.selects = $('.js-select2');
	}

	General.prototype.iniciar = function() {
		this.selects.select2();
	}

	return General;
})();

$(function() {
	var select2 = new Brewer.General();
	select2.iniciar();
});