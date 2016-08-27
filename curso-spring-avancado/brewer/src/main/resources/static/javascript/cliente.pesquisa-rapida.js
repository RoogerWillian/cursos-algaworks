Brewer = Brewer || {};

Brewer.PesquisaRapidaCliente = (function(){
	
	function PesquisaRapidaCliente(){
		this.pesquisaRapidaClientesModal = $('#pesquisaRapidaClientes');
		this.nomeInput = $('#nomeClienteModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-clientes-btn');
		this.containerPesquisaClientes = $('#containerPesquisaClientes');
		this.hmltTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.hmltTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaCliente.prototype.iniciar = function (){
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidoClicado.bind(this));
	
	}
	
	function onPesquisaRapidoClicado(event){
		event.preventDefault();
		
		$.ajax({
			url : this.pesquisaRapidaClientesModal.find('form').attr('action'),
			method : 'GET',
			contentType : 'application/json',
			data : {
				nome : this.nomeInput.val()
			},
			success : onPesquisaConcluida.bind(this),
			error : onErroPesquisa.bind(this)
		});
	}
	
	function onErroPesquisa(){
		this.mensagemErro.removeClass('hidden');
	}
	
	function onPesquisaConcluida(resultado){
		this.mensagemErro.addClass('hidden');
	
		var html = this.template(resultado);
		this.containerPesquisaClientes.html(html);
		
		var tabelaPesquisaRapida = new Brewer.TabelaClientePesquisaRapida(this.pesquisaRapidaClientesModal);
		tabelaPesquisaRapida.iniciar();
	}
	
	return PesquisaRapidaCliente;
	
}());

Brewer.TabelaClientePesquisaRapida = (function() {
	
	function TabelaClientePesquisaRapida(modal){
		this.clienteModal = modal;
		this.cliente = $('.js-cliente-pesquisa-rapida');
	}
	
	TabelaClientePesquisaRapida.prototype.iniciar = function(){
		this.cliente.on('click', onClienteSelecionado.bind(this))
	}
	
	function onClienteSelecionado(event){
		this.clienteModal.modal('hide');
		
		var clienteSelecionado = $(event.currentTarget);
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#codigoCliente').val(clienteSelecionado.data('codigo'));
	}
	
	return TabelaClientePesquisaRapida;
	
}());

$(function(){
	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});