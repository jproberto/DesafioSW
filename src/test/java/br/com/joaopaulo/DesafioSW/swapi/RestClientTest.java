package br.com.joaopaulo.DesafioSW.swapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;

public class RestClientTest {

	private RestClient restClient;

	@Before
	public void setUp() {
		restClient = new RestClient();
	}

	@Test
	public void testaSucesso() throws JsonToResultadoAPIException {
		//Nome de um planeta válido, previamente verificado na API externa
		String nomePlanetaValido = "Alderaan";

		Assert.assertEquals(2, restClient.getQuantidadeAparicoesFilmesPorNomePlaneta(nomePlanetaValido));
	}

	@Test
	public void testaNomeInvalido() throws JsonToResultadoAPIException {
		//Nome de um planeta inválido
		String nomePlanetaInvalido = "PlanetaInválido";

		Assert.assertEquals(0, restClient.getQuantidadeAparicoesFilmesPorNomePlaneta(nomePlanetaInvalido));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaNomeNulo() throws JsonToResultadoAPIException {
		//Nome de  planeta nulo
		String nomePlanetaNulo = null;

		Assert.assertEquals(0, restClient.getQuantidadeAparicoesFilmesPorNomePlaneta(nomePlanetaNulo));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaNomeVazio() throws JsonToResultadoAPIException {
		//Nome de  planeta vazio
		String nomePlanetaNulo = "";

		Assert.assertEquals(0, restClient.getQuantidadeAparicoesFilmesPorNomePlaneta(nomePlanetaNulo));
	}
}
