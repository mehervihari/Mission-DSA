package Strategies.botplayingstrategies;

import Models.Board;
import Models.Cell;
import Models.CellState;
import Models.Move;

import java.util.List;

public class EasyBotPlayingStrategy implements IBotPlayingStrategy{

    @Override
    public Move makeMove(Board board) {
        for(List<Cell> row: board.getBoard())
        {
            for(Cell cell: row)
            {
                if(cell.getCellState().equals(CellState.EMPTY))
                {
                    return new Move(
                            cell,
                            null
                    );
                }
            }
        }
        return null;
    }
}
