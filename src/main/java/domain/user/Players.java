package domain.user;

import java.util.Iterator;
import java.util.List;

public class Players implements Iterable<Player> {
    private final List<Player> players;

    public Players(final List<Player> players) {
        this.players = players;
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }
}
