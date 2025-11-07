package nl.stijnklijn.mes.constants;

public class Constants {

    //Internal
    public static final String QUESTIONS_FILE = "/static/questions.json";
    public static final String PLAYER_ID_QUERY_STRING = "playerId";
    public static final Integer GAME_ID_START_INDEX = 9;
    public static final Integer GAME_ID_END_INDEX = 13;
    public static final Integer PLAYERS_PER_GAME = 2;
    public static final Integer QUESTIONS_PER_ROUND = 4;
    public static final Integer START_SCORE = 750;
    public static final Integer INITIAL_STAKE = 10;
    public static final Double SIMILARITY_CUTOFF = 0.85;

    //Shared
    public static final String GAME_ID_HEADER = "Game-Id";
    public static final String CREATE_GAME_PATH = "/create-game";
    public static final String CAN_JOIN_PATH = "/can-join";
    public static final String WEBSOCKET_SUBSCRIBE_BASE_PATH = "/subscribe";
    public static final String WEBSOCKET_PUBLISH_BASE_PATH = "/publish";
    public static final String JOIN_GAME_PATH = "/join-game";
    public static final String SUBMIT_ANSWERS_PATH = "/submit-answers";
    public static final String SUBMIT_BID_PATH = "/submit-bid";
    public static final String CHAT_PATH = "/chat";
    public static final String HEARTBEAT_PATH = "/heartbeat";
    public static final Integer HEARTBEAT_INTERVAL = 30;
    public static final Integer MAX_ROUNDS = 4;
    public static final Integer COUNT_DOWN = 5;
    public static final Integer ROUND_TIME = 10;
    public static final Integer MAX_INFO_MESSAGES = 500;
    public static final Integer MAX_CHAT_MESSAGE_LENGTH = 256;

    //Computed
    public static final Integer QUESTIONS_PER_GAME = QUESTIONS_PER_ROUND * MAX_ROUNDS;

}
