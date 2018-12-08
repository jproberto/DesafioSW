package br.com.joaopaulo.DesafioSW.dto.converter;

import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.model.Planeta;
import br.com.joaopaulo.DesafioSW.model.Planeta.PlanetaBuilder;

public class PlanetaConverter {
	/**
	 * Cria um DTO com as informações do planeta passado
	 * 
	 * @param planeta
	 *            O planeta do qual se deseja ter um DTO para manipulação
	 * @param quantidadeAparicoesFilme
	 *            quantidade de vezes que o planeta apareceu em filmes da franquia, recuperado pela API externa
	 * @return planetaDTO DTO com as informações do planeta passado
	 */
	public static PlanetaDTO convertToDTO(Planeta planeta, int quantidadeAparicoesFilme) {
		PlanetaDTO dto = new PlanetaDTO();

		//copia as informações do planeta para o DTO
		dto.setId(planeta.getId());
		dto.setNome(planeta.getNome());
		dto.setClima(planeta.getClima());
		dto.setTerreno(planeta.getTerreno());

		//essa informação não existe em nosso banco, é obtida da API externa
		dto.setQuantidadeAparicoesFilmes(quantidadeAparicoesFilme);

		return dto;
	}

	/**
	 * Cria uma instância de planeta com as informações do DTO passado
	 * 
	 * @param planetaDTO
	 *            O DTO a partir do qual o planeta deve ser criado
	 * @return planeta planeta criado com as informações do DTO
	 */
	public static Planeta convertToPlaneta(PlanetaDTO planetaDTO) {
		PlanetaBuilder builder = Planeta.getBuilder();
		builder.nome(planetaDTO.getNome());
		builder.clima(planetaDTO.getClima());
		builder.terreno(planetaDTO.getTerreno());

		return builder.build();
	}
}
