package AgentBot;

import com.github.blovemaple.mj.object.TileSuit;
import com.github.blovemaple.mj.object.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generate a hand according to unknown tiles
 */
public class HandGenerator {

  public HandGenerator() {}

  //Random generate n combinations with total 36 sets,
  // player 1: 0-11, player 2: 12-23, player 3:24-35
  //steps to waiting: 0 or 1
  public List<List<List<TileType>>> randomHandsFor3Players(Map<TileSuit,
          int[]> grouped, int num, int stepsToWaiting){
    List<List<List<TileType>>> result = new ArrayList<>();
    List<List<TileType>> complete = genereate3Sets(grouped);
    List<List<TileType>> mayWait = new ArrayList<>();
    if(stepsToWaiting == 0){
      mayWait = genereate2Sets(grouped);
    }else if(stepsToWaiting == 1){
      mayWait = genereate2Sets(grouped);
    }


    for(int i = 0; i< num; i++){
      Collections.shuffle(complete);
      Collections.shuffle(mayWait);
      List<List<TileType>> comb = new ArrayList<>(complete);
      comb.add(11,mayWait.get(0));
      comb.add(23, mayWait.get(1));
      comb.add(mayWait.get(2));
      result.add(comb);
    }
    return result;
  }


  //Generate 12 complete sets. grouped will be updated.
  public List<List<TileType>> genereate3Sets(Map<TileSuit, int[]> grouped) {
    List<List<TileType>> result = new ArrayList<>();
    int count = 0;
    int loop = 0;
    while(count < 12 && loop < 10){
      set3Helper(grouped, result, count);
      loop ++;
    }
    return result;
  }

  public void set3Helper(Map<TileSuit, int[]> grouped, List<List<TileType>>
          result, int count ){
    for (Map.Entry<TileSuit, int[]> entry : grouped.entrySet()) {
      TileSuit suit = entry.getKey();
      int[] suitArray = entry.getValue();
      for (int i = 0; i < suitArray.length; i++) {
        List<TileType> set = new ArrayList<>();

        //triplet or quaternate
        if (suitArray[i] >= 3) {
          for (int j = 0; j < suitArray[i]; j++) {
            if (suit.equals(TileSuit.ZI)) {
              set.add(TileType.of(suit, i));
            } else {
              set.add(TileType.of(suit, i + 1));
            }
          }
          suitArray[i] = 0;
          result.add(set);
          count++;
          continue;
        }

        if (suit.equals(TileSuit.ZI)) continue;
        //sequence
        if (suitArray[i] >= 1 && i + 1 < suitArray.length && suitArray[i + 1] > 0
                && i + 2 < suitArray.length && suitArray[i + 2] > 0) {
          for (int j = 0; j < 3; j++) {
            set.add(TileType.of(suit, i + j + 1));
            suitArray[i + j]--;
          }
          result.add(set);
          count++;
        }
        if (count >= 12) break; // Only 12 sets are needed
      }
    }
  }


  //Generate 3 incomplete sets. grouped will be updated.
  public List<List<TileType>> genereate2Sets(Map<TileSuit, int[]> grouped) {
    List<List<TileType>> result = new ArrayList<>();
    int count = 0;
    for (Map.Entry<TileSuit, int[]> entry : grouped.entrySet()) {
      TileSuit suit = entry.getKey();
      int[] suitArray = entry.getValue();
      for (int i = 0; i < suitArray.length; i++) {
        List<TileType> set = new ArrayList<>();

        //triplet or quaternate
        if (suitArray[i] == 2) {
          if (suit.equals(TileSuit.ZI)) {
            set.add(TileType.of(suit, i));
            set.add(TileType.of(suit, i));
          } else {
            set.add(TileType.of(suit, i + 1));
            set.add(TileType.of(suit, i + 1));
          }
          suitArray[i] = 0;
          result.add(set);
          count++;
          continue;
        }

        if (suit.equals(TileSuit.ZI)) continue;
        //sequence
        if (suitArray[i] >= 1 && i + 1 < suitArray.length && suitArray[i + 1] > 0) {
          set.add(TileType.of(suit, i + 1));
          set.add(TileType.of(suit, i + 2));
          suitArray[i]--;
          suitArray[i + 1]--;
          result.add(set);
          count++;
        }
      }
    }
    return result;
  }


  //generate random 2 tile type. Not sure about the size. grouped will be
  // updated.
  public List<List<TileType>> generateRandom(Map<TileSuit, int[]> grouped) {
    List<List<TileType>> result = new ArrayList<>();
    for (Map.Entry<TileSuit, int[]> entry : grouped.entrySet()) {
      TileSuit suit = entry.getKey();
      int[] suitArray = entry.getValue();
      List<TileType> set = new ArrayList<>();
      for (int i = 0; i < suitArray.length; i++) {
        if (suitArray[i] > 0) {
          if (suit.equals(TileSuit.ZI)) {
            set.add(TileType.of(suit, i));
          } else {
            set.add(TileType.of(suit, i + 1));
          }
          suitArray[i]--;
        }
        if (set.size() == 2) {
          result.add(new ArrayList<>(set));
          set.clear();
        }
      }
    }
    return result;
  }


}
