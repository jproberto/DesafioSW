package br.com.joaopaulo.DesafioSW.swapi;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;

/**
 * Classe que faz a interface com a SWAPI (https://swapi.co/), API externa com dados dos filmes da franquia Star Wars.
 * 
 */
public class RestClient {

	private RestTemplate		restTemplate;
	private HttpEntity<String>	entity;
	private String				uri;

	//configurações necessárias para fazer a conexão
	public RestClient() {
		restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "DesafioSW API");
		entity = new HttpEntity<String>("parameters", headers);

		uri = "https://swapi.co/api/planets/?search=";
	}

	/**
	 * Dado o nome de um planeta, busca na SWAPI em quantos filmes ele aparece.
	 * 
	 * @param nomePlaneta
	 *            o nome do planeta a ser procurado
	 * @return quantidade de filmes em que o planeta procurado aparece
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws JsonToResultadoAPIException
	 */
	public int getQuantidadeAparicoesFilmesPorNomePlaneta(String nomePlaneta) throws JsonToResultadoAPIException {
		//Chamada à SWAPI, concatenando o nome do planeta à String da URI
		ResponseEntity<String> result = restTemplate.exchange(uri + nomePlaneta, HttpMethod.GET, entity, String.class);

		//Transforma a String obtida pela API num objeto do tipo ResultadoAPI que contém apenas as informações que nos interesam 
		ResultadoAPI resultado = getResultadoFromJson(result.getBody());

		//Como não há planetas com o mesmo nome, apenas o primeiro nos interessa
		Optional<PlanetaAPI> planetaAPI = resultado.getPlanetas().stream().findFirst();

		//Caso tenha sido encontrado um planeta com o nome passado na SWAPI, retorna a quantidade de filmes em que ele aparece
		//Caso contrário retorna 0
		return planetaAPI.isPresent() ? planetaAPI.get().getQuantidadeAparicoesEmFilmes() : 0;
	}

	/**
	 * Transforma uma String que represente um JSON num objeto da classe {@link ResultadoAPI}
	 * 
	 * @param json
	 *            String que representa o JSON retornado pela consulta feita à SWAPI
	 * @return objeto da classe ResultadoAPI
	 * @throws JsonToResultadoAPIException
	 */
	private ResultadoAPI getResultadoFromJson(String json) throws JsonToResultadoAPIException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, ResultadoAPI.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new JsonToResultadoAPIException();
		}
	}
}
