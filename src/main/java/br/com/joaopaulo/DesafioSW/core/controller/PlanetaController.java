package br.com.joaopaulo.DesafioSW.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaopaulo.DesafioSW.core.service.PlanetaService;
import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.exception.PlanetaNotFoundException;

/**
 * Esse controlador é o responsável pela API pública usada para gerenciar as informações dos planetas
 *
 */
//TODO conferir exceções e status http dos retornos etc
//TODO comentar os métodos
@RestController
@RequestMapping("/planetas")
public final class PlanetaController {

	@Autowired
	private PlanetaService service;

	@GetMapping
	public ResponseEntity<List<PlanetaDTO>> find(@RequestParam(value = "nome", required = false) String nome) {
		List<PlanetaDTO> planetas;

		//Pesquisar o query by example
		if (nome == null || "".equals(nome)) {
			planetas = service.findAll();
		} else {
			planetas = new ArrayList<PlanetaDTO>();
			planetas.add(service.findByNome(nome));
		}

		//TODO apagar comentário depois
		//Se a lista vier completa do findAll ou o planeta vier certo no findByNome, tudo certo
		//Se a lista vier vazia do findAll, o mais corrento é o status 200 tbm, pra não confundir o cliente
		//Se o findByNome não encontrar ninguém, o service levanta exceção que é tratada pelo @ExceptionHandler
		//Pensar se o service deve levantar exceção mesmo. No momento acho que sim
		return ResponseEntity.ok(planetas);
	}

	@PostMapping
	public ResponseEntity<PlanetaDTO> create(@RequestBody @Valid PlanetaDTO planetaDTO) {
		service.create(planetaDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(planetaDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		service.delete(id);

		//TODO apagar comentário depois
		//Se encontrar, tudo certo
		//Se o findById não encontrar ninguém, o service levanta exceção que é tratada pelo @ExceptionHandler
		//Pensar se o service deve levantar exceção mesmo. No momento acho que sim
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PlanetaDTO> findById(@PathVariable("id") String id) {
		PlanetaDTO planeta = service.findById(id);
		
		//TODO apagar comentário depois
		//Se encontrar, tudo certo
		//Se o findById não encontrar ninguém, o service levanta exceção que é tratada pelo @ExceptionHandler
		//Pensar se o service deve levantar exceção mesmo. No momento acho que sim
		return ResponseEntity.ok(planeta);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handlePlanetaNotFound(PlanetaNotFoundException ex) {}
}
