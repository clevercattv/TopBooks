package com.clevercattv.top.book.service;

import com.clevercattv.top.book.dto.client.BookResponse;
import com.clevercattv.top.book.entity.Book;
import com.clevercattv.top.book.entity.User;
import com.clevercattv.top.book.repository.BookRepository;
import com.clevercattv.top.book.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    private final BookMapper mapper;

    public Book findBook(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(NoSuchFieldError::new); // change exception
    }

    public List<Book> findAllBooks(Long userId) {
        return repository.findAllByUserId(userId);
    }

    public Long addBook(BookResponse bookResponse, User user) {
        Book book = mapper.fromResponse(bookResponse);
        book.setUser(user);
        return repository.save(book).getId();
    }

    public List<Long> addBooks(List<BookResponse> bookResponses, User user) {
        List<Book> books = bookResponses.stream()
                .map(mapper::fromResponse)
                .peek(book -> book.setUser(user))
                .collect(Collectors.toList());

        return repository.saveAll(books)
                .stream()
                .map(Book::getId)
                .collect(Collectors.toList());
    }

    public Book updateBook(Book changedBook, Long userId) {
        Book newBook = repository.findByIdAndUserId(changedBook.getId(), userId)
                .orElseThrow(NoSuchMethodError::new); // todo change exception
        BeanUtils.copyProperties(changedBook, newBook); // mutate newBook
        repository.save(newBook);
        return newBook;
    }

    public void deleteBook(Long id, Long userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    public void deleteBooks(List<Long> ids, Long userId) {
        repository.deleteAllByIdInAndUserId(ids, userId);
    }

}
