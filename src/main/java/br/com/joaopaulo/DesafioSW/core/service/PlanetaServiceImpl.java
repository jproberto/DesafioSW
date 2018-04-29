package br.com.joaopaulo.DesafioSW.core.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaopaulo.DesafioSW.core.repository.PlanetaRepository;
import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.exception.CamposPlanetaInvalidosException;
import br.com.joaopaulo.DesafioSW.exception.JsonToResultadoAPIException;
import br.com.joaopaulo.DesafioSW.exception.NomeDuplicadoPlanetaException;
import br.com.joaopaulo.DesafioSW.exception.PlanetaNotFoundException;
import br.com.joaopaulo.DesafioSW.model.Planeta;
import br.com.joaopaulo.DesafioSW.model.Planeta.PlanetaBuilder;
import br.com.joaopaulo.DesafioSW.swapi.RestClient;

/**
 * Essa classe da camada de serviço manipula os objetos de {@link Planeta}, usa {@link PlanetaRepository} para as operações com banco de dados, converte objetos da classe de modelo
 * para DTO e vice-versa, e cuida de eventuais regras de negócio.
 *
 */

@Service
public class PlanetaServiceImpl implements PlanetaService {

	private final PlanetaRepository repository;
	private final RestClient		restClient;

	@Autowired
	public PlanetaServiceImpl(PlanetaRepository repository) {
		this.repository = repository;
		this.restClient = new RestClient();
	}

	/**
	 * Cria um planeta no banco de dados com as informações do DTO entregue.
	 * 
	 * @param planetaDTO
	 *            Informações do planeta que deve ser criado
	 * @return Informações do planeta criado
	 * @throws NomeDuplicadoPlanetaException
	 * @throws CamposPlanetaInvalidosException
	 */
	@Override
	public PlanetaDTO create(PlanetaDTO planetaDTO) throws NomeDuplicadoPlanetaException, CamposPlanetaInvalidosException {
		//Testa nulidade do nome do planeta para evitar erros na consulta
		if (planetaDTO.getNome() == null) {
			throw new CamposPlanetaInvalidosException();
		}

		//Primeiro testa se já existe um planeta cadastrado com o mesmo nome
		Optional<Planeta> resultado = repository.findByNomeIgnoreCase(planetaDTO.getNome());

		if (resultado.isPresent()) {
			throw new NomeDuplicadoPlanetaException(planetaDTO.getNome());
		}

		//Usa o builder para montar o planeta de acordo com as informações recebidas no DTO
		PlanetaBuilder builder = Planeta.getBuilder();
		builder.nome(planetaDTO.getNome());
		builder.clima(planetaDTO.getClima());
		builder.terreno(planetaDTO.getTerreno());
		
		Planeta planeta = builder.build();
		
		//Salva o planeta
		try {
			repository.save(planeta);
		} catch (ConstraintViolationException c) {
			throw new CamposPlanetaInvalidosException();
		}

		return convertToDTO(planeta);
	}

	/**
	 * Apaga um planeta do banco de dados
	 * 
	 * @param id
	 *            id do planeta a ser apagado
	 */
	@Override
	public PlanetaDTO delete(String id) {
		//Busca o planeta pelo id para garantir que existe um registro referente
		Planeta planeta = findPlanetaById(id);
		repository.delete(planeta);

		return convertToDTO(planeta);
	}

	/**
	 * Lista todos os registros de planetas do banco de dados
	 * 
	 * @return As informações de todos os registros de planeta encontrados no banco de dados
	 */
	@Override
	public List<PlanetaDTO> findAll() {
		List<Planeta> todosPlanetas = repository.findAll();

		//Converte os planetas retornados do banco de dados para DTOs com as informações necessárias
		return convertToDTOs(todosPlanetas);
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
		return convertToDTO(planeta);
	}

	/**
	 * Busca a informação de um único registro
	 * 
	 * @param nome
	 *            O nome do planeta desejado
	 * @return As informações do planeta desejado. Se nenhum registro for encontrado para aquele nome, retorna um objeto {@link Optional} vazio.
	 */
	@Override
	public PlanetaDTO findByNome(String nome) {
		//para garantir que existe um planeta para o nome passado
		Planeta planeta = findPlanetaByNome(nome);

		//retorna o DTO referente ao planeta encontrado
		return convertToDTO(planeta);
	}

	/**
	 * Cria um DTO com as informações do planeta passado
	 * 
	 * @param planeta
	 *            O planeta do qual se deseja ter um DTO para manipulação
	 * @return planetaDTO DTO com as informações do planeta passado
	 */
	private PlanetaDTO convertToDTO(Planeta planeta) {
		PlanetaDTO dto = new PlanetaDTO();

		//copia as informações do planeta para o DTO
		dto.setId(planeta.getId());
		dto.setNome(planeta.getNome());
		dto.setClima(planeta.getClima());
		dto.setTerreno(planeta.getTerreno());

		//essa informação não existe em nosso banco, é obtida da API externa
		dto.setQuantidadeAparicoesFilmes(getQuantidadeAparicoesFilmesPorNomePlaneta(planeta.getNome()));

		return dto;
	}

	/**
	 * Dado uma lista de planetas, devolve uma lista de DTOs correspondentes.
	 * 
	 * @param planetas
	 *            lista de planetas para serem transformados em DTOs
	 * @return lista de DTOs que representam os planetas passados
	 */
	private List<PlanetaDTO> convertToDTOs(List<Planeta> planetas) {
		//Chama o método convertToDTO para cada elemento da lista de planetas, e retorna uma nova lista com todos esses DTOs
		return planetas.stream().map(this::convertToDTO).collect(toList());
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
	 * Método para validar que existe um {@link Planeta} para o nome passado.
	 * 
	 * @param nome
	 * @return o {@link Planeta} referente ao nome passado
	 * @throws PlanetaNotFoundException
	 *             quando não existir um {@link Planeta} para o nome
	 */
	private Planeta findPlanetaByNome(String nome) {
		Optional<Planeta> resultado = repository.findByNomeIgnoreCase(nome);
		return resultado.orElseThrow(() -> new PlanetaNotFoundException("nome", nome));
	}

	private int getQuantidadeAparicoesFilmesPorNomePlaneta(String nomePlaneta) {
		try {
			return restClient.getQuantidadeAparicoesFilmesPorNomePlaneta(nomePlaneta);
		} catch (JsonToResultadoAPIException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
