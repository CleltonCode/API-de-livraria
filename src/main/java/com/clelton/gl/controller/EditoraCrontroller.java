package com.clelton.gl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.EditorasPageDTO;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.exceptions.EditoraException;
import com.clelton.gl.service.EditoraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "${api.string.url}/editoras",
		        produces = "application/json")

public class EditoraCrontroller {
	
	@Autowired
	private EditoraService editoraService;

	@Operation(
			summary = "Salvar Editora",
			description = "Endpoint para salvar uma Editora no banco de dados.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Editora salva com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao salvar Editora")
			}
	)
	@PostMapping
    @Operation(summary = "Post salvar editora", description = "Inputa dados no banco, retornando a entidade após o input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
	public ResponseEntity<Editora> salvarEditora(@Valid @RequestBody EditoraDTO editoraDTO){
		try {
			Editora editora = editoraService.salvarEditora(editoraDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(editora);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar Editora", e);
		}
	}

	@Operation(
			summary = "Editar Editora",
			description = "Endpoint para editar uma Editora no banco de dados. O ID da Editora deve ser passado como parâmetro na URL.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Editora editada com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao editar Editora")
			}
	)
	@PutMapping("/{id}")
    @Operation(summary = "Editar editora", description = "Método para edição do editora")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully ok"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
	public ResponseEntity<Editora> editarEditora(@Valid @RequestBody EditoraDTO editoraDTO, @PathVariable("id")Long id){
		try {
			Editora editora = editoraService.editarEditora(editoraDTO, id);
			return ResponseEntity.status(HttpStatus.OK).body(editora);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao modificar Editora!", e);
		}
	}

	@Operation(
			summary = "Listar Editora",
			description = "Endpoint para listar uma Editora no banco de dados, lista paginada.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Lista de Editora obtida com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao obter lista de Editora")
			}
	)
	@GetMapping
	@Operation(summary = "Listar editora", description = "Método para Listar editoras, com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully ok"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
	public ResponseEntity<EditorasPageDTO> listarEditoras(
			  @RequestParam(defaultValue = "0") int page, 
			  @RequestParam(defaultValue = "8") int PageSize
			){
		try {
			EditorasPageDTO listaEditoras = editoraService.listarEditoras(page, PageSize);
			return ResponseEntity.status(HttpStatus.OK).body(listaEditoras);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar Editoras!", e);
		}
	}

	@Operation(
			summary = "Buscar Editora",
			description = "Endpoint para efetuar a busca de uma Editora no banco de dados. O ID da Editora deve ser passado como parâmetro na URL.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Editora obtida com sucesso"),
					@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao obter Editora")
			}
	)
	@GetMapping("/{id}")
	@Operation(summary = "Busca editora por id", description = "Método para buscar editora passando id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully ok"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
	public ResponseEntity<Editora> buscarEditoraPorId(@PathVariable("id") Long id){
		try {
			Editora editora = editoraService.buscarEditoraPorId(id);
			return ResponseEntity.status(HttpStatus.OK).body(editora);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Editora por id!", e);	
		}
	}

}
