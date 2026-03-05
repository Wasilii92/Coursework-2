package org.skypro.exam.service;

import jakarta.annotation.PostConstruct;
import org.skypro.exam.model.Question;
import org.skypro.exam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Random;

@Service
public class MathQuestionService implements QuestionService {
    private final QuestionRepository repository;
    private final Random random = new Random();

    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        return repository.add(question);
    }

    @Override
    public Question remove(Question question) {
        return repository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return repository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> all = repository.getAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.stream().skip(index).findFirst().orElse(null);
    }
    @PostConstruct
    public void init() {
        add(new Question("2 + 2", "4"));
        add(new Question("3 * 3", "9"));
        add(new Question("10 / 2", "5"));
        add(new Question("Квадратный корень из 16", "4"));
        add(new Question("sin(0)", "0"));
    }

}