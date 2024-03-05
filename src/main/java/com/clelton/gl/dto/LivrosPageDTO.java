package com.clelton.gl.dto;

import java.util.List;

public record LivrosPageDTO (List<LivroDTO> livros, long totalElements, long totalPages){

}
