package com.livingletters.bookapi.service;

import com.livingletters.bookapi.dto.BookDTO;
import com.livingletters.bookapi.dto.CreateBookDTO;
import java.util.List;

/*
  Service interface for Book.
*/
public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO createBook(CreateBookDTO dto);
    BookDTO updateBook(Long id, CreateBookDTO dto);
    void deleteBook(Long id);
    List<BookDTO> searchByTitle(String title);
}
