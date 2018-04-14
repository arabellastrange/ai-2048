//package ai;
//
//import eval.BonusEvaluator;
//import eval.Evaluator;
//import model.AbstractState;
//import model.State;
//
//import java.awt.event.MouseListener;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//public class DepthLimited4 extends AbstractPlayer{
//    int depth = 4;
//    int iterations = 64;
//    double best;
//    Evaluator ev = new BonusEvaluator();
//    AbstractState.MOVE winningMove;
//    ArrayList<AbstractState.MOVE> winningMoves;
//    HashMap<Double, AbstractState.MOVE> moveScoreMap;
//
//    @Override
//    public AbstractState.MOVE getMove(State game) {
//        pause();
//        moveScoreMap = new HashMap<>();
//        List<AbstractState.MOVE> moves = game.getMoves();
//
//        for(int i = 0; i < iterations; i++){
//            for(AbstractState.MOVE m : moves){
//                moveScoreMap.put(runSimulation(m, game), m);
//            }
//
//        }
//        return winningMove; // the hieghest scoreing move from map
//    }
//
//    private double runSimulation(AbstractState.MOVE m, State node) {
//        int currentDepth = 0;
//        State copy = node.copy();
//        copy.move(m); // now on dpeth 1
//        currentDepth++;
//
//        List<AbstractState.MOVE> moves = copy.getMoves();
//        AbstractState.MOVE bestNextMove = moves.get((int) Math.random() * moves.size());
//
//        if(currentDepth == depth){
//            return ev.evaluate(copy);
//        }
//
//        while(currentDepth < depth && !copy.getMoves().isEmpty()){
//            double best = Double.NEGATIVE_INFINITY;
//            for(AbstractState.MOVE move : moves){
//                double nextscore = runSimulation(move, copy);
//                if(nextscore > best){
//                    best = nextscore;
//                    bestNextMove = m;
//                }
//            }
//            copy.move(bestNextMove);
//            currentDepth++;
//        }
//
//        copy.undo();
//
//        return ;
//    }
//
//    private double getScore(State node){
//        int score = 0;
//
//        //return ev.evaluate(node);
//
//        for(currentDepth = 0; currentDepth < depth && !copy.getMoves().isEmpty(); currentDepth++){
//            double best = Double.NEGATIVE_INFINITY;
//
//            for(AbstractState.MOVE m : moves){
//                copy.move(m);
//                double nextscore = getScore(copy);
//                if(nextscore > best){
//                    best = nextscore;
//                    bestNextMove = m;
//                }
//            }
//            copy.move(bestNextMove);
//        }
//        score += copy.getScore();
//        copy.undo();
//
//        return (score/iterations);
//    }
//}
//
