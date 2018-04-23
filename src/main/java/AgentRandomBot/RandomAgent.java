package AgentRandomBot;

import com.github.blovemaple.mj.action.Action;
import com.github.blovemaple.mj.action.ActionType;
import com.github.blovemaple.mj.game.GameContextPlayerView;
import com.github.blovemaple.mj.local.AbstractBot;
import com.github.blovemaple.mj.object.Tile;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.github.blovemaple.mj.action.standard.StandardActionType.ANGANG;
import static com.github.blovemaple.mj.action.standard.StandardActionType.BUGANG;
import static com.github.blovemaple.mj.action.standard.StandardActionType.BUHUA;
import static com.github.blovemaple.mj.action.standard.StandardActionType.WIN;
import static com.github.blovemaple.mj.action.standard.StandardActionType.ZHIGANG;

public class RandomAgent extends AbstractBot {
  public RandomAgent(String name) {
    super(name);
  }

  public RandomAgent() {
    this("Random");
  }

  @Override
  protected Action chooseCpgdAction(GameContextPlayerView contextView, Set<ActionType> actionTypes, List<Action> actions) throws InterruptedException {
    if (actionTypes.contains(WIN))
      return new Action(WIN);

    if (actionTypes.contains(BUHUA)) {
      Collection<Set<Tile>> buhuas = BUHUA.getLegalActionTiles(contextView);
      if (!buhuas.isEmpty()) {
        return new Action(BUHUA, buhuas.iterator().next());
      }
    }

    if(actionTypes.contains(ZHIGANG))
      return new Action(ZHIGANG);

    if(actionTypes.contains(BUGANG))
      return new Action(BUGANG);

    if(actionTypes.contains(ANGANG))
      return new Action(ANGANG);

    int numActions = actions.size();
    return actions.get((int)Math.random()*numActions);
  }
}
