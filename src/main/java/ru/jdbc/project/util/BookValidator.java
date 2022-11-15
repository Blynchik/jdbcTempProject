package ru.jdbc.project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jdbc.project.DAO.BookDAO;
import ru.jdbc.project.model.Book;


@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        // Проверяем, что у человека имя начинается с заглавной буквы
        // Если имя не начинается с заглавной буквы - выдаем ошибку
        if (!Character.isUpperCase(book.getTitle().codePointAt(0)))
            errors.rejectValue("title", "", "Название книги должно начинаться с заглавной буквы");

        if (!Character.isUpperCase(book.getAuthor().codePointAt(0)))
            errors.rejectValue("author", "", "Имя автора должно начинаться с заглавной буквы");
    }
}
