package nl.stijnklijn.mes.model;

import nl.stijnklijn.mes.constants.Constants;

public record SharedConstants(String GAME_ID_HEADER, String CREATE_GAME_PATH, String CAN_JOIN_PATH,
                              String WEBSOCKET_SUBSCRIBE_BASE_PATH, String WEBSOCKET_PUBLISH_BASE_PATH,
                              String JOIN_GAME_PATH, String SUBMIT_ANSWERS_PATH, String SUBMIT_BID_PATH,
                              String CHAT_PATH, String HEARTBEAT_PATH, Integer HEARTBEAT_INTERVAL, Integer MAX_ROUNDS,
                              Integer COUNT_DOWN, Integer ROUND_TIME, Integer MAX_INFO_MESSAGES,
                              Integer MAX_CHAT_MESSAGE_LENGTH) {

    public SharedConstants() {
        this(Constants.GAME_ID_HEADER, Constants.CREATE_GAME_PATH, Constants.CAN_JOIN_PATH,
                Constants.WEBSOCKET_SUBSCRIBE_BASE_PATH, Constants.WEBSOCKET_PUBLISH_BASE_PATH,
                Constants.JOIN_GAME_PATH, Constants.SUBMIT_ANSWERS_PATH, Constants.SUBMIT_BID_PATH, Constants.CHAT_PATH,
                Constants.HEARTBEAT_PATH, Constants.HEARTBEAT_INTERVAL, Constants.MAX_ROUNDS, Constants.COUNT_DOWN,
                Constants.ROUND_TIME, Constants.MAX_INFO_MESSAGES, Constants.MAX_CHAT_MESSAGE_LENGTH);
    }

}
