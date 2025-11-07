package com.livingletters.bookapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
  DTO for creating/updating a book. Validations applied.
*/
public class CreateBookDTO {

    @NotBlank(message = "Field 'title' is required")
    @Size(min = 1, max = 200, message = "Title must be 1-200 chars")
    private String title;

    @NotBlank(message = "Field 'author' is required")
    @Size(min = 1, max = 100, message = "Author must be 1-100 chars")
    private String author;

    @NotNull(message = "Field 'publicationYear' is required")
    @Min(value = 1, message = "publicationYear must be a positive integer")
    private Integer publicationYear;

    // Getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
}
