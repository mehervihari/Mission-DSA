package Models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMismatchException;
import Strategies.winningstrategies.IWinningStrategy;

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
    private List<IWinningStrategy> winningStrategies;
    public static Builder getBuilder(){
        return new Builder();
    }

    private Game(int dimension, List<Player> players, List<IWinningStrategy> winningStrategies)
    {
        this.players = players;
        this.moves = new ArrayList<>();
        this.board = new Board(dimension);
        this.nextPlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.winningStrategies = winningStrategies;
    }
    public static class Builder
    {
        public List<Player> players;
        public List<IWinningStrategy> winningStrategies;
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

        public Builder setWinningStrategies(List<IWinningStrategy> winningStrategies){
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension){
            this.dimension = dimension;
            return this;
        }

        public Builder addPlayer(Player player){
            this.players.add(player);
            return this;
        }

        public Builder addWinningStategy(IWinningStrategy winningStrategy){
            this.winningStrategies.add(winningStrategy);
            return this;
        }

        private boolean validate() throws DuplicateSymbolException, PlayersCountDimensionMismatchException, MoreThanOneBotException
        {
            validateBotCount();
            validateDimensionAndPlayersCount();
            validateUniqueSymbolsForPlayer();
            return true;
        }

        private boolean validateBotCount() throws MoreThanOneBotException {
            int botCount = 0;

            for(Player player : players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount += 1;
                }
            }

            if(botCount > 1){
                throw new MoreThanOneBotException();
            }
            return true;
        }

        private boolean validateDimensionAndPlayersCount() throws PlayersCountDimensionMismatchException {
            if(players.size() != dimension - 1){
                throw new PlayersCountDimensionMismatchException();
            }
            return true;
        }

        private boolean validateUniqueSymbolsForPlayer() throws DuplicateSymbolException {
            Map<Character, Integer> symbolCounts = new HashMap<>();

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

        public Game build() throws DuplicateSymbolException, PlayersCountDimensionMismatchException, MoreThanOneBotException {
            try{
                validate();
            }
            catch (Exception ex){
                throw ex;
            }
            return new Game(dimension, players, winningStrategies);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void printBoard()
    {
        board.printBoard();
    }

    public boolean checkWinner(Board board, Move move)
    {
        for(IWinningStrategy winningStrategy: winningStrategies)
        {
            if(winningStrategy.checkWinner(board, move))
            {
                return true;
            }
        }
        return false;
    }

    public void makeMove()
    {
        Player currentMovePlayer = players.get(nextPlayerIndex);

        System.out.println("It is " + currentMovePlayer.getName() + "'s turn. Please make your move.");

        Move move = currentMovePlayer.makeMove(board);

        System.out.println(currentMovePlayer.getName() + " has made a move at row: " + move.getCell().getRow()
                + " column: " + move.getCell().getCol() + ".");

        if(!validateMove(move))
        {
            System.out.println("Invalid Move. Please try again.");
            return;
        }

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);

        Move finalMoveObject = new Move(cellToChange, currentMovePlayer);
        moves.add(finalMoveObject);

        nextPlayerIndex += 1;
        nextPlayerIndex %= players.size();

        if(checkWinner(board, finalMoveObject)){
            gameState = GameState.WIN;
            winner = finalMoveObject.getPlayer();
        }
        else if(moves.size() == this.board.getDimension() * this.board.getDimension()){
            gameState = GameState.DRAW;
        }
    }
    private boolean validateMove(Move move)
    {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if(row >= board.getDimension() || col >= board.getDimension())
        {
            return false;
        }

        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY))
            return true;

        return false;
    }

    public Player getWinner()
    {
        return winner;
    }

    public void setWinner(Player winner)
    {
        this.winner = winner;
    }

    public void undo(){
        if(moves.size() == 0)
        {
            System.out.println("no move has been made so far to undo");
            return;
        }

        Move lastMove = moves.get(moves.size() - 1);

        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);

        for(IWinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(board, lastMove);
        }

        nextPlayerIndex -= 1;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();
    }
}
