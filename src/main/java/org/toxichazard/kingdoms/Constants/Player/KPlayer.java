package org.toxichazard.kingdoms.Constants.Player;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Managers.ScoreBoardManager;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.Utils.PlayerUtil;
import org.toxichazard.kingdoms.main;

import java.util.UUID;

public class KPlayer {

    private Player player;
    private String kingdomName;
    private Rank rank = Rank.CITIZEN;
    private boolean lookingAtStructureInventory=false;
    private String inventoryNeeded=null;
    private Block turretClicked=null;
    private boolean InFight;
    public BukkitTask task= null;
    private UUID championInvading=null;

    public KPlayer(Player player)
    {
        this.player=player;
        this.kingdomName = PlayerUtil.getKingdomName(player.getUniqueId());
        if(!main.getCKingdoms().containsKey(kingdomName)) {
            main.getCKingdoms().put(kingdomName, KingdomUtil.getKingdom(kingdomName));

        }

    }
    public KPlayer(Player player,Rank rank,String kingdomName)
    {
        this.player=player;
        this.rank=rank;
        this.kingdomName=kingdomName;
        if(!main.getCKingdoms().containsKey(kingdomName)) {
            main.getCKingdoms().put(kingdomName, KingdomUtil.getKingdom(kingdomName));

        }
    }

    public KPlayer(Player player,String kingdomName)
    {
        this.player=player;
        this.kingdomName=kingdomName;
        if(!main.getCKingdoms().containsKey(kingdomName)) {
            main.getCKingdoms().put(kingdomName, KingdomUtil.getKingdom(kingdomName));

        }
    }

    public Kingdom getKingdom()
    {
        return (main.getCKingdoms().get(this.kingdomName));
    }

    public Player getPlayer() {
        return player;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public Rank getRank() {
        return rank;
    }
    public void setChampionInvading(UUID championInvading) {
        this.championInvading = championInvading;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public void setInFight(boolean inFight) {
        InFight = inFight;
    }

    public boolean isLookingAtStructureInventory() {
        return lookingAtStructureInventory;
    }

    public String getInventoryNeeded() {
        return inventoryNeeded;
    }

    public Block getTurretClicked() {
        return turretClicked;
    }

    public boolean isInFight() {
        return InFight;
    }

    public BukkitTask getTask() {
        return task;
    }

    public UUID getChampionInvading() {
        return championInvading;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setLookingAtStructureInventory(boolean lookingAtStructureInventory) {
        this.lookingAtStructureInventory = lookingAtStructureInventory;
    }
    public boolean hasRequiredRank(Rank target) {
        return (this.rank.getRank() <= target.getRank());
    }

    public void setInventoryNeeded(String inventoryNeeded) {
        this.inventoryNeeded = inventoryNeeded;
    }

    public void setTurretClicked(Block turretClicked) {
        this.turretClicked = turretClicked;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void updateScoreBoard()
    {
        ScoreBoardManager.newScoreBoardManager(this);
    }
}
