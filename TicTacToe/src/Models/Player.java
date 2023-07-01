package Models;

import java.util.Scanner;

public class Player {
    private Scanner scanner;
    private Symbol symbol;
    private String name;
    private Long id;
    private PlayerType playerType;

    public Player(Long id, String name, Symbol symbol, PlayerType type) {
        this.id = id;
        this.playerType = type;
        this.name = name;
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }

    public Move makeMove(Board board)
    {
        System.out.println("please tell in which row you want to make a move starting from 0");
        int row = scanner.nextInt();
        System.out.println("please tell in which col you want to make a move starting from 0");
        int col = scanner.nextInt();

        return new Move(new Cell(row, col), this);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType){
        this.playerType = playerType;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
