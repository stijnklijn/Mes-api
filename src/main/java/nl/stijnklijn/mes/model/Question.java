package nl.stijnklijn.mes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Question {

    private Integer id;
    private String content;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> correctAnswers;
    private Boolean strict;

}
