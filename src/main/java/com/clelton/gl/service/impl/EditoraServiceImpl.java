package com.clelton.gl.service.impl;

import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.entity.Editora;
import com.clelton.gl.repository.EditoraRepository;
import com.clelton.gl.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditoraServiceImpl implements EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;


    @Override
    public EditoraDTO criarEditora(EditoraDTO editoraDTO) {
        return null;
    }

    @Override
    public EditoraDTO obterEditoraPorId(EditoraDTO editoraDTO) {
        return null;
    }

    @Override
    public List<EditoraDTO> obterLivrosDaEditora(EditoraDTO editoraDTO) {
        return null;
    }


}
