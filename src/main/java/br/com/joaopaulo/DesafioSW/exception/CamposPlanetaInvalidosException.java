package br.com.joaopaulo.DesafioSW.exception;

import br.com.joaopaulo.DesafioSW.model.Planeta;

/**
 * Exceção lançada quando ocorre uma tentativa de salvar um {@link Planeta} com valores inválidos.
 * 
 */
public class CamposPlanetaInvalidosException extends Exception {
	private static final long serialVersionUID = 1577448715775903031L;

	public CamposPlanetaInvalidosException() {
		super("Um ou mais campos são inválidos");
	}
}
