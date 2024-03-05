package com.clelton.gl.repository;

import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByAutorNome(String nomeAutor);
}
