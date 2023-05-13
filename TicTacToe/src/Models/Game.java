package Models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMismatchException;
import winningstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;

    public static class Builder
    {
        public List<Player> players;
        public List<WinningStrategy> winningStrategies;
        public int dimension;

        private Builder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
            this.dimension = 0;
        }

        public Builder setPlayers(List<Player> players){
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies){
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension){
            this.dimension = dimension;
            return this;
        }

        private boolean validate()
        {
            int botCount = 0;

            for(Player player : players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount += 1;
                }
            }

            if(botCount > 1){
                throw new MoreThanOneBotException();
            }

            if(players.size() != dimension - 1){
                throw new PlayersCountDimensionMismatchException();
            }

            Map<Symbol, Integer> symbolCounts = new HashMap<>();

            for(Player player : players)
            {
                if(!symbolCounts.containsKey(player.getSymbol().getaChar()))
                {
                    symbolCounts.put(player.getSymbol().getaChar(), 0);
                }

                symbolCounts.put(
                        player.getSymbol().getaChar(),
                        symbolCounts.get(player.getSymbol().getaChar()) + 1
                );

                if(symbolCounts.get(player.getSymbol().getaChar()) > 1){
                    throw new DuplicateSymbolException();
                }
            }
            return true;
        }
    }
}
