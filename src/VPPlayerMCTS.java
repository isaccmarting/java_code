import java.util.List;
import java.util.Random;
import java.lang.Math;

/**
 * @author Vaibhav
 *
 */
public class VPPlayerMCTS extends OthelloPlayer {
	int iter;
	
	public VPPlayerMCTS(int iterations)
	{
		iter=iterations;
	}

	@Override
	public OthelloMove getMove(OthelloState state) {
		// TODO Auto-generated method st
		return MonteCarloTreeSearch(state,iter);
		
		//return null;
	}
	
	public OthelloMove MonteCarloTreeSearch(OthelloState state, int iterations)
	{
		Node root = createNode(state); 
		Node node/*, node2*/;
		
		for(int i = 0; i < iterations; i++)
		{
			node = treePolicy(root);
			// node is not null 
			// node2 = defaultPolicy(node);
			// double Node2Score = score(node2);
			boolean nextPlayer; 
			if(node.state.nextPlayerToMove == 0)
				nextPlayer = true; 
			else 
				nextPlayer = false; 
			double bestScore = minimax_UCT(node.state, 6, nextPlayer); 
			backup(node, bestScore/*Node2Score*/);
		}
		
		return bestChild(root, 0).actionLed;
	}

	public Node bestChild(Node node, double c)
	{
		double score, best_score; 
		
		List<Node> children = node.getChildren();
		Node best = node;
		if(node.state.nextPlayerToMove == 0)
		{
			best_score = Double.NEGATIVE_INFINITY; 
			for(Node n:children)
			{
				score = n.avgScore + c * Math.sqrt(2 * Math.log(node.visited) / n.visited); 
				if(score > best_score)
				{
					best_score = score;
					best = n;
				}
			}
		}
		else if(node.state.nextPlayerToMove == 1)
		{
			best_score = Double.POSITIVE_INFINITY; 
			for(Node n:children)
			{
				score = n.avgScore + c * Math.sqrt(2 * Math.log(node.visited) / n.visited); 
				if(score < best_score)
				{
					best_score = score;
					best = n;
				}
			}
		}
		//System.out.println(best);
		return best;
	}
	public void backup(Node node, double score)
	{
		// int i = 0; 
		while(node != null) 
		{
			node.visited++; 
		    node.scoreList.add(score);
			double total = 0;
			for(double d:node.scoreList)
			{
				total += d;
			}
			node.avgScore = total / node.scoreList.size();
			node = node.parent; 
			// System.out.println(i++); 
		}
	}
	
	public double score(Node node)
	{
		return node.state.score();
	}
	
	// add min-max here 
	public Node defaultPolicy(Node node)
	{
		List<OthelloMove> moves;// = node.state.generateMoves();   
		Random r = new Random();
		OthelloState temp = node.state;// = node.state.applyMoveCloning(moves.get(r.nextInt(moves.size())));
		
		while(true)
		{
			moves = temp.generateMoves(); 
			if(!moves.isEmpty())
			{
				int x = r.nextInt(moves.size());
				temp = temp.applyMoveCloning(moves.get(x));
				/*Node newNode = createNode(temp); 
				newNode.setActionLed(moves.get(x)); 
				newNode.parent = node; 
				node.setChildren(newNode); */
			}
			else
				break;
			
			if(temp.gameOver())
				break;
		}
       
        return new Node(temp);
	}
	
	public Node treePolicy(Node node)
	{
		List<OthelloMove> moves= node.state.generateMoves();
		
        while(!moves.isEmpty()) 
        {
        	for(OthelloMove m:moves)
    		{
    			OthelloState temp=node.state.applyMoveCloning(m);
    			if(!node.checkChild(temp))
    			{
    				Node newNode = new Node(temp);
    				newNode.actionLed=m;
    				node.setChildren(newNode);
    				newNode.parent = node; 
    				return newNode;
    			}
    		}
        	node = bestChild(node, 0); 
        	moves= node.state.generateMoves(); 
        }
        return node; 
	}
	
	public Node createNode(OthelloState board)
	{
		return new Node(board);
	}
	
	private double minimax_UCT(OthelloState currentState, int depth, boolean Maxplayer)
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
				
				v = minimax_UCT(temp, depth-1, false);
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
				v = minimax_UCT(temp, depth-1, true);
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
