package br.com.joaopaulo.DesafioSW.core.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.joaopaulo.DesafioSW.core.repository.PlanetaRepository;
import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.dto.converter.PlanetaConverter;
import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;
import br.com.joaopaulo.DesafioSW.exception.NomeDuplicadoPlanetaException;
import br.com.joaopaulo.DesafioSW.exception.PlanetaNotFoundException;
import br.com.joaopaulo.DesafioSW.model.Planeta;
import br.com.joaopaulo.DesafioSW.swapi.service.SWAPIServicePlaneta;

/**
 * Essa classe da camada de serviço manipula os objetos de {@link Planeta}, usa {@link PlanetaRepository} para as operações com banco de dados, converte objetos da classe de modelo
 * para DTO e vice-versa, e cuida de eventuais regras de negócio.
 *
 */

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	private PlanetaRepository	repository;

	@Autowired
	private SWAPIServicePlaneta	swapiService;

	/**
	 * Cria um planeta no banco de dados com as informações do DTO entregue.
	 * 
	 * @param planetaDTO
	 *            Informações do planeta que deve ser criado
	 */
	@Override
	public void create(PlanetaDTO planetaDTO) {
		//Verifica se os campos estão todos preenchidos correta
		checaNuloOuVazio(planetaDTO.getNome());
		checaNuloOuVazio(planetaDTO.getClima());
		checaNuloOuVazio(planetaDTO.getTerreno());

		//Testa se já existe um planeta cadastrado com o mesmo nome
		Optional<Planeta> resultado = repository.findByNomeIgnoreCase(planetaDTO.getNome());

		if (resultado.isPresent()) {
			throw new NomeDuplicadoPlanetaException(planetaDTO.getNome());
		}

		Planeta planeta = PlanetaConverter.convertToPlaneta(planetaDTO);
		
		//Salva o planeta
		repository.save(planeta);

		//Seta o id salvo no DTO para retorno
		planetaDTO.setId(planeta.getId());
	}

	//Verifica se os valores passados para os atributos são válidos.
	private void checaNuloOuVazio(String string) {
		if (string == null || string.equals("")) {
			throw new IllegalArgumentException("Valor inválido");
		}
	}

	/**
	 * Apaga um planeta do banco de dados
	 * 
	 * @param id
	 *            id do planeta a ser apagado
	 */
	@Override
	public void delete(String id) {
		//Busca o planeta pelo id para garantir que existe um registro referente
		Planeta planeta = findPlanetaById(id);
		repository.delete(planeta);
	}

	/**
	 * Busca a informação de um único registro
	 * 
	 * @param id
	 *            O id do planeta desejado
	 * @return As informações do planeta desejado. Se nenhum registro for encontrado para aquele id, retorna um objeto {@link Optional} vazio.
	 */
	@Override
	public PlanetaDTO findById(String id) {
		//para garantir que existe um planeta para o id passado
		Planeta planeta = findPlanetaById(id);

		//retorna o DTO referente ao planeta encontrado
		return PlanetaConverter.convertToDTO(planeta, getTotalAparicoesFilmesPorNomePlaneta(planeta.getNome()));
	}

	/**
	 * Busca todos os registros de planeta que tenham propriedades iguais ao exemplo passado. As propriedades setadas como null no exemplo serão ignoradas.
	 * 
	 * @param planetaDTO
	 *            Um DTO preenchido com as informações dos planetas que se deseja buscar
	 * @return Todos os planetas que se encaixem nos critérios do DTO de exemplo
	 */
	@Override
	public List<PlanetaDTO> findPlanetas(PlanetaDTO planetaDTO) {
		List<Planeta> planetas = repository.findAll(Example.of(PlanetaConverter.convertToPlaneta(planetaDTO)));

		//Chama o método convertToDTO para cada elemento da lista de planetas, e retorna uma nova lista com todos esses DTOs
		List<PlanetaDTO> planetasDTO = planetas.stream().map(planeta -> PlanetaConverter.convertToDTO(planeta, getTotalAparicoesFilmesPorNomePlaneta(planeta.getNome())))
				.collect(toList());

		return planetasDTO;
	}

	/**
	 * Método para validar que existe um {@link Planeta} para o id passado.
	 * 
	 * @param id
	 * @return o {@link Planeta} referente ao id passado
	 * @throws PlanetaNotFoundException
	 *             quando não existir um {@link Planeta} para o id
	 */
	private Planeta findPlanetaById(String id) {
		Optional<Planeta> resultado = repository.findById(id);
		return resultado.orElseThrow(() -> new PlanetaNotFoundException("id", id));
	}

	/**
	 * Delega a busca para o serviço {@link SWAPIServicePlaneta} e trata em caso de erro
	 * 
	 * @param nomePlaneta
	 *            Nome do planeta do qual se deseja saber a quantidade de filmes em que aparece
	 * @return
	 * 		total de filmes em que o planeta com aquele nome aparece ou 0 em caso de erro
	 */
	public Integer getTotalAparicoesFilmesPorNomePlaneta(String nomePlaneta) {
		try {
			return swapiService.getTotalAparicoesFilmes(nomePlaneta);
		} catch (JsonToResultadoAPIException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
