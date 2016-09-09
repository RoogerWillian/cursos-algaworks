Brewer.TabelaItens = (function() {

	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaCervejasContainer = $('.js-container-cervejas');
		this.uuid = $('#uuid').val();
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}

	TabelaItens.prototype.iniciar = function() {
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
	}

	function onItemSelecionado(evento, item) {

		var resposta = $.ajax({
			url : 'item',
			method : 'POST',
			data : {
				codigoCerveja : item.codigo,
				uuid : this.uuid
			}
		});

		resposta.done(onItemAdicionadoNoServidor.bind(this));

	}

	function onItemAdicionadoNoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		
		var quantidadeItemInput = $('.js-alterar-quantidade-cerveja');
		quantidadeItemInput.on('change', onItemQuantidadeAlterada.bind(this));
		quantidadeItemInput.maskMoney({ precision : 0, thousands : '' });
		
		var tabelaItem = $('.js-tabela-item');
		tabelaItem.on('dblclick', onDoubleClick);
		$('.js-exclusao-item-btn').on('click', onExcluirItemClick.bind(this));
		
		this.autocomplete.skuOuNomeInput.focus();
		
		this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
	}

	function onItemQuantidadeAlterada(evento) {

		var input = $(evento.target);
		var quantidade = input.val();
		
		if(quantidade <= 0){
			input.val(1);
			quantidade = 1;
		}
		
		var codigoCerveja = input.data('codigo-cerveja');

		var resposta = $.ajax({
			url : 'item/' + codigoCerveja,
			method : 'PUT',
			data : {
				quantidade : quantidade,
				uuid : this.uuid
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
			url : 'item/' + this.uuid + '/' +  codigoCerveja,
			method : 'DELETE'
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	return TabelaItens;
}());