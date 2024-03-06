package com.clelton.gl.service.impl;

import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.EditorasPageDTO;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.exceptions.EditoraException;
import com.clelton.gl.repository.EditoraRepository;
import com.clelton.gl.service.EditoraService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class EditoraServiceImpl implements EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    
    private ModelMapper modelMapper = new ModelMapper();
    
	@Override
	public Editora salvarEditora(EditoraDTO editoraDTO) {
		Editora editora = this.modelMapper.map(editoraDTO, Editora.class);
		return editoraRepository.save(editora);
		
	}

	@Override
	public Editora editarEditora(EditoraDTO editoraDTO, Long id) {
		editoraRepository.findById(id).orElseThrow(
					() -> new EditoraException("Erro ao buscar editora ID: " + id)
				);
		Editora editora = modelMapper.map(editoraRepository, Editora.class);
		
		return editoraRepository.save(editora);
	}

	@Override
	public EditorasPageDTO listarEditoras(int page, int pageSize) {
		Page<Editora> pageEditora = editoraRepository.findAll(PageRequest.of(page, pageSize));
		List<EditoraDTO> listaEditoras = pageEditora
				.map(editoras -> modelMapper.map(pageEditora, EditoraDTO.class)).getContent();
				
		EditorasPageDTO editorasPageDTO = new EditorasPageDTO(listaEditoras, page, pageSize);
		
		return editorasPageDTO;
	}


	@Override
	public Editora buscarEditoraPorId(Long id) {
		
		return editoraRepository.findById(id).orElseThrow(()->new EditoraException("Erro ao buscar editora ID: " + id));
	}





}
