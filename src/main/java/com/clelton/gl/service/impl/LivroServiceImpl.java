package com.clelton.gl.service.impl;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.repository.EditoraRepository;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.LivroService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Livro salvarLivro(LivroDTO livroDTO) {

        Livro livro = this.modelMapper.map(livroDTO, Livro.class);

        Autor autorRecebido= livro.getAutor();
        Editora editoraRecebida = livro.getEditora();

        Autor autor = criarOuRecuperarAutor(autorRecebido);
        Editora editora = criarOuRecupararEditora(editoraRecebida);
        livro.setAutor(autor);
        livro.setEditora(editora);
        System.out.println("Livro: " + livro);
        return this.livroRepository.save(livro);

    }

    @Override
    public Optional<Livro> buscarLivroPorId(Long id){
       return this.livroRepository.findById(id);

    }

    @Override
    public Livro atualizarLivro(Livro livro, Long id) {

        Optional<Livro> livroExist = this.livroRepository.findById(id);
        if (livroExist.isEmpty()){
            return null;
        }

        return this.livroRepository.save(livro);
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
