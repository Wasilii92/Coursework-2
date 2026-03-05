package org.skypro.exam.service;

import org.skypro.exam.model.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
