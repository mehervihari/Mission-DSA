package Strategies.botplayingstrategies;

import Models.Board;
import Models.Move;

public interface IBotPlayingStrategy {
    public Move makeMove(Board board);
}
