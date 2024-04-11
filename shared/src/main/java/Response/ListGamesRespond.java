package Response;

import Model.GameData;

import java.util.Collection;

public class ListGamesRespond {
    private Collection<GameData> games;

    public Collection<GameData> getGames() {
        return games;
    }

    public ListGamesRespond(Collection<GameData> games){
        this.games = games;
    }
}
