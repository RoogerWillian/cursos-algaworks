var pessoa =  '';

if (pessoa === 'FISICA'){
	// Aplica máscara de CPF
	console.log('Selecionado pessoa FISICA');
} else if (pessoa === 'JURIDICA'){
	// Aplica máscara de CNPJ
	console.log('Selecionado pessoa JURIDICA');
} else {
	console.log('Tipo pessoa inválido');
}