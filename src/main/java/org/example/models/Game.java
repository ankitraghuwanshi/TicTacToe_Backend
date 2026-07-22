package org.example.models;

import org.example.exceptions.InvalidMoveException;
import org.example.strategies.winningStrategy.ColWinningStrategy;
import org.example.strategies.winningStrategy.DiagonalWinningStrategy;
import org.example.strategies.winningStrategy.RowWinningStrategy;
import org.example.strategies.winningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private GameState gameState;
    private Player winner;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(dimension);
        this.players = players;
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.winner = null;
        this.nextPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public void makeMove() throws InvalidMoveException {
        Player currentPlayer = players.get(nextPlayerIndex);
        System.out.println("this is " + currentPlayer.getName() + "'s move");

        Move move = currentPlayer.makeMove();

        //check if move valid or not.
        if(!validateMove(move)) {
            throw new InvalidMoveException("Invalid move, please try again");
        }

        //else make move on the board.
        int row=move.getCell().getRow();
        int col=move.getCell().getCol();

        //print
        System.out.println(currentPlayer.getName() + " is making a move at " + row + ", " + col);

        //get cell
        Cell cell= board.getBoard().get(row).get(col);

        //cell filled after move
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(currentPlayer);

        //nextPlayer turn
        nextPlayerIndex=(nextPlayerIndex+1) % players.size();

        //add move to list of moves
        Move finalMove = new Move(currentPlayer, cell);
        moves.add(finalMove);

        //check if current player has won the game or not
        if(checkWinner(finalMove, board)) {
            winner = currentPlayer;
            gameState = GameState.ENDED;
        } else if (moves.size() == board.getBoard().size() * board.getBoard().size()) {
            //draw
            gameState = GameState.DRAW;
        }
    }

    private boolean checkWinner(Move move,  Board board) {
        for(WinningStrategy winningStrategy : winningStrategies) {
            if(winningStrategy.checkWinner(move,board)){
                return true;
            }
        }
        return false;
    }

    private boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if(row<0 || row>board.getDimension()-1 || col<0 || col>board.getDimension()-1){
            return false;
        }

        return board.getBoard().get(row).get(col).isEmpty();
    }

    public static class Builder {
        private int dimension;
        private List<Player> players;

        public int getDimension() {
            return dimension;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Game build(){
            //validate
            gameValidate();

            //
            List<WinningStrategy> winningStrategies=new ArrayList<>();
            winningStrategies.add(new RowWinningStrategy());
            winningStrategies.add(new ColWinningStrategy());
            winningStrategies.add(new DiagonalWinningStrategy());

            return new Game(dimension, players, winningStrategies);
        }

        public void gameValidate(){
            //number of players should be dimension-1
            validatePlayerCount();
            //players should not have same symbol
            validatePlayerSymbol();
            //only one bot is allowed per game
            validateBotCount();
        }

        //TODO
        public void validatePlayerCount(){}
        public void validatePlayerSymbol(){}
        public void validateBotCount(){}
    }
}
