package com.clelton.gl.service;


import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Livro;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LivroService {

    LivroDTO salvarLivro(LivroDTO livroDTO);
    Optional<Livro> buscarLivroPorId(Long id);

    LivrosPageDTO todosOsLivros(@PositiveOrZero int page, @Positive @Max(10) int pageSize);

    Livro atualizarLivro(LivroDTO livroDTO, Long id);
    void excluirLivro(Long id);
    Livro buscarLivroPorTitulo(String titulo);

    List<Livro> buscarLivrosPorAutor(String autorNome);

}
