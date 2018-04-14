//package ai;
//
//import eval.BonusEvaluator;
//import eval.Evaluator;
//import model.AbstractState;
//import model.State;
//
//import java.util.HashSet;
//
//public class DepthLimited2 extends AbstractPlayer {
//    HashSet<State> visited = new HashSet<>();
//    int[] instances = new int[4];
//    Evaluator ev = new BonusEvaluator();
//    double score;
//    double best;
//
//    @Override
//    public AbstractState.MOVE getMove(State game) {
//        best = Double.NEGATIVE_INFINITY;
//        for (int n = 0; n < 64; n++){
//            for(int i = 0; i < 4; i++){
//                double newScore = dls(game, i);
//                if(newScore > best){
//                    best = newScore;
//                    //get move associate with
//                }
//            }
//            best = Double.NEGATIVE_INFINITY;
//
//            // store the number of times each move shows up in an iteration
//        }
//
//        return null; // return most common move
//    }
//
//    public double dls(State node, int depth){
//
//        if(depth == 0){
//            return  ev.evaluate(node);
//        }
//
//        visited.add(node);
//
//        if(depth > 0){
//            for (State child: node.nextFirstHalfMoveStates()){
//                if(!visited.contains(child)){
//                    score += dls(child, depth -1);
//                    return score;
//                }
//
//            }
//        }
//
//        return  0;
//    }
//}
