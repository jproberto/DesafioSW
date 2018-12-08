package br.com.joaopaulo.DesafioSW.swapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;
import br.com.joaopaulo.DesafioSW.swapi.model.PlanetaSWAPI;
import br.com.joaopaulo.DesafioSW.swapi.wrapper.PlanetaSWAPIWrapper;

/**
 * Classe que faz a interface com a SWAPI (https://swapi.co/), API externa com dados dos filmes da franquia Star Wars.
 * 
 */
@Service
public class SWAPIServicePlaneta extends SWAPIService {

	//TODO cache
	/*
	 * @Autowired
	 * private PlanetaCache planetaCache;
	 */

	public SWAPIServicePlaneta() {
		super("/planets/");
	}

	//Delega a chamada à API
	private PlanetaSWAPIWrapper buscaPorPlanetas(final String nomePlaneta) throws JsonToResultadoAPIException {
		return (PlanetaSWAPIWrapper) this.buscaRecurso(nomePlaneta, PlanetaSWAPIWrapper.class);
	}

	/**
	 * Retorna o total de filmes da franquia em que um determinado planeta aparece
	 * 
	 * @param nomePlaneta
	 *            Nome do planeta a ser buscado
	 * @return Quantidade de filmes em que o planeta aparece
	 * @throws JsonToResultadoAPIException
	 */
	public Integer getTotalAparicoesFilmes(String nomePlaneta) throws JsonToResultadoAPIException {
		if (nomePlaneta == null || nomePlaneta.equals("")) {
			throw new IllegalArgumentException();
		}

		//TODO implementar cache
		Integer totalAparicoesFilmes = 0;

		//Faz a chamada a API
		PlanetaSWAPIWrapper wrapper = buscaPorPlanetas(nomePlaneta);

		//Como não há planetas com o mesmo nome, apenas o primeiro nos interessa
		Optional<PlanetaSWAPI> planetaAPI = wrapper.getPlanetas().stream().findFirst();

		//Caso tenha sido encontrado um planeta com o nome passado na SWAPI, retorna a quantidade de filmes em que ele aparece
		//Caso contrário, considera 0
		totalAparicoesFilmes = planetaAPI.isPresent() ? planetaAPI.get().getQuantidadeAparicoesEmFilmes() : 0;

		//TODO cache
		//		if (planetaCache.isCacheAtivo()) {
		//			planetaCache.put(nomeDoPlaneta, totalAparicoes);
		//		}

		return totalAparicoesFilmes;
	}

	//	/**
	//	 * Dado o nome de um planeta, busca na SWAPI em quantos filmes ele aparece.
	//	 * 
	//	 * @param nomePlaneta
	//	 *            o nome do planeta a ser procurado
	//	 * @return quantidade de filmes em que o planeta procurado aparece
	//	 * @throws IOException
	//	 * @throws JsonMappingException
	//	 * @throws JsonParseException
	//	 * @throws JsonToResultadoAPIException
	//	 */
	//	public int getQuantidadeAparicoesFilmesPorNomePlaneta(String nomePlaneta) throws JsonToResultadoAPIException {
	//		if (nomePlaneta == null || nomePlaneta.equals("")) {
	//			throw new IllegalArgumentException();
	//		}
	//
	//		//Chamada à SWAPI, concatenando o nome do planeta à String da URI
	//		ResponseEntity<String> result = restTemplate.exchange(uri + nomePlaneta, HttpMethod.GET, entity, String.class);
	//
	//		//Transforma a String obtida pela API num objeto do tipo ResultadoAPI que contém apenas as informações que nos interesam 
	//		ResultadoAPI resultado = getResultadoFromJson(result.getBody());
	//
	//		//Como não há planetas com o mesmo nome, apenas o primeiro nos interessa
	//		Optional<PlanetaAPI> planetaAPI = resultado.getPlanetas().stream().findFirst();
	//
	//		//Caso tenha sido encontrado um planeta com o nome passado na SWAPI, retorna a quantidade de filmes em que ele aparece
	//		//Caso contrário retorna 0
	//		return planetaAPI.isPresent() ? planetaAPI.get().getQuantidadeAparicoesEmFilmes() : 0;
	//	}
}
