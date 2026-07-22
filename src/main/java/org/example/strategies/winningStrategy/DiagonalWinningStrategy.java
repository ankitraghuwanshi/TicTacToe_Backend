package org.example.strategies.winningStrategy;

import org.example.models.Board;
import org.example.models.Move;
import org.example.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy {
    private Map<Symbol, Integer> leftMap=new HashMap<>();
    private Map<Symbol, Integer> rightMap=new HashMap<>();

    @Override
    public boolean checkWinner(Move move, Board board) {
        int row=move.getCell().getRow();
        int col=move.getCell().getCol();
        Symbol symbol=move.getPlayer().getSymbol();

        if(row==col){ //[[0,0],[1,1],[2,2]] for 3x3
            if(!leftMap.containsKey(symbol)){
                leftMap.put(symbol,1);
            }else{
                leftMap.put(symbol,leftMap.get(symbol)+1);
            }
            return leftMap.get(symbol)==board.getDimension();
        }
        else if(row+col==board.getDimension()-1){ //[[2,0],[1,1],[0,2]] for 3x3
            if(!rightMap.containsKey(symbol)){
                rightMap.put(symbol,1);
            }else{
                rightMap.put(symbol,rightMap.get(symbol)+1);
            }
            return rightMap.get(symbol)==board.getDimension();
        }

        return false;
    }
}
