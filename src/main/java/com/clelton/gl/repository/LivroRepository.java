package com.clelton.gl.repository;

import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l  WHERE l.titulo LIKE %:titulo% ")
    Livro findByTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Livro l JOIN l.autor a WHERE a.autorNome = :autorNome")
    List<Livro> findByAutorNome(@Param("autorNome") String autorNome);
}
