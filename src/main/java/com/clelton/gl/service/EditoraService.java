package com.clelton.gl.service;

import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.entity.Editora;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EditoraService {
    EditoraDTO criarEditora(EditoraDTO editoraDTO);
    EditoraDTO obterEditoraPorId(EditoraDTO editoraDTO);
    List<EditoraDTO> obterLivrosDaEditora(EditoraDTO editoraDTO);

}
