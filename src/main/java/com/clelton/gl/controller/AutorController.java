package com.clelton.gl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "/api/autores",
		        produces = "application/json")
public class AutorController {
	
	@Autowired
	private AutorService autorService;


	@Operation(
			summary = "Salvar Autor",
			description = "Endpoint para salvar um Autor no banco de dados.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Autor salvo com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao salvar Autor")
			}
	)
	@PostMapping
	public ResponseEntity<Autor> salvarAutor(@Valid @RequestBody AutorDTO autorDTO){
		
		try {
			Autor autorSalvo = autorService.salvarAutor(autorDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(autorSalvo);
		} catch (AutorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar Autor!", e);
		}
	}

	@Operation(
			summary = "Listar Autor",
			description = "Endpoint para listar Autores, lista paginada.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Lista de Autores obtida com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao listar Autores")
			}
	)
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


	@Operation(
			summary = "Editar Autor",
			description = "Endpoint para editar Autor. O ID do Autor deve ser passado como parâmetro na URL.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Autor editado com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao editar Autor")
			}
	)
	@PutMapping("/{id}")
	public ResponseEntity<Autor> editarAutor(@Valid @RequestBody AutorDTO autorDTO, @PathVariable("id") Long id){
		try {
			Autor autor = autorService.editarAutor(autorDTO, id);
			return ResponseEntity.status(HttpStatus.OK).body(autor);
		}catch (AutorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar Autor!", e);
		}
	}


	@Operation(
			summary = "Buscar Autor por id",
			description = "Endpoint efetuar busca de Autor. O ID do Autor deve ser passado como parâmetro na URL.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Autor encontr.ado com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao obter Autor por id")
			}
	)
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
