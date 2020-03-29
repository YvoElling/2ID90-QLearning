//nl.tue.s2id90.qlearning.peach.QInputReader
public class QLearner {  
    /**
     * This method performs Q-learning. TODO complete documentation.
     * @param rewards
     * @param paths
     * @param gamma
     * @param noIterations
     * @return 
     */
    public String execute(Integer[][] rewards, Integer[][] paths, Double gamma,
            Integer noIterations)
    {
        //Create the Q(uality)-table
        Double[][] Q = new Double[rewards.length][rewards[0].length];
        
        //Initialize the Q-table for all pairs(state, action) to 0.0
        //Rewards is of shape (s, a), so we use this to create the Q_value of 
        //the correct size
        for(int state = 0; state < rewards.length; ++state) {
            for( int action = 0; action < rewards[state].length; ++action) {
                //If the rewards for this action is NULL, then is is not
                //a valid action. Therefore we will make that position in Q
                //null too
                if (rewards[state][action] == null) {
                    Q[state][action] = null;
                } else {
                    //If the rewards is not null, then we set it equal to 0
                    Q[state][action] = 0.0;
                }           
            }
        }
        
        //Repeat the process of 
        for (int it = 0; it < noIterations; ++it) {
            
            for (Integer[] path : paths) {
                this.execute(Q, rewards, path, gamma);
            }
            
        }
       
        return policy(Q);
    }
    
    
    /** do Q-learning for one path.  TODO complete documentation. */
    private void execute(Double[][] Q, Integer[][] rewards, Integer[] path, 
            Double gamma) {
        for (int i = 1; i < path.length; ++i) {
            int state = path[i-1];
            int action = path[i];
            
            double reward = rewards[state][action];

            int nextState = action;
            int nextBestAction = getBestActionIndex(Q[nextState]);
            
            if (nextBestAction == -1) {
                Q[state][action] = reward * 1d;
            } else {
                Q[state][action] = reward + gamma*(Q[nextState]
                        [nextBestAction]);
            }
                   
        }
    }
    
    private int getBestActionIndex(Double[] actions) {
        
        int index = -1;
        double bestAction = -1;
        
        for(int i = 0; i < actions.length; ++i) {
            if (actions[i] != null) {
                if (actions[i] > bestAction) {
                    bestAction = actions[i];
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * computes the policy. TODO complete documentation.
     */
    private String policy(Double[][] Q) {   
    String output = "";
    for (int i = 0; i < Q.length; ++i) {
        String bestAction = Integer.toString(getBestActionIndex(Q[i]));
        if (bestAction.compareTo("-1") != 0) {
            output += (bestAction);
            if (i != Q.length -1) {
                output += " ";
            }
        } else {
            output += "n";
            if (i != Q.length -1) {
                output += " ";
            }
        }
    }
    return output; // TODO compute policy
    }
}