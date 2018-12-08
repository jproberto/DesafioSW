package br.com.joaopaulo.DesafioSW.swapi.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;
import br.com.joaopaulo.DesafioSW.swapi.wrapper.SWAPIWrapper;

public abstract class SWAPIService {
	private final RestTemplate	restTemplate	= new RestTemplate();

	/**
	 * A URL base de toda a API
	 */
	private final String		url				= "https://swapi.co/api/";

	/**
	 * String para o recurso planets da API.
	 */
	private final String		busca			= "?search=";

	/**
	 * Pode ser films, people, planets, species, starships, vehicles
	 */
	private final String		recurso;

	public SWAPIService(String recurso) {
		this.recurso = recurso;
	}

	/**
	 * monta o Header a ser usado na requisição enviada à API
	 * 
	 * @return
	 */
	private HttpEntity<String> montaHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("user-agent", "desafio-b2w");
		return new HttpEntity<String>("parameters", headers);
	}

	/**
	 * Monta a busca concatenando a url base, o recurso, a string de busca e o parâmetro
	 * 
	 * @param param
	 *            Parâmetro a ser buscado
	 * @return String referente a URL desejada
	 */
	private String montaBuscaPorParametro(String param) {
		return this.url + this.recurso + this.busca + param;
	}

	/**
	 * Chamada feita à API para buscar o recurso pelo parâmetro
	 * 
	 * @param param
	 *            Parâmetro a ser buscado
	 * @param wrapperClass
	 *            Classe wrapper correspondente ao recurso buscado
	 * 
	 * @return representação envelopada dos recursos retornados pela API
	 * @throws JsonToResultadoAPIException
	 */
	protected SWAPIWrapper buscaRecurso(String param, Class<? extends SWAPIWrapper> wrapperClass) throws JsonToResultadoAPIException {
		ResponseEntity<String> response = restTemplate.exchange(this.montaBuscaPorParametro(param), HttpMethod.GET, this.montaHeaders(), String.class);
		return getFromJson(response.getBody(), wrapperClass);

	}

	/**
	 * Transforma uma String que represente um JSON num objeto envelopado que corresponde ao recurso desejado
	 * 
	 * @param json
	 *            String que representa o JSON retornado pela consulta feita à SWAPI
	 * @return objeto envelopado do recurso
	 * @throws JsonToResultadoAPIException
	 */
	private SWAPIWrapper getFromJson(String json, Class<? extends SWAPIWrapper> wrapperClass) throws JsonToResultadoAPIException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, wrapperClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JsonToResultadoAPIException();
		}
	}
}
