package ru.itis.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PageDto<T> {

    @Schema(description = "Список элементов")
    private final List<T> elements;

    @Schema(description = "Общее количество страниц", example = "1")
    private final int totalPages;

    @Schema(description = "Общее количество элементов", example = "1")
    private final long totalElements;

    private PageDto(List<T> elements, int totalPages, long totalElements) {
        this.elements = elements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public static <T> PageDto<T> from(List<T> elements, int totalPages, long totalElements) {
        return new PageDto<>(elements, totalPages, totalElements);
    }
}
