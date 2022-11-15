package ru.jdbc.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jdbc.project.DAO.BookDAO;
import ru.jdbc.project.DAO.PersonDAO;
import ru.jdbc.project.model.Book;
import ru.jdbc.project.model.Person;
import ru.jdbc.project.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
//это контроллер
@RequestMapping("/books")
//дефолтный запрос
public class BookController {
    private BookDAO bookDAO;
    private PersonDAO personDAO;
    private BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
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

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
            //если у книга у человека, показываем его
        } else {
            model.addAttribute("people", personDAO.showAll());
            //если нет, то передаем список людей, для выбора из списка
        }

        return "books/one";
    }

    @GetMapping("/new")
    /*получаем*/
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    /*добавляем на сервере*/
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult/*если будет ошибка, она поместиться сюда*/) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {//если есть ошибка будет выполнено...
            return "books/new";
        }

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
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    /*удаялем на сервере*/
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
