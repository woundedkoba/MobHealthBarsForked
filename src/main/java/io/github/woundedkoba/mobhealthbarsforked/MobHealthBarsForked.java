package io.github.woundedkoba.mobhealthbarsforked;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class MobHealthBarsForked extends JavaPlugin implements Listener {
  private HashMap<LivingEntity, BossBar> bars = new HashMap<>();
  private HashMap<Player, BukkitTask> bartasks = new HashMap<>();
  
  public void onEnable() {
    saveDefaultConfig();
    getServer().getPluginManager().registerEvents(this, (Plugin)this);
  }
  
  public void onDisable() {
    this.bars.forEach((k, v) -> v.removeAll());
    this.bartasks.forEach((k, v) -> v.cancel());
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("mobhealthbars")) {
      if (args.length < 1)
        return false; 
      switch (args[0]) {
        case "reload":
          reloadConfig();
          sender.sendMessage("Config reloaded.");
          break;
      } 
      return true;
    } 
    return false;
  }
  
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player) {
      if (!(event.getEntity() instanceof LivingEntity))
        return; 
      if (event.getEntity() instanceof org.bukkit.entity.Wither)
        return; 
      if (event.getEntity() instanceof org.bukkit.entity.EnderDragon)
        return; 
      Player player = (Player)event.getDamager();
      LivingEntity entity = (LivingEntity)event.getEntity();
      if (entity == player)
        return; 
      double maxhp = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
      double hp = entity.getHealth() - event.getFinalDamage();
      BossBar bar = this.bars.get(entity);
      if (bar == null) {
        bar = getBar(entity);
        bar.setProgress(Math.max(0.0D, Math.min(1.0D, 1.0D / maxhp * hp)));
        this.bars.put(entity, bar);
      } 
      bar.addPlayer(player);
      bar.setProgress(Math.max(1.0D / maxhp * hp, 0.0D));
      refreshBarcooldown(player, getConfig().getLong("duration"));
    } 
  }
  
  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    if (!(event.getEntity() instanceof LivingEntity))
      return; 
    destroyHealthbar((LivingEntity)event.getEntity());
  }
  
  @EventHandler
  public void onEntityRegainHealth(EntityRegainHealthEvent event) {
    if (!(event.getEntity() instanceof LivingEntity))
      return; 
    LivingEntity entity = (LivingEntity)event.getEntity();
    if (this.bars.containsKey(entity)) {
      double maxhp = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
      double hp = entity.getHealth() + event.getAmount();
      BossBar bar = this.bars.get(entity);
      bar.setProgress(Math.max(0.0D, Math.min(1.0D, 1.0D / maxhp * hp)));
    } 
  }
  
  @EventHandler
  public void onMobDamage(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof LivingEntity))
      return; 
    LivingEntity entity = (LivingEntity)event.getEntity();
    if (this.bars.containsKey(entity)) {
      double maxhp = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
      double hp = entity.getHealth() - event.getFinalDamage();
      BossBar bar = this.bars.get(entity);
      bar.setProgress(Math.max(0.0D, Math.min(1.0D, 1.0D / maxhp * hp)));
    } 
  }
  
  public void destroyHealthbar(LivingEntity entity) {
    BossBar bar = this.bars.get(entity);
    if (bar != null) {
      bar.removeAll();
      this.bars.remove(entity);
    } 
  }
  
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    destroyHealthbar(event.getEntity());
  }
  
  public BossBar getBar(LivingEntity entity) {
    BarColor color = BarColor.RED;
    BarStyle style = BarStyle.SOLID;
    for (String item : getConfig().getConfigurationSection("styles").getKeys(false)) {
      try {
        if (Class.forName("org.bukkit.entity." + item).isInstance(entity)) {
          String cColor = getConfig().getString("styles." + item + ".color");
          String cStyle = getConfig().getString("styles." + item + ".style");
          if (cColor != null)
            color = BarColor.valueOf(cColor); 
          if (cStyle != null)
            style = BarStyle.valueOf(cStyle); 
          break;
        } 
      } catch (Exception e) {
        getLogger().severe(item + " is not a valid entity type.");
        break;
      } 
    } 
    return Bukkit.createBossBar(entity.getName(), color, style);
  }
  
  private void refreshBarcooldown(final Player player, long delay) {
    BukkitTask task = this.bartasks.get(player);
    if (task != null) {
      task.cancel();
      this.bartasks.remove(player);
    } 
    BukkitTask newtask = new BarCooldownTask(player).runTaskLater((Plugin)this, delay);
    this.bartasks.put(player, newtask);
  }

  private class BarCooldownTask extends BukkitRunnable {
    private final Player player;
    
    public BarCooldownTask(Player player) {
      this.player = player;
    }

    @Override
    public void run() {
      for (Iterator<Map.Entry<LivingEntity, BossBar>> it = bars.entrySet().iterator(); it.hasNext(); ) {
        Map.Entry<LivingEntity, BossBar> entry = it.next();
        BossBar v = entry.getValue();
        if (v.getPlayers().contains(player)) {
          v.removePlayer(player);
          if (v.getPlayers().isEmpty()) {
            it.remove();
            bartasks.remove(player);
          } 
        } 
      } 
    }
  }
}
