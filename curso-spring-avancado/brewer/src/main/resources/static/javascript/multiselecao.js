Brewer = Brewer || {};

Brewer.MultiSelecao = (function() {

	function MultiSelecao() {
		this.statusBtn = $('.js-status-btn');
		this.selecaoTodosCheckBox = $('.js-selecao-todos');
		this.selecaoCheckbox = $('.js-selecao');
	}

	MultiSelecao.prototype.iniciar = function() {
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
		this.selecaoTodosCheckBox.on('click', onSelecaoTodosClicado.bind(this));
		this.selecaoCheckbox.on('click', onSelecaoClicado.bind(this));
	}

	function onSelecaoClicado() {
		var selecaoTodosClicado = this.selecaoCheckbox.filter(':checked');
		this.selecaoTodosCheckBox.prop('checked', selecaoTodosClicado.length >= this.selecaoCheckbox.length);
		statusBtnAcao.call(this, selecaoTodosClicado.length);
	}

	function onSelecaoTodosClicado() {
		var status = this.selecaoTodosCheckBox.prop('checked');
		this.selecaoCheckbox.prop('checked', status);
		statusBtnAcao.call(this, status);
	}
	
	function statusBtnAcao(ativar){
		ativar ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}
	
	function onStatusBtnClicado(event) {
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status');
		var url = botaoClicado.data('url');
		var checkBoxSelecionados = this.selecaoCheckbox.filter(':checked');
		
		var codigos = $.map(checkBoxSelecionados, function(c) {
			return $(c).data('codigo');
		});

		if (codigos.length > 0) {
			$.ajax({
				url : url,
				method : 'PUT',
				data : {
					codigos : codigos,
					status : status
				},
				success : function() {
					window.location.reload();
				}
			});
		}
	}

	return MultiSelecao;
}());

$(function() {
	var multiselecao = new Brewer.MultiSelecao();
	multiselecao.iniciar();
});