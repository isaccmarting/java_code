import java.util.List;

/**
 * 
 */

/**
 * @author Vaibhav
 *
 */
public class VPPlayer extends OthelloPlayer {
	
	//OthelloMove action;
	int depth;
	
	public VPPlayer(int depth)
	{
		this.depth=depth;
	}

	@Override
	public OthelloMove getMove(OthelloState state) {
        double util;
        if(state.nextPlayerToMove==0)
        	util =minimax(state,depth,true);
        else
        	util =minimax(state,depth,false);
        return state.nextMove;
	}
	
	//minimax pseudocode from wiki
	private double minimax(OthelloState currentState, int depth, boolean Maxplayer)
	{
		if(depth==0 || currentState.gameOver())
			return currentState.score();
		
		if(Maxplayer)
		{
			double bestVal = Double.NEGATIVE_INFINITY;
			double v;
			OthelloState temp;
			List<OthelloMove> moves = currentState.generateMoves(); 
			for(OthelloMove m:moves)
			{
				temp = currentState.applyMoveCloning(m);
				
				v = minimax(temp, depth-1, false);
				if(v > bestVal)
				{
					bestVal = v;
					currentState.nextMove = m;
				}
			}
			return bestVal;
		}
		else
		{
			double bestVal = Double.POSITIVE_INFINITY;
			double v;
			OthelloState temp;
			List<OthelloMove> moves = currentState.generateMoves(); 
			for(OthelloMove m:moves)
			{
				temp = currentState.applyMoveCloning(m);
				v = minimax(temp, depth-1, true);
				if(v < bestVal)
				{
					bestVal = v;
					currentState.nextMove = m;
				}
			}
			return bestVal;
		}		
	}
}
