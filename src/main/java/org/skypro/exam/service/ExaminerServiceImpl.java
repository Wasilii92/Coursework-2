package org.skypro.exam.service;

import org.skypro.exam.exception.BadRequestException;
import org.skypro.exam.model.Question;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final List<QuestionService> questionServices;

    public ExaminerServiceImpl(List<QuestionService> questionServices) {
        this.questionServices = questionServices;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount < 0) {
            throw new BadRequestException("Количество вопросов не может быть отрицательным");
        }

        Set<Question> uniqueQuestions = new LinkedHashSet<>();
        for (QuestionService service : questionServices) {
            uniqueQuestions.addAll(service.getAll());
        }

        if (amount > uniqueQuestions.size()) {
            throw new BadRequestException("Запрошено больше вопросов, чем доступно. Доступно уникальных: " + uniqueQuestions.size());
        }

        List<Question> shuffled = new ArrayList<>(uniqueQuestions);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, amount);
    }
}