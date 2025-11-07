package nl.stijnklijn.mes.model;

import nl.stijnklijn.mes.enums.MessageType;

public record Message(MessageType type, Object payload) {}
