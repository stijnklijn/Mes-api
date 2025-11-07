package nl.stijnklijn.mes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Question(Integer id, String content,
                       @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) List<String> correctAnswers,
                       Boolean strict) {}
