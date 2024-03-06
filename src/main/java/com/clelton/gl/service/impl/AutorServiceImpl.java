package com.clelton.gl.service.impl;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.dto.AutoresPageDTO;
import com.clelton.gl.entity.Autor;
import com.clelton.gl.exceptions.AutorException;
import com.clelton.gl.repository.AutorRepository;
import com.clelton.gl.service.AutorService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;


    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Autor salvarAutor(AutorDTO autorDTO) {
    	
    	Autor autor = modelMapper.map(autorDTO, Autor.class);
    	return autorRepository.save(autor);
        
    }


	@Override
	public AutoresPageDTO listarAutores(int page, int pageSize) {
		
		Page<Autor> pageAutor =  autorRepository.findAll(PageRequest.of(page, pageSize));
		
		List<AutorDTO> listaAutores = pageAutor
				.map(autores -> modelMapper.map(pageAutor, AutorDTO.class)).getContent();
		
		AutoresPageDTO autoresPageDTO = new AutoresPageDTO(listaAutores, page, pageSize);
		
		return autoresPageDTO;
	}
	
    @Override
    public Autor buscarAutorPorId(Long id) {
    	return autorRepository.findById(id).orElseThrow(() -> new AutorException("Erro ao buscar Autor com ID: " + id));
        
    }


	@Override
	public Autor editarAutor(AutorDTO autorDTO, Long id) {
		autorRepository.findById(id).orElseThrow(
				() -> new AutorException("Erro ao buscar Autor com ID: " + id));
		Autor autor = modelMapper.map(autorDTO, Autor.class);
		
		return autorRepository.save(autor);
		
	}
}
