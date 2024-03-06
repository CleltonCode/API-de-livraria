package com.clelton.gl.service;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.AutoresPageDTO;
import com.clelton.gl.entity.Autor;



import org.springframework.stereotype.Service;


@Service
public interface AutorService {

    Autor salvarAutor(AutorDTO autorDTO);
    Autor editarAutor(AutorDTO autorDTO, Long id);
    AutoresPageDTO listarAutores(int page, int pageSize);
    Autor buscarAutorPorId(Long id);
}
