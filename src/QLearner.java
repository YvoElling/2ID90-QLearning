//nl.tue.s2id90.qlearning.peach.QInputReader
public class QLearner {  
    /**
     * This method performs Q-learning.
     * 
     * @author Yvo Elling (1009111)
     * @param rewards the matrix containing rewards for every position
     * @param paths the possible paths that the algorithm may take
     * @param gamma learning rate
     * @param noIterations amount of iterations the algorithm can take
     * 
     * @return string that contains the best action for every state
     */
    public String execute(Integer[][] rewards, Integer[][] paths, Double gamma,
            Integer noIterations)
    {
        //Create the Q(uality)-table of the same size as rewards
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
        
        //Repeat the process of learning @noIterations times
        for (int it = 0; it < noIterations; ++it) {
            
            //For all possible paths in pahts
            for (Integer[] path : paths) {
                //Execute on that path
                this.execute(Q, rewards, path, gamma);
            }
            
        }
       
        //Determine the policy of the Q table
        return policy(Q);
    }
    
    
    /** do Q-learning for one path */
    private void execute(Double[][] Q, Integer[][] rewards, Integer[] path, 
            Double gamma) {
        //for all actions on the paths
        for (int i = 0; i < path.length-1; ++i) {           
            //Take the current state 
            int state = path[i];
            //Take the action (which is the first action after the state)
            int action = path[i+1];
            
            //Retrieve the reward from the rewards table based on this
            //action and state
            double reward = rewards[state][action];

            //The nextState is the previous action 
            //(Because action is a 'move to state' call
            int nextState = action;
            //Compute the bestAction for this state
            int nextBestAction = getBestActionIndex(Q[nextState]);
            
            //If the bestAction for the new state is -1, then we know 
            //that there was no valid path in that state hence there is no
            //learning and Q[state][action] will just be the reward
            if (nextBestAction == -1) {
                Q[state][action] = reward;
            } else {
                //If nexBestAction >= 0 then at least one possible action was
                //found hence and we can use the best of the found action to
                //let Q[state][action] learn. That is performed by multiplyling
                //the Q-value of nextState and action with the learning rate
                //and add it to the reward for Q[state][action]
                Q[state][action] = reward + gamma*(Q[nextState]
                        [nextBestAction]);
            }
                   
        }
    }
    
    /**
     * returns the best action from an array of actions
     * 
     * @param actions actions from which to retrieve the best actions
     * @return best action for this state
     */
    private int getBestActionIndex(Double[] actions) {
        //Set default values to -1, as 0 is a possible action value. With this
        //we can detect endstates in the algorithm
        int index = -1;
        double bestAction = -1;
        
        //For all possible actions...
        for(int i = 0; i < actions.length; ++i) {
            //If an action is null, skip it. It has no value to us
            if (actions[i] != null) {
                //If the action found is better than the best action, 
                //then set this action to be the new best action and
                //store its index
                if (actions[i] > bestAction) {
                    bestAction = actions[i];
                    index = i;
                }
            }
        }
        
        //return the index of the bestAction for this state
        return index;
    }

    /**
     * computes the policy.
     * Generates a string that gives the best action for each each state
     * based on the Q table. 
     * 
     * @param Q the Q table
     * @return a string that gives the best action for each state
     */
    private String policy(Double[][] Q) {  
    //Create empty string as base    
    String output = "";
    
    //For all states in Q
    for (int i = 0; i < Q.length; ++i) {
        // Get the best action for that state and convert to String value
        String bestAction = Integer.toString(getBestActionIndex(Q[i]));
        
        // If the value is not -1, then we have found an action. Add this action
        // to the output string and if it is not the last value of the string
        // add a space to keep the actions apart. 
        if (bestAction.compareTo("-1") != 0) {
            output += (bestAction);
            if (i != Q.length -1) {
                //If it is the last value, do not add the space.
                output += " ";
            }
        } else {
            //If the bestAction is -1, then there was no action found. Now we
           //have to place an 'n'. 
            output += "n";
            if (i != Q.length -1) {
                //If it is the last value, do not add the space.
                output += " ";
            }
        }
    }
    
    //Return the output string
    return output; // TODO compute policy
    }
}