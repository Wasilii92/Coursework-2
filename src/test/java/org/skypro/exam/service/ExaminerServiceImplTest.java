package org.skypro.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.exam.exception.BadRequestException;
import org.skypro.exam.model.Question;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService javaService;

    @Mock
    private QuestionService mathService;

    private ExaminerServiceImpl examinerService;

    private Question qJava1, qJava2, qMath1, qMath2;

    @BeforeEach
    void setUp() {
        // Создаём тестовый объект вручную, передавая список моков
        examinerService = new ExaminerServiceImpl(List.of(javaService, mathService));

        qJava1 = new Question("Java Q1", "A1");
        qJava2 = new Question("Java Q2", "A2");
        qMath1 = new Question("Math Q1", "A1");
        qMath2 = new Question("Math Q2", "A2");
    }

    @Test
    void getQuestions_shouldReturnUniqueQuestionsFromBothServices() {
        when(javaService.getAll()).thenReturn(Set.of(qJava1, qJava2));
        when(mathService.getAll()).thenReturn(Set.of(qMath1, qMath2));

        Collection<Question> result = examinerService.getQuestions(3);

        assertThat(result).hasSize(3);
        Set<Question> all = Set.of(qJava1, qJava2, qMath1, qMath2);
        assertThat(result).allMatch(all::contains);
    }

    @Test
    void getQuestions_whenAmountGreaterThanUniqueAvailable_shouldThrow() {
        when(javaService.getAll()).thenReturn(Set.of(qJava1));
        when(mathService.getAll()).thenReturn(Set.of(qMath1));

        assertThatThrownBy(() -> examinerService.getQuestions(3))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("больше вопросов, чем доступно");
    }

    @Test
    void getQuestions_whenAmountZero_shouldReturnEmpty() {
        when(javaService.getAll()).thenReturn(Set.of(qJava1));
        when(mathService.getAll()).thenReturn(Set.of(qMath1));

        Collection<Question> result = examinerService.getQuestions(0);
        assertThat(result).isEmpty();
    }

    @Test
    void getQuestions_whenAmountNegative_shouldThrow() {
        assertThatThrownBy(() -> examinerService.getQuestions(-1))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("не может быть отрицательным");
    }

    @Test
    void getQuestions_shouldReturnEmpty_whenAllServicesEmptyAndAmountZero() {
        when(javaService.getAll()).thenReturn(Set.of());
        when(mathService.getAll()).thenReturn(Set.of());

        Collection<Question> result = examinerService.getQuestions(0);
        assertThat(result).isEmpty();
    }

    @Test
    void getQuestions_shouldThrow_whenAllServicesEmptyAndAmountPositive() {
        when(javaService.getAll()).thenReturn(Set.of());
        when(mathService.getAll()).thenReturn(Set.of());

        assertThatThrownBy(() -> examinerService.getQuestions(1))
                .isInstanceOf(BadRequestException.class);
    }
}