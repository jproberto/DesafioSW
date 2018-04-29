package br.com.joaopaulo.DesafioSW.swapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa o planeta obtido pela consulta à SWAPI, com um array dos filmes em que ele aparece.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetaAPI {
	private String[] films;

	public String[] getFilms() {
		return films;
	}

	public void setFilms(String[] films) {
		this.films = films;
	}

	/**
	 * Método que usa a informação retornada pela API para dizer a quantidade de filmes em que o planeta aparece
	 * 
	 * @return quantidade de filmes em que o planeta buscado aparece
	 */
	public int getQuantidadeAparicoesEmFilmes() {
		return films.length;
	}
}
