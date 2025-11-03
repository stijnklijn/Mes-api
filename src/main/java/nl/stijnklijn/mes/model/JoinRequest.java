package nl.stijnklijn.mes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinRequest {

    private String gameId;
    private String name;

}
