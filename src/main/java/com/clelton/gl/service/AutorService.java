package com.clelton.gl.service;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.LivroDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AutorService {

    AutorDTO criarAutor(AutorDTO autorDTO);
    AutorDTO obterAutorPorId(AutorDTO autorDTO);
    List<LivroDTO> obterLivrosDoAutor(AutorDTO autorDTO);
}
