package Strategies.winningstrategies;

import Models.Board;
import Models.Move;

public interface IWinningStrategy {
    public boolean checkWinner(Board board, Move move);

    public void handleUndo(Board board, Move move);
}
