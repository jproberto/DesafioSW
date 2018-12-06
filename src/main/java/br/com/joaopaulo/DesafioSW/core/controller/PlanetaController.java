package br.com.joaopaulo.DesafioSW.core.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaopaulo.DesafioSW.core.service.PlanetaService;
import br.com.joaopaulo.DesafioSW.dto.PlanetaDTO;
import br.com.joaopaulo.DesafioSW.exception.PlanetaNotFoundException;

/**
 * Esse controlador é o responsável pela API pública usada para gerenciar as informações dos planetas
 *
 */

@RestController
@RequestMapping("/planetas")
public final class PlanetaController {

	private final PlanetaService service;

	@Autowired
	public PlanetaController(PlanetaService service) {
		this.service = service;
	}

	@RequestMapping(value = "criar", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public List<PlanetaDTO> create(@RequestBody @Valid PlanetaDTO planetaDTO) {
		service.create(planetaDTO);
		return service.findAll();
	}

	@RequestMapping(value = "excluir/{id}", method = RequestMethod.DELETE)
	public List<PlanetaDTO> delete(@PathVariable("id") String id) {
		service.delete(id);
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<PlanetaDTO> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "id/{id}", method = RequestMethod.GET)
	public PlanetaDTO findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@RequestMapping(value = "nome/{nome}", method = RequestMethod.GET)
	public PlanetaDTO findByNome(@PathVariable("nome") String nome) {
		return service.findByNome(nome);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handlePlanetaNotFound(PlanetaNotFoundException ex) {}
}
