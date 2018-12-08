package br.com.joaopaulo.DesafioSW.swapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.joaopaulo.DesafioSW.swapi.cache.PlanetaCache;

public class PlanetaCacheTest {

	@Test
	public void testaPutAndContains() {
		//Para garantir que existe alguém com esse nome
		String nomePlaneta = "PlanetaTeste";
		PlanetaCache.put(nomePlaneta, 0);

		Assert.assertTrue(PlanetaCache.contains(nomePlaneta));
	}

	@Test
	public void testaLimpaAndContainsComNomeInexistente() {
		//Limpa para garantir que não tem ninguém
		PlanetaCache.limpaCache();

		Assert.assertFalse(PlanetaCache.contains("QualquerNome"));
	}

	@Test
	public void testaGetQuantidadeAparicoesSucesso() {
		String nomePlaneta = "PlanetaTeste";
		PlanetaCache.put(nomePlaneta, 5);

		PlanetaCache.getQuantidadeAparicoes(nomePlaneta);

		Assert.assertEquals(new Integer(5), PlanetaCache.getQuantidadeAparicoes(nomePlaneta));
	}

	@Test
	public void testaGetQuantidadeAparicoesComNomeInexistente() {
		//Limpa para garantir que não tem ninguém
		PlanetaCache.limpaCache();

		Assert.assertNull(PlanetaCache.getQuantidadeAparicoes("QualquerNome"));
	}

	@After
	public void limpaCache() {
		PlanetaCache.limpaCache();
	}
}
