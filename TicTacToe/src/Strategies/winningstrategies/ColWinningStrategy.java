package Strategies.winningstrategies;

import Models.Board;
import Models.Move;
import Models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements IWinningStrategy{
    private Map<Integer, HashMap<Symbol, Integer>> counts = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol sym = move.getPlayer().getSymbol();
        // | 0 | -> {X -> 0, 0 -> 0}
        // | 1 | -> {X -> 1, 0 -> 2}
        // | 2 | -> {X -> 2, 0 -> 1}
        if(!counts.containsKey(col))
        {
            counts.put(move.getCell().getRow(), new HashMap<>());
        }

        Map<Symbol, Integer> colMap = counts.get(col);

        if(!colMap.containsKey(sym))
        {
            colMap.put(sym, 0);
        }

        colMap.put(sym, colMap.get(sym) + 1);

        if(colMap.get(sym).equals(board.getDimension()))
            return true;

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol sym = move.getPlayer().getSymbol();

        Map<Symbol, Integer> colMap = counts.get(col);

        colMap.put(sym, colMap.get(sym) - 1);
    }
}
