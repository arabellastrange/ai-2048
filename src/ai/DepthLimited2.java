package ai;

import model.AbstractState;
import model.State;

import java.util.*;

public class DepthLimited2 extends AbstractPlayer {
    HashSet<State> visited = new HashSet<>();
    HashMap<AbstractState.MOVE, Double> moveScoreMap;
    double score;

    @Override
    public AbstractState.MOVE getMove(State game) {
        pause();

        moveScoreMap = new HashMap<>();
        List<AbstractState.MOVE> moves = game.getMoves();

        for (int n = 0; n < 256; n++){
            for(AbstractState.MOVE m : moves){
                if(moveScoreMap.containsKey(m)){
                    moveScoreMap.put(m, moveScoreMap.get(m) + dls(game.copy(), 64));
                } else {
                    moveScoreMap.put(m, dls(game.copy(), 4));
                }
            }
        }

        return moveScoreMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    public double dls(State node, int depth){
        if(depth == 0 || node.getMoves().isEmpty()){
            return node.getScore();
        }

        visited.add(node);

        if(depth > 0 && !node.getMoves().isEmpty()){
            for (State child: node.nextFirstHalfMoveStates()){
                if(child != null){
                    if(!visited.contains(child)){
                        score += dls(child, depth -1);
                        return score;
                    }
                }
            }
        }

        return  0;
    }
}
