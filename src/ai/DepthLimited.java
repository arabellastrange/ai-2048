package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthLimited extends AbstractPlayer {
    State initial;
    int depth = 4;
    int iterations = 64;

    Stack<State> stack = new Stack<>();
    ArrayList<State> visited = new ArrayList<>();

    AbstractState.MOVE winningMove;
    ArrayList<Integer> winningMoves = new ArrayList<>();

    double winningMoveScore;
    Evaluator ev = new BonusEvaluator();

    @Override
    public AbstractState.MOVE getMove(State game) {
        int instanceU = 0;
        int instanceD = 0;
        int instanceL = 0;
        int instanceR = 0;
        pause();
        initial = game.copy();
        stack.push(initial);

        List<AbstractState.MOVE> moves = initial.getMoves();
        winningMove = moves.get(0);
        winningMoveScore = Double.NEGATIVE_INFINITY;

        for(int i = 0; i < iterations; i ++){
            for(AbstractState.MOVE move : moves){
                initial.halfMove(move);
                double newScore = dfs();
                if(newScore > winningMoveScore){
                    winningMoveScore = newScore;
                    switch (move){
                        case UP:
                            winningMoves.add(0, instanceU++);
                            break;
                        case DOWN:
                            winningMoves.add(1,instanceD++);
                            break;
                        case LEFT:
                            winningMoves.add(2, instanceL++);
                            break;
                        case RIGHT:
                            winningMoves.add(3, instanceR++);
                            break;
                    }
                }
                initial.undo();
            }
            //reset score
            winningMoveScore = Double.NEGATIVE_INFINITY;
        }

        double best = Double.NEGATIVE_INFINITY;
        for(int i = 0; i < winningMoves.size(); i++){
            if(winningMoves.get(i) > best){
                best = winningMoves.get(i);
                switch (i){
                    case 0:
                        winningMove = AbstractState.MOVE.UP;
                        break;
                    case 1:
                        winningMove = AbstractState.MOVE.DOWN;
                        break;
                    case 2:
                        winningMove = AbstractState.MOVE.LEFT;
                        break;
                    case 3:
                        winningMove = AbstractState.MOVE.RIGHT;
                        break;
                }
            }
        }

        return winningMove; // return the most popular move in the array of winningmoves
    }

    // want to return the average value for each move - somehow - then pick best in for loop call above
    public Double dfs(){
        int currentDepth = 0;
        double parent  = 0;

        while (!stack.isEmpty()){
            if(currentDepth < depth){
                State c = stack.pop();
                parent  = ev.evaluate(c);

                visited.add(c);
                State[] children = c.nextFirstHalfMoveStates();
                double childrenScore = 0;

                for (State child : children){
                    childrenScore += ev.evaluate(child);
                    stack.push(child);
                }

                parent += (childrenScore/children.length);
                currentDepth++;

            }
        }

        return parent;
    }


}
