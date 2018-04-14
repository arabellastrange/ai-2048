package ai;

import eval.BonusEvaluator;
import eval.Evaluator;
import model.AbstractState;
import model.State;

import java.util.List;

public class DepthLimited3 extends AbstractPlayer {
    int depth = 6; //150
    int iterations = 64; //500
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
        int currentDepth = 0;
        for(int i = 0; i < iterations; i++){
            State copy = node.copy();
            //copy.move(move);
            copy.halfMove(move);
            while (currentDepth < depth && !copy.getMoves().isEmpty()){
                List<AbstractState.MOVE> moves = copy.getMoves();
                //copy.move(moves.get((int) Math.random() * moves.size()));
                //or
                for(AbstractState.MOVE m : moves){
                    //copy.move(m);
                    copy.halfMove(m);
                }
                //*/
                currentDepth++;
            }
            score += ev.evaluate(copy);
            //score += copy.getScore();
            copy.undo();
        }

        return (score/iterations);
    }
}
