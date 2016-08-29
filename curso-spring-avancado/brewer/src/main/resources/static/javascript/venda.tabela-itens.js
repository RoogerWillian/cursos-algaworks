Brewer.TabelaItens = (function(){
	
	function TabelaItens(autocomplete){
		this.autocomplete = autocomplete;
		this.containerCervejas = $('.js-container-cervejas');
	}
	
	TabelaItens.prototype.iniciar = function(){
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(evento, item){
		var resposta = $.ajax({
			url : 'item',
			method : 'POST',
			data : {
				codigoCerveja : item.codigo
			}
		});
		
		resposta.done(onItemCarregadoNoServidor.bind(this));
	}
	
	function onItemCarregadoNoServidor(html){
		this.containerCervejas.html(html);
		$('.js-tabela-cerveja-quantidade-item').on('change',onQuantidadeItemAlterado.bind(this));
	}
	
	function onQuantidadeItemAlterado(evento){
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
		
		resposta.done(onItemCarregadoNoServidor.bind(this));
	}
	
	return TabelaItens;
	
}());

$(function(){
	
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
});