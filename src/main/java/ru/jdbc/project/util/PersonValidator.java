package ru.jdbc.project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jdbc.project.DAO.PersonDAO;
import ru.jdbc.project.model.Person;

@Component
public class PersonValidator implements Validator {//более сложная валидация
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDAO.getPersonByName(person.getName()).isPresent()) {
            // поле, код ошибки, сообщение ошибки
            errors.rejectValue("name", "", "Человек с таким именем уже существует");
        }

        // Проверяем, что у человека имя начинается с заглавной буквы
        // Если имя не начинается с заглавной буквы - выдаем ошибку
        if (!Character.isUpperCase(person.getName().codePointAt(0)))
            errors.rejectValue("name", "", "Имя должно начинаться с заглавной буквы");
    }
}
