package Controllers;

import Models.Game;
import Models.GameState;
import Models.Player;
import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMismatchException;
import Strategies.winningstrategies.IWinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int dimensionOfBoard, List<Player> players,
                                            List<IWinningStrategy> winningStrategies)
            throws DuplicateSymbolException, PlayersCountDimensionMismatchException, MoreThanOneBotException
    {
        return Game.getBuilder()
                .setDimension(dimensionOfBoard)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public Player getWinner(Game game){
        return game.getWinner();
    }

    public void printBoard(Game game){
        game.printBoard();
    }

    public void makeMove(Game game)
    {
        game.makeMove();
    }

    public void undo(Game game){
        game.undo();
    }
}
