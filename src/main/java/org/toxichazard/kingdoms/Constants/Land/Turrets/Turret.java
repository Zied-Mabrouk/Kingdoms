package org.toxichazard.kingdoms.Constants.Land.Turrets;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turret{

    protected int range;
    protected Material head;
    protected int level=0;
    protected Block block;
    protected boolean activated;
    protected BukkitTask task;
    protected long accuracy;
    protected double speed;
    protected TurretType type;
    protected int repetition;
    protected String title,lore;
    protected String owner;

    public Turret()
    {

    }
    public Turret(Block block)
    {
        this.block=block;
    }

    public Turret(int level, Block block)
    {
        this.level=level;
        this.block=block;
    }

    public void setActivated(boolean activated) {
        if(activated) {
            this.task = new BukkitRunnable() {
                @Override
                public void run() {
                    if(Turret.isOnFence(block))
                        Shoot();
                }
            }.runTaskTimer(main.getPlugin(),0,this.repetition);
        }
        else {
            this.task.cancel();
        }

        this.activated = activated;
    }

    public static boolean isOnFence(Block block)
    {
        return (block.getRelative(0,-1,0).getType().toString().endsWith("_FENCE"));
    }

    public double getDistance(double x,double y)
    {
        return Math.abs(x-y);
    }
    public boolean checkApproximity(Location from,Location to,Location tmp)
    {
        if( getDistance(from.getX(),to.getX()) < getDistance(from.getX(),tmp.getX()))
            return false;

        if( getDistance(from.getY(),to.getY()) < getDistance(from.getY(),tmp.getY()))
            return false;

        if( getDistance(from.getZ(),to.getZ()) < getDistance(from.getZ(),tmp.getZ()))
            return false;

        return true;
    }

    public boolean checkTrajectoryIsNotObstructed(Entity target,double part)
    {
        Location To = target.getLocation().clone().add(0.0D, part, 0.0D);
        Vector ToVector =To.toVector();

        Location From = this.block.getLocation().clone().add(0.5D, 0.75D, 0.5D);
        Vector FromVector = From.toVector();


        Location tmp = From.clone();
        Vector direction = ToVector.subtract(FromVector);
        direction.normalize();

        Block block=null;
        int q = 3;
        while(checkApproximity(From,To,tmp))
        {
            block =target.getWorld().getBlockAt(tmp);
            if(!block.getType().equals(Material.AIR)
                    && !main.turrets.contains(block.getType()) && block.getBoundingBox().getVolume()==1
            ) {
                return false;
            }
            tmp.add(direction.getX()/q,direction.getY()/q,direction.getZ()/q);

        }
        return true;
    }

    public HashMap<Float,Entity> AimForPart(Land land,Float part,HashMap<Float,Entity> entities)
    {
        for(Entity entity: this.block.getLocation().getWorld().getNearbyEntities(this.block.getLocation(),this.range,this.range,this.range))
        {
            if (entity instanceof Monster) {
                if(!main.getInvades().containsKey(entity.getUniqueId())) {
                    if (checkTrajectoryIsNotObstructed(entity, part)) {
                        entities.put(part, entity);
                    }
                }
                else{
                    Champion champion = main.getInvades().get(entity.getUniqueId());
                    if(!champion.getKingdom().getName().equals(getLand().getKingdomName()))
                    {
                        if (checkTrajectoryIsNotObstructed(entity, part)) {
                            entities.put(part, entity);
                        }
                    }
                }
            }

            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (!main.getCKPlayers().containsKey(player.getUniqueId())) {
                    if(checkTrajectoryIsNotObstructed(entity,part))
                        entities.put(part,entity);
                } else {

                    KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

                    if (!land.getKingdomName().equals(kPlayer.getKingdomName())) {
                        if(checkTrajectoryIsNotObstructed(entity,part))
                            entities.put(part,entity);
                    }
                }
            }

        }
        return entities;
    }

    public HashMap<Float,Entity> getClosestTarget(HashMap<Float,Entity>targets)
    {
        HashMap<Float,Entity> newOne = new HashMap<>();
        Entity target1 = (Entity) targets.values().toArray()[0];
        Float target1Int = (Float) targets.keySet().toArray()[0];


        for(Map.Entry<Float, Entity> entry: targets.entrySet())
        {
            if(this.block.getLocation().distance(entry.getValue().getLocation())<this.block.getLocation().distance(target1.getLocation())) {
                target1 = entry.getValue();
                target1Int = entry.getKey();
            }
        }
        newOne.put(target1Int,target1);
        return newOne;
    }


    public void Shoot()
    {


    }

    public Turret(int level, Block block, TurretType type)
    {
        this.level=level;
        this.block=block;
        this.type=type;
    }

    public Turret transform()
    {
        switch (type)
        {
            case ARROW:
                return (new Arrow(this));
            case FLAME:
                return (new Flame(this));
            case HEALING:
                return (new Healing(this));
        }
        return null;
    }

    public Turret Turret(Material m)
    {
        switch (m)
        {
            case SKELETON_SKULL:
                return new Arrow();


        }
        return null;
    }

    public ItemStack toItem()
    {
        ItemStack item = new ItemStack(this.head);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        switch (this.getClass().getSimpleName())
        {
            case "Arrow":
                meta.setDisplayName(main.getKingdomConfig().getArrow().title);
                lore.add(main.getKingdomConfig().getArrow().lore);
                break;
            case "Flame":
                meta.setDisplayName(main.getKingdomConfig().getFlame().title);
                lore.add(main.getKingdomConfig().getFlame().lore);
                break;
            case "Healing":
                meta.setDisplayName(main.getKingdomConfig().getHealing().title);
                lore.add(main.getKingdomConfig().getHealing().lore);
                break;
        }
        lore.add(ChatColor.DARK_RED+"Level :"+this.level);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return (item);

    }

    public boolean isTurret(ItemStack item)
    {
        return (item.hasItemMeta() && main.turretTitles.contains(item.getItemMeta().getDisplayName()));
    }


    public Block getBlock() {
        return block;
    }

    public TurretType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setType(TurretType type) {
        this.type = type;
    }


    public void toBlock(String kingdomName)
    {
        Location loc = block.getLocation();
        block.setType(this.head);
        block.setMetadata("Turret",new FixedMetadataValue(main.getPlugin(),
                type+","+loc.getX()+","+loc.getY()+","+loc.getZ()+","+level+","+kingdomName));
        setActivated(true);

    }

    public static boolean isTurretItem(ItemStack item)
    {
        String displayName = item.getItemMeta().getDisplayName();

        ItemStack itemStack = new ItemStack(item.getType());

        if(displayName.equals(itemStack.getItemMeta().getDisplayName()))
            return false;

        if(displayName.endsWith("Turret"))
            return true;

        return false;
    }

    public void Break(Player player)
    {
        Land land = main.getCLands().get(block.getLocation().getChunk().toString());
        ItemStack item = toItem();
        block.getWorld().dropItemNaturally(block.getLocation(),item);
        setActivated(false);

        block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,block.getLocation(),40,.5,.5,.5,0);
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,2.0F,1.0F);
        land.getTurrets().remove(block.getLocation().toString());
        player.sendMessage(ChatColor.GREEN+"Turret broken !");
        LandsUtil.update(land.getChunk());

        block.removeMetadata(land.getKingdomName(), main.getPlugin());
        block.setType(Material.AIR);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public static boolean isTurret(Block block)
    {
        return (block.hasMetadata("Turret"));
    }

    public Chunk getChunk()
    {
        return block.getChunk();
    }

    public Land getLand()
    {
        if(main.getCLands().containsKey(getChunk().toString()))
            return (main.getCLands().get(getChunk().toString()));
        return null;
    }

    public int getRange() {
        return range;
    }

    public double getSpeed() {
        return speed;
    }

    public int getRepetition() {
        return repetition;
    }

    public long getAccuracy() {
        return accuracy;
    }

    public Material getHead() {
        return head;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setHead(Material head) {
        this.head = head;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void setAccuracy(long accuracy) {
        this.accuracy = accuracy;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }
}
