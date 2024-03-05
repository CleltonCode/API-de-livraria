package com.clelton.gl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.LivroService;
import com.clelton.gl.service.impl.LivroServiceImpl;


@SpringBootTest
public class GerenciaLivravriaTest {
	
	@Autowired
	private LivroService livroService;
	
	@InjectMocks
	private LivroServiceImpl livroServiceImpl;
	
	@Mock
	private LivroRepository livroRepository;
	
	private LivroDTO criarLivro() {
		LivroDTO livroDTO = new LivroDTO();
		
		livroDTO.setIsbn("123456");
		livroDTO.setTitulo("Titulo teste");
		livroDTO.setAnoPublicacao(2024);

		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setNomeAutor("Autor teste");
		livroDTO.setAutor(autorDTO);

		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO.setNomeEditora("Editora teste");
		livroDTO.setEditora(editoraDTO);
		return livroDTO;
	}
	
	@Test
	public void salvaLivro() throws Exception {
		
		//Arrange
		LivroDTO livroDTO = criarLivro();
		Livro livro = null;
		
		//Act
		try {
			livro = livroService.salvarLivro(livroDTO);
		} catch (LivroException e) {
			// TODO Auto-generated catch block
			throw new LivroException(e.getMessage());
		}
		
		//Assert
		assertEquals("Titulo teste", livro.getTitulo());
//		assertEquals("Autor teste", livro.getAutor().getNomeAutor());
//		assertEquals("Editora teste", livro.getEditora().getNomeEditora());
		
	}
	
	@Test
	public void editarLivro() {
		
		//Arrange
		LivroDTO livroDTO = criarLivro();

		livroDTO.setTitulo("Titulo teste 2");
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setNomeAutor("Clelton");
		livroDTO.setAutor(autorDTO);
		
		
		//Act
		try {
			 livroService.atualizarLivro(livroDTO, 1L);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		//Assert
		assertEquals("Titulo teste 2", livroDTO.getTitulo());
		assertEquals("Clelton", livroDTO.getAutor().getNomeAutor());
		
	}
	
	@Test
	public void removerLivro() {
		
		//Arrange
		Long livroId = 1L;
		when(livroRepository.existsById(livroId)).thenReturn(true);

		
		//Act
		livroServiceImpl.excluirLivro(livroId);
		
		//Assert
		verify(livroRepository).deleteById(livroId);
	}
	
	@Test
	public void buscaLivroPorTitulo() {
		criarLivro();
		
		Livro livro = livroService.buscarLivroPorTitulo("Titulo teste");
		assertEquals("123456", livro.getIsbn());
		assertEquals(1L, livro.getId());
		assertEquals("Autor teste", livro.getAutor().getNomeAutor());
		assertEquals("Editora teste", livro.getEditora().getNomeEditora());
		
		/*
		 * livroDTO.setIsbn("123456");
		livroDTO.setTitulo("Titulo teste");
		livroDTO.setAnoPublicacao(2024);

		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setNomeAutor("Autor teste");
		livroDTO.setAutor(autorDTO);

		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO.setNomeEditora("Editora teste");
		livroDTO.setEditora(editoraDTO);
		 * */
		
	}
	
	@Test
	public void buscaLivroPorAutor() {
		criarLivro();
		
		Autor autor = new Autor();
		autor.setNomeAutor("Autor teste");
		
		List<Livro> livro =  livroService.buscarLivrosPorAutor(autor);
		System.out.println("BOSTA "+ livro);
		assertEquals("123456", livro.get(0).getIsbn());
		assertEquals(1L, livro.get(0).getId());
		assertEquals("Autor teste", livro.get(0).getAutor().getNomeAutor());
		assertEquals("Editora teste", livro.get(0).getEditora().getNomeEditora());
		
		/*
		 * livroDTO.setIsbn("123456");
		livroDTO.setTitulo("Titulo teste");
		livroDTO.setAnoPublicacao(2024);

		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setNomeAutor("Autor teste");
		livroDTO.setAutor(autorDTO);

		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO.setNomeEditora("Editora teste");
		livroDTO.setEditora(editoraDTO);
		 * */
		
	}
	
	

}
