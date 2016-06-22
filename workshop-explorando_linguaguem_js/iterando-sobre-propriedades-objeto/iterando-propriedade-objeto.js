var pessoa = {
	nome : 'João',
	idade : 25,
	endereco : {
		logradouro : 'Joaquim Anacleto Bueno',
		numero : '2-50',
		bairro : 'Jardim Contorno',
		cidade : 'Bauru-SP'
	} 
}

for(var prop in pessoa){
	console.log(prop , '-> ', pessoa[prop]);
}

