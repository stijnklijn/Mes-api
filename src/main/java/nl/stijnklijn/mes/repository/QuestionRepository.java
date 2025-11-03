package nl.stijnklijn.mes.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stijnklijn.mes.model.Question;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static nl.stijnklijn.mes.constants.Constants.QUESTIONS_FILE;

@Repository
public class QuestionRepository {

    private final List<Question> questions;

    public QuestionRepository(ObjectMapper objectMapper) {
        this.questions = loadQuestions(objectMapper);
    }

    private List<Question> loadQuestions(ObjectMapper objectMapper) {
        try (InputStream inputStream = new ClassPathResource(QUESTIONS_FILE).getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Question getQuestionById(Integer id) {
        return questions.get(id);
    }

    public int getSize() {
        return questions.size();
    }

}
