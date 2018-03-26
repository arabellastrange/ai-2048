package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

import java.util.List;

public class MinMax extends AbstractPlayer {
    State initial;
    int depthLimit = 5;
    AbstractState.MOVE winningMove;
    double winningMoveScore;
    Evaluator ev = new BonusEvaluator(); // make average of all evaluators

    @Override
    public AbstractState.MOVE getMove(State game) {
        pause();

        initial = game.copy();

        List<AbstractState.MOVE> moves = initial.getMoves();
        winningMove = moves.get(0);
        winningMoveScore = Double.NEGATIVE_INFINITY;

        for(AbstractState.MOVE m : moves){
            initial.halfMove(m);
            double newScore = minmax(initial, depthLimit, false);
            if(newScore > winningMoveScore){
                winningMoveScore = newScore;
                winningMove = m;
            }
            initial.undo();
        }

        return winningMove;
    }

    public double minmax(State node, int depth, boolean maxP){
        double best;
        if(depth == 0){ // or is game over  - how to find out if game is over - if number of legal moves is 0
            best = ev.evaluate(node);
        }else {
            if(maxP){
                best = Double.NEGATIVE_INFINITY;
                for(State child : node.nextFirstHalfMoveStates()){
                    double currentValue = minmax(child, depth -1, false);
                    best = Math.max(best, currentValue);
                }

            }else{
                best = Double.POSITIVE_INFINITY;
                for(State child : node.nextSecondHalfMoveStates()){
                    double currentValue = minmax(child, depth -1, true);
                    best = Math.min(best, currentValue);
                }

            }
        }

        return  best;
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
