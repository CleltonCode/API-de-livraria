package com.clelton.gl.service;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.entity.Autor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AutorService {

    AutorDTO criarAutor(AutorDTO autorDTO);
    AutorDTO obterAutorPorId(AutorDTO autorDTO);
    AutorDTO buscarAutorPorId(Long id);
}
