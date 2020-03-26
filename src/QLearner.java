
import java.util.Random;

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
        for(int row = 0; row < rewards.length; ++row) {
            for( int column = 0; column < rewards[row].length; ++column) {
                Q[row][column] = 0.0;
            }
        }
        
        //Repeat the process of 
            int state = new Random().nextInt(paths.length);
            Integer[] actions = paths[state];
            this.execute(Q, rewards, actions, gamma);     
        // Do Q-learning
       
        return policy(Q);
    }
    
    private Integer[] selectRandomState(Integer[][] paths){
        int rnd = 
        return paths[rnd];
    }
    
    /** do Q-learning for one path.  TODO complete documentation. */
    private void execute(Double[][] Q, Integer[][] rewards, Integer[] path, 
            Double gamma) {
        for (Integer action : path) {
            Integer newState = action;
            
       }
    }

    /**
     * computes the policy. TODO complete documentation.
     */
    private String policy(Double[][] Q) {
        return ""; // TODO compute policy
    }
}