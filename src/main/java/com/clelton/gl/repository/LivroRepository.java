package com.clelton.gl.repository;

import com.clelton.gl.entity.Livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

//	 @Query("SELECT l FROM Livro l JOIN l.autor a WHERE LOWER(a.autor) = LOWER(:autor)")
//	 Page<Livro> findByAutorNomeContainingIgnoreCase(String autor, Pageable pageable);
	
     Page<Livro> findByAutorAutorNomeContainingIgnoreCase(String autorNome, Pageable pageable);

    
	 @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(concat('%', :titulo, '%'))")
	 Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	 
	 @Query("SELECT l FROM Livro l JOIN l.autor a WHERE LOWER(a.autorNome) = LOWER(:autor) AND LOWER(l.titulo) LIKE LOWER(concat('%', :titulo, '%'))")
	 Page<Livro> findByAutorNomeContainingIgnoreCaseAndTituloContainingIgnoreCase(String autor, String titulo, Pageable pageable);

}
