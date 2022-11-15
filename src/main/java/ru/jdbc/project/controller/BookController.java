package ru.jdbc.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.jdbc.project.DAO.BookDAO;
import ru.jdbc.project.DAO.PersonDAO;
import ru.jdbc.project.model.Book;
import ru.jdbc.project.model.Person;

@Controller
//это контроллер
@RequestMapping("/books")
//дефолтный запрос
public class BookController {
    private BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    /*получаем по какому запросу, сейчас по дефолтному*/
    public String showAll(Model model) {
        model.addAttribute("books"/*как обратиться на странице отображения*/,
                bookDAO.showAll())/*что получим при обращении*/;
        return "books/all";
        /*на какую страницу переход*/
    }

    @GetMapping("/{id}")
    /*получаем по какому запросу, сейчас по id*/
    public String showOne(@PathVariable("id") /*обращение может меняться*/ int id, Model model,
                          @ModelAttribute("person"/*какую переменную внедрить на страницу отобраения*/)
                          Person person /*что внедряем*/) {
        model.addAttribute("book", bookDAO.showOne(id));

        return "books/one";
    }

    @GetMapping("/new")
    /*получаем*/
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    /*добавляем на сервере*/
    public String create(@ModelAttribute("book")  Book book) {

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.showOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    /*изменяем на сервере*/
    public String updateBook(@ModelAttribute("book") Book book, @PathVariable("id") int id) {

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    /*удаялем на сервере*/
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
