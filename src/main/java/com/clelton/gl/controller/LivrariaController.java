package com.clelton.gl.controller;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.service.LivroService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/livros/")
public class LivrariaController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> salvarLivro(@RequestBody LivroDTO livroDTO){
        try{
            LivroDTO bodyResult = this.livroService.salvarLivro(livroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bodyResult);
        }catch (LivroException e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> editarLivro(@PathVariable("id") Long id, @RequestBody LivroDTO livroDTO){
        try{
            Livro livro = this.livroService.atualizarLivro(livroDTO, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(livro);
        }catch (LivroException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Ao salvar livro", e);
        }
    }

    @GetMapping
    public ResponseEntity<LivrosPageDTO> listarLivros(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                       @RequestParam(defaultValue = "8") @Positive @Max(100) int pageSize){

        
        try{


            LivrosPageDTO livrosList = this.livroService.todosOsLivros(page, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(livrosList);
        }catch (LivroException e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao carregar lista de livros", e);
        }
    }

//    @GetMapping("/buscaLivroAutor/{nomeAutor}")
//    public ResponseEntity<List<LivroDTO>> buscarLivrosPorAutor(@PathVariable("nomeAutor") String nomeAutor){
//        return null;
//    }
//
//    @GetMapping("/buscaLivroTitulo/{tituloLivro}")
//    public ResponseEntity<List<LivroDTO>> buscarLivrosPorAutor(@PathVariable("tituloLivro") String tituloLivro){
//        return null;
//    }

    @DeleteMapping("/{livroId}")
    public void deletarLivro(@PathVariable("livroId") Long livroId){

    }

 }
