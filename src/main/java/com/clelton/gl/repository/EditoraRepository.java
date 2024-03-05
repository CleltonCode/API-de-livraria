package com.clelton.gl.repository;

import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
    Optional<Editora>  findByEditoraNome(String nomeEditora);

}
