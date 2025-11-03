package nl.stijnklijn.mes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Answer {

    private Integer id;
    private String content;
    private Set<String> correctAnswers;
    private Boolean correct;

}
