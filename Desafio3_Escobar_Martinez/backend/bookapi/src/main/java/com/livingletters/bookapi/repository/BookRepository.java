package com.livingletters.bookapi.repository;


import com.livingletters.bookapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
  Repository for Book with search-by-title method.
*/
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
}