package com.clelton.gl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "livro")
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String isbn;
	private String titulo;
	private int anoPublicacao;
	private int numeroDePaginas;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;
	
	@ManyToOne
	@JoinColumn(name = "editora_id")
	private Editora editora;

	// Método para obter o ID do Autor
	public Long getAutorId() {
		return (autor != null) ? autor.getId() : null;
	}

	// Método para obter o Nome do Autor
	public String getAutorNome() {
		return (autor != null) ? autor.getNomeAutor() : null;
	}

	// Método para obter o ID da Editora
	public Long getEditoraId() {
		return (editora != null) ? editora.getId() : null;
	}

	// Método para obter o Nome da Editora
	public String getEditoraNome() {
		return (editora != null) ? editora.getNomeEditora() : null;
	}
	
	
}
