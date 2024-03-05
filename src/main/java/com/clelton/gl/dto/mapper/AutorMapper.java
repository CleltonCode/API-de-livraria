package com.clelton.gl.dto.mapper;

import com.clelton.gl.dto.AutorDTO;
import com.clelton.gl.entity.Autor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AutorMapper {

    public AutorDTO autorToDTO(Autor autor){
        Objects.requireNonNull(autor, "Objeto Autor null");
        return new AutorDTO(autor.getId(), autor.getAutorNome());
    }

    public Autor autorToEntity(AutorDTO autorDTO){
        Objects.requireNonNull(autorDTO, "Objeto AutorDTO null");
        return new Autor(autorDTO.getId(), autorDTO.getAutorNome());
    }
}
