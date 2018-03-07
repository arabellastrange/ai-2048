package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

import java.util.HashMap;
import java.util.List;

public class MinMax extends AbstractPlayer {
    int depthLimit = 6;
    double best;
    HashMap< State, Integer> stateMove = new HashMap<>();
    State winningState;
    Evaluator ev = new BonusEvaluator(); // make average of all evaluators

    @Override
    public AbstractState.MOVE getMove(State game) {
        pause();

        List<AbstractState.MOVE> moves = game.getMoves();

        minmax(game, depthLimit, true);

        return moves.get(stateMove.get(winningState));
    }

    public Double minmax(State node, int depth, boolean maxP){
        if(depth == 0){ // or is game over  - how to find out if game is over
            return best = ev.evaluate(node);

        }else {
            if(maxP){
                best = Double.NEGATIVE_INFINITY;

                State[] next = node.nextFirstHalfMoveStates();
                for(int i  = 0; i < next.length; i ++){
                    stateMove.put(next[i], i);
                }

                for(State child : node.nextSecondHalfMoveStates()){
                    double currentValue = minmax(child, depth -1, false);
                    best = Math.max(best, currentValue);
                    if(best < currentValue){
                       winningState = child;
                    }
                    return best;
                }

            }else{
                best = Double.POSITIVE_INFINITY;

                State[] next = node.nextFirstHalfMoveStates();
                for(int i  = 0; i < next.length; i ++){
                    stateMove.put(next[i], i);
                }

                for(State child : node.nextSecondHalfMoveStates()){
                    double currentValue = minmax(child, depth -1, true);
                    best = Math.min(best, currentValue);
                    if(best > currentValue){
                        winningState = child;
                    }
                    return best;
                }

            }
        }

        return  0.0;
    }

    /*
    01 function minimax(node, depth, maximizingPlayer)
    02     if depth = 0 or node is a terminal node
    03         return the heuristic value of node

    04     if maximizingPlayer
    05         bestValue := −∞
    06         for each child of node
    07             v := minimax(child, depth − 1, FALSE)
    08             bestValue := max(bestValue, v)
    09         return bestValue

    10     else    (* minimizing player *)
    11         bestValue := +∞
    12         for each child of node
    13             v := minimax(child, depth − 1, TRUE)
    14             bestValue := min(bestValue, v)
    15         return bestValue


    (* Initial call for maximizing player *)

    minimax(origin, depth, TRUE)
    */
}
