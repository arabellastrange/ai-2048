package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;
import java.util.List;

public class DepthLimited3 extends AbstractPlayer {
    int depth = 200; //4
    int iterations = 400; //64
    double best;
    Evaluator ev = new BonusEvaluator();
    AbstractState.MOVE winningMove;

    @Override
    public AbstractState.MOVE getMove(State game) {
        pause();
        best = Double.NEGATIVE_INFINITY;
        List<AbstractState.MOVE> moves = game.getMoves();
        for (AbstractState.MOVE m : moves){
            double score = runSimulation(m, game);
            if(score > best){
                best = score;
                winningMove = m;
            }
        }
        return winningMove;
    }

    private double runSimulation(AbstractState.MOVE move, State node) {
        int score = 0;
        int currentDepth;
        for(int i = 0; i < iterations; i++){
            State copy = node.copy();
            copy.move(move);
            for(currentDepth = 0; currentDepth < depth && !copy.getMoves().isEmpty(); currentDepth++){
                double best = Double.NEGATIVE_INFINITY;
                List<AbstractState.MOVE> moves = copy.getMoves();
                AbstractState.MOVE bestNextMove = moves.get((int) Math.random() * moves.size());
                //for(AbstractState.MOVE m : moves){
                    // copy.move(m);
                    //double nextscore = runSimulation(m, copy);
                    //if(nextscore > best){
                     //   best = nextscore;
                     //   bestNextMove = m;
                    //}
                //}
                copy.move(bestNextMove);
            }
            score += copy.getScore();
            copy.undo();
        }

        return (score/iterations);
    }
}
