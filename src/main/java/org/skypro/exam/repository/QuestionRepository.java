package org.skypro.exam.repository;

import org.skypro.exam.model.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
}

