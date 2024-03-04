package com.clelton.gl.service.impl;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.mapper.AutorMapper;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorMapper autorMapper;

    @Override
    public AutorDTO criarAutor(AutorDTO autorDTO) {
        return null;
    }

    @Override
    public AutorDTO obterAutorPorId(AutorDTO autorDTO) {
        return null;
    }



    @Override
    public AutorDTO buscarAutorPorId(Long id) {
        return null;
    }
}
