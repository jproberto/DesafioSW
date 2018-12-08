package br.com.joaopaulo.DesafioSW.core.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlanetaControllerTest {

	@LocalServerPort
	private int				port;

	private String			URL;

	private RestTemplate	rest;

	@Before
	public void setUp() {
		rest = new RestTemplate();
		URL = "http://localhost:" + port + "/planetas/";
	}

	@Test
	public void testaCreateSucesso() {
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaCreate");

		//Testa se o create retornou o HTTP Status 201
		ResponseEntity<PlanetaDTO> response = rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		Assert.assertEquals(201, response.getStatusCodeValue());

		//Deleta o planeta inserido
		rest.delete(URL + response.getBody().getId());
	}

	@Test
	public void testaCreateNomeRepetido() {
		//Monta 2 DTOs para teste
		PlanetaDTO planetaDTO1 = montaPlanetaDTOTeste("PlanetaMesmoNome");
		PlanetaDTO planetaDTO2 = montaPlanetaDTOTeste("PlanetaMesmoNome");

		//Salva o primeiro DTO
		ResponseEntity<PlanetaDTO> response = rest.postForEntity(URL, planetaDTO1, PlanetaDTO.class);

		//Tenta salvar o segundo e testa se retorna o HTTP Status 409
		try {
			rest.postForEntity(URL, planetaDTO2, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("409 null", e.getMessage());
		}

		//Deleta o primeiro planeta que foi inserido
		rest.delete(URL + response.getBody().getId());
	}

	@Test
	public void testaCreateNomeVazio() {
		//Monta o DTO padrão de teste, mas sobrescreve o nome com uma string vazia
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("");

		//Testa se o create retornou o HTTP Status 400
		try {
			rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("400 null", e.getMessage());
		}
	}

	@Test
	public void testaCreateNomeNulo() {
		//Monta o DTO padrão de teste, mas sobrescreve o nome com null
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste(null);

		//Testa se o create retornou o HTTP Status 400
		try {
			rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("400 null", e.getMessage());
		}
	}

	@Test
	public void testaCreateClimaVazio() {
		//Monta o DTO padrão de teste, mas sobrescreve o clima com uma string vazia
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaClimaVazio");
		planetaDTO.setClima("");

		//Testa se o create retornou o HTTP Status 400
		try {
			rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("400 null", e.getMessage());
		}
	}

	@Test
	public void testaCreateClimaNulo() {
		//Monta o DTO padrão de teste, mas sobrescreve o clima com null
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaClimaNulo");
		planetaDTO.setClima(null);

		//Testa se o create retornou o HTTP Status 400
		try {
			rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("400 null", e.getMessage());
		}
	}

	@Test
	public void testaCreateTerrenoVazio() {
		//Monta o DTO padrão de teste, mas sobrescreve o terreno com uma string vazia
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaTerrenoVazio");
		planetaDTO.setTerreno("");

		//Testa se o create retornou o HTTP Status 400
		try {
			rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("400 null", e.getMessage());
		}
	}

	@Test
	public void testaCreateTerrenoNulo() {
		//Monta o DTO padrão de teste, mas sobrescreve o terreno com null
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaTerrenoNulo");
		planetaDTO.setTerreno(null);

		//Testa se o create retornou o HTTP Status 400
		try {
			rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("400 null", e.getMessage());
		}
	}

	@Test
	public void testaFindByIdSucesso() {
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaFindById");

		//Faz a inserção
		ResponseEntity<PlanetaDTO> responseCreate = rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);

		//Testa se o findById retornou o HTTP Status 200 e se o nome da entidade retornada bate com o DTO de teste
		ResponseEntity<PlanetaDTO> responseFindById = rest.getForEntity(URL + responseCreate.getBody().getId(), PlanetaDTO.class);
		Assert.assertEquals(200, responseFindById.getStatusCodeValue());
		Assert.assertEquals(planetaDTO.getNome(), responseFindById.getBody().getNome());

		//Deleta o planeta inserido
		rest.delete(URL + responseCreate.getBody().getId());
	}

	@Test
	public void testaFindByIdInexistente() {
		//Testa se ao buscar por um id inexistente retorna o HTTP Status 404
		try {
			rest.getForEntity(URL + "IDINEXISTENTE", PlanetaDTO.class);
		} catch (Exception e) {
			Assert.assertEquals("404 null", e.getMessage());
		}
	}

	@Test
	public void testaFilterByNome() {
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaFilterByName");

		//Faz a inserção
		ResponseEntity<PlanetaDTO> responseCreate = rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);

		//Teste se ao filtrar por nome retornou o HTTP Status 200 e se a lista retornada possui um elemento
		ResponseEntity<List> responseFindById = rest.getForEntity(URL + "?nome=" + planetaDTO.getNome(), List.class);
		Assert.assertEquals(200, responseFindById.getStatusCodeValue());
		Assert.assertEquals(1, responseFindById.getBody().size());

		//Deleta o planeta inserido
		rest.delete(URL + responseCreate.getBody().getId());
	}

	@Test
	public void testaFilterByNomeInexistente() {
		//Teste se ao filtrar por nome que não existe o HTTP Status 200 e se o retorno está vazio
		ResponseEntity<String> responseFindById = rest.getForEntity(URL + "?nome=NOMEINEXISTENTE", String.class);
		Assert.assertEquals(200, responseFindById.getStatusCodeValue());
		Assert.assertEquals("[]", responseFindById.getBody());
	}

	@Test
	public void testaFindAll() {
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaFindAll");

		//Faz a inserção
		ResponseEntity<PlanetaDTO> responseCreate = rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);

		//Teste se ao filtrar por nome retornou o HTTP Status 200 e se a lista retornada possui pelo menos um elemento
		ResponseEntity<List> responseFindById = rest.getForEntity(URL + "?nome=" + planetaDTO.getNome(), List.class);
		Assert.assertEquals(200, responseFindById.getStatusCodeValue());
		Assert.assertTrue(responseFindById.getBody().size() > 0);

		//Deleta o planeta inserido
		rest.delete(URL + responseCreate.getBody().getId());
	}

	@Test
	public void testaDeleteSucesso() {
		PlanetaDTO planetaDTO = montaPlanetaDTOTeste("PlanetaDelete");

		//Faz a inserção
		ResponseEntity<PlanetaDTO> responseCreate = rest.postForEntity(URL, planetaDTO, PlanetaDTO.class);

		//Testa se o delete retornou o HTTP Status 204
		ResponseEntity<Void> responseDelete = rest.exchange(URL + responseCreate.getBody().getId(), HttpMethod.DELETE, criaHeader(), Void.class);
		Assert.assertEquals(204, responseDelete.getStatusCodeValue());
	}

	@Test
	public void testaDeleteIdInexistente() {
		//Testa se ao tentar deletar um id inexistente retorna o HTTP Status 404
		try {
			rest.exchange(URL + "IDINEXISTENTE", HttpMethod.DELETE, criaHeader(), Void.class);
		} catch (Exception e) {
			Assert.assertEquals("404 null", e.getMessage());
		}
	}

	@Test
	public void testaDeleteSemId() {
		//Testa se ao tentar deletar sem passar o id retorna o HTTP Status 405
		try {
			rest.exchange(URL, HttpMethod.DELETE, criaHeader(), Void.class);
		} catch (Exception e) {
			Assert.assertEquals("405 null", e.getMessage());
		}
	}

	private PlanetaDTO montaPlanetaDTOTeste(String nome) {
		PlanetaDTO planetaDTO = new PlanetaDTO();
		planetaDTO.setNome(nome);
		planetaDTO.setClima("Quente");
		planetaDTO.setTerreno("Deserto");

		return planetaDTO;
	}

	private HttpEntity<String> criaHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "DesafioSW");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		return entity;
	}

}
