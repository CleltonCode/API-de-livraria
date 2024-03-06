package com.clelton.gl.service;


import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.dto.EditorasPageDTO;
import com.clelton.gl.entity.Editora;

import org.springframework.stereotype.Service;


@Service
public interface EditoraService {
    Editora salvarEditora(EditoraDTO editoraDTO);
    Editora editarEditora(EditoraDTO editoraDTO, Long id);
    EditorasPageDTO listarEditoras(int page, int pageSize);
    Editora buscarEditoraPorId(Long id);
   

}
