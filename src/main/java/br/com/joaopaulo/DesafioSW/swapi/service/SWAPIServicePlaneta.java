package br.com.joaopaulo.DesafioSW.swapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;
import br.com.joaopaulo.DesafioSW.swapi.cache.PlanetaCache;
import br.com.joaopaulo.DesafioSW.swapi.model.PlanetaSWAPI;
import br.com.joaopaulo.DesafioSW.swapi.wrapper.PlanetaSWAPIWrapper;


/**
 * Classe que faz a interface com a SWAPI (https://swapi.co/), API externa com dados dos filmes da franquia Star Wars.
 * 
 */
@Service
public class SWAPIServicePlaneta extends SWAPIService {

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

		//Se o planeta estiver presente no cache, busca a informação de lá
		if (PlanetaCache.contains(nomePlaneta)) {
			return PlanetaCache.getQuantidadeAparicoes(nomePlaneta);
		}

		Integer totalAparicoesFilmes = 0;

		//Faz a chamada a API
		PlanetaSWAPIWrapper wrapper = buscaPorPlanetas(nomePlaneta);

		//Como não há planetas com o mesmo nome, apenas o primeiro nos interessa
		Optional<PlanetaSWAPI> planetaAPI = wrapper.getPlanetas().stream().findFirst();

		//Caso tenha sido encontrado um planeta com o nome passado na SWAPI, retorna a quantidade de filmes em que ele aparece
		//Caso contrário, considera 0
		totalAparicoesFilmes = planetaAPI.isPresent() ? planetaAPI.get().getQuantidadeAparicoesEmFilmes() : 0;

		//Como o planeta ainda não estava adicionado ao cache, adiciona agora
		PlanetaCache.put(nomePlaneta, totalAparicoesFilmes);

		return totalAparicoesFilmes;
	}
}
