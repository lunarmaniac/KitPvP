package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class TeamCommand {

    public TeamCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    private final static String[] TEAM_MESSAGE = new String[]{
            CC.translate("&eWarning: this command is in development and may not work as expected."),
            CC.translate("&7&m-------------------------------------------"),
            CC.translate("&5&lTeams &7\u2503 &fCommand Information"),
            CC.translate("&7&m-------------------------------------------"),
            CC.translate("&5/team create <name> &7\u2503 &fCreate a team"),
            CC.translate("&5/team invite <player> &7\u2503 &fInvite a player to your team"),
            CC.translate("&5/team open &7\u2503 &fAllow players to join without an invite"),
            CC.translate("&5/team join <name> &7\u2503 &fJoin a team"),
            CC.translate("&5/team info &7\u2503 &fView your teams information"),
            CC.translate("&5/team disband &7\u2503 &fDisband your team"),
            CC.translate("&7&m-------------------------------------------")
    };

    @Command(name = "team", permission = "kitpvp.player")
    public void teamCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(TEAM_MESSAGE);

    }
//
//    @Command(name = "team.create", permission = "kitpvp.player")
//    public void createTeamCommand(CommandArgs args) {
//        Player player = args.getPlayer();
//        String teamName = args.getArgs(0);
//
//        if (Main.getInstance().teamManager.isPlayerInTeam(player.getUniqueId())) {
//            player.sendMessage(CC.RED + "You are already in a team.");
//            return;
//        }
//
//        if (Main.getInstance().teamManager.teamExists(teamName)) {
//            player.sendMessage(CC.RED + "A team with that name already exists.");
//            return;
//        }
//
//        // Create the team
//        Main.getInstance().teamManager.createTeam(teamName);
//        Main.getInstance().teamManager.addMember(teamName, player.getUniqueId());
//
//        // Set player's team in profile
//        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTeam(Main.getInstance().teamManager.getTeam(teamName));
//        player.sendMessage(CC.GREEN + "Team created successfully!");
//        Main.getInstance().teamManager.saveTeam(teamName);
//    }
//
//    @Command(name = "team.invite", permission = "kitpvp.player")
//    public void invitePlayerCommand(CommandArgs args) {
//        Player inviter = args.getPlayer();
//        String playerName = args.getArgs(0);
//
//        Player player = Bukkit.getPlayer(playerName);
//        if (Main.getInstance().teamManager.isPlayerInTeam(player.getUniqueId())) {
//            inviter.sendMessage(CC.RED + "This player is already in a team.");
//            return;
//        }
//
//        if (player == null) {
//            inviter.sendMessage(CC.RED + "Player not found.");
//            return;
//        }
//
//        // Invite player to the team
//        String teamName = String.valueOf(Main.getInstance().teamManager.getPlayerTeam(inviter.getUniqueId()));
//        if (teamName != null) {
//            Main.getInstance().teamManager.invitePlayer(teamName, player.getUniqueId());
//            player.sendMessage(CC.GREEN + "You have been invited to join the team " + teamName);
//            player.sendMessage(CC.GREEN + "Type '/team join " + teamName + "' to accept the invitation.");
//            inviter.sendMessage(CC.GREEN + "Invitation sent to " + player.getName() + ".");
//        } else {
//            inviter.sendMessage(CC.RED + "You are not in a team.");
//        }
//
//    }
//
//    @Command(name = "team.join", permission = "kitpvp.player")
//    public void joinTeamCommand(CommandArgs args) {
//        Player player = args.getPlayer();
//        String teamName = args.getArgs(0);
//
//        if (!Main.getInstance().teamManager.teamExists(teamName)) {
//            player.sendMessage(CC.RED + "Team not found.");
//            return;
//        }
//
//        if (Main.getInstance().teamManager.isPlayerInTeam(player.getUniqueId())) {
//            player.sendMessage(CC.RED + "You are already in a team.");
//            return;
//        }
//
//        // Join the team
//        if (Main.getInstance().teamManager.isTeamPublic(teamName) || Main.getInstance().teamManager.isPlayerInvited(teamName, player.getUniqueId())) {
//            Main.getInstance().teamManager.addMember(teamName, player.getUniqueId());
//            Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTeam(Main.getInstance().teamManager.getTeam(teamName));
//            player.sendMessage(CC.GREEN + "You have joined the team " + teamName);
//            Main.getInstance().teamManager.saveTeam(teamName);
//
//        } else {
//            player.sendMessage(CC.RED + "You need an invitation to join this team.");
//        }
//    }
//
//    @Command(name = "team.open", permission = "kitpvp.player")
//    public void openTeamCommand(CommandArgs args) {
//        Player player = args.getPlayer();
//        String teamName = args.getArgs(0);
//
//        if (!Main.getInstance().teamManager.teamExists(teamName)) {
//            player.sendMessage(CC.RED + "Team not found.");
//            return;
//        }
//
//        // Toggle team to public or private mode
//        Main.getInstance().teamManager.toggleTeamPublic(teamName);
//        boolean isPublic = Main.getInstance().teamManager.isTeamPublic(teamName);
//        Main.getInstance().teamManager.saveTeam(teamName);
//
//        player.sendMessage(CC.GREEN + "Team " + teamName + " is now " + (isPublic ? "public" : "private"));
//    }
//
//    @Command(name = "team.disband", permission = "kitpvp.player")
//    public void disbandTeamCommand(CommandArgs args) {
//        Player player = args.getPlayer();
//        String teamName = args.getArgs(0);
//
//        if (!Main.getInstance().teamManager.teamExists(teamName)) {
//            player.sendMessage(CC.RED + "Team not found.");
//            return;
//        }
//
//        // Disband the team
//        if (Main.getInstance().teamManager.isPlayerTeamOwner(teamName, player.getUniqueId())) {
//            Main.getInstance().teamManager.disbandTeam(teamName);
//            Main.getInstance().teamManager.saveTeam(teamName);
//
//            player.sendMessage(CC.GREEN + "Team " + teamName + " has been disbanded.");
//        } else {
//            player.sendMessage(CC.RED + "Only the team owner can disband the team.");
//        }
//    }
//
//    @Command(name = "team.forcedisband", permission = "kitpvp.admin", inGameOnly = false)
//    public void forceDisbandTeamCommand(CommandArgs args) {
//        // TODO: Implement forcedisband command (admin command)
//    }
//
//    @Command(name = "team.info", permission = "kitpvp.player")
//    public void teamInfoCommand(CommandArgs args) {
//        Player player = args.getPlayer();
//        String teamName = args.getArgs(0);
//
//        if (!Main.getInstance().teamManager.teamExists(teamName)) {
//            player.sendMessage(CC.RED + "Team not found.");
//        }
//
//        // TODO: Display team information

}
