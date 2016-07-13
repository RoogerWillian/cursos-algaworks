$(function(){
	var decimal = $('.js-decimal');
	decimal.maskMoney({decimal : ',', thousands : '.'});
	
	var plain = $('.js-plain');
	plain.maskMoney({precision : 0, thousands : '.'});

	$('.js-select2').select2({
		placeholder: $(this).data('placeholder'),
		allowClear: false,
		noResults: function(term) {
            return 'Opção não encontrada!';
       }
	});
	
	$('.js-error-detail').on('click', function(e){
		e.preventDefault();
		$('#msgErrors').hide();
		$('#msgDetailedErrors').show(1000);
	});
});