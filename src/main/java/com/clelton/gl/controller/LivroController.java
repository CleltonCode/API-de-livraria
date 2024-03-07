package com.clelton.gl.controller;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.service.LivroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(value = "${api.string.url}/livros",
                produces = "application/json")
public class LivroController {

    @Autowired
    private LivroService livroService;



    @Operation(
            summary = "Salvar Livro",
            description = "Endpoint para salvar um Livro no banco de dados",
            responses = {
                @ApiResponse(responseCode = "201", description = "Livro Salvo com sucesso"),
                @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao Salvar Livro")
            }
    )
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@Valid @RequestBody LivroDTO livroDTO){

        try{

            Livro bodyResult = livroService.salvarLivro(livroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bodyResult);
        }catch (LivroException e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }


    @Operation(
            summary = "Editar Livro",
            description = "Endpoint para Editar um Livro. O ID do Livro deve ser passado como parâmetro na URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livro editado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao Editar Livro")
            }
    )
    @PutMapping("/{id}")
    @Operation(summary = "Editar livro", description = "Método para edição do livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    public ResponseEntity<Livro> editarLivro(@PathVariable("id") Long id, @RequestBody LivroDTO livroDTO){
        try{
            Livro livro = livroService.atualizarLivro(livroDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(livro);
        }catch (LivroException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao Salvar Livro", e);
        }
    }


    @Operation(
            summary = "Listar Livros",
            description = "Endpoint para Listar Livros, lista paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Livros obtida com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao carregar Lista de Livros")
            }
    )
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
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao carregar Lista de Livros", e);
        }
    }


    @Operation(
            summary = "Buscar livros por Autor",
            description = "Endpoint para efetuar buscar por autor Lista de Livros, lista paginada. O Nome do Autor do Livro deve ser passado como parâmetro na URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Livros obtida com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao carregar Lista de Livros por Autor")
            }
    )
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


    @Operation(
            summary = "Buscar livros por Título",
            description = "Endpoint para efetuar busca de Livros por Título, lista paginada O Título do Livro deve ser passado como parâmetro na URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Livros obtida com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao carregar Lista de Livros")
            }
    )
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
        	LivrosPageDTO livros = livroService.buscarLivroPorTitulo( page, pageSize, tituloLivro);
        	return ResponseEntity.status(HttpStatus.OK).body(livros);
        }catch (LivroException e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar Livros por Título");
		}
    }

    @Operation(
            summary = "Buscar livros por Autor e Título",
            description = "Endpoit para efetuar busca de livros por autor e  título, lista paginada. O Autor e o Título do Livro devem ser passados como parâmetros na URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Livros obtida com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao carregar Lista de Livros por Autor e Título")
            }
    )
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
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao Buscar Livros por Autor e Título");
		}
    }


    @Operation(
            summary = "Deletar Livro",
            description = "Endpoint para Excluir um Livro. O ID do Livro deve ser passado como parâmetro na URL.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Livro Excluído com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao Excluir Livro.")
            }
    )
    @DeleteMapping("/deletarLivro/{id}")
    public void deletarLivro(@PathVariable("livroId") Long id){
        livroService.excluirLivro(id);

    }


 }
