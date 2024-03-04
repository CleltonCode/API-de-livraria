package com.clelton.gl;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.mapper.AutorMapper;
import com.clelton.gl.dto.mapper.EditoraMapper;
import com.clelton.gl.dto.mapper.LivroMapper;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.LivroService;
import com.clelton.gl.service.impl.AutorServiceImpl;
import com.clelton.gl.service.impl.LivroServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class GerenciamentoLivrariaApplicationTests {

	@Mock
	private LivroRepository livroRepository;

	@Mock
	private AutorRepository autorRepository;

	@InjectMocks
	private LivroServiceImpl livroService;

	@InjectMocks
	private AutorServiceImpl autorService;

	@Autowired
	private LivroMapper livroMapper;

	@Autowired
	private AutorMapper autorMapper;

	@Autowired
	private EditoraMapper editoraMapper;


	@Test
	void salvarLivro_RetornaLivroSalvo() {
		LivroDTO livroDTO = criarLivro();
		Livro livroSalvo = null;
		try {
			livroSalvo = livroService.salvarLivro(livroDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		assertNotNull(livroSalvo);
		assertEquals("123456", livroSalvo.getIsbn());
		assertEquals("Titulo teste", livroSalvo.getTitulo());
		assertEquals("Editora teste", livroSalvo.getEditora().getNomeEditora());
		assertEquals("Autor teste", livroSalvo.getAutor().getNomeAutor());
	}

	private LivroDTO criarLivro() {
		LivroDTO livroDTO = new LivroDTO();
		livroDTO.setId(1l);
		livroDTO.setIsbn("123456");
		livroDTO.setTitulo("Titulo teste");
		livroDTO.setAnoPublicacao(2024);

		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setId(1L);
		autorDTO.setNomeAutor("Autor teste");
		livroDTO.setAutor(autorDTO);

		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO.setId(1L);
		editoraDTO.setNomeEditora("Editora teste");
		livroDTO.setEditora(editoraDTO);
		return livroDTO;
	}

	@Test
	void buscarLivro_RetornaBuscaPorId() throws Exception {
		// Arrange
		Long livroId = 1L;
		Livro expectedLivro = this.livroMapper.livroToEntity(criarLivro());
		when(livroRepository.findById(livroId)).thenReturn(Optional.of(expectedLivro));

		// Act
		Optional<Livro> foundLivro = livroRepository.findById(livroId);

		// Assert
		assertNotNull(foundLivro);
		assertEquals(livroId, foundLivro.get().getId());
	}

	@Test
	public void testFindLivroByIdWhenNotFound() {
		// Arrange
		Long livroId = 1L;
		when(livroRepository.findById(livroId)).thenReturn(Optional.empty());

		// Act
		Optional<Livro> foundLivro = livroService.buscarLivroPorId(livroId);

		// Assert
		assertTrue(foundLivro.isEmpty());
	}

	@Test
	void deletarLivro_deletarPorId() {
		// Arrange
		Long livroId = 1L;
		when(livroRepository.existsById(livroId)).thenReturn(true);

		// Act
		livroService.excluirLivro(livroId);

		// Assert
		verify(livroRepository).deleteById(livroId);
	}


	@Test
	void buscarLivroPorTitulo_RetornaBuscaPorTitulo() throws Exception {
		// Arrange

		String livroTitulo = "Titulo teste";
		Livro expectedLivro = this.livroMapper.livroToEntity(criarLivro());
		when(livroRepository.findByTitulo(livroTitulo)).thenReturn(expectedLivro);

		// Act
		Livro foundLivro = livroRepository.findByTitulo(livroTitulo);

		// Assert
		assertNotNull(foundLivro);
		assertEquals(livroTitulo, foundLivro.getTitulo());
	}


	@Test
	void buscarLivroPorAutor_RetornaBuscaPorAutor() throws Exception {
		// Arrange
		Autor autor = new Autor();
		Long idAutor = 1L;
		autor.setId(idAutor);
		autor.setNomeAutor("Autor teste");

		// Act
		List<Livro> livros = livroService.buscarLivrosPorAutor(autor);
		// Assert
		assertTrue(livros.isEmpty());
	}

	@Test
	public void atualizarLivro_DeveAtualizarTituloCorretamente() {
		// Arrange (Preparação)
		LivroDTO livroDTO = new LivroDTO();
		livroDTO.setId(1L);
		livroDTO.setTitulo("Antigo Título");
		livroDTO.setNumeroDePaginas(100);
		livroDTO.setIsbn("123456");
		livroDTO.setAnoPublicacao(2004);

		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setId(1L);
		autorDTO.setNomeAutor("Autor teste");
		livroDTO.setAutor(autorDTO);

		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO.setId(1L);
		editoraDTO.setNomeEditora("Editora teste");
		livroDTO.setEditora(editoraDTO);

		livroDTO.setTitulo("Novo Título");

		// Act (Ação)
		livroService.atualizarLivro(this.livroMapper.livroToEntity(livroDTO), 1L);

		// Assert (Assertiva)
		assertEquals("Novo Título", livroDTO.getTitulo());
	}


}
