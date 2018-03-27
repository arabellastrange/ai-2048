package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

import java.util.ArrayList;
import java.util.Stack;

public class DepthLimited extends AbstractPlayer {
    State initial;
    int depth = 4;
    int iterations = 64;

    Stack<State> stack = new Stack<>();
    ArrayList<State> visited = new ArrayList<>();
    ArrayList<Double> predict = new ArrayList<>();

    //AbstractState.MOVE winningMove;
   // double winningMoveScore;
    Evaluator ev = new BonusEvaluator();

    @Override
    public AbstractState.MOVE getMove(State game) {
        pause();

        initial = game.copy();

        stack.push(initial);

        for(int i = 0; i < iterations; i ++){
           predict.add(dfs());
        }
        return null;
    }
    // want to return the average value for each move - somehow - then pick best in for loop call above
    public Double dfs(){
        int currentDepth = 0;
        while (!stack.isEmpty()){
            if(currentDepth < depth){
                State c = stack.pop();

                //store this + associtated move
                ev.evaluate(c);

                visited.add(c);
                State[] children = c.nextFirstHalfMoveStates();
                for (State child : children){
                    ev.evaluate(child);
                }
                currentDepth++;

            }
        }
        return 0.0; // maybe return best at each level
    }


}
