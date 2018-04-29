package br.com.joaopaulo.DesafioSW.dto;

import br.com.joaopaulo.DesafioSW.model.Planeta;

/**
 * DTO da classe {@link Planeta}, usado para transfeir dados sem necessidade de manipulação direta de objetos da classe de modelo.
 *
 */
public class PlanetaDTO {
	private String	id;
	private String	nome;
	private String	clima;
	private String	terreno;
	private int		quantidadeAparicoesFilmes;

	public PlanetaDTO() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	//Atributo não existente na classe Planeta, obitdo de API externa
	public int getQuantidadeAparicoesFilmes() {
		return quantidadeAparicoesFilmes;
	}

	public void setQuantidadeAparicoesFilmes(int quantidadeAparicoesFilmes) {
		this.quantidadeAparicoesFilmes = quantidadeAparicoesFilmes;
	}

	@Override
	public String toString() {
		return String.format("PlanetaDTO[id=%s, nome=%s, clima=%s, terreno=%s, quantidadeAparicoesFilmes=%s]", this.id, this.nome, this.clima, this.terreno);
	}
}
