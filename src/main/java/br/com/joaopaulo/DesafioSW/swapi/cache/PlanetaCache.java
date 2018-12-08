package br.com.joaopaulo.DesafioSW.swapi.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa um cache para guardar as aparições dos planetas buscados em filmes e evitar chamadas excessivas à API externa.
 *
 */
public class PlanetaCache {
	private static Map<String, Integer> cache = new HashMap<String, Integer>();

	/**
	 * Dado o nome de um planeta, retorna a quantidade de filmes em que ele apareceu. Ou nulo, caso não esteja presente no cache.
	 * 
	 * @param nomePlaneta
	 *            O nome do planeta que se deseja saber a quantidade de aparições.
	 * @return Quantidade de filmes em que ele apareceu. Ou nulo, caso não esteja presente no cache.
	 */
	public static Integer getQuantidadeAparicoes(String nomePlaneta) {
		return cache.get(nomePlaneta);
	}

	/**
	 * Adiciona o nome do planeta e sua quantidade de aparições no cache. Caso o nome já conste, atualiza a quantidade.
	 * 
	 * @param nomePlaneta
	 *            Nome do planeta adicionado
	 * @param quantidadeAparicoes
	 *            Quantidade de filmes em que apareceu
	 */
	public static void put(String nomePlaneta, Integer quantidadeAparicoes) {
		cache.put(nomePlaneta, quantidadeAparicoes);
	}

	/**
	 * Retorna verdadeiro se o nome do planeta já existir no cache.
	 * 
	 * @param nomePlaneta
	 *            Nome do planeta a ser procurado.
	 * 
	 * @return Verdadeiro se existir uma entrada para o nome passado.
	 */
	public static boolean contains(String nomePlaneta) {
		return cache.containsKey(nomePlaneta);
	}

	/**
	 * Método chamado para apagar todo o cache guardado e iniciar um novo, sem nenhuma informação.
	 */
	public static void limpaCache() {
		cache = new HashMap<String, Integer>();
	}
}
