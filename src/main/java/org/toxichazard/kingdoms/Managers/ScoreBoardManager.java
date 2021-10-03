package org.toxichazard.kingdoms.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;

public class ScoreBoardManager {

    public static void  newScoreBoardManager(KPlayer kPlayer)
    {

        Kingdom kingdom = kPlayer.getKingdom();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("test","dummy", ChatColor.WHITE+">"+ChatColor.GOLD+" Kingdom "+ChatColor.WHITE+"<");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score s1 = objective.getScore(ChatColor.DARK_RED+"--------------------------");
        s1.setScore(1);

        Score s2 = objective.getScore(String.valueOf(kingdom.getRP()));
        s2.setScore(2);
        Score s3 = objective.getScore(ChatColor.RED+"RP: ");
        s3.setScore(3);

        Score s4 = objective.getScore( Bukkit.getOfflinePlayer(kingdom.getKing()).getName());
        s4.setScore(4);
        Score s5 = objective.getScore(ChatColor.RED+"King: ");
        s5.setScore(5);

        Score s6 = objective.getScore(kingdom.getName());
        s6.setScore(6);
        Score s7 = objective.getScore(ChatColor.RED+"Kingdom: ");
        s7.setScore(7);

        s6.getScore();



        Score s8 = objective.getScore(ChatColor.DARK_RED+"-------------------------");
        s8.setScore(8);


        kPlayer.getPlayer().setScoreboard(scoreboard);
    }

}
