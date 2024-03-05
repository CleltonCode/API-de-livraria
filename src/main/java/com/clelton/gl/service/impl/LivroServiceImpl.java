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
import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOG = LogManager.getLogger(LivroServiceImpl.class);

    @Autowired
    public LivroServiceImpl(LivroRepository livroRepository, AutorRepository autorRepository, EditoraRepository editoraRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
    }

    @Override
    public LivroDTO salvarLivro(LivroDTO livroDTO) {
        try{
            Livro livro = this.modelMapper.map(livroDTO, Livro.class);

            Autor autorRecebido= livro.getAutor();
            Editora editoraRecebida = livro.getEditora();

            Autor autor = criarOuRecuperarAutor(autorRecebido);
            Editora editora = criarOuRecupararEditora(editoraRecebida);
            livro.setAutor(autor);
            livro.setEditora(editora);
            this.livroRepository.save(livro);

            return livroDTO;
        }catch (LivroException e){
            throw new LivroException("Erro ao salvar novo livro");
        }

    }

    @Override
    public Optional<Livro> buscarLivroPorId(Long id){
       return this.livroRepository.findById(id);

    }

    @Override
    public LivrosPageDTO todosOsLivros(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Livro> livroPage = this.livroRepository.findAll(pageRequest);

        List<LivroDTO> livroDTOList = livroPage.map(livro -> modelMapper.map(livro, LivroDTO.class)).getContent();

        LivrosPageDTO livrosPageDTO = new LivrosPageDTO(livroDTOList, page, page);

        return livrosPageDTO;
    }

    @Override
    public Livro atualizarLivro(LivroDTO livroDTO, Long id) {

        Optional<Livro> livroExist = this.livroRepository.findById(id);
        if (!livroExist.isEmpty()){
            Livro livro = modelMapper.map(livroDTO, Livro.class);
            return this.livroRepository.save(livro);
        }

        return null;
    }



    @Override
    public void excluirLivro(Long id) {
         this.livroRepository.deleteById(id);
    }

    @Override
    public Livro buscarLivroPorTitulo(String titulo) {
        return this.livroRepository.findByTitulo(titulo);
    }

    @Override
    public List<Livro> buscarLivrosPorAutor(String autorNome) {
        return this.livroRepository.findByAutorNome(autorNome);
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
