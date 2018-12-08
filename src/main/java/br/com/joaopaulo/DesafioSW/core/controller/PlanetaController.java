package br.com.joaopaulo.DesafioSW.core.controller;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.joaopaulo.DesafioSW.core.service.PlanetaService;
import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.exception.NomeDuplicadoPlanetaException;
import br.com.joaopaulo.DesafioSW.exception.PlanetaNotFoundException;

/**
 * Esse controlador é o responsável pela API pública usada para gerenciar as informações dos planetas
 *
 */
@RestController
@RequestMapping("/planetas")
public final class PlanetaController {

	@Autowired
	private PlanetaService service;

	@GetMapping
	public ResponseEntity<List<PlanetaDTO>> find(@RequestParam(value = "nome", required = false) String nome) {
		List<PlanetaDTO> planetas;

		PlanetaDTO planetaDTO = new PlanetaDTO();
		planetaDTO.setNome(nome);

		planetas = service.findPlanetas(planetaDTO);

		return ResponseEntity.ok(planetas);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PlanetaDTO> findById(@PathVariable("id") String id) {
		PlanetaDTO planeta = service.findById(id);

		return ResponseEntity.ok(planeta);
	}

	@PostMapping
	public ResponseEntity<PlanetaDTO> create(@RequestBody @Valid PlanetaDTO planetaDTO) {
		service.create(planetaDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(planetaDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler
	public ResponseEntity<String> handlePlanetaNotFound(PlanetaNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNomeDuplicado(NomeDuplicadoPlanetaException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
