package nl.stijnklijn.mes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Answer {

    private Integer id;
    private String content;
    private List<String> correctAnswers;
    private Boolean correct;

}
