package ru.jdbc.project.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int id;
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min =2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
    private String name;
    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min =2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
    private String author;
    @Min(value = 1500, message = "Год издания должен быть больше 1500")
    private int yearOfProduction;

    public Book() {
    }

    public Book(String name, String author, int yearOfProduction) {
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}