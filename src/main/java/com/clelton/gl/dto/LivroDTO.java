package com.clelton.gl.dto;


import com.clelton.gl.entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroDTO{
	
	
		private Long id;
		private String isbn;
		private String titulo;
		private int anoPublicacao;
		private int numeroDePaginas;
		private AutorDTO autor;
		private EditoraDTO editora;


}
