package org.skypro.exam.service;

import jakarta.annotation.PostConstruct;
import org.skypro.exam.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;

    @Service
    public class JavaQuestionService implements QuestionService {
        private final Set<Question> questions=new HashSet<>();
        private final Random random = new Random();


        @Override
        public Question add(String question, String answer) {
            Question q = new Question(question, answer);
            return add(q);
        }

        @Override
        public Question add(Question question) {
            questions.add(question);
            return question;
        }

        @Override
        public Question remove(Question question) {
            if (questions.remove(question)) {
                return question;
            }
            return null;
        }

        @Override
        public Collection<Question> getAll() {
            return Collections.unmodifiableSet(questions);
        }

        @Override
        public Question getRandomQuestion() {
            if (questions.isEmpty()) {
                return null;
            }
            int index = random.nextInt(questions.size());
            // преобразуем Set в массив или список для доступа по индексу
            return questions.stream().skip(index).findFirst().orElse(null);
        }
        @PostConstruct
        public void init() {
            add("Что такое Java?", "Язык программирования");
            add("Что такое JVM?", "Виртуальная машина Java");
            add("Что такое полиморфизм?", "Способность объекта принимать разные формы");
            add("Что такое наследование?", "Механизм создания нового класса на основе существующего");
            add("Что такое инкапсуляция?", "Сокрытие деталей реализации и защита данных");
        }

    }

