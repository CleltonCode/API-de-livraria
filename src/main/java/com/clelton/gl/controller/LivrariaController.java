package com.clelton.gl.controller;

import com.clelton.gl.dto.LivroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/livros/")
public class LivrariaController {


    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody LivroDTO livroDTO){
        return null;
    }
}
