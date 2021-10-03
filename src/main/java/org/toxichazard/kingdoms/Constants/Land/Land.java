package org.toxichazard.kingdoms.Constants.Land;

import org.bukkit.Chunk;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Land.Structures.Structure;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.main;
import org.toxichazard.kingdoms.Utils.LandsUtil;

import java.util.HashMap;

public class Land {

    private Chunk chunk;
    private Champion champion;
    private String kingdomName;
    private Structure structure;
    private HashMap<String, Turret> turrets= new HashMap<>();
    private Rank requiredRank = Rank.CITIZEN;



    public Land(Chunk chunk)
    {
        this.chunk = chunk;
        this.kingdomName = LandsUtil.getKingdomName(chunk.toString());
    }

    public Kingdom getConnectedEmpire()
    {
        return (main.getCKingdoms().get(kingdomName));
    }


    public HashMap<String, Turret> getTurrets() {
        return turrets;
    }

    public Structure getStructure() {
        return structure;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public void setRequiredRank(Rank requiredRank) {
        this.requiredRank = requiredRank;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public void setTurrets(HashMap<String, Turret> turrets) {
        this.turrets = turrets;
    }
    public void invade(KPlayer invader, Champion champion)
    {
        this.champion = champion;

        champion.prepareChampion(this,invader);
    }

    public Kingdom getKingdom()
    {
        return (main.getCKingdoms().get(kingdomName));
    }


}
