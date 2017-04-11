import java.util.Scanner;

/**
 *
 * @author santi
 */
public class Test {
    
    
    public static void main(String args[]) {
    	int w=0,t=0;
    	Scanner sc= new Scanner(System.in);
    	OthelloState state;
    	OthelloPlayer players[]= new OthelloPlayer[2];
        
       // OthelloPlayer players[];
    	while(true)
    	{
    		System.out.println("1: Minimax(p1) vs Random Player(p2)");
    		System.out.println("2: MCTS(p1) vs Random Player(p2)");
    		System.out.println("3: MCTS(p1) vs Minimax(p2)");
    		System.out.println("Enter the number: ");
    		int choice=sc.nextInt();
    		int iter,depth;
    		
    		if(choice==1)
    		{
    			System.out.println("Enter depth of minimax");
    			depth=sc.nextInt();
    			   // Create the game state with the initial position for an 8x8 board:
         state = new OthelloState(8);
        //OthelloPlayer players[] = {new VPPlayer(5),new OthelloRandomPlayer()};
         players[0] = new VPPlayer(depth);
         players[1] = new OthelloRandomPlayer();
    		}
    		else if(choice==2)
    		{
    			System.out.println("Enter number of iterations for MCTS");
    			iter=sc.nextInt();
    			   // Create the game state with the initial position for an 8x8 board:
         state = new OthelloState(8);
        //OthelloPlayer players[] = {new VPPlayer(5),new OthelloRandomPlayer()};
         players[0] = new VPPlayerMCTS(iter);
         players[1] = new OthelloRandomPlayer();
    		}
    		else if(choice==3)
    		{
    			System.out.println("Enter depth of minimax");
    			depth=sc.nextInt();
    			System.out.println("Enter number of iterations for MCTS");
    			iter=sc.nextInt();
    			   // Create the game state with the initial position for an 8x8 board:
         state = new OthelloState(8);
        //OthelloPlayer players[] = {new VPPlayer(5),new OthelloRandomPlayer()};
         players[0] = new VPPlayerMCTS(iter);
         players[1] = new VPPlayer(depth);
    		}
    		else
    			break;
     
        
        do{
            // Display the current state in the console:
            System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
            System.out.print(state);
            
            // Get the move from the player:
            OthelloMove move = players[state.nextPlayerToMove].getMove(state);            
            System.out.println(move);
//            System.out.println(state.score());
            state = state.applyMoveCloning(move);            
        }while(!state.gameOver());

        // Show the result of the game:
        System.out.println("\nFinal state with score: " + state.score());
        System.out.println(state);
        
//        if(state.score()>0)
//        	w++;
//        t++;
    	}
    	
    	//System.out.println(w+"/"+t);
    }    
    
}
