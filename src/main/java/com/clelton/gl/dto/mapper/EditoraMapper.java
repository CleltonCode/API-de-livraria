package com.clelton.gl.dto.mapper;

import com.clelton.gl.dto.EditoraDTO;
import com.clelton.gl.entity.Editora;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EditoraMapper {

    public EditoraDTO editoraToDTO(Editora editora){
        Objects.requireNonNull(editora, "Objeto Editora null");
        return new EditoraDTO(editora.getId(), editora.getEditoraNome());
    }

    public Editora editoraToEntity(EditoraDTO editoraDTO){
        Objects.requireNonNull(editoraDTO, "Objeto EditoraDTO null");
        return new Editora(editoraDTO.getId(), editoraDTO.getEditoraNome());
    }

}
