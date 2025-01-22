package com.example.spring_boot_jpa_example._common.dtos;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
public class PageDTO<T> {
    private final Integer totalPages;
    private final Long totalElements;
    private final Integer size;
    private final Integer number;
    private final Integer numberOfElements;
    private final Boolean first;
    private final Boolean last;
    private final Boolean empty;
    private final List<T> content;

    public PageDTO(PageImpl page, List<T> content) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
        this.content = content;
    }

    public PageDTO(Page page, List<T> content) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
        this.content = content;
    }
}
