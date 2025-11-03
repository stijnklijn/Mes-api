package nl.stijnklijn.mes.state;

import nl.stijnklijn.mes.context.GameContext;
import nl.stijnklijn.mes.event.Event;

public interface State<T extends Event> {

    State<?> handle(T event, GameContext ctx);

}
