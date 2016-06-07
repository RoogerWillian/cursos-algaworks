var botaoChaveador = document.querySelector('.js-botao-chaveador');

botaoChaveador.onclick = function (){
	var menu = document.querySelector('.js-menu');
   	menu.classList.toggle('menu--exibindo');
}