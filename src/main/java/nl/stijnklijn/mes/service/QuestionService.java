package nl.stijnklijn.mes.service;

import nl.stijnklijn.mes.model.Question;
import nl.stijnklijn.mes.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static nl.stijnklijn.mes.constants.Constants.QUESTIONS_PER_GAME;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestions() {
        return new Random().ints(0, questionRepository.getSize())
                .distinct()
                .limit(QUESTIONS_PER_GAME)
                .mapToObj(questionRepository::getQuestionById).toList();
    }

}
