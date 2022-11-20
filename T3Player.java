package main.t3;

import java.util.*;


/**
 * Samuel Richard
 * 22 February 2022
 * 
 * Artificial Intelligence responsible for playing the game of T3!
 * Implements the alpha-beta-pruning mini-max search algorithm
 */
public class T3Player {
    
    public static boolean MIN = false;
    public static boolean MAX = true;
    public int depth;
    
    /**
     * Workhorse of an AI T3Player's choice mechanics that, given a game state,
     * makes the optimal choice from that state as defined by the mechanics of
     * the game of Tic-Tac-Total.
     * Note: In the event that multiple moves have equivalently maximal minimax
     * scores, ties are broken by move col, then row, then move number in ascending
     * order (see spec and unit tests for more info). The agent will also always
     * take an immediately winning move over a delayed one (e.g., 2 moves in the future).
     * @param state The state from which the T3Player is making a move decision.
     * @return The T3Player's optimal action.
     */
    public T3Action choose (T3State state) {
        
        int score = Integer.MIN_VALUE;
        T3Action bestAction = new T3Action(10, 10, 10);
        Map<T3Action,T3State> transitions = state.getTransitions();
        
        for (Map.Entry<T3Action,T3State> entry : transitions.entrySet()) {
            T3Action currentAction = entry.getKey();
            int minimax = alphabeta(entry.getValue(), Integer.MIN_VALUE, Integer.MAX_VALUE, MIN);
            
            if (entry.getValue().isWin()) {
                bestAction = currentAction;
                break;
            } else if (minimax == score) {
                if (bestAction.compareTo(currentAction) > 0) {
                    bestAction = currentAction;
                }
            }
            
            if (minimax > score) {
                score = minimax;
                bestAction = currentAction;
            }
        }
        return bestAction;
        
    }
    
    /**
     * Returns the minimax score of a given state.
     * @param currentState The state that which the T3Player is checking.
     * @param worst The worst minimax score for the state.
     * @param best The best minimax score for the state.
     * @param turn Boolean for whether or not the currentState is a MIN(false) or MAX(true).
     * @return The minimax score of the currentState.
     */
    public int alphabeta(T3State currentState, int worst, int best, boolean turn) {
        
        int value;
        Map<T3Action,T3State> transitions = currentState.getTransitions();   
        
        
        if (currentState.isTie()) {
            return 0;
        }
        
        if (currentState.isWin()) {
            if (turn) {
                return 1;
            } else {
                return -1;
            }
        }

        
        if (turn) { //MAX Node
            value = Integer.MIN_VALUE;
            for (Map.Entry<T3Action,T3State> entry : transitions.entrySet()) {
                value = Math.max(value, alphabeta(entry.getValue(), worst, best, MIN));
                worst = Math.max(worst, value);
                if (best <= worst) {
                    break;
                }
            }
        } else { //MIN Node
            value = Integer.MAX_VALUE;
            for (Map.Entry<T3Action,T3State> entry : transitions.entrySet()) {
                value = Math.min(value, alphabeta(entry.getValue(), worst, best, MAX));
                best = Math.min(best, value);
                if (best <= worst) {
                    break;
                }
            }
        }
        return value;
        
    }
}

