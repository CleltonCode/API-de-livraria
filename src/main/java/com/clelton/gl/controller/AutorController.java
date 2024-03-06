package com.clelton.gl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.AutoresPageDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.exceptions.AutorException;
import com.clelton.gl.service.AutorService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
	
	@Autowired
	private AutorService autorService;
	

	
	@PostMapping
	public ResponseEntity<Autor> salvarAutor(@Valid @RequestBody AutorDTO autorDTO){
		
		try {
			Autor autorSalvo = autorService.salvarAutor(autorDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(autorSalvo);
		} catch (AutorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar Autor!", e);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<AutoresPageDTO> listarAutores(
			@RequestParam(defaultValue = "0") @PositiveOrZero int page, 
			@RequestParam(defaultValue = "8")@Positive @Max(100) int pageSize) {
		
		try {
			AutoresPageDTO autores = autorService.listarAutores(page, pageSize);
			return ResponseEntity.status(HttpStatus.OK).body(autores);
		}catch (AutorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar Autores");
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Autor> editarAutor(@Valid @RequestBody AutorDTO autorDTO, @PathVariable("id") Long id){
		try {
			Autor autor = autorService.editarAutor(autorDTO, id);
			return ResponseEntity.status(HttpStatus.OK).body(autor);
		}catch (AutorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar Autor!", e);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscarAutorPorId(@PathVariable("id") Long id){
		try {
			Autor autor = autorService.buscarAutorPorId(id);
			return ResponseEntity.status(HttpStatus.OK).body(autor);
		}catch (AutorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Autor por ID!", e);
		}
	}

}
