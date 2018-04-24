package AgentBot.MonteCarloSimulation;

import com.github.blovemaple.mj.object.TileType;

/**
 * For CS5100 Final Project
 * @author Luyao Wang, Chenwei Yin
 */

public class StateNode {
  private TileType gotTile;
  private TileType discardTile;
  private int reward;
  private StateNode parent;

  public StateNode() {

  }

  public StateNode(TileType gotTile, TileType discardTile, StateNode parent) {
    this.gotTile = gotTile;
    this.discardTile = discardTile;
    this.parent = parent;
    this.reward = 0;
  }

  public TileType getGotTile() {
    return gotTile;
  }

  public TileType getDiscardTile() {
    return discardTile;
  }

  public void setDiscardTile(TileType discardTile) {
    this.discardTile = discardTile;
  }

  public StateNode getParent() {
    return parent;
  }

  public int getReward() {
    return reward;
  }

  public void setReward(int reward) {
    this.reward = reward;
  }


}
