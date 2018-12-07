package br.com.joaopaulo.DesafioSW.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.Repository;

import br.com.joaopaulo.DesafioSW.model.Planeta;

/**
 * Fornece CRUD para {@link Planeta}
 */

public interface PlanetaRepository extends Repository<Planeta, String> {
	/**
	 * Apaga um planeta do banco de dados
	 * 
	 * @param planeta
	 *            a referência do planeta a ser apagado
	 */
	public void delete(Planeta planeta);

	//TODO documentar
	public List<Planeta> findAll(Example<Planeta> planetaExample);

	/**
	 * Busca a informação de um único registro
	 * 
	 * @param id
	 *            O id do planeta desejado
	 * @return As informações do planeta desejado. Se nenhum registro for encontrado para aquele id, retorna um objeto {@link Optional} vazio.
	 */
	public Optional<Planeta> findById(String id);

	/**
	 * Busca a informação de um único registro
	 * 
	 * @param nome
	 *            O nome do planeta desejado
	 * @return As informações do planeta desejado. Se nenhum registro for encontrado para aquele nome, retorna um objeto {@link Optional} vazio.
	 */
	public Optional<Planeta> findByNomeIgnoreCase(String nome);

	/**
	 * Salva um novo registro de planeta no banco de dados
	 * 
	 * @param planeta
	 *            Informações do registro salvo
	 * @return Informações do registro salvo
	 */
	public Planeta save(Planeta planeta);
}
