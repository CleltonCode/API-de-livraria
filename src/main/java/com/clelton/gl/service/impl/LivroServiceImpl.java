package com.clelton.gl.service.impl;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.LivrosPageDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.repository.EditoraRepository;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.LivroService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LivroServiceImpl implements LivroService {

    @Autowired
    private  LivroRepository livroRepository;

    @Autowired
    private  AutorRepository autorRepository;
    
    @Autowired
    private EditoraRepository editoraRepository;

    private  ModelMapper modelMapper = new ModelMapper() ;
    private static final Logger LOG = LogManager.getLogger(LivroServiceImpl.class);


    @Override
    public Livro salvarLivro(LivroDTO livroDTO) {
        try{
            Livro livro = modelMapper.map(livroDTO, Livro.class);

            Autor autorRecebido= livro.getAutor();
            Editora editoraRecebida = livro.getEditora();

            Autor autor = criarOuRecuperarAutor(autorRecebido);
            Editora editora = criarOuRecupararEditora(editoraRecebida);
            livro.setAutor(autor);
            livro.setEditora(editora);

            return livroRepository.save(livro);
        }catch (LivroException e){
            throw new LivroException("Erro ao salvar novo livro");
        }

    }

    @Override
    public Optional<Livro> buscarLivroPorId(Long id){
       return livroRepository.findById(id);

    }

    @Override
    public LivrosPageDTO listarLivros(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        LOG.info("buscarTodosOsLivros");
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Livro> livroPage = livroRepository.findAll(pageRequest);


        List<LivroDTO> livroDTOList = livroPage.hasContent() ?
                livroPage.map(livro -> modelMapper.map(livro, LivroDTO.class)).getContent():
                Collections.emptyList();

        LivrosPageDTO livrosPageDTO = new LivrosPageDTO(livroDTOList, page, livroPage.getTotalPages());

        return livrosPageDTO;
    }

    @Override
    public Livro atualizarLivro(LivroDTO livroDTO, Long id) {

        Optional<Livro> livroExist = livroRepository.findById(id);
        if (!livroExist.isEmpty()){
            Livro livro = modelMapper.map(livroDTO, Livro.class);
            
            Autor autor = criarOuRecuperarAutor(livro.getAutor());
            Editora editora = criarOuRecupararEditora(livro.getEditora());
            
            livro.setAutor(autor);
            livro.setEditora(editora);
            
            return livroRepository.save(livro);
            
        }

        return null;
    }

    @Override
    public void excluirLivro(Long id) {
         livroRepository.deleteById(id);
    }

    @Override
    public LivrosPageDTO buscarLivrosPorAutor(int page, int pageSize, String nomeAutor) {
    	LOG.info("buscar Por Autor");
    	PageRequest pageable = PageRequest.of(page, pageSize);
    	Page<Livro> livroPage = livroRepository.findByAutorAutorNomeContainingIgnoreCase(nomeAutor, pageable);
    	List<LivroDTO> livroDTOList = livroPage.hasContent() ?
    	        livroPage.map(livro -> modelMapper.map(livro, LivroDTO.class)).getContent() :
    	        Collections.emptyList();

    	return new LivrosPageDTO(livroDTOList, page, livroPage.getTotalPages());
    	
    }

    @Override
    public LivrosPageDTO buscarLivroPorTitulo( int page, int pageSize, String tituloLivro) {
        LOG.info("buscar Por Título");
    	PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Livro> livroPage = livroRepository.findByTituloContainingIgnoreCase(tituloLivro, pageable);
        List<LivroDTO> livroDTOList = livroPage.hasContent() ?
                livroPage.map(livro -> modelMapper.map(livro, LivroDTO.class)).getContent() :
                Collections.emptyList();
        return new LivrosPageDTO(livroDTOList, page, livroPage.getTotalPages());

    }
    
    @Override
    public LivrosPageDTO buscarLivrosPorAutorTitulo(int page, int pageSize, String nomeAutor, String tituloLivro) {
      LOG.info("buscar Por Autor e Título");
    	
    	PageRequest pageable = PageRequest.of(page, pageSize);
    	
    	Page<Livro> livroPage = livroRepository.findByAutorAutorNomeContainingIgnoreCaseAndTituloContainingIgnoreCase(nomeAutor, tituloLivro, pageable);


    	 List<LivroDTO> livroDTOList = livroPage.hasContent() ?
    	            livroPage.map(livro -> modelMapper.map(livro, LivroDTO.class)).getContent() :
    	            Collections.emptyList();

    	return new LivrosPageDTO(livroDTOList, page, livroPage.getTotalPages());
    	
    }



    private Autor criarOuRecuperarAutor(Autor  autor){
        LOG.info("Criar ou recurperar: " + autor);
        return this.autorRepository.findByAutorNome(autor.getAutorNome())
                .orElseGet(() -> {
                    Autor novoAutor = new Autor();
                    novoAutor.setAutorNome(autor.getAutorNome());
                    Autor savedAutor =  this.autorRepository.save(novoAutor);
                    LOG.info("Novo Autor salvo: " + savedAutor);
                    return savedAutor;
                });
    }

    private Editora criarOuRecupararEditora(Editora  editora){
        LOG.info("Criar ou recurperar editora: " + editora);
        return this.editoraRepository.findByEditoraNome(editora.getEditoraNome())
                .orElseGet(() -> {
                    Editora novaEditora = new Editora();
                    novaEditora.setEditoraNome(editora.getEditoraNome());
                    Editora savedEditora = this.editoraRepository.save(novaEditora);
                    LOG.info("Nova Editora salva: " + savedEditora);
                    return savedEditora;
                });

    }



}
