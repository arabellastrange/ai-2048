package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

public class ExpectiMax extends AbstractPlayer {
    State initial;
    int depthLimit = 5;
    AbstractState.MOVE winningMove;
    double winningMoveScore;
    Evaluator ev = new BonusEvaluator();

    @Override
    public AbstractState.MOVE getMove(State game) {

        return null;
    }

    public double expectimax(State node, int depth, boolean ourTurn){
        double best;

        if(depth == 0 || node.getMoves().isEmpty()){
            return ev.evaluate(node);
        }

        return 0.0;
    }
}

/*
 function expectiminimax(node, depth)
    if node is a terminal node or depth = 0
        return the heuristic value of node
    if the adversary is to play at node
        // Return value of minimum-valued child node
        let α := +∞
        foreach child of node
            α := min(α, expectiminimax(child, depth-1))
    else if we are to play at node
        // Return value of maximum-valued child node
        let α := -∞
        foreach child of node
            α := max(α, expectiminimax(child, depth-1))
    else if random event at node
        // Return weighted average of all child nodes' values
        let α := 0
        foreach child of node
            α := α + (Probability[child] * expectiminimax(child, depth-1))
    return α
    */
