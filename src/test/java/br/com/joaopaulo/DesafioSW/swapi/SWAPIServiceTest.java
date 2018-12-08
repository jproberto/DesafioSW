package br.com.joaopaulo.DesafioSW.swapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;
import br.com.joaopaulo.DesafioSW.swapi.service.SWAPIServicePlaneta;

public class SWAPIServiceTest {

	private SWAPIServicePlaneta swapiService;

	@Before
	public void setUp() {
		swapiService = new SWAPIServicePlaneta();
	}

	@Test
	public void testaSucesso() throws JsonToResultadoAPIException {
		//Nome de um planeta válido, previamente verificado na API externa
		String nomePlanetaValido = "Alderaan";

		Assert.assertEquals(new Integer(2), swapiService.getTotalAparicoesFilmes(nomePlanetaValido));
	}

	@Test
	public void testaNomeInvalido() throws JsonToResultadoAPIException {
		//Nome de um planeta inválido
		String nomePlanetaInvalido = "PlanetaInválido";

		Assert.assertEquals(new Integer(0), swapiService.getTotalAparicoesFilmes(nomePlanetaInvalido));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaNomeNulo() throws JsonToResultadoAPIException {
		//Nome de  planeta nulo
		String nomePlanetaNulo = null;

		swapiService.getTotalAparicoesFilmes(nomePlanetaNulo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaNomeVazio() throws JsonToResultadoAPIException {
		//Nome de  planeta vazio
		String nomePlanetaVazio = "";

		swapiService.getTotalAparicoesFilmes(nomePlanetaVazio);
	}
}
