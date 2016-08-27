var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function(){
	
	function EstiloCadastroRapido(){
		this.modal = $('#modalCadastroRapidoEstilo');
		this.botaoSalvar = modal.find('.js-modal-cadastro-estilo-salvar-btn');
		this.form = modal.find('form');
		this.url = form.attr('action');
		this.inputEstilo = modal.find('#nomeEstilo');
		this.containerMessage = modal.find('.js-cadastro-estilo-message');				
	}
	
	EstiloCadastroRapido.prototype.iniciar = function (){
		this.form.on('submit', function(event) { event.preventDefault() });
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this))
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
	}
	
	function onBotaoSalvarClick(){
		var nomeEstilo = this.inputEstilo.val().trim();
		$.ajax({
			url : this.url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({ nome : nomeEstilo }),
			error : onErroSalvandoEstilo.bind(this),
			success : onSalvarEstilo.bind(this)
		});
	}
	
	function onSalvarEstilo(estilo){
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value=' + estilo.codigo +  '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');
	}
	
	function onErroSalvandoEstilo(obj){
		var message = obj.responseText;
		this.containerMessage.removeClass('hidden');
		this.containerMessage.html('<span>' + message + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onModalClose(){
		inputEstilo.val('');
		containerMessage.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	function onModalShow(){
		inputEstilo.focus();
	} 
	
	return EstiloCadastroRapido;
});

$(function(){
	
	
});