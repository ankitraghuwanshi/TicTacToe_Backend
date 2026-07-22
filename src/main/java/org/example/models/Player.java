package org.example.models;

import java.util.Scanner;

public class Player {
    private String name;
    private Symbol symbol;
    private PlayerType playerType;
    private Scanner scanner;

    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = PlayerType.HUMAN;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Move makeMove(){

        System.out.println("please enter row number: ");
        int row = scanner.nextInt();

        System.out.println("please enter column number: ");
        int col = scanner.nextInt();

        return new Move(this, new Cell(row,col));
    }
}
