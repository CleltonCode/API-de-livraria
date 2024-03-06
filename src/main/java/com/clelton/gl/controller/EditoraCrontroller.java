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

import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.EditorasPageDTO;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.exceptions.EditoraException;
import com.clelton.gl.service.EditoraService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editoras")
public class EditoraCrontroller {
	
	@Autowired
	private EditoraService editoraService;
	
	
	@PostMapping
	public ResponseEntity<Editora> salvarEditora(@Valid @RequestBody EditoraDTO editoraDTO){
		try {
			Editora editora = editoraService.salvarEditora(editoraDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(editora);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar Editora", e);
		}
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Editora> editarEditora(@Valid @RequestBody EditoraDTO editoraDTO, @PathVariable("id")Long id){
		try {
			Editora editora = editoraService.editarEditora(editoraDTO, id);
			return ResponseEntity.status(HttpStatus.OK).body(editora);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao modificar Editora!", e);
		}
	}
	
	@GetMapping
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
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Editora> buscarEditoraPorId(@PathVariable("id") Long id){
		try {
			Editora editora = editoraService.buscarEditoraPorId(id);
			return ResponseEntity.status(HttpStatus.OK).body(editora);
		}catch (EditoraException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Editora por id!", e);	
		}
	}

}
