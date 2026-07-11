package org.example.controllers;

import org.example.models.Game;
import org.example.models.GameState;
import org.example.models.Player;

import java.util.List;

public class GameController {
    //Players will be interacting with GameController to make any operation
    //startgame(), makeMove(), getGameState(), printBoard(), getWinner()

    public Game startGame(int dimension, List<Player> players) {
        return Game.getBuilder()
                   .setDimension(dimension)
                   .setPlayers(players)
                   .build();
    }

    public void makeMove(Game game) {
        return;
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }
    public void printBoard(Game game) {

    }
    public Player getWinner(Game game) {
        return game.getWinner();
    }
}
