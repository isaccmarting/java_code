import java.util.Arrays;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Vaibhav
 *
 */
public class Node {
	
	OthelloState state;
	Node parent=null;
	LinkedList<Node> children;
	OthelloMove actionLed=null;
	
	int visited=0;
	
	LinkedList<Double> scoreList;
	double avgScore;
	
	public Node(OthelloState state)
	{
		this.state=state;
		children=new LinkedList<Node>();
		scoreList=new LinkedList<Double>();
	}
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public LinkedList<Node> getChildren() {
		return children;
	}
	public void setChildren(Node children) {
		this.children.add(children);
	}
	
	public boolean checkChild(OthelloState state)
	{
		for(Node n:children)
		{
			if(Arrays.deepEquals(n.state.board, state.board))
					return true;
		}
		
		return false;
	}
	public OthelloMove getActionLed() {
		return actionLed;
	}
	public void setActionLed(OthelloMove actionLed) {
		this.actionLed = actionLed;
	}
	public int getVisited() {
		return visited;
	}
	public void setVisited(int visited) {
		this.visited = visited;
	}
	public LinkedList<Double> getScoreList() {
		return scoreList;
	}
	public void setScoreList(LinkedList<Double> scoreList) {
		this.scoreList = scoreList;
	}
	public double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}

}
