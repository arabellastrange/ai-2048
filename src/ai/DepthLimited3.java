package ai;

import eval.BonusEvaluator;
import model.AbstractState;
import model.State;
import java.util.List;
import java.util.Random;

public class DepthLimited3 extends AbstractPlayer {
    int depth = 6; //4
    int iterations = 700; //64
    double best;
    Random random;
    AbstractState.MOVE winningMove;
    BonusEvaluator ev = new BonusEvaluator();

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
        random = new Random();
        int score = 0;
        int currentDepth;
        for(int i = 0; i < iterations; i++){
            State copy = node.copy();
            copy.move(move);
            currentDepth = 0;
            while(currentDepth < depth && !copy.getMoves().isEmpty()){
                List<AbstractState.MOVE> moves = copy.getMoves();
                AbstractState.MOVE bestNextMove = moves.get(random.nextInt(moves.size()));
                copy.move(bestNextMove);
                currentDepth++;
            }
            //score += copy.getScore();
            score += ev.evaluate(copy);
            copy.undo();
        }

        return (score/iterations);
    }
}
