package com.clelton.gl.service;


import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Livro;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public interface LivroService {

    Livro salvarLivro(LivroDTO livroDTO);
    
    Optional<Livro> buscarLivroPorId(Long id);
    
    LivrosPageDTO listarLivros(@PositiveOrZero int page, @Positive @Max(100) int pageSize);

    Livro atualizarLivro(LivroDTO livroDTO, Long id);
    
    void excluirLivro(Long id);
       
    LivrosPageDTO buscarLivrosPorAutor(int page, int pageSize, String nomeAutor);
    LivrosPageDTO buscarLivroPorTitulo(int page, int pageSize, String tituloLivro);
    LivrosPageDTO buscarLivrosPorAutorTitulo(int page, int pageSize, String nomeAutor, String TituloLivro);
    

}
