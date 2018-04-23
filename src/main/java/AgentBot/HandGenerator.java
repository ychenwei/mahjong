package AgentBot;

import com.github.blovemaple.mj.object.TileSuit;
import com.github.blovemaple.mj.object.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generate a hand according to unknown tiles
 */
public class HandGenerator {
  private Map<Map<TileSuit,int[]>,
          Map<String, List<List<TileType>>>> cache = new HashMap<>();
  private static String COMPLETE = "COMPLETE";
  private static String MAYWAIT = "MAYWAIT";

  public HandGenerator() {}

  //For key 1, 2, 3 represent player 1, 2, 3
  public Map<Integer, List<TileType>> randomHandsFor3Players(Map<TileSuit,
          int[]> grouped, int[] nums){
    Map<Integer, List<TileType>> map = new HashMap<>();
    int sizes = nums[0] + nums[1] + nums[2];
    List<List<TileType>> complete;
    List<List<TileType>> mayWait;
    if(cache.containsKey(grouped)){
      Map<String, List<List<TileType>>> cacheResult = cache.get(grouped);
      complete = cacheResult.get(COMPLETE);
      Collections.shuffle(complete);
      mayWait = cacheResult.get(MAYWAIT);
      Collections.shuffle(mayWait);
    } else{
      complete = genereate3Sets(grouped,sizes);
      Collections.shuffle(complete);
      mayWait = genereate2Sets(grouped);
      mayWait.addAll(generateRandom(grouped));
      Map<String, List<List<TileType>>> temp = new HashMap<>();
      temp.put(COMPLETE, complete);
      temp.put(MAYWAIT, mayWait);
      cache.put(grouped, temp);
    }
    System.out.println("complete" + complete.toString());
    System.out.println("mayWait" + mayWait.toString());
    for(int i = 1; i<4; i++){
      List<TileType> playerHand = randomHandFor1(nums[i-1], complete, mayWait);
      map.put(i,playerHand);
    }
    return map;
  }

  public List<TileType> randomHandFor1(int num, List<List<TileType>>
          complete, List<List<TileType>> mayWait){
    List<TileType> hand = new ArrayList<>();
    while (num > 0){
        if(num == 1){
          if(mayWait.isEmpty()) throw new IllegalArgumentException("mayWait "
                  + "is EMPTY!");
          for(int i = 0; i< mayWait.size();i++) {
            if (mayWait.get(i).size() >= 1) {
              hand.add(mayWait.get(i).get(0));
              mayWait.get(i).remove(0);
              num = 0;
              break;
            }
          }
        } else if(num == 2){
          if(mayWait.isEmpty()) throw new IllegalArgumentException("mayWait "
                  + "is EMPTY!");
          for(int i = 0; i< mayWait.size();i++){
            if(mayWait.get(i).size() == 2){
              hand.addAll(mayWait.get(i));
              mayWait.remove(i);
              num = 0;
              break;
            }
          }
      } else {
          if(complete.isEmpty()) throw new IllegalArgumentException("complete "
                  + "is EMPTY!");
          hand.addAll(complete.get(0));
          complete.remove(0);
          num -= 3;
        }
    }
    return hand;
  }

  //Generate complete sets according to other players' hand size.
  // grouped will be updated.
  public List<List<TileType>> genereate3Sets(Map<TileSuit, int[]> grouped,
                                             int size) {
    int num = size % (3 * 3);
    List<List<TileType>> result = new ArrayList<>();
    int count = 0;
    int loop = 0;
    while(count < num && loop < 10){
      set3Helper(grouped, result, count);
      loop ++;
    }
    return result;
  }

  public void set3Helper(Map<TileSuit, int[]> grouped, List<List<TileType>>
          result, int count){
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
    int loop = 0;
    while(count < 6 && loop <10){
      set2Helper(grouped, result, count);
      loop ++;
    }
    return result;
  }

  private void set2Helper(Map<TileSuit, int[]> grouped, List<List<TileType>>
          result, int count){
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
