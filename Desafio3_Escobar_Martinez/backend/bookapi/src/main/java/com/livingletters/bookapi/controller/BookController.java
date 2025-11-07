package com.livingletters.bookapi.controller;

import com.livingletters.bookapi.dto.BookDTO;
import com.livingletters.bookapi.dto.CreateBookDTO;
import com.livingletters.bookapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
  REST controller for book management. Comments in ES/EN included.
*/
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) { this.bookService = bookService; }

    @Operation(summary = "Get all books", description = "Retrieve the list of all books.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "Get book by id", description = "Retrieve a book by its id.")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "Create a new book", description = "Create a new book using CreateBookDTO with validations.")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody CreateBookDTO dto) {
        BookDTO created = bookService.createBook(dto);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Update an existing book", description = "Update a book by id.")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @Valid @RequestBody CreateBookDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @Operation(summary = "Delete a book", description = "Delete a book by id.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search books by title", description = "Find books where title contains the given text.")
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> search(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }
}
