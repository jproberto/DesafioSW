package br.com.joaopaulo.DesafioSW.exception;

/**
 * Essa exceção é lançada quando uma referência de planeta não é encontrada.
 *
 */

public class PlanetaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8856020938067058007L;

	public PlanetaNotFoundException(String campo, String valor) {
		super(String.format("Não foi encontrado planeta para o %s: %s", campo, valor));
	}
}
