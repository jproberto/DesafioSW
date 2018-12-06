package br.com.joaopaulo.DesafioSW.exception;

/**
 * Essa exceção é lançada quando existe a tentativa de inserir um planeta com nome de outro já existente no banco de dados.
 *
 */
public class NomeDuplicadoPlanetaException extends RuntimeException {
	private static final long serialVersionUID = 5051307968590129418L;

	public NomeDuplicadoPlanetaException(String nome) {
		super(String.format("Já existe planeta cadastrado com o nome: %s", nome));
	}

}
