Brewer.TabelaItens = (function() {

	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaCervejasContainer = $('.js-container-cervejas');
	}

	TabelaItens.prototype.iniciar = function() {
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
	}

	function onItemSelecionado(evento, item) {

		var resposta = $.ajax({
			url : 'item',
			method : 'POST',
			data : {
				codigoCerveja : item.codigo
			}
		});

		resposta.done(onItemAdicionadoNoServidor.bind(this));

	}

	function onItemAdicionadoNoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		$('.js-alterar-quantidade-cerveja').on('change', onItemQuantidadeAlterada.bind(this));
		$('.js-tabela-item').on('dblclick', onDoubleClick);
		$('.js-exclusao-item-btn').on('click', onExcluirItemClick.bind(this));
		
		this.autocomplete.skuOuNomeInput.focus();
	}

	function onItemQuantidadeAlterada(evento) {

		var input = $(evento.target);
		var quantidade = input.val();
		var codigoCerveja = input.data('codigo-cerveja');

		var resposta = $.ajax({
			url : 'item/' + codigoCerveja,
			method : 'PUT',
			data : {
				quantidade : quantidade
			}
		});

		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	function onDoubleClick(evento){
		$(this).toggleClass('solicitando-exclusao');
	}

	function onExcluirItemClick(evento){
		var codigoCerveja = $(evento.target).data('codigo-cerveja');
		
		var resposta = $.ajax({
			url : 'item/' + codigoCerveja,
			method : 'DELETE'
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	return TabelaItens;
}());

$(function() {

	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();

	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
});