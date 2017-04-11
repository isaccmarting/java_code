import java.util.List;
import java.util.Random;

/**
 * 
 */

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
		Node node,node2;
		
		for(int i=0; i<iterations;i++)
		{
			node=treePolicy(root);
			if(node!= null)
			{
				node2=defaultPolicy(node);
				double Node2Score=score(node2);
				backup(node,Node2Score);
			}
		}
		
		return bestChild(root).actionLed;
	}
	public Node bestChild(Node node)
	{
		double avg=Double.NEGATIVE_INFINITY;
		double avg2=Double.POSITIVE_INFINITY;
		
		List<Node> c=node.getChildren();
		Node best=node;
		if(node.state.nextPlayerToMove==0)
		{
			for(Node n:c)
			{
				if(n.avgScore>avg){
					avg=n.avgScore;
					best=n;
				}
			}
		}
		else if(node.state.nextPlayerToMove==1)
		{
			for(Node n:c)
			{
				if(n.avgScore<avg2){
					avg2=n.avgScore;
					best=n;
				}
			}
		}
		//System.out.println(best);
		return best;
	}
	public void backup(Node node, double score)
	{
		node.visited++;
		node.scoreList.add(score);
		double total=0;
		for(double d:node.scoreList)
		{
			total+=d;
		}
		
		node.avgScore=total/node.scoreList.size();
		
		if(node.parent!=null)
			backup(node.parent,score);
	}
	
	public double score(Node node)
	{
		return node.state.score();
	}
	
	public Node defaultPolicy(Node node)
	{
		List<OthelloMove> moves;// = node.state.generateMoves();   
		Random r = new Random();
        //System.out.println(state.nextPlayerToMove);
        // return one at random:
		OthelloState temp=node.state;// = node.state.applyMoveCloning(moves.get(r.nextInt(moves.size())));
		
		while(true)
		{
			//System.out.println(temp.toString());
			moves = temp.generateMoves(); 
		//	System.out.println(moves.size());
			//System.out.println(temp.score());
			if(!moves.isEmpty())
			{
			int x=r.nextInt(moves.size());
			//System.out.println(x);
			temp = temp.applyMoveCloning(moves.get(x));
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
		
		if(moves.isEmpty()) return node;
		
		Node nodeTmp;
		nodeTmp = bestChild(node);
        
        return treePolicy(nodeTmp);
		
		
	}
	public Node createNode(OthelloState board)
	{
		return new Node(board);
	}
}
