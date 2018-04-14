package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class DepthLimited extends AbstractPlayer {
    State initial;
    int depth = 5;
    int iterations = 64;

    Stack<State> stack = new Stack<>();
    HashSet<State> visited = new HashSet<>();

    AbstractState.MOVE winningMove;
    int[] winningMoves = new int[4];
    double winningMoveScore;

    Evaluator ev = new BonusEvaluator();

    int instanceU = 0;
    int instanceD = 0;
    int instanceL = 0;
    int instanceR = 0;

    @Override
    public AbstractState.MOVE getMove(State game) {
        pause();

        initial = game.copy();
        stack.push(initial);

        List<AbstractState.MOVE> moves = initial.getMoves();
        winningMove = moves.get(0);
        winningMoveScore = Double.NEGATIVE_INFINITY;
        for(int i = 0; i < iterations; i++){
            for(AbstractState.MOVE move : moves){
                initial.halfMove(move);
                double newScore = dfs();
                if(newScore > winningMoveScore){
                    winningMoveScore = newScore;
                    winningMove = move;
                }
                initial.undo();
            }

            switch (winningMove){
                case UP:
                    winningMoves[0] = instanceU++;
                    break;
                case DOWN:
                    winningMoves[1] = instanceD++;
                    break;
                case LEFT:
                    winningMoves[2] = instanceL++;
                    break;
                case RIGHT:
                    winningMoves[3] = instanceR++;
                    break;
            }

            winningMoveScore = Double.NEGATIVE_INFINITY;
        }

        double best = Double.NEGATIVE_INFINITY;
        for(int i = 0; i < winningMoves.length; i++){
            if(winningMoves[i] > best){
                best = winningMoves[i];
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
        double childrenScore;

        while (!stack.isEmpty() && currentDepth < depth){
            //if(currentDepth < depth){
                State c = stack.pop();
                if(c != null){
                    parent  = ev.evaluate(c);
                }
                visited.add(c);


                State[] children = c.nextFirstHalfMoveStates();
                childrenScore = 0;
                for (State child : children){
                    if(child != null){
                        if(!visited.contains(child) && !stack.contains(child)){
                            childrenScore += ev.evaluate(child);
                            stack.push(child);
                            //visited.add(child);
                        }
                    }
                }

                parent += (childrenScore/children.length);
                currentDepth++;
            //}
        }

        return parent;
    }


}
