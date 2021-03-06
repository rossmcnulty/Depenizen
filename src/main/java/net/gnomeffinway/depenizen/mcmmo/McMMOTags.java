package net.gnomeffinway.depenizen.mcmmo;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.aufdemrand.denizen.events.ReplaceableTagEvent;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.database.LeaderboardManager;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.party.PartyManager;

import net.gnomeffinway.depenizen.Depenizen;

public class McMMOTags implements Listener {
    
    public McMMOTags(Depenizen depenizen) {
        depenizen.getServer().getPluginManager().registerEvents(this, depenizen);
    }
    
    @EventHandler
    public void mcmmoTags(ReplaceableTagEvent event) {        
        
        Player p = event.getPlayer();
        String nameContext = event.getNameContext() != null ? event.getNameContext().toUpperCase() : "";
        String type = event.getType() != null ? event.getType().toUpperCase() : "";
        String typeContext = event.getTypeContext() != null ? event.getTypeContext().toUpperCase() : "";
        String subType = event.getSubType() != null ? event.getSubType().toUpperCase() : "";
        String subTypeContext = event.getSubTypeContext() != null ? event.getSubTypeContext().toUpperCase() : "";
        String specifier = event.getSpecifier() != null ? event.getSpecifier().toUpperCase() : "";  

        if(event.matches("PLAYER")) {
            if (type.equals("MCMMO")) {
                if (subType.equals("LEVEL")) {
                    if(subTypeContext == "") {
                        if(p.isOnline()) {
                            event.setReplaced(String.valueOf(ExperienceAPI.getPowerLevel(p)));
                        } else {
                            event.setReplaced(String.valueOf(ExperienceAPI.getPowerLevelOffline(p.getName())));
                        }
                    } else {
                        if(p.isOnline()) {
                            event.setReplaced(String.valueOf(ExperienceAPI.getLevel(p, subTypeContext)));
                        } else {
                            event.setReplaced(String.valueOf(ExperienceAPI.getLevelOffline(p.getName(), subTypeContext)));
                        }
                    }
                } else if (subType.equals("PARTY")) {
                    if(PartyAPI.inParty(p)) {
                        event.setReplaced(String.valueOf(PartyAPI.getPartyName(p)));
                    } else {
                        event.setReplaced("none");
                    }
                } else if (subType.equals("RANK")) {
                    if(subTypeContext == "") {
                        event.setReplaced(String.valueOf(LeaderboardManager.getPlayerRank(p.getName())[0]));
                    } else {
                        event.setReplaced(String.valueOf(LeaderboardManager.getPlayerRank(p.getName(),SkillType.getSkill(subTypeContext))));
                    }
                } else if (subType.equals("XP")) {
                    if(specifier.equals("TONEXTLEVEL")) {
                        if(p.isOnline()) {
                            event.setReplaced(String.valueOf(ExperienceAPI.getXPToNextLevel(p, subTypeContext)));
                        } else {
                            event.setReplaced(String.valueOf(ExperienceAPI.getOfflineXPToNextLevel(p.getName(), subTypeContext)));
                        }
                    } else {
                        if(p.isOnline()) {
                            event.setReplaced(String.valueOf(ExperienceAPI.getXP(p, subTypeContext)));
                        } else {
                            event.setReplaced(String.valueOf(ExperienceAPI.getOfflineXP(p.getName(), subTypeContext)));
                        }
                    }
                }
            }
        } else if (event.matches("PARTY")) {
            if (type.equals("LEADER")) {
                event.setReplaced(String.valueOf(PartyAPI.getPartyLeader(nameContext)));
            } else if(type.equals("PLAYERCOUNT")) {
                event.setReplaced(String.valueOf(PartyManager.getParty(nameContext).getMembers().size()));
            }
        }
        
    }

    
}
