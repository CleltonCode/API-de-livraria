package com.clelton.gl.dto;

import java.util.List;

public record AutoresPageDTO(List<AutorDTO> autores, long totalElements, long totalPages) {

}
