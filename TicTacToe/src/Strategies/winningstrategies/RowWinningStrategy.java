package Strategies.winningstrategies;

import Models.Board;
import Models.Move;
import Models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements IWinningStrategy{
    private Map<Integer, HashMap<Symbol, Integer>> counts = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol sym = move.getPlayer().getSymbol();

        if(!counts.containsKey(row))
        {
            counts.put(row, new HashMap<>());
        }

        Map<Symbol, Integer> rowMap = counts.get(row);

        if(!rowMap.containsKey(sym))
        {
            rowMap.put(sym, 0);
        }

        rowMap.put(sym, rowMap.get(sym) + 1);

        if(rowMap.get(sym).equals(board.getDimension()))
            return true;

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol sym = move.getPlayer().getSymbol();

        Map<Symbol, Integer> rowMap = counts.get(row);

        rowMap.put(sym, rowMap.get(sym) - 1);
    }

}
