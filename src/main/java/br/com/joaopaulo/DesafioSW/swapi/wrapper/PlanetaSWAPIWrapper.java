package br.com.joaopaulo.DesafioSW.swapi.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.joaopaulo.DesafioSW.swapi.model.PlanetaSWAPI;

/**
 * Representa o resultado da consulta feita à API para que se possa extrair as informações necessárias.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetaSWAPIWrapper extends SWAPIWrapper {
	@JsonProperty("results")
	private List<PlanetaSWAPI> planetas;

	public List<PlanetaSWAPI> getPlanetas() {
		return planetas;
	}

	public void setPlanetas(List<PlanetaSWAPI> planetas) {
		this.planetas = planetas;
	}
}
