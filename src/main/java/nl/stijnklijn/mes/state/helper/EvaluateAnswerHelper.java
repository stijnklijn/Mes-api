package nl.stijnklijn.mes.state.helper;

import nl.stijnklijn.mes.model.Answer;
import nl.stijnklijn.mes.model.Question;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.text.Normalizer;

import static nl.stijnklijn.mes.constants.Constants.SIMILARITY_CUTOFF;

public class EvaluateAnswerHelper {

    public void evaluateAnswer(Question question, Answer playerAnswer) {
        playerAnswer.setCorrectAnswers(question.getCorrectAnswers());
        playerAnswer.setCorrect(false);

        for (String correctAnswer : question.getCorrectAnswers()) {
            if (isCorrect(question, correctAnswer, playerAnswer.getContent())) {
                playerAnswer.setCorrect(true);
            }
        }
    }

    private boolean isCorrect(Question question, String correctAnswer, String playerAnswer) {
        if (question.getStrict()) {
            return correctAnswer.equalsIgnoreCase(playerAnswer);
        } else {
            return similarity(normalize(correctAnswer), normalize(playerAnswer)) >= SIMILARITY_CUTOFF;
        }
    }

    private String normalize(String answer) {
        answer = removeCharacters(answer);
        answer = removeArticles(answer);
        answer = removeSpaces(answer);
        return answer;
    }

    private String removeCharacters(String answer) {
        answer = answer.trim().toLowerCase();
        answer = answer.replaceAll("[^\\p{L}\\p{Nd} ]", ""); //remove punctuation marks
        answer = answer.replaceAll("\\s+", " "); //replace multiple spaces with single space
        answer = Normalizer.normalize(answer, Normalizer.Form.NFD).replaceAll("\\p{M}", ""); //remove accents
        return answer;
    }

    private String removeArticles(String answer) {
        String[] articles = {"de", "het", "een"};
        for (String article : articles) {
            answer = answer.replaceAll("\\b" + article + "\\b", "");
        }
        return answer;
    }

    private String removeSpaces(String answer) {
        return answer.trim().replaceAll("\\s", "");
    }

    private double similarity(String correctAnswer, String playerAnswer) {
        int dist = LevenshteinDistance.getDefaultInstance().apply(correctAnswer, playerAnswer);
        int maxLen = Math.max(correctAnswer.length(), playerAnswer.length());
        return 1.0 - (double) dist / maxLen;
    }

}
