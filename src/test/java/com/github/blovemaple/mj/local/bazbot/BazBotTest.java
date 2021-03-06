package com.github.blovemaple.mj.local.bazbot;

import java.util.Set;

import static com.github.blovemaple.mj.object.TileSuit.*;
import static com.github.blovemaple.mj.object.TileRank.NumberRank.*;
import static com.github.blovemaple.mj.object.TileRank.ZiRank.*;

import com.github.blovemaple.mj.object.Tile;
import com.github.blovemaple.mj.object.TileType;

@SuppressWarnings("unused")
public class BazBotTest {
  public static void main(String[] args) {
    bazBotAliveTilesTest();
  }

  private static void bazBotAliveTilesTest() {
    Set<Tile> aliveTiles = Set.of(//
            Tile.of(TileType.of(WAN, YI), 0), //
            Tile.of(TileType.of(WAN, ER), 0), //
            Tile.of(TileType.of(WAN, SAN), 0), //
            Tile.of(TileType.of(WAN, SI), 0), //
            Tile.of(TileType.of(WAN, WU), 0), //
            Tile.of(TileType.of(WAN, QI), 0), //
            Tile.of(TileType.of(WAN, QI), 1) //
    );
    BazBotAliveTiles at = BazBotAliveTiles.of(aliveTiles);
    at.tileTypesToWin();
    System.out.println("Neighborhoods: ");
    at.neighborhoods().forEach(System.out::println);
    System.out.println("Tile type to win: ");
    at.tileTypesToWin().forEach(System.out::println);
  }
}
