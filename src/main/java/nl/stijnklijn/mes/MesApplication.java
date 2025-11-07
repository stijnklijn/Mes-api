package nl.stijnklijn.mes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MesApplication {

    //TODO: Er is duplicate data tussen BE en FE, zoals countdown time, roundtime en startscore. FE deze info laten ophalen bij BE?
    //TODO: Records voor immutable objects gebruiken?
    //TODO: Alle input valideren.
    //TODO: CustomExceptions implementeren in plaats van RunTimeExceptions.
    //TODO: Meer vragen implementeren.
    /*
    Verwerkte afleveringen:
    2012 najaar afl 1.
    2013 voorjaar afl 1.
    2013 voorjaar afl 2.
    2013 voorjaar afl 3.
    2013 voorjaar afl 4.
    2013 voorjaar afl 5.
    2013 voorjaar afl 6.
    2013 voorjaar afl 7.
    2013 voorjaar afl 8.
     */

    public static void main(String[] args) {
        SpringApplication.run(MesApplication.class, args);
    }

}
