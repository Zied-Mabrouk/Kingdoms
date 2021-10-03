package org.toxichazard.kingdoms.Constants.Kingdom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.main;

public class Champion {


    private String kingdomName;
    private Zombie entity;
    private KPlayer invader;
    private BossBar bossBar;
    private Chunk chunk;
    private int hit=0;

    public Champion(Zombie e, KPlayer invader, Chunk chunk)
    {
        this.entity=e;
        this.invader=invader;
        this.chunk=chunk;
    }
    public void getGearByLevel(int lv)
    {
        if(lv ==1)
            return;
        String madeOf="LEATHER_";
        switch (lv)
        {
            case 2:
                madeOf="LEATHER_";
                break;
            case 3:
                madeOf="GOLDEN_";
                break;
            case 4:
                madeOf="IRON_";
                break;
            case 5:
                madeOf="DIAMOND_";
                break;
            case 6:
                madeOf="NETHERITE_";
                break;
        }
        entity.getEquipment().setHelmet(new ItemStack(Material.getMaterial(madeOf+"HELMET")));
        entity.getEquipment().setChestplate(new ItemStack(Material.getMaterial(madeOf+"CHESTPLATE")));
        entity.getEquipment().setLeggings(new ItemStack(Material.getMaterial(madeOf+"LEGGINGS")));
        entity.getEquipment().setBoots(new ItemStack(Material.getMaterial(madeOf+"BOOTS")));
    }
    public void prepareChampion(Land land, KPlayer invader)
    {
        this.kingdomName = land.getKingdomName();
        if(!main.getCKingdoms().containsKey(kingdomName))
            main.getCKingdoms().put(kingdomName, KingdomUtil.getKingdom(kingdomName));

        Kingdom kingdom = main.getCKingdoms().get(kingdomName);
        Player player = invader.getPlayer();
        getGearByLevel(kingdom.getChampionGearUpgrade().getLevel());

       setMaxHealth(150+(kingdom.getChampionHealthUpgrade().getLevel()*50));
       setMovementSpeed(kingdom.getChampionSpeedUpgrade().getLevel());
       setDamage(kingdom.getChampionDamageUpgrade().getLevel());
       setRange();

        entity.setMetadata(String.valueOf(invader.getPlayer().getUniqueId()),new FixedMetadataValue(main.getPlugin(),kingdomName));

        entity.setTarget((LivingEntity)player);
        entity.setAdult();
        entity.setCustomName(ChatColor.RED+kingdomName+"'s Champion");
        entity.setCustomNameVisible(true);
        String health = ChatColor.RED+"["+entity.getHealth() +" / "+getMaxHealth()+"]";
        bossBar =  Bukkit.getServer().createBossBar(ChatColor.GOLD+kingdomName+"'s Champion  "+health, BarColor.RED, BarStyle.SOLID);
        bossBar.setVisible(true);
        bossBar.setProgress(1);
        bossBar.addPlayer(player);

    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }





    public Zombie getEntity() {
        return entity;
    }


    public Chunk getChunk() {
        return chunk;
    }

    public Land getLand() {
        return (main.getCLands().get(chunk.toString()));
    }

    public void setMovementSpeed(int m){
        double attr = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(attr+(attr*m*0.1));
    }
    public void setMaxHealth(double m){
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(m);
        entity.setHealth(m);
    }
    public double getMaxHealth(){return entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();}

    public void setDamage(int m){
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue()+(m*0.75));
    }
    public void setRange(){
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getBaseValue()+15);
    }
    public void updateHealth()
    {
        String health = ChatColor.RED+"["+(int)entity.getHealth()+" / "+getMaxHealth()+"]";
        bossBar.setTitle(ChatColor.GOLD+kingdomName+"'s Champion  "+health);
    }

    public Kingdom getKingdom()
    {
        return main.getCKingdoms().get(kingdomName);
    }

    public BossBar getBossBar() {
        return bossBar;
    }
    public void DisableBar()
    {
        bossBar.setVisible(false);
        bossBar=null;
    }

    public KPlayer getInvader() {
        return invader;
    }

    public void kill()
    {
        bossBar.removeAll();
        DisableBar();
        invader.setChampionInvading(null);
        entity.remove();
    }



}
