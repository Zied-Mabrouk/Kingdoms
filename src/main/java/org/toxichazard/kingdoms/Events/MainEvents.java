package org.toxichazard.kingdoms.Events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgrade;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgradeType;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.NexusUpgrade;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.NexusUpgradeType;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.Nexus;
import org.toxichazard.kingdoms.Constants.Land.Structures.Structure;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Ammo;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Events.CustomEvents.*;
import org.toxichazard.kingdoms.Managers.NexusManager;
import org.toxichazard.kingdoms.Managers.TurretManager;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Settings.Upgrades.*;
import org.toxichazard.kingdoms.Utils.Converter;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.Utils.PlayerUtil;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainEvents implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Chunk exit = event.getFrom().getChunk();
        Chunk entrance = event.getTo().getChunk();

        if(!exit.equals(entrance)) {
            String exitChunkClaimer = "", entranceChunkClaimer = "";


            if (main.getCLands().containsKey(exit.toString()))
                exitChunkClaimer = main.getCLands().get(exit.toString()).getKingdomName();

            if (main.getCLands().containsKey(entrance.toString()))
                entranceChunkClaimer = main.getCLands().get(entrance.toString()).getKingdomName();

            if (!exitChunkClaimer.equals(entranceChunkClaimer))
                Bukkit.getServer().getPluginManager().callEvent(new MoveBetweenLandsEvent(entranceChunkClaimer, event.getPlayer()));
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Chunk chunk = event.getBlock().getChunk();

        if(main.getCLands().containsKey(chunk.toString()))
            Bukkit.getServer().getPluginManager().callEvent( new BlockBreakInLandEvent(event,main.getCLands().get(chunk.toString())) );
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        Chunk chunk = event.getBlock().getChunk();

        if(!main.getCLands().containsKey(chunk.toString()))
        {
            if(Turret.isTurretItem(event.getItemInHand()) || Structure.isStructureItem(event.getItemInHand()))
                event.setCancelled(true);
            return;
        }
            Bukkit.getServer().getPluginManager().callEvent( new BlockPlaceInLandEvent(event,main.getCLands().get(chunk.toString())) );
    }


    @EventHandler
    public void onConnect(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        if(PlayerUtil.isInKingdom(uuid))
        {
            KPlayer kPlayer = PlayerUtil.getKPlayer(uuid);
            if(!main.getCKPlayers().containsKey(uuid)){
                main.getCKPlayers().put(uuid,kPlayer);
            }
            kPlayer.updateScoreBoard();
        }
    }

    public boolean isAnyOneConnected(Kingdom kingdom, UUID uuid)
    {
        for(UUID id: kingdom.getMembers())
        {
            if(Bukkit.getOfflinePlayer(id).isOnline() && id!=uuid){
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        if (!main.getCKPlayers().containsKey(uuid))
            return;

        KPlayer kPlayer = main.getCKPlayers().get(uuid);
        Kingdom kingdom = kPlayer.getKingdom();

        if (kPlayer.getChampionInvading() != null) {
            UUID championUUID = kPlayer.getChampionInvading();
            main.getInvades().get(championUUID).kill();
            main.getInvades().remove(championUUID);
        }

        if (!isAnyOneConnected(kingdom, uuid)) {
            KingdomUtil.update(kingdom.getName());
            main.getCKingdoms().remove(kingdom.getName());
        }
        main.getCKPlayers().remove(uuid);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();


        if(main.getInvades().containsKey(entity.getUniqueId()))
        {
            Champion champion = main.getInvades().get(entity.getUniqueId());
            champion.getBossBar().setProgress(champion.getEntity().getHealth() / champion.getMaxHealth());
            champion.updateHealth();
        }
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {

        Entity entity = event.getDamager();


        if(entity instanceof Arrow && Ammo.isAmmo(entity)) {
            Ammo ammo = Converter.toAmmo(entity);
            Bukkit.getServer().getPluginManager().callEvent(new AmmoDamageEvent(ammo,event));
            return;
        }
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (main.getCKPlayers().containsKey(player.getUniqueId())) {

                KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
                kPlayer.setInFight(true);

                if (main.getInvades().containsKey(entity.getUniqueId())) {

                    Bukkit.getServer().getPluginManager().callEvent(new ChampionDamageEvent(event));
                }

            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        if(main.getInvades().containsKey(entity.getUniqueId()))
        {
            Champion champion = main.getInvades().get(entity.getUniqueId());
            main.getInvades().remove(entity.getUniqueId());
            champion.kill();
            event.getDrops().clear();
            event.setDroppedExp(0);
            Bukkit.broadcastMessage(main.getCLands().get(champion.getChunk().toString()).getKingdomName()+ " --> "+champion.getInvader().getKingdomName());
        }
    }


    @EventHandler
    public void onArrowShot(ProjectileHitEvent event)
    {

        Entity entity= event.getEntity();
        if((entity instanceof Arrow)) {
            if(Ammo.isAmmo(entity))
            {
                entity.remove();
                return;
            }
            Arrow arrow = (Arrow) entity;
            Player player = (Player) arrow.getShooter();
            player.teleport(arrow.getLocation());
        }

    }


    @EventHandler
    public void onLoad(ChunkLoadEvent event)
    {
        Chunk chunk = event.getChunk();

        if(main.getCLands().containsKey(chunk.toString())) {
            Land land =main.getCLands().get(chunk.toString());
            if(land.getTurrets().size()!=0)
            {
                for(Map.Entry<String, Turret> entry: land.getTurrets().entrySet())
                {
                    entry.getValue().setActivated(true);
                }
            }
        }
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event)
    {
        Chunk chunk = event.getChunk();

        if(main.getCLands().containsKey(chunk.toString())) {
            Land land =main.getCLands().get(chunk.toString());
            if(land.getTurrets().size()!=0)
            {
                for(Map.Entry<String, Turret> entry: land.getTurrets().entrySet())
                {
                    entry.getValue().setActivated(false);
                }
            }
        }

    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();

        if(lastDamageCause != null) {

            if (lastDamageCause instanceof EntityDamageByEntityEvent){

                EntityDamageByEntityEvent lastEntityDamageEvent = (EntityDamageByEntityEvent) lastDamageCause;

                if (lastEntityDamageEvent.getDamager() instanceof Arrow) {

                    if (lastEntityDamageEvent.getDamager().hasMetadata("Ammo"))
                    {

                        event.setDeathMessage(null);

                        Entity entity = lastEntityDamageEvent.getDamager();
                        Player target = (Player) lastEntityDamageEvent.getEntity();
                        String str = entity.getMetadata("Ammo").get(0).asString();
                        entity.remove();
                        String[] arrOfStr = str.split(",",3);
                        Bukkit.broadcastMessage(target.getName()+" was killed by a "+ ChatColor.GOLD+arrOfStr[0].toLowerCase()+" turret");
                    }
                }

            }
        }
    }


    @EventHandler
    public void onTargetChange(EntityTargetEvent event)
    {
        Entity entity = event.getEntity();

        if(main.getInvades().containsKey(entity.getUniqueId()))
        {
            Champion champion = main.getInvades().get(event.getEntity().getUniqueId());
            Player player = champion.getInvader().getPlayer();

            if(event.getTarget()==null)
            {
                player.teleport(champion.getEntity().getLocation());
                champion.getEntity().setTarget(player);
                return;
            }

            if(!(event.getTarget() instanceof Player))
            {
                player.teleport(champion.getEntity().getLocation());
                champion.getEntity().setTarget(player);
                return;
            }
        }
    }


    @EventHandler
    public void onRegain(EntityRegainHealthEvent event)
    {
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if(main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            if(main.getCLands().containsKey(player.getLocation().getChunk().toString()))
            {
                Land land = main.getCLands().get(player.getLocation().getChunk().toString());

                KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

                if(kPlayer.isInFight())
                    return;

                if(!land.getKingdomName().equals(kPlayer.getKingdomName()))
                    return;

                event.setAmount(event.getAmount()+(event.getAmount()*(0.2*land.getKingdom().getRegenerationUpgrade().getLevel())));
            }
        }

    }


    @EventHandler
    public void onLiquidMove(BlockFromToEvent event) {
        Chunk source = event.getBlock().getChunk();
        Chunk goTo = event.getToBlock().getChunk();
        String exitChunkClaimer="",entranceChunkClaimer="";

        if(source.equals(goTo))
            return;

        if(main.getCLands().containsKey(source.toString()))
            exitChunkClaimer = main.getCLands().get(source.toString()).getKingdomName();

        if(main.getCLands().containsKey(goTo.toString()))
            entranceChunkClaimer = main.getCLands().get(goTo.toString()).getKingdomName();

        if(entranceChunkClaimer.isEmpty())
            return;

        if(entranceChunkClaimer.equals(exitChunkClaimer))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Chunk chunk = event.getBlock().getChunk();

        if(!main.getCLands().containsKey(chunk.toString()))
        {
            Land land = main.getCLands().get(chunk.toString());
            Player player = event.getPlayer();

            if(!main.getCKPlayers().containsKey(player.getUniqueId()))
            {
                event.setCancelled(true);
                player.sendMessage(Messages.Not_Here);
                return;
            }

            KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

            if(land.getKingdomName().equals(kPlayer.getKingdomName()))
                return;
            event.setCancelled(true);
            player.sendMessage(Messages.Not_Here);
        }

    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Chunk chunk = event.getBlock().getChunk();

        if(!main.getCLands().containsKey(chunk.toString()))
        {
            Land land = main.getCLands().get(chunk.toString());
            Player player = event.getPlayer();

            if(!main.getCKPlayers().containsKey(player.getUniqueId()))
            {
                event.setCancelled(true);
                player.sendMessage(Messages.Not_Here);
                return;
            }

            KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

            if(land.getKingdomName().equals(kPlayer.getKingdomName()))
                return;
            event.setCancelled(true);
            player.sendMessage(Messages.Not_Here);
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {

        String pistonChunk= event.getBlock().getChunk().toString();
        String blockChunk = event.getBlock().getRelative(event.getDirection().getOppositeFace()).getRelative(event.getDirection().getOppositeFace()).getChunk().toString();
        String block="",piston="";

        if(blockChunk.equals(pistonChunk))
            return;

        if(main.getCLands().containsKey(pistonChunk))
            piston = main.getCLands().get(pistonChunk).getKingdomName();

        if(main.getCLands().containsKey(blockChunk))
            block = main.getCLands().get(blockChunk).getKingdomName();

        if(block.isEmpty())
            return;

        if(block.equals(piston))
            return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event)
    {
        Block piston = event.getBlock();
        Block block;

        if(event.getBlocks().size()>1)
            block= event.getBlocks().get(event.getBlocks().size()-1).getRelative(event.getDirection());
        else
            block =event.getBlock().getRelative(event.getDirection()).getRelative(event.getDirection());

        String blockClaimer="",pistonClaimer="";

        if(main.getCLands().containsKey(piston.getChunk().toString()))
            pistonClaimer = main.getCLands().get(piston.getChunk().toString()).getKingdomName();

        if(main.getCLands().containsKey(block.getChunk().toString()))
            blockClaimer = main.getCLands().get(block.getChunk().toString()).getKingdomName();

        if(blockClaimer.isEmpty())
            return;

        if(blockClaimer.equals(pistonClaimer))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public static void onExplode(EntityExplodeEvent event) {

        List<Block> blocks = event.blockList();
        List<Block> remove = new ArrayList<>();

        List<Chunk> chunks = new ArrayList<>();

        for(Block b : blocks)
        {

            Chunk chunk = b.getChunk();
            if(chunks.size() == 4)
                break;
            if(chunks.contains(chunk))
                continue;

            chunks.add(chunk);
            if(main.getCLands().containsKey(chunk.toString())) {

                Land land = main.getCLands().get(chunk.toString());
                System.out.println(land.getKingdom().getTntUpgrade().getLevel());
                if (land.getKingdom().getTntUpgrade().getLevel() != 0) {
                    for(Block bl: event.blockList())
                    {
                        if(bl.getChunk().equals(chunk))
                            remove.add(bl);
                    }
                }
                else{
                    if (land.getStructure() != null && blocks.contains(land.getStructure().getBlock()))
                        remove.add(land.getStructure().getBlock());
                if (!land.getTurrets().isEmpty()) {
                    for (Map.Entry<String, Turret> entry : land.getTurrets().entrySet()) {
                        if (blocks.contains(entry.getValue().getBlock()))
                            remove.add(entry.getValue().getBlock());
                    }
                }
            }

            }

        }

        for(Block r: remove)
            event.blockList().remove(r);

    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {

        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        Block block = event.getClickedBlock();

        Player player = event.getPlayer();

        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
            return;

        KPlayer kPlayer =main.getCKPlayers().get(player.getUniqueId());
        Land land = main.getCLands().get(block.getChunk().toString());



        if(Turret.isTurret(block))
        {
            String meta = block.getMetadata("Turret").get(0).asString();
            String[] metas = meta.split(",",6);

            if(metas[5].equals(kPlayer.getKingdomName())) {

                kPlayer.setLookingAtStructureInventory(true);
                Turret turret = land.getTurrets().get(block.getLocation().toString());
                kPlayer.setInventoryNeeded("TURRET");
                kPlayer.setTurretClicked(block);
                TurretManager.openMainGUI(player, turret);
            }
            else
            {
                player.sendMessage(Messages.Turret_Interact);
            }
            return;
        }


        if(Nexus.isNexus(block)) {
            if(block.getMetadata("Structure").get(0).asString().equals(kPlayer.getKingdomName())) {
                kPlayer.setLookingAtStructureInventory(true);
                kPlayer.setInventoryNeeded("NEXUS");
            }
            else
            {
                player.sendMessage(Messages.Nexus_Interact);
            }
            return;
        }
    }

    @EventHandler
    public void onNexusInteract(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();

        if(!event.getInventory().getType().equals(InventoryType.BEACON))
            return;

        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
            return;

        KPlayer kPlayer =main.getCKPlayers().get(player.getUniqueId());

        if(main.getCLands().containsKey(player.getLocation().getChunk().toString()))
        {
            Land land =main.getCLands().get(player.getLocation().getChunk().toString());
            if(!land.getKingdomName().equals(kPlayer.getKingdomName()))
                event.setCancelled(true);
        }



        if(kPlayer.isLookingAtStructureInventory()) {
            event.setCancelled(true);
            player.closeInventory();
            if(kPlayer.getInventoryNeeded().equals("NEXUS"))
                NexusManager.openMainGUI(player,kPlayer.getKingdom());
        }

    }


    public void upgradeClicked(Kingdom kingdom, UpgradeSetting upgradeSetting, ChampionUpgrade championUpgrade,Player player)
    {
        if(kingdom.getRP()>= upgradeSetting.cost && championUpgrade.getLevel()<upgradeSetting.maxLevel)
        {
            kingdom.setNeedsUpdate(true);
            championUpgrade.setLevel(championUpgrade.getLevel()+1);
            kingdom.setRP(kingdom.getRP()-upgradeSetting.cost);
            kingdom.updateMembersScoreBoard();
            NexusManager.openChampionUpgradeGUI(player,kingdom);
            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_DEATH,2.0F,1.0F);
        }
        else {
            player.playSound(player.getLocation(), Sound.ENTITY_HOSTILE_BIG_FALL,2.0F,1.0F);
        }
    }

    public void upgradeClicked(Kingdom kingdom, UpgradeSetting upgradeSetting, NexusUpgrade nexusUpgrade,Player player)
    {
        if(kingdom.getRP()>= upgradeSetting.cost && nexusUpgrade.getLevel()<upgradeSetting.maxLevel)
        {
            kingdom.setNeedsUpdate(true);
            nexusUpgrade.setLevel(nexusUpgrade.getLevel()+1);
            kingdom.setRP(kingdom.getRP()-upgradeSetting.cost);
            kingdom.updateMembersScoreBoard();
            NexusManager.openMainGUI(player,kingdom);
            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_DEATH,2.0F,1.0F);
        }
        else {
            player.playSound(player.getLocation(), Sound.ENTITY_HOSTILE_BIG_FALL,2.0F,1.0F);
        }
    }


    @EventHandler
    public void onClickNexusGUI(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
        Kingdom kingdom = main.getCKingdoms().get(kPlayer.getKingdomName());
        if (event.equals(null))
            return;
        if (event.getSlot() < 0)
            return;


        if (main.turretTitles.contains(event.getView().getTitle())) {
            event.setCancelled(true);
            TurretManager.onClickTurretGUI(event, kPlayer);
            return;
        }
        switch (event.getView().getTitle()) {
            case Messages.nexusGUITitle:
                event.setCancelled(true);

                if(event.getCurrentItem()==null)
                    return;
                switch (event.getCurrentItem().getType()) {
                    case WHEAT:
                        NexusManager.openResourcePointsConverterGUI(player);
                        break;
                    case DIAMOND:
                        NexusManager.openChampionUpgradeGUI(player, kingdom);
                        break;
                    case POTION:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getRegeneration(),
                                kingdom.getNexusUpgrade(NexusUpgradeType.REGENERATION.getIndex()),
                                player
                        );
                        break;
                }
                break;


            case Messages.championUpgradeGUITitle:
                event.setCancelled(true);
                if(event.getCurrentItem()==null)
                    return;
                switch (event.getCurrentItem().getType()) {
                    case APPLE:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getHealth(),
                                kingdom.getChampionUpgrade(ChampionUpgradeType.HEALTH.getIndex()),
                                player
                        );
                        break;
                    case ARMOR_STAND:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getGear(),
                                kingdom.getChampionUpgrade(ChampionUpgradeType.GEAR.getIndex()),
                                player
                        );
                        break;
                    case LAVA_BUCKET:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getLava(),
                                kingdom.getChampionUpgrade(ChampionUpgradeType.LAVA.getIndex()),
                                player
                        );
                        break;
                    case NETHERITE_SWORD:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getDamage(),
                                kingdom.getChampionUpgrade(ChampionUpgradeType.DAMAGE.getIndex()),
                                player
                        );
                        break;

                    case FIREWORK_ROCKET:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getHits(),
                                kingdom.getChampionUpgrade(ChampionUpgradeType.HITS.getIndex()),
                                player
                        );
                        break;
                    case FEATHER:
                        upgradeClicked(
                                kingdom,
                                main.getKingdomConfig().getSpeed(),
                                kingdom.getChampionUpgrade(ChampionUpgradeType.SPEED.getIndex()),
                                player
                        );
                        break;

                }
                break;
        }
    }


    public int getItemValue(Material m)
    {
        switch (m)
        {
            case IRON_INGOT:
                return 5;
            case GOLD_INGOT:
                return 10;
            case DIAMOND:
                return 20;
            case EMERALD:
                return 50;
            case IRON_BLOCK:
                return 45;
            case GOLD_BLOCK:
                return 90;
            case DIAMOND_BLOCK:
                return 180;
            case EMERALD_BLOCK:
                return 450;
        }
        return 1;
    }


    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event)
    {

        if(!main.getCKPlayers().containsKey(event.getPlayer().getUniqueId()))
            return;

        KPlayer kPlayer = main.getCKPlayers().get(event.getPlayer().getUniqueId());

        switch(event.getView().getTitle())
        {
            case Messages.RPGUITitle:
                Kingdom kingdom = main.getCKingdoms().get(kPlayer.getKingdomName());
                int RP = 0;
                for(int i=0;i<event.getInventory().getSize();i++)
                {
                    if(event.getInventory().getItem(i)!=null)
                    {
                        if(!main.forbiddenItems.contains(event.getInventory().getItem(i).getType()))
                            RP+=(getItemValue(event.getInventory().getItem(i).getType())* event.getInventory().getItem(i).getAmount());
                        else
                            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(),event.getInventory().getItem(i));
                    }
                }
                if(RP!=0) {
                    kingdom.addRP(RP);
                    kingdom.updateMembersScoreBoard();
                    kingdom.setNeedsUpdate(true);
                }
                kPlayer.setLookingAtStructureInventory(false);
                break;
            case Messages.championUpgradeGUITitle:
            case Messages.nexusGUITitle:
                kPlayer.setLookingAtStructureInventory(false);
                break;
        }

    }


}
