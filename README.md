# DesafioSW

API feita por João Paulo de Souza Roberto como parte do desafio proposto pelo processo seletivo da B2W.

## Considerações 
* o banco utilizado foi o MongoDB;
* para colocar a API no ar, basta executar a classe AppConfig que se encontra no pacote br.com.joaopaulo.DesafioSW.core.

## Endpoints
| Endpoint | Método | Ação |
| -------- | ------ |----- |
| /planetas | GET | Retorna a lista de planetas. Suporta filtro por nome, basta adicionar "?nome={nome}".
| /planetas/{id} | GET | Retorna o planeta especificado pelo id.
| /planetas/{id} | DELETE | Exclui o planeta especificado pelo id. |
| /planetas | POST | Cria um planeta. O corpo da requisição deve ser um json com o formato especificado abaixo. |
	
 ```json
 {
   "nome": "Tatooine",
    "clima": "arid",
    "terreno": "desert"
   }
   ```
	
**Do. Or do not. There is no try.**