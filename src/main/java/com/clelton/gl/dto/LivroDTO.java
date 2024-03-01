package com.clelton.gl.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroDTO {
	
	
		private Long id;
		private String isbn;
		private int anoPublicacao;
		private int numeroDePaginas;
		private Long autor;
		private Long editora;
		
		
	
}
