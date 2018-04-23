package AgentBot;

import com.github.blovemaple.mj.action.Action;
import com.github.blovemaple.mj.action.ActionType;
import com.github.blovemaple.mj.game.GameContextPlayerView;
import com.github.blovemaple.mj.local.AbstractBot;
import com.github.blovemaple.mj.object.PlayerInfo;
import com.github.blovemaple.mj.object.PlayerInfoPlayerView;
import com.github.blovemaple.mj.object.PlayerLocation;
import com.github.blovemaple.mj.object.Tile;
import com.github.blovemaple.mj.object.TileGroupPlayerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.blovemaple.mj.action.standard.StandardActionType.BUHUA;
import static com.github.blovemaple.mj.action.standard.StandardActionType.WIN;

/**
 * For CS5100 Final Project.
 * Agent bot.
 */
public class Agent extends AbstractBot {
  private PlayerLocation playerLocation;

//  private OnePlayerStrategy strategy = OnePlayerStrategy.getInstance();
  private OnePlayerStrategy strategy = new OnePlayerStrategy();
  private int round = 0;

  public Agent(String name) {
    super(name);
  }

  public Agent() {
    this("Agent");
    strategy.setParameter(10,5,1,1,-1);
  }


  @Override
  protected Action chooseCpgdAction(GameContextPlayerView contextView, Set<ActionType> actionTypes, List<Action> actions) throws InterruptedException {
    round++;
    System.out.println("round " + round + ": ");
    System.out.println("Agent in hand: " + getCurrentHand(contextView));
    if(actions.size() ==1) return actions.get(0);

    if (actionTypes.contains(WIN))
      return new Action(WIN);

    if (actionTypes.contains(BUHUA)) {
      Collection<Set<Tile>> buhuas = BUHUA.getLegalActionTiles(contextView);
      if (!buhuas.isEmpty()) {
        return new Action(BUHUA, buhuas.iterator().next());
      }
    }

//    if(actionTypes.contains(ZHIGANG))
//      return new Action(ZHIGANG);
//
//    if(actionTypes.contains(BUGANG))
//      return new Action(BUGANG);
//
//    if(actionTypes.contains(ANGANG))
//      return new Action(ANGANG);

    List<Action> notDiscard = actions.stream().filter(action -> {
              if(action == null || action.getType()== null || action.getType()
                      .name() == null)
                return false;
              return !action.getType().name().equalsIgnoreCase("discard");
    }
            ).collect(Collectors.toList());
    if(!notDiscard.isEmpty()) return notDiscard.get(0);
    strategy.setTiles(new ArrayList<>(getCurrentHand(contextView)),actions,getUnknownTiles
            (contextView.getMyInfo(),contextView));
//    System.out.println("Actions: " + actions.toString());
    int[] sizesForPlayer = new int[3];
    int index = 0;
    for (Map.Entry<PlayerLocation, PlayerInfoPlayerView> view: contextView.getTableView().getPlayerInfoView().entrySet()) {
      if (view.getKey().equals(contextView.getMyLocation())) {
        continue;
      }
      sizesForPlayer[index++] = view.getValue().getAliveTileSize();
    }
    strategy.setRound(round);
    strategy.setAliveTileSizes(sizesForPlayer);
    return strategy.discardOnePlayerStategy();
  }


  /**
   * Tiles in hand.
   */
  private Set<Tile> getCurrentHand(GameContextPlayerView contextView) {
    return contextView.getMyInfo().getAliveTiles();
  }

  private List<Tile> getUnknownTiles(PlayerInfo myInfo, GameContextPlayerView context) {
    List<Tile> unknownTiles = new ArrayList<>
            (context.getGameStrategy().getAllTiles());
//    unknownTiles.removeAll(myInfo.getDiscardedTiles());
    unknownTiles.removeAll(myInfo.getAliveTiles());
//    for(TileGroup group:myInfo.getTileGroups()){
//      unknownTiles.removeAll(group.getTiles());
//    }
    context.getTableView()
            .getPlayerInfoView().forEach(
            (location, playerInfoPlayerView) ->
            {
              unknownTiles.removeAll(playerInfoPlayerView.getDiscardedTiles());
              for (TileGroupPlayerView group : playerInfoPlayerView.getTileGroups()) {
                if(group == null || group.getTiles() == null) continue;
                unknownTiles.removeAll(group.getTiles());
              }
            });
    return unknownTiles;
  }
}
