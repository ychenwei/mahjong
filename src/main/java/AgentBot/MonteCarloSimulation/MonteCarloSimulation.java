package AgentBot.MonteCarloSimulation;

import com.github.blovemaple.mj.object.Tile;
import com.github.blovemaple.mj.object.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import AgentBot.OnePlayerStrategy;

public class MonteCarloSimulation {
  private List<Tile> leftTileWall;
  private OnePlayerStrategy agentStrat;
  private List<Tile> myHand;
  private Map<Integer, List<Tile>> simuHands;

  public MonteCarloSimulation(List<Tile> leftTileWall, List<Tile> myHand, List<Tile> simuAliveTile1, List<Tile> simuAliveTile2, List<Tile> simuAliveTile3) {
    this.leftTileWall = leftTileWall;
    this.agentStrat = OnePlayerStrategy.getInstance();
    this.myHand = myHand;
    simuHands = new HashMap<>();
    simuHands.put(1, simuAliveTile1);
    simuHands.put(2, simuAliveTile2);
    simuHands.put(3, simuAliveTile3);
  }

  public TileType chooseDiscardTile(Set<TileType> discardChoices) {
    int maxScore = Integer.MIN_VALUE;
    TileType maxTile = null;
    //choose the tile with max score to discard
    for (TileType tileType: discardChoices) {
      StateNode root = new StateNode(null, tileType, null);
      List<Tile> curtMyHand = new ArrayList<Tile>(myHand);
      simuHands.put(4, curtMyHand);
      int score = simulateGame(root, discardTile(tileType, curtMyHand), curtMyHand);
      if (score > maxScore) {
        maxScore = score;
        maxTile = tileType;
      }
    }
    return maxTile;
  }

  private int simulateGame(StateNode root, Tile discardTile, List<Tile> curtMyHand) {
    if (checkMeDianPao(root, discardTile)) {
      return root.getReward();
    }

    StateNode curtNode = root;
    StateNode nextNode = null;
    while (true) {
      for (int i = 1; i <= 4; i++) {
        Tile next = leftTileWall.remove(new Random().nextInt(leftTileWall.size()));
        nextNode = (i == 4)? myTurn(i, curtNode, next, curtMyHand):otherTurn(i, curtNode, next, curtMyHand);
        if (nextNode == null) {
          return curtNode.getReward();
        }
        curtNode = nextNode;
      }
    }
  }

  private Tile discardTile(TileType tileType, List<Tile> curtMyHand) {
    for (Tile tile: curtMyHand) {
      if (tile.type() == tileType) {
        curtMyHand.remove(tile);
        return tile;
      }
    }
    return null;
  }

  /**
   * Assume other player get a tile and discard it right away
   * @return
   */
  private StateNode otherTurn(int id, StateNode parent, Tile newTile, List<Tile> myCurtHand) {
    StateNode newNode = new StateNode(newTile.type(), null, parent);
    if (checkZiMo(id, newNode, newTile, simuHands.get(id))) {
      backPropagation(newNode);
      return null;
    }
    newNode.setDiscardTile(newTile.type());
    if (isWin(newTile, myCurtHand)) {
      newNode.setReward(1);
      backPropagation(newNode);
      return null;
    }
    return newNode;
  }

  private boolean isWin(Tile newTile, List<Tile> hand) {
    List<Tile> newHand = new ArrayList<>(hand);
    newHand.add(newTile);
    //todo:placeholder for isWin(newHand)
    return false;
  }

  private boolean checkZiMo(int id, StateNode curtNode, Tile newTile, List<Tile> curtHand) {
    boolean isZiMo = isWin(newTile, curtHand);
    if (isZiMo) {
      int curtReward = (id == 4)? 3:-1;
      curtNode.setReward(curtReward);
      return true;
    }
    return false;
  }

  private StateNode myTurn(int id, StateNode parent, Tile newTile, List<Tile> curtHand) {
    StateNode newNode = new StateNode(newTile.type(), null, parent);
    if (checkZiMo(id, newNode, newTile, curtHand)) {
      backPropagation(newNode);
      return null;
    }
    curtHand.add(newTile);
    Set<TileType> discardCandis = agentStrat.discardChoice(agentStrat.divideBySuit(curtHand));
    int rd = new Random().nextInt(discardCandis.size());
    int i = 0;
    for (TileType toDiscard: discardCandis) {
      if (i == rd) {
        newNode.setDiscardTile(toDiscard);
        for (Tile t: curtHand) {
          if (t.type() == toDiscard) {
            curtHand.remove(t);
            if (checkMeDianPao(newNode, t)) {
              backPropagation(newNode);
              return null;
            }
            return newNode;
          }
        }
      }
      i++;
    }
    return null;
  }

  private boolean checkMeDianPao(StateNode curtNode, Tile newTile) {
    int numOfWinPlys = 0;
    for (Map.Entry<Integer, List<Tile>> ply: simuHands.entrySet()) {
      if (isWin(newTile, ply.getValue())) {
        numOfWinPlys += 1;
      }
    }
    curtNode.setReward(curtNode.getReward()-numOfWinPlys);
    return numOfWinPlys!=0;
  }

  private void backPropagation(StateNode node) {
    StateNode parent = node.getParent();
    while (parent != null) {
      parent.setReward(parent.getReward() + node.getReward());
      parent = parent.getParent();
    }
  }
}
