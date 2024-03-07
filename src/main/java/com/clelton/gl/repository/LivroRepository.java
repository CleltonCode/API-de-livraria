package com.clelton.gl.repository;

import com.clelton.gl.entity.Livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

     Page<Livro> findByAutorAutorNomeContainingIgnoreCase(String nomeAutor, Pageable pageable);

	 Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	 
	 Page<Livro> findByAutorAutorNomeContainingIgnoreCaseAndTituloContainingIgnoreCase(String autor, String titulo, Pageable pageable);

}
