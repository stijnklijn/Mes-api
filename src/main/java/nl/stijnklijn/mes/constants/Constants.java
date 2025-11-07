package nl.stijnklijn.mes.constants;

public class Constants {

    public static final Integer PLAYERS_PER_GAME = 2;
    public static final Integer QUESTIONS_PER_ROUND = 4;
    public static final Integer MAX_ROUNDS = 4;
    public static final Integer QUESTIONS_PER_GAME = QUESTIONS_PER_ROUND * MAX_ROUNDS;
    public static final Integer START_SCORE = 750;
    public static final Integer INITIAL_STAKE = 10;
    public static final Integer COUNT_DOWN = 10;
    public static final Integer ROUND_TIME = 60;
    public static final Double SIMILARITY_CUTOFF = 0.85;

    public static final String QUESTIONS_FILE = "/static/questions.json";

}
