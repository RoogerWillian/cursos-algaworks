$(function(){

	$('#botao1').on('click',function(event) {
		console.log('Botão 1 clicado');
	});

	$('#link1').on('click', function(e){
		console.log('Link1 clicado');
		e.preventDefault();
	});

	$('#input1').on('keyup', function(e){
		console.log(e.keyCode);
	});
});