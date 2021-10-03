package org.toxichazard.kingdoms.Settings;

import org.bukkit.ChatColor;

public class Messages {
    public static String Block_Break_In_Other_Kingdom = ChatColor.RED+"You cannot break a block in another kingdom's land";
    public static String Block_Place_In_Other_Kingdom = ChatColor.RED+"You cannot place a block in another kingdom's land";
    public static String Not_Here = ChatColor.RED+"You cannot do that here !";
    public static String Welcome_Wilderness_SubTitle = ChatColor.GREEN+"Welcome to the wilderness";
    public static String Welcome_Wilderness_Title = ChatColor.GREEN+"Wilderness";
    public static String Welcome_Home = ChatColor.BLUE+"Welcome Home !";
    public static String Kingdom_Found(String kingdomName) {return ChatColor.GRAY+"Be careful ! You are walking on "+ChatColor.GOLD+kingdomName+ChatColor.GRAY+"'s lands";}
    public static String Extra_Arg = ChatColor.RED+"This command doesn't require a second argument";
    public static String Third_Arg = ChatColor.RED+"This command doesn't require a third argument";
    public static String Needs_Second_Argument = ChatColor.RED+"This command requires a second argument";
    public static String Claim_Command= ChatColor.GREEN+"/e claim";
    public static String Disband_Command= ChatColor.GREEN+"/e disband";
    public static String Nexus_Command= ChatColor.GREEN+"/e nexus";
    public static String Invade_Command= ChatColor.GREEN+"/e invade";
    public static String Unclaim_Command= ChatColor.GREEN+"/e unclaim";
    public static String Accept_Command= ChatColor.GREEN+"/e accept";
    public static String Deny_Command= ChatColor.GREEN+"/e deny";
    public static String Home_Command= ChatColor.GREEN+"/e home";
    public static String Create_Command= ChatColor.GREEN+"/e create [Kingdom Name]";
    public static String Kick_Command= ChatColor.GREEN+"/e kick [Player Name]";
    public static String Invite_Command= ChatColor.GREEN+"/e invite [Player Name]";
    public static String Others_Kingdom_Interact= ChatColor.RED+"This Land Doesn't Belong To You";
    public static String Invade_Own_Land= ChatColor.RED+"This land belongs to your kingdom !";
    public static String Kingdom_Necessity= ChatColor.RED+"You must be in an kingdom to do that !";
    public static String Not_Claimed_Chunk= ChatColor.RED+"This Land Isn't Claimed";
    public static String Structure_Place_On_Wilderness= ChatColor.RED+"You must claim the land in order to put a structure on it";
    public static String Player_Not_Found= ChatColor.RED+"This player is not connected or doesn't even exist";
    public static String Player_Already_In_Kingdom= ChatColor.RED+"This player is already in an kingdom";
    public static String No_Invitation= ChatColor.RED+"You have no pending invitation";
    public static String Unclaimed_Successfully= ChatColor.GREEN+"You unclaimed this land";
    public static String Claimed_Successfully= ChatColor.GREEN+"You claimed this land";
    public static String Invitation_Sent= ChatColor.GREEN+"Your invitation was sent !The player you invited has 10 seconds to accept";
    public static String Invitation_Received(String kingdomName){
        return ChatColor.GREEN+"You received an invitation from "+ChatColor.GOLD
                +kingdomName+ChatColor.GREEN+" !You have 10 seconds to accept";
    }
    public static String Already_Claimed_By(String kingdomName){
        return ChatColor.RED+"This land is already claimed by " + ChatColor.GOLD+kingdomName;
    }

    public static String Already_Yours = ChatColor.RED+"This land is already yours";
    public static String You_Denied = ChatColor.RED+"You denied the invitation";
    public static String Player_Not_In_Kingdom = ChatColor.RED+"This player isn't in an kingdom";
    public static String Player_Not_In_Your_Kingdom = ChatColor.RED+"This player isn't in your kingdom";
    public static String Turret_On_Fence = ChatColor.RED+"You can only place turrets on a fence";
    public static String Denied_Invitation(String playerName){
        return ChatColor.GOLD+playerName+ChatColor.RED+" Denied your invitation";
    }
    public static String You_Kingdom_Join(String kingdomName){ return ChatColor.GREEN+"Welcome to "+ChatColor.GOLD+kingdomName+ChatColor.GREEN+" kingdom !";}
    public static String New_Member_Join(String playerName)
    {
        return ChatColor.GOLD+playerName+ChatColor.GREEN+" joined the kingdom";
    }

    public static String Kick_Yourself = ChatColor.RED +"You cannot kick yourself !";
    public static String Kicked_You = ChatColor.RED +"You were kicked !";
    public static String Kicked_Player(String kingdomName)
    {
        return ChatColor.GOLD+kingdomName+ChatColor.GREEN+" was kicked successfuly !";
    }

    public static String No_Permission = ChatColor.RED+"You don't have the permission to do that !";
    public static String Nexus_Air = ChatColor.RED+"You can't place your nexus in the air!";
    public static String Nexus_Break = ChatColor.RED+"You can't break your own nexus !";
    public static String Nexus_Interact = ChatColor.RED+"You can't interact with another kingdom's nexus !";
    public static String Turret_Interact = ChatColor.RED+"You can't interact with another kingdom's turret !";

    public final static String nexusGUITitle = "Nexus GUI";
    public final static String RPGUITitle = "Resource Points Converter";
    public final static String championUpgradeGUITitle ="Champion Upgrade GUI";
    public final static String Not_Enough_RP = ChatColor.RED+"Not enough resource points";
    public final static String Claim_Limit = ChatColor.RED+"You reached your claim limit !";
}
