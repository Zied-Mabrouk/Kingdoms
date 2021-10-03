package org.toxichazard.kingdoms.Constants.Kingdom;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgrade;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgradeType;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.NexusUpgrade;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.NexusUpgradeType;
import org.toxichazard.kingdoms.Managers.ScoreBoardManager;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Kingdom {

    private UUID king;
    private List<UUID> members =new ArrayList<>();
    private List<ChampionUpgrade> championUpgrades;
    private List<NexusUpgrade> nexusUpgrades;
    private String name;
    private int RP =10;
    private int numberOfLands =0;
    private Chunk nexusLand = null;
    private boolean needsUpdate = false;


    public Kingdom()
    {
        List<ChampionUpgrade> championUpgrades = Arrays.asList(
                new ChampionUpgrade(ChampionUpgradeType.HEALTH,1),
                new ChampionUpgrade(ChampionUpgradeType.SPEED,1),
                new ChampionUpgrade(ChampionUpgradeType.DAMAGE,1),
                new ChampionUpgrade(ChampionUpgradeType.GEAR,1),
                new ChampionUpgrade(ChampionUpgradeType.LAVA,0),
                new ChampionUpgrade(ChampionUpgradeType.HITS,0),
                new ChampionUpgrade(ChampionUpgradeType.DISARM,0)
        );
        List<NexusUpgrade> nexusUpgrades = Arrays.asList(
                new NexusUpgrade(NexusUpgradeType.REGENERATION,0),
                new NexusUpgrade(NexusUpgradeType.TNT,0)
        );
        this.championUpgrades =championUpgrades;
        this.nexusUpgrades =nexusUpgrades;
    }

    public String getName() {
        return name;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public UUID getKing() {
        return king;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKing(UUID king) {
        this.king = king;
    }

    public void setRP(int RP) {
        this.RP = RP;
    }


    public int getRP() {
        return RP;
    }

    public int getNumberOfLands() {
        return numberOfLands;
    }
    public int getClaimLimit()
    {
        return ( 3);
    }

    public void addOne()
    {
        numberOfLands++;
    }
    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
    public void addRP(int val)
    {
        RP+=val;
    }
    public void updateMembersScoreBoard()
    {
        for (UUID uuid :members)
        {
            if(main.getCKPlayers().containsKey(uuid))
                ScoreBoardManager.newScoreBoardManager(main.getCKPlayers().get(uuid));

        }
    }

    public Chunk getNexusLand() {
        return nexusLand;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public void setNexusLand(Chunk nexusLand) {
        this.nexusLand = nexusLand;
    }

    public void setNumberOfLands(int numberOfLands) {
        this.numberOfLands = numberOfLands;
    }

    public void removeOne()
    {
        numberOfLands--;
    }

    public void notifyJoin(String playerName)
    {
        for(UUID uuid: members)
        {
            if(main.getCKPlayers().containsKey(uuid))
                Bukkit.getPlayer(uuid).sendMessage(Messages.New_Member_Join(playerName));
        }
    }

    public Block getNexus()
    {
        return (main.getCLands().get(nexusLand.toString()).getStructure().getBlock());
    }


    public ChampionUpgrade getChampionHealthUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.HEALTH.getIndex());
    }
    public ChampionUpgrade getChampionSpeedUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.SPEED.getIndex());
    }
    public ChampionUpgrade getChampionDamageUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.DAMAGE.getIndex());
    }
    public ChampionUpgrade getChampionGearUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.GEAR.getIndex());
    }
    public ChampionUpgrade getChampionLavaUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.LAVA.getIndex());
    }
    public ChampionUpgrade getChampionHitsUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.HITS.getIndex());
    }
    public ChampionUpgrade getChampionDisarmUpgrade()
    {
        return championUpgrades.get(ChampionUpgradeType.DISARM.getIndex());
    }
    public ChampionUpgrade getChampionUpgrade(int index)
    {
        return championUpgrades.get(index);
    }
    public NexusUpgrade getNexusUpgrade(int index)
    {
        return nexusUpgrades.get(index);
    }

    public void setChampionUpgrades(List<ChampionUpgrade> championUpgrades) {
        this.championUpgrades = championUpgrades;
    }

    public void setNexusUpgrades(List<NexusUpgrade> nexusUpgrades) {
        this.nexusUpgrades = nexusUpgrades;
    }
    public NexusUpgrade getRegenerationUpgrade()
    {
        return nexusUpgrades.get(NexusUpgradeType.REGENERATION.getIndex());
    }
    public NexusUpgrade getTntUpgrade()
    {
        return nexusUpgrades.get(NexusUpgradeType.TNT.getIndex());
    }

    public static String getGearByLevel(int lv)
    {
        String val ="";
        if(lv ==1)
            val+= "No";
        switch (lv)
        {
            case 2:
                val+= "Leather";
                break;
            case 3:
                val+=  "Golden";
                break;
            case 4:
                val+=  "Iron";
                break;
            case 5:
                val+=  "Diamond";
                break;
            case 6:
                val+=  "Netherite";
                break;
        }
        val+=" armor";
        return val;
    }

    public String getGearByLevel()
    {
        int lv = getChampionGearUpgrade().getLevel();
        String val ="";
        if(lv ==1)
            val+= "No";
        switch (lv)
        {
            case 2:
                val+= "Leather";
                break;
            case 3:
                val+=  "Golden";
                break;
            case 4:
                val+=  "Iron";
                break;
            case 5:
                val+=  "Diamond";
                break;
            case 6:
                val+=  "Netherite";
                break;
        }
        val+=" armor";
        return val;
    }
    public int getHealthPoints()
    {
        return (150+(getChampionHealthUpgrade().getLevel()*50));
    }
}
