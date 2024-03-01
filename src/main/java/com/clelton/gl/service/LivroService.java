package com.clelton.gl.service;


import com.clelton.gl.dto.LivroDTO;
import org.springframework.stereotype.Service;

@Service
public interface LivroService {

    LivroDTO criarLivro(LivroDTO livroDTO);
    LivroDTO buscarLivro(Long id);
    LivroDTO atrualizarLivro(LivroDTO livroDTO);
    LivroDTO excluirLivro(LivroDTO livroDTO);
    LivroDTO buscarLivroPorNome(String nomeLivro);

}
