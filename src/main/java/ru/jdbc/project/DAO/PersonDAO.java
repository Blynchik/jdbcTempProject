package ru.jdbc.project.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.jdbc.project.model.Book;
import ru.jdbc.project.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAll(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showOne(int id){
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("insert into person (name, year_of_birth) values (?, ?)", person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("update person set name=?, year_of_birth=? where id=?", person.getName(), person.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("select * from person where name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}