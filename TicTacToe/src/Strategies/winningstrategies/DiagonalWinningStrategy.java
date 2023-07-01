package Strategies.winningstrategies;

import Models.Board;
import Models.Move;
import Models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements IWinningStrategy{
    private Map<Symbol, Integer> leftDiagCounts = new HashMap<>();
    private Map<Symbol, Integer> rightDiagCounts = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol sym = move.getPlayer().getSymbol();

        if(row == col)
        {
            if(!leftDiagCounts.containsKey(sym))
            {
                leftDiagCounts.put(sym, 0);
            }
            leftDiagCounts.put(sym, leftDiagCounts.get(sym) + 1);

            if(leftDiagCounts.get(sym).equals(board.getDimension()))
                return true;
        }

        if(row + col == board.getDimension() - 1)
        {
            if(!rightDiagCounts.containsKey(sym))
            {
                rightDiagCounts.put(sym, 0);
            }
            rightDiagCounts.put(sym, rightDiagCounts.get(sym) + 1);

            if(rightDiagCounts.get(sym).equals(board.getDimension()))
                return true;
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol sym = move.getPlayer().getSymbol();

        if(row == col)
        {
            leftDiagCounts.put(sym, leftDiagCounts.get(sym) - 1);
        }

        if(row + col == board.getDimension() - 1)
        {
            rightDiagCounts.put(sym, rightDiagCounts.get(sym) - 1);
        }
    }
}
