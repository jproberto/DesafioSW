package br.com.joaopaulo.DesafioSW.core.service;

import java.util.List;

import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.exception.PlanetaNotFoundException;
import br.com.joaopaulo.DesafioSW.model.Planeta;

/**
 * Interface que decladra os métodos que fornecem operações de CRUD para objetos de {@link Planeta}.
 *
 */

public interface PlanetaService {

	/**
	 * Cria um novo registro de planeta
	 * 
	 * @param planetaDTO
	 *            Informações do novo registro de planeta
	 * @return Informações do novo registro de planeta
	 */
	public void create(PlanetaDTO planetaDTO);

	/**
	 * Apaga um registro de planeta
	 * 
	 * @param id
	 *            O id do registro a ser apagado
	 * @return Informações do registro apagado
	 * @throws PlanetaNotFoundException
	 *             se nenhum registro é encontrado para o id informado
	 */
	public PlanetaDTO delete(String id);

	/**
	 * Encontra todos os registros de planeta
	 * 
	 * @return Informações de todos os registros encontrados
	 */
	public List<PlanetaDTO> findAll();

	/**
	 * Busca por um único registro
	 * 
	 * @param id
	 *            O id do planeta desejado
	 * @return A informação encontrada para o planeta desejado
	 * @throws PlanetaNotFoundException
	 *             se nenhum planeta é encontrado para o id informado
	 */
	public PlanetaDTO findById(String id);

	/**
	 * Busca por um único registro
	 * 
	 * @param nome
	 *            O nome do planeta desejado
	 * @return A informação encontrada para o planeta desejado
	 * @throws PlanetaNotFoundException
	 *             se nenhum planeta é encontrado para o nome informado
	 */
	public PlanetaDTO findByNome(String nome);
}
