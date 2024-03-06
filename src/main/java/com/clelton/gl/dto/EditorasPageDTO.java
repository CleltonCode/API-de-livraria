package com.clelton.gl.dto;

import java.util.List;

public record EditorasPageDTO(List<EditoraDTO> editoras, long totalElements, long totalPages) {}
