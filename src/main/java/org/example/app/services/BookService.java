package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        if(!book.getAuthor().isEmpty() || !book.getTitle().isEmpty() || book.getSize() != null)
            bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByRegex(String queryRegex) {
        boolean removed = false;
        Pattern pattern = Pattern.compile(queryRegex);

        for(Book book: bookRepo.retreiveAll()) {
            if(pattern.matcher(bookToString(book)).find())
                removed = bookRepo.removeItemById(book.getId());
        }

        return removed;
    }

    private String bookToString(Book book) {
        return String.format("%s;%s;%d", book.getAuthor(), book.getTitle(), book.getSize());
    }
}
