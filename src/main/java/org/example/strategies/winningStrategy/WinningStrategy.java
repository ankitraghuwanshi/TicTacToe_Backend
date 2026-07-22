package org.example.strategies.winningStrategy;

import org.example.models.Board;
import org.example.models.Move;
import org.example.models.Player;

public interface WinningStrategy {
    boolean checkWinner(Move move, Board board);
}
