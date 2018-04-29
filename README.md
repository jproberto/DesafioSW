DesafioSW

API feita por João Paulo de Souza Roberto como parte do desafio.

Algumas considerações: 
- o banco utilizado foi o MongoDB;
- para colocar a API no ar, basta executar a classe AppConfig que se encontra no pacote br.com.joaopaulo.DesafioSW.core;
- para utilizar as funcionalidades solicitadas:
	- Adicionar um planeta (com nome, clima e terreno)
		POST /planetas/criar
		Corpo da requisição: {"nome":"Nome do planeta","terreno":"Terreno do planeta","clima":"Clima do planeta"}
	- Listar planetas
		GET /planetas
	- Buscar por nome
		GET /planetas/nome/{nome do planeta}
	- Buscar por ID
		GET planetas/id/{id do planeta}
	- Remover planeta
		DELETE planetas/excluir/{id do planeta}
	
Do. Or do not. There is no try.