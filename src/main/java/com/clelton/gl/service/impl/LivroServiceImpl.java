package com.clelton.gl.service.impl;

import com.clelton.gl.dto.LivroDTO;
import com.clelton.gl.dto.mapper.LivroMapper;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.entity.Livro;
import com.clelton.gl.exceptions.LivroException;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.repository.EditoraRepository;
import com.clelton.gl.repository.LivroRepository;
import com.clelton.gl.service.LivroService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class LivroServiceImpl implements LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private LivroMapper livroMapper;

    private static final Logger LOG = LogManager.getLogger(LivroServiceImpl.class);
    @Override
    public Livro salvarLivro(LivroDTO livroDTO) {
        Livro livro;
        livro = livroMapper.livroToEntity(livroDTO);

        Autor autor = criarOuRecuperarAutor(livro.getAutorNome());

        Editora editora = criarOuRecupararEditora(livro.getEditoraNome());


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

        //Livro livro = livroMapper.livroToEntity(livroDTO);

//        this.livroRepository.findById(id)
//                .ifPresentOrElse(item ->this.livroRepository.save(livro)
//                        , ()-> {
//                            System.out.println("DADOS " + livro);
//                        });
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
    public List<Livro> buscarLivrosPorAutor(Autor autor) {
        return this.livroRepository.findByAutor(autor);
    }


    private Autor criarOuRecuperarAutor(String nomeAutor){
        LOG.info("Criar ou recurperar: " + nomeAutor);
        return this.autorRepository.findByNomeAutor(nomeAutor)
                .orElseGet(() -> {
                    Autor novoAutor = new Autor();
                    novoAutor.setNomeAutor(nomeAutor);
                    return this.autorRepository.save(novoAutor);
                });
    }

    private Editora criarOuRecupararEditora(String nomeEditora){
        LOG.info("Criar ou recurperar editora: " + nomeEditora);
        return this.editoraRepository.findByNomeEditora(nomeEditora)
                .orElseGet(()->{
                    Editora novaEditora = new Editora();
                    novaEditora.setNomeEditora(nomeEditora);
                    return this.editoraRepository.save(novaEditora);
                });
    }


}
