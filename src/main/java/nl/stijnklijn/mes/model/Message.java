package nl.stijnklijn.mes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.stijnklijn.mes.enums.MessageType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private MessageType type;
    private Object payload;

}