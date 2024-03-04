package com.clelton.gl.service;


import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Livro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LivroService {

    Livro salvarLivro(LivroDTO livroDTO) throws Exception;
    Optional<Livro> buscarLivroPorId(Long id);
    Livro atualizarLivro(Livro livro, Long id);
    void excluirLivro(Long id);
    Livro buscarLivroPorTitulo(String titulo);

                                                                                                                                                            List<Livro> buscarLivrosPorAutor(Autor autor);

}
