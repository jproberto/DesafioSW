package br.com.joaopaulo.DesafioSW.swapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa o resultado da consulta feita à API para que se possa extrair as informações necessárias.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoAPI {
	@JsonProperty("results")
	private List<PlanetaAPI> planetas;

	public List<PlanetaAPI> getPlanetas() {
		return planetas;
	}

	public void setPlanetas(List<PlanetaAPI> planetas) {
		this.planetas = planetas;
	}
}
