package com.clelton.gl.dto.mapper;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.entity.Livro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LivroMapper {
    private static final Logger LOG = LogManager.getLogger(LivroMapper.class);

    public LivroDTO livroToDTO(Livro livro){
        Objects.requireNonNull(livro, "Objeto Livro null");
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(livro.getId());
        livroDTO.setIsbn(livroDTO.getIsbn());
        livroDTO.setEditora(livroDTO.getEditora());
        livroDTO.setTitulo(livro.getTitulo());
        livroDTO.setAnoPublicacao(livro.getAnoPublicacao());
        livroDTO.setNumeroDePaginas(livro.getNumeroDePaginas());
        livroDTO.setAutor(livroDTO.getAutor());

        LOG.info("Livro to DTO: "+ livroDTO);

        return livroDTO;
    }

    public Livro livroToEntity(LivroDTO livroDTO){
//        Objects.requireNonNull(livroDTO, "Objeto LivroDTO null");
//        Livro livro = new Livro();
//
//        livro.setId(livroDTO.getId());
//        livro.setIsbn(livroDTO.getIsbn());
//        livro.setTitulo(livroDTO.getTitulo());
//        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
//        livro.setNumeroDePaginas(livroDTO.getNumeroDePaginas());
//
//        Editora editora = new Editora();
//        editora.setId(livroDTO.getEditora().getId());
//        editora.setEditoraNome(livroDTO.getEditora().getEditoraNome());
//        livro.setEditora(editora);
//
//        Autor autor = new Autor();
//        autor.setId(livroDTO.getAutor().getId());
//        autor.setAutorNome(livroDTO.getAutor().getAutorNome());
//        livro.setAutor(autor);
//
//        LOG.info("Livro to ENTITY: " + livro);
//        return livro;
        Livro livro = new Livro();
        livro.setId(livroDTO.getId());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());

        Autor autor = new Autor();
        autor.setId(livroDTO.getAutor().getId());
        autor.setAutorNome(livroDTO.getAutor().getAutorNome());
        livro.setAutor(autor);

        Editora editora = new Editora();
        editora.setId(livroDTO.getEditora().getId());
        editora.setEditoraNome(livroDTO.getEditora().getEditoraNome());
        livro.setEditora(editora);

        return livro;
    }
}
