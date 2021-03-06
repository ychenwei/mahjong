package AgentBot.MonteCarloSimulation;

import com.github.blovemaple.mj.object.Tile;
import com.github.blovemaple.mj.object.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import AgentBot.OnePlayerStrategy;
import AgentBot.WinJudge;

/**
 * For CS5100 Final Project
 * @author Luyao Wang, Chenwei Yin
 */

public class MonteCarloSimulation {
  private List<TileType> leftTileWall;
  private OnePlayerStrategy agentStrat;
  private List<TileType> myHand;
  private Map<Integer, List<TileType>> simuHands;

  public MonteCarloSimulation(List<TileType> leftTileWall, List<Tile> originHand, Map<Integer, List<TileType>> simuHands) {
    this.leftTileWall = leftTileWall;
//    this.agentStrat = OnePlayerStrategy.getInstance();
    this.agentStrat = new OnePlayerStrategy();
    this.myHand = new ArrayList<>();
    for (Tile tile : originHand) {
      myHand.add(tile.type());
    }
    this.simuHands = simuHands;
  }

  public TileType chooseDiscardTile(Set<TileType> discardChoices) {
    long startTime = System.nanoTime();
    int maxScore = Integer.MIN_VALUE;
    TileType maxTile = null;
    //todo:try the tile with highest UTC
    //choose the tile with max score to discard
    Map<TileType, StateNode> discardCandis = new HashMap<>();
    for (TileType candi : discardChoices) {
      discardCandis.put(candi, new StateNode(null, candi, null));
    }
    while (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime) < 10) {
      for (TileType tileType : discardChoices) {
        StateNode root = discardCandis.get(tileType);
        List<TileType> curtMyHand = new ArrayList<>(myHand);
        curtMyHand.remove(tileType);
        simuHands.put(4, curtMyHand);
        int score = simulateGame(root, tileType, curtMyHand);
        if (score > maxScore) {
          maxScore = score;
          maxTile = tileType;
        }
      }
    }
    return maxTile;
  }

  private int simulateGame(StateNode root, TileType discardTile, List<TileType> curtMyHand) {
    if (checkMeDianPao(root, discardTile)) {
      return root.getReward();
    }

    StateNode curtNode = root;
    StateNode nextNode = null;
    while (true) {
      for (int i = 1; i <= 4; i++) {
        if (leftTileWall.size() == 0) {
          return root.getReward();
        }
        TileType next = leftTileWall.remove(new Random().nextInt(leftTileWall.size()));
        nextNode = (i == 4) ? myTurn(i, curtNode, next, curtMyHand) : otherTurn(i, curtNode, next, curtMyHand);
        if (nextNode == null) {
          return root.getReward();
        }
        curtNode = nextNode;
      }
    }
  }

  /**
   * Assume other player get a tile and discard it right away
   */
  private StateNode otherTurn(int id, StateNode parent, TileType newTile, List<TileType> myCurtHand) {
//    System.out.println("enter other turn");
    StateNode newNode = new StateNode(newTile, null, parent);
    if (checkZiMo(id, newNode, newTile, simuHands.get(id))) {
      backPropagation(newNode);
//      System.out.println("leave other turn");
      return null;
    }
    newNode.setDiscardTile(newTile);
    if (isWin(newTile, myCurtHand)) {
      newNode.setReward(1);
      backPropagation(newNode);
//      System.out.println("leave other turn");
      return null;
    }
//    System.out.println("leave other turn");
    return newNode;
  }

  private boolean isWin(TileType newTile, List<TileType> hand) {
    List<TileType> newHand = new ArrayList<>(hand);
    newHand.add(newTile);
    return new WinJudge().isWin(agentStrat.divideTypeBySuit(newHand));
  }

  private boolean checkZiMo(int id, StateNode curtNode, TileType newTile, List<TileType> curtHand) {
    boolean isZiMo = isWin(newTile, curtHand);
    if (isZiMo) {
      int curtReward = (id == 4) ? 3 : -1;
      curtNode.setReward(curtReward);
      return true;
    }
    return false;
  }

  private StateNode myTurn(int id, StateNode parent, TileType newTile, List<TileType> curtHand) {
//    System.out.println("enter my turn");
    StateNode newNode = new StateNode(newTile, null, parent);
    if (checkZiMo(id, newNode, newTile, curtHand)) {
      backPropagation(newNode);
//      System.out.println("leave my turn");
      return null;
    }
    curtHand.add(newTile);
    Set<TileType> discardCandis = agentStrat.discardChoice(agentStrat.divideTypeBySuit(curtHand));
    int rd = new Random().nextInt(discardCandis.size());
    int i = 0;
    for (TileType toDiscard : discardCandis) {
      if (i == rd) {
        newNode.setDiscardTile(toDiscard);
        curtHand.remove(toDiscard);
        if (checkMeDianPao(newNode, toDiscard)) {
          backPropagation(newNode);
//          System.out.println("leave my turn");
          return null;
        }
//        System.out.println("leave my turn");
        return newNode;
      }
      i++;
    }
//    System.out.println("leave my turn");
    return null;
  }

  private boolean checkMeDianPao(StateNode curtNode, TileType newTile) {
    int numOfWinPlys = 0;
    for (Map.Entry<Integer, List<TileType>> ply : simuHands.entrySet()) {
      if (isWin(newTile, ply.getValue())) {
        numOfWinPlys += 1;
      }
    }
    curtNode.setReward(curtNode.getReward() - numOfWinPlys);
    return numOfWinPlys != 0;
  }

  private void backPropagation(StateNode node) {
    StateNode parent = node.getParent();
    while (parent != null) {
      parent.setReward(parent.getReward() + node.getReward());
      parent = parent.getParent();
    }
  }
}
