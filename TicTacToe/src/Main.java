import Controllers.GameController;
import Models.*;
import Strategies.winningstrategies.ColWinningStrategy;
import Strategies.winningstrategies.DiagonalWinningStrategy;
import Strategies.winningstrategies.IWinningStrategy;
import Strategies.winningstrategies.RowWinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        try
        {
            int dimensionOfGame = 3;

            List<Player> playerList = new ArrayList<>();
            playerList.add(new Player(1l, "Meher", new Symbol('X'), PlayerType.HUMAN));
            playerList.add(new Bot(2l, "GPT", new Symbol('X'), BotDifficultyLevel.EASY));

            List<IWinningStrategy> winningStrategies = List.of(
                    new RowWinningStrategy(),
                    new ColWinningStrategy(),
                    new DiagonalWinningStrategy()
            );

            Game game = gameController.startGame(
                    dimensionOfGame,
                    playerList,
                    winningStrategies
            );

            while(gameController.checkState(game) == (GameState.IN_PROGRESS))
            {
                // 1. print board
                // 2. x's turn
                // 3. ask to makeMove
                gameController.printBoard(game);

                System.out.println("Does anyone want to undo? (y/n)");
                String undoAnswer = scanner.next();

                if(undoAnswer.equalsIgnoreCase("y"))
                    gameController.undo(game);

                gameController.makeMove(game);
            }

            System.out.println("game is finished");
            GameState state = gameController.checkState(game);

            if(state.equals(GameState.DRAW))
            {
                System.out.println("game is drawn");
            }
            else{
                System.out.println("winner is " + gameController.getWinner(game).getName());
            }
        }
        catch (Exception ex)
        {
            System.out.println("something went wrong");
        }
//        System.out.println("Game has starting");
//        Game game = gameController.startGame();
//
//        while(gameController.getGameStatus(game).equals(IN_PROGRESS))
//        {
//            gameController.printBoard(game);
//            gameController.makeNextMove(game);
//        }
//
//        if(gameController.getGameStatus(game).equals(DRAW))
//        {
//            System.out.println("Game is drawn");
//        }
//        else{
//            System.out.println("Game is won");
//        }
    }
}