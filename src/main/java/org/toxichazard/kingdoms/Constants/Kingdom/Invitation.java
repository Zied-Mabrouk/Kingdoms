package org.toxichazard.kingdoms.Constants.Kingdom;

import java.util.UUID;

public class Invitation {

    String kingdomName;
    UUID inviter;
    boolean accepted = false;


    public Invitation(String empireName,UUID uuid)
    {
        this.kingdomName=empireName;
        this.inviter = uuid;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public UUID getInviter() {
        return inviter;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public void setInviter(UUID inviter) {
        this.inviter = inviter;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
