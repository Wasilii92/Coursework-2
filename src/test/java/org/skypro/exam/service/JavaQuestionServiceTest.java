package org.skypro.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.exam.model.Question;
import static org.mockito.ArgumentMatchers.any;
import org.skypro.exam.repository.QuestionRepository;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    @Mock
    private QuestionRepository repository;

    @InjectMocks
    private JavaQuestionService service;

    private Question q1, q2;

    @BeforeEach
    void setUp() {
        q1 = new Question("Q1", "A1");
        q2 = new Question("Q2", "A2");
    }

    @Test
    void add_shouldCallRepositoryAdd() {
        when(repository.add(q1)).thenReturn(q1);
        Question result = service.add(q1);
        assertThat(result).isEqualTo(q1);
        verify(repository).add(q1);
    }

    @Test
    void addWithStrings_shouldCreateQuestionAndAdd() {
        when(repository.add(any(Question.class))).thenAnswer(inv -> inv.getArgument(0));
        Question result = service.add("Q1", "A1");
        assertThat(result.getQuestion()).isEqualTo("Q1");
        assertThat(result.getAnswer()).isEqualTo("A1");
        verify(repository).add(any(Question.class));
    }

    @Test
    void remove_shouldCallRepositoryRemove() {
        when(repository.remove(q1)).thenReturn(q1);
        Question result = service.remove(q1);
        assertThat(result).isEqualTo(q1);
        verify(repository).remove(q1);
    }

    @Test
    void removeNonExistent_shouldReturnNull() {
        when(repository.remove(q1)).thenReturn(null);
        Question result = service.remove(q1);
        assertThat(result).isNull();
    }

    @Test
    void getAll_shouldReturnFromRepository() {
        when(repository.getAll()).thenReturn(Set.of(q1, q2));
        Collection<Question> all = service.getAll();
        assertThat(all).containsExactlyInAnyOrder(q1, q2);
    }

    @Test
    void getRandomQuestion_shouldReturnRandom_whenNotEmpty() {
        when(repository.getAll()).thenReturn(Set.of(q1, q2));
        Question random = service.getRandomQuestion();
        assertThat(random).isIn(q1, q2);
    }

    @Test
    void getRandomQuestion_whenEmpty_shouldReturnNull() {
        when(repository.getAll()).thenReturn(Set.of());
        assertThat(service.getRandomQuestion()).isNull();
    }
}