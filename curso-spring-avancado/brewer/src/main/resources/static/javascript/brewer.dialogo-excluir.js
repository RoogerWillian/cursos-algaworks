Brewer = Brewer || {};

Brewer.DialogoExcluir = (function() {

	function DialogoExcluir(){
		this.exclusaoBtn = $('.js-exclusao-btn');
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this))
	}
	
	function onExcluirClicado(event){
		event.preventDefault();
		
		var botaoClicado = $(event.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
	}
	
	return DialogoExcluir;
	
}())
