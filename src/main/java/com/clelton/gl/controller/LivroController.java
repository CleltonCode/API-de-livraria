package com.clelton.gl.controller;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.service.LivroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(value = "api/livros",produces = "application/json")
public class LivroController {

    @Autowired
    private LivroService livroService;


    @PostMapping
    @Operation(summary = "Post salvar livro", description = "Inputa dados no banco, retornando a entidade após o input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<LivroDTO> salvarLivro(@Valid @RequestBody LivroDTO livroDTO){
        try{

            LivroDTO bodyResult = livroService.salvarLivro(livroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bodyResult);
        }catch (LivroException e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar livro", description = "Método para edição do livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<Livro> editarLivro(@PathVariable("id") Long id, @RequestBody LivroDTO livroDTO){
        try{
            Livro livro = livroService.atualizarLivro(livroDTO, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(livro);
        }catch (LivroException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }


    @GetMapping
  
    @Operation(summary = "Listar livro", description = "Método para Listar livros, com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<LivrosPageDTO> listarLivros(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize){

        try{
            LivrosPageDTO livros = livroService.listarLivros(page, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(livros);
        }catch (LivroException e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao carregar lista de livros", e);
        }
    }

    @GetMapping("/buscaLivroAutor/{nome_autor}")
    
    @Operation(summary = "Buscar livros por Autor", description = "Método para buscar livros passando o Autor, com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully OK"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<LivrosPageDTO> buscarLivrosPorAutor(@PathVariable("nome_autor") String nomeAutor,
    		@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize){
        try {
        	LivrosPageDTO livros = livroService.buscarLivrosPorAutor(page, pageSize, nomeAutor);
        	return ResponseEntity.status(HttpStatus.OK).body(livros);
        }catch (LivroException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Livros por Autor");
		}
    
    }

    @GetMapping("/buscaLivroTitulo/{titulo_livro}")
    
    @Operation(summary = "Buscar livros por Título", description = "Método para buscar livros passando o Título, com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully OK"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<LivrosPageDTO> buscarLivrosPorTitulo(@PathVariable("titulo_livro") String tituloLivro, 
    		@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize){
        try {
        	LivrosPageDTO livros = livroService.buscarLivroPorTitulo(page, pageSize, tituloLivro);
        	return ResponseEntity.status(HttpStatus.OK).body(livros);
        }catch (LivroException e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Livros pelo título");
		}
    }
    
    @GetMapping("/buscaLivroTitulo/{nome_autor}/{titulo_livro}")

    @Operation(summary = "Buscar livros por Autor e Título", description = "Método para buscar livros passando o Autor e Título, com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully OK"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<LivrosPageDTO> buscarLivrosPorAutorTitulo(
    		@PathVariable("nome_autor") String nomeAutor,
    		@PathVariable("titulo_livro") String tituloLivro, 
    		@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize){
        try {
        	LivrosPageDTO livros = livroService.buscarLivrosPorAutorTitulo(page, pageSize , nomeAutor, tituloLivro);
        	return ResponseEntity.status(HttpStatus.OK).body(livros);
        }catch (LivroException e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Livros pelo título");
		}
    }
    
    

    @DeleteMapping("/deletarLivro/{livroId}")

    @Operation(summary = "Deletar livro", description = "Método para deletar livros passando o ID")
    
    public void deletarLivro(@PathVariable("livroId") Long livroId){
    		
    	livroService.excluirLivro(livroId);
    		
    }

 }
