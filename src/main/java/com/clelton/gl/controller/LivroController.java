package com.clelton.gl.controller;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.service.LivroService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    private static final Logger LOG = LogManager.getLogger(LivroController.class);

    @PostMapping
    public ResponseEntity<LivroDTO> salvarLivro(@RequestBody LivroDTO livroDTO){
        try{

            LivroDTO bodyResult = livroService.salvarLivro(livroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bodyResult);
        }catch (LivroException e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> editarLivro(@PathVariable("id") Long id, @RequestBody LivroDTO livroDTO){
        try{
            Livro livro = livroService.atualizarLivro(livroDTO, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(livro);
        }catch (LivroException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }


    @GetMapping
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
    public void deletarLivro(@PathVariable("livroId") Long livroId){

    }

 }