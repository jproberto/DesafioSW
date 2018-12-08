package br.com.joaopaulo.DesafioSW.core.service;

import java.util.List;

import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
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
	 */
	public void delete(String id);

	/**
	 * Busca por um único registro
	 * 
	 * @param id
	 *            O id do planeta desejado
	 * @return A informação encontrada para o planeta desejado
	 */
	public PlanetaDTO findById(String id);

	/**
	 * Busca todos os registros de planeta que tenham propriedades iguais ao exemplo passado. As propriedades setadas como null no exemplo serão ignoradas.
	 * 
	 * @param planetaDTO
	 *            Um DTO preenchido com as informações dos planetas que se deseja buscar
	 * @return Todos os planetas que se encaixem nos critérios do DTO de exemplo
	 */
	public List<PlanetaDTO> findPlanetas(PlanetaDTO planetaDTO);
}
