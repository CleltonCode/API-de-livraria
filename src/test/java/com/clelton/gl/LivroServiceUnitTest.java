package com.clelton.gl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.repository.EditoraRepository;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.impl.LivroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class LivroServiceUnitTest {

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;

    @Mock
    private EditoraRepository editoraRepository;

    @InjectMocks
    private LivroServiceImpl livroServiceImpl;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveSalvarLivroComSucesso() throws LivroException{


        // Arrange
        LivroDTO livroDTO = criarLivroDTO();
        Livro livroEntity = criarLivroEntity(livroDTO);

        // Mock para autorRepository
        Autor autorEntity = criarAutorEntity(livroDTO.getAutor());
        when(autorRepository.findByAutorNome(anyString())).thenReturn(Optional.of(autorEntity));

        // Mock para editoraRepository
        Editora editoraEntity = criarEditoraEntity(livroDTO.getEditora());
        when(editoraRepository.findByEditoraNome(anyString())).thenReturn(Optional.of(editoraEntity));

        // Mock para livroRepository
        when(livroRepository.save(any(Livro.class))).thenReturn(livroEntity);

        // Act
        Livro result = livroServiceImpl.salvarLivro(livroDTO);


        // Assert
        assertNotNull(result);
        assertEquals(livroEntity, result);

        // Verificar chamadas aos repositórios
        verify(autorRepository, times(1)).findByAutorNome(anyString());
        verify(editoraRepository, times(1)).findByEditoraNome(anyString());

    }


    @Test
    public void deveEditarLivroComSucesso() {
        // Arrange
        LivroDTO livroDTO = criarLivroDTO();
        livroDTO.setId(1L);
        livroDTO.setTitulo("Titulo teste 2");
        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setAutorNome("Clelton");
        livroDTO.setAutor(autorDTO);
        Livro livroEntity = criarLivroEntity(livroDTO);

        // Mockando o retorno do repository
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livroEntity));

        // Act
        try {
            livroServiceImpl.atualizarLivro(livroDTO, 1L);
        }catch (LivroException e){
            fail("Erro ao editar livro", e);
        }

        // Assert
        assertEquals("Titulo teste 2", livroEntity.getTitulo());
        assertEquals("Clelton", livroEntity.getAutor().getAutorNome());
    }


    @Test
    public void deveRemoverLivroComSucesso(){
        // Arreng
        Long livroId = 1L;
        when(livroRepository.existsById(livroId)).thenReturn(true);

        // Act
        livroServiceImpl.excluirLivro(livroId);

        // Assert
        verify(livroRepository).deleteById(livroId);

    }


    @Test
    public void deveBuscarlivroPorTituloComSucesso(){
        // Arrange
        LivroDTO livroDTO = criarLivroDTO();
        Livro livroEntity = criarLivroEntity(livroDTO);

        when(livroRepository.findByTituloContainingIgnoreCase("Titulo Livro 1", PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(Collections.singletonList(livroEntity)));
        //old -> when(livroRepository.findByTitulo("Titulo teste")).thenReturn(livroEntity);

        // Act
        //odo -> Livro result = livroServiceImpl.buscarLivroPorTitulo("Titulo teste");
        LivrosPageDTO result = livroServiceImpl.buscarLivroPorTitulo( 0, 10, "Titulo Livro 1" );
        Livro livroResult = modelMapper.map(result.livros().get(0), Livro.class);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.livros().size());
        assertEquals(livroEntity, livroResult);

    }

    @Test
    public void deveBuscaLivroPorAutorComSucesso(){
        // Arrange
        LivroDTO livroDTO = criarLivroDTO();
        Livro livroEntity = criarLivroEntity(livroDTO);
        String autorNome = "Nome Autor 1";
        // old -> when(livroRepository.findByAutorNome("Autor teste")).thenReturn(List.of(livroEntity));
        when(livroRepository.findByAutorAutorNomeContainingIgnoreCase(autorNome,
                PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(Collections.singletonList(livroEntity)));

        // Act
        // old -> List<Livro> result = livroServiceImpl.buscarLivrosPorAutor("Autor teste");
        LivrosPageDTO result  = livroServiceImpl.buscarLivrosPorAutor(0, 10, autorNome);
        Livro livroResult = modelMapper.map(result.livros().get(0), Livro.class);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.livros().size());
        assertEquals(livroEntity, livroResult);
    }
/*
    @Test
    public void deveBuscaLivroPorAutorETituloComSucesso(){
        // Arrange
        LivroDTO livroDTO = criarLivroDTO();
        Livro livroEntity = criarLivroEntity(livroDTO);
        when(livroRepository
                .findByAutorNomeContainingIgnoreCaseAndTituloContainingIgnoreCase("Nome Autor 1", "Titulo Livro 1",
                        PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(Collections.singletonList(livroEntity)));

        // Act
        LivrosPageDTO result = livroServiceImpl.buscarLivrosPorAutorTitulo(0, 10, "Nome Autor 1", "Titulo Livro 1");


        // Assert
        assertNotNull(result);
        assertEquals(1, result.livros().size());
        assertEquals(livroEntity, result.livros().get(0));

    }*/

    @Test
    public void deveMapearDTOEmEntity(){
        // Arrange
        LivroDTO livroDTO = criarLivroDTO();

        // Act
        Livro livro = modelMapper.map(livroDTO, Livro.class);

        // Assert
        assertEquals(livroDTO.getId(), livro.getId());
        assertEquals(livroDTO.getIsbn(), livro.getIsbn());
        assertEquals(livroDTO.getTitulo(), livro.getTitulo());
        assertEquals(livroDTO.getAnoPublicacao(), livro.getAnoPublicacao());

        assertEquals(livroDTO.getAutor().getId(), livro.getAutor().getId());
        assertEquals(livroDTO.getAutor().getAutorNome(), livro.getAutor().getAutorNome());

        assertEquals(livroDTO.getEditora().getId(), livro.getEditora().getId());
        assertEquals(livroDTO.getEditora().getEditoraNome(), livro.getEditora().getEditoraNome());

    }

    private LivroDTO criarLivroDTO() {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(1L);
        livroDTO.setIsbn("123456");
        livroDTO.setTitulo("Titulo Livro 1");
        livroDTO.setAnoPublicacao(2024);

        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setId(1L);
        autorDTO.setAutorNome("Nome Autor 1");
        livroDTO.setAutor(autorDTO);

        EditoraDTO editoraDTO = new EditoraDTO();
        editoraDTO.setId(1L);
        editoraDTO.setEditoraNome("Nome Editora 1");
        livroDTO.setEditora(editoraDTO);

        return livroDTO;
    }



        private Livro criarLivroEntity(LivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setId(livroDTO.getId());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());

        Autor autor = new Autor();
        autor.setId(livroDTO.getAutor().getId());
        autor.setAutorNome(livroDTO.getAutor().getAutorNome());
        livro.setAutor(autor);

        Editora editora = new Editora();
        editora.setId(livroDTO.getEditora().getId());
        editora.setEditoraNome(livroDTO.getEditora().getEditoraNome());
        livro.setEditora(editora);

        return livro;
    }


    private Autor criarAutorEntity(AutorDTO autorDTO) {
        Autor autorEntity = new Autor();
        autorEntity.setAutorNome(autorDTO.getAutorNome());
        // Setar outros campos do Autor, se necessário
        return autorEntity;
    }

    private Editora criarEditoraEntity(EditoraDTO editoraDTO) {
        Editora editoraEntity = new Editora();
        editoraEntity.setEditoraNome(editoraDTO.getEditoraNome());
        // Setar outros campos da Editora, se necessário
        return editoraEntity;
    }
}
