package com.clelton.gl.service.impl;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;

public class LivroServiceImpl implements LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Override
    public LivroDTO criarLivro(LivroDTO livroDTO) {
        return null;
    }

    @Override
    public LivroDTO buscarLivro(Long id) {
        return null;
    }

    @Override
    public LivroDTO atrualizarLivro(LivroDTO livroDTO) {
        return null;
    }

    @Override
    public LivroDTO excluirLivro(LivroDTO livroDTO) {
        return null;
    }

    @Override
    public LivroDTO buscarLivroPorTitulo(String titulo) {
        return this.livroRepository.findbByTitulo(titulo);
    }
}
