package io.github.woundedkoba.mobhealthbarsforked;

import java.util.List;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

public class EnemyBar implements BossBar, Metadatable {
  public String getTitle() {
    return null;
  }
  
  public void setTitle(String s) {}
  
  public BarColor getColor() {
    return null;
  }
  
  public void setColor(BarColor barColor) {}
  
  public BarStyle getStyle() {
    return null;
  }
  
  public void setStyle(BarStyle barStyle) {}
  
  public void removeFlag(BarFlag barFlag) {}
  
  public void addFlag(BarFlag barFlag) {}
  
  public boolean hasFlag(BarFlag barFlag) {
    return false;
  }
  
  public void setProgress(double v) {}
  
  public double getProgress() {
    return 0.0D;
  }
  
  public void addPlayer(Player player) {}
  
  public void removePlayer(Player player) {}
  
  public void removeAll() {}
  
  public List<Player> getPlayers() {
    return null;
  }
  
  public void setVisible(boolean b) {}
  
  public boolean isVisible() {
    return false;
  }
  
  public void show() {}
  
  public void hide() {}
  
  public void setMetadata(String s, MetadataValue metadataValue) {}
  
  public List<MetadataValue> getMetadata(String s) {
    return null;
  }
  
  public boolean hasMetadata(String s) {
    return false;
  }
  
  public void removeMetadata(String s, Plugin plugin) {}
}
