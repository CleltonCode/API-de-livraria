package com.clelton.gl.repository;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l WHERE l.titulo LIKE %:titulo% ")
    LivroDTO findbByTitulo(@Param("titulo") String titulo);
}
