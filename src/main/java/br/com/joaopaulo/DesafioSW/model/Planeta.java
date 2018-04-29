package br.com.joaopaulo.DesafioSW.model;

import org.springframework.data.annotation.Id;

/**
 * Entidade que representa os planetas no sistema e persistida no banco de dados. Apesar de possuir poucos atributos, existe um builder para facilitar a construção de objetos desse
 * tipo.
 *
 */

public class Planeta {

	@Id
	private String	id;

	private String	nome;
	private String	clima;
	private String	terreno;

	public Planeta() {}

	//Invocado pelo próprio builder para criar o objeto e setar os valores
	private Planeta(PlanetaBuilder builder) {
		this.nome = builder.nome;
		this.clima = builder.clima;
		this.terreno = builder.terreno;
	}

	/**
	 * Retorna o {@link PlanetaBuilder} para auxiliar na criação de objetos do tipo {@link Planeta}
	 * 
	 */
	public static PlanetaBuilder getBuilder() {
		return new PlanetaBuilder();
	}

	//***** início dos getters e setters *****//
	public String getId() {
		return id;
	}

	public void setId(String id) {
		checaNuloOuVazio(id);
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		checaNuloOuVazio(nome);
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		checaNuloOuVazio(clima);
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		checaNuloOuVazio(terreno);
		this.terreno = terreno;
	}
	//***** fim dos getters and setters *****//
	
	//Verifica se os valores passados para os atributos são válidos.
	private void checaNuloOuVazio(String string) {
		if (string == null || string.equals("")) {
			throw new IllegalArgumentException("Valor inválido");
		}
	}

	/**
	 * Builder que auxilia a criação de objetos do tipo {@link Planeta}
	 */
	public static class PlanetaBuilder {
		private String	nome;
		private String	clima;
		private String	terreno;

		public PlanetaBuilder nome(String nome) {
			this.nome = nome;
			return this;
		}

		public PlanetaBuilder clima(String clima) {
			this.clima = clima;
			return this;
		}

		public PlanetaBuilder terreno(String terreno) {
			this.terreno = terreno;
			return this;
		}

		/**
		 * Cria uma nova instância de objeto do tipo {@link Planeta} com base nas informações passadas para o builder.
		 */
		public Planeta build() {
			return new Planeta(this);
		}
	}
}
