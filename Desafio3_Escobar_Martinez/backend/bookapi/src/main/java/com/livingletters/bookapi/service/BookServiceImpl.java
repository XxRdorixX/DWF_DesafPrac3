package com.livingletters.bookapi.service;

import com.livingletters.bookapi.dto.BookDTO;
import com.livingletters.bookapi.dto.CreateBookDTO;
import com.livingletters.bookapi.model.Book;
import com.livingletters.bookapi.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
  Implementation of Book service with manual mapping Entity <-> DTO.
*/
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book b = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return toDTO(b);
    }

    @Override
    public BookDTO createBook(CreateBookDTO dto) {
        Book b = new Book(dto.getTitle(), dto.getAuthor(), dto.getPublicationYear());
        Book saved = bookRepository.save(b);
        return toDTO(saved);
    }

    @Override
    public BookDTO updateBook(Long id, CreateBookDTO dto) {
        Book updated = bookRepository.findById(id).map(existing -> {
            existing.setTitle(dto.getTitle());
            existing.setAuthor(dto.getAuthor());
            existing.setPublicationYear(dto.getPublicationYear());
            return bookRepository.save(existing);
        }).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return toDTO(updated);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) throw new EntityNotFoundException("Book not found with id: " + id);
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Helper mapping methods
    private BookDTO toDTO(Book b) {
        BookDTO dto = new BookDTO();
        dto.setId(b.getId());
        dto.setTitle(b.getTitle());
        dto.setAuthor(b.getAuthor());
        dto.setPublicationYear(b.getPublicationYear());
        return dto;
    }
}
