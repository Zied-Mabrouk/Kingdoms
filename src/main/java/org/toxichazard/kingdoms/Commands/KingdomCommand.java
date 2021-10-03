package org.toxichazard.kingdoms.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Settings.Messages;

public class KingdomCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player player = (Player)sender ;
            if(args.length!=0){
                switch(args[0])
                {
                    case "claim":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Claim_Command);
                        }
                        else
                            Claim.command(player);
                        break;

                    case "disband":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Disband_Command);
                        }
                        else
                            Disband.command(player);

                        break;
                    case "nexus":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Nexus_Command);
                        }
                        else
                            NexusCmd.command(player);

                        break;

                    case "invade":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Invade_Command);
                        }
                        else
                            Invade.command(player);

                        break;

                    case "unclaim":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Unclaim_Command);
                        }
                        else
                            Unclaim.command(player);

                        break;

                    case "create":
                        if(args.length>2 || args.length==1)
                        {
                            if(args.length==1) {
                                player.sendMessage(Messages.Needs_Second_Argument);
                                player.sendMessage(Messages.Create_Command);
                            }
                            else
                            {
                                player.sendMessage(Messages.Third_Arg);
                                player.sendMessage(Messages.Create_Command);
                            }
                        }
                        else
                            Create.command(player,args[1]);

                        break;
                    case "invite":
                        if(args.length>2 || args.length==1)
                        {
                            if(args.length==1) {
                                player.sendMessage(Messages.Needs_Second_Argument);
                                player.sendMessage(Messages.Invite_Command);
                            }
                            else
                            {
                                player.sendMessage(Messages.Third_Arg);
                                player.sendMessage(Messages.Invite_Command);
                            }
                        }
                        else
                            Invite.command(player,args[1]);

                        break;

                    case "accept":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Accept_Command);
                        }
                        else
                            Accept.command(player);

                        break;
                    case "deny":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Deny_Command);
                        }
                        else
                            Deny.command(player);

                        break;
                    case "kick":
                        if(args.length>2 || args.length==1)
                        {
                            if(args.length==1) {
                                player.sendMessage(Messages.Needs_Second_Argument);
                                player.sendMessage(Messages.Kick_Command);
                            }
                            else
                            {
                                player.sendMessage(Messages.Third_Arg);
                                player.sendMessage(Messages.Kick_Command);
                            }
                        }
                        else
                            Kick.command(player,args[1]);

                        break;
                    case "home":
                        if(args.length>1)
                        {
                            player.sendMessage(Messages.Extra_Arg);
                            player.sendMessage(Messages.Home_Command);
                        }
                        else
                            Home.command(player);

                        break;
//                    case "sp":
//                        Bukkit.broadcastMessage("List Of Players");
//                        showPlayers.command();
//                        break;
//                    case "se":
//                        Bukkit.broadcastMessage("List Of Empires");
//                        showEmpires.command();
//                        break;
//                    case "sc":
//                        Bukkit.broadcastMessage("List Of Claims");
//                        showClaims.command();
//                        break;
//                    case "inv":
//                        Inv.command(player);
//                        break;
                    case "getTurret":
                        getTurret.command(player,args);
                        break;
                }
            }}

        return true;
    }

}
