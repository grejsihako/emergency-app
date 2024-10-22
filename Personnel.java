package EmergencyApp;

import java.util.ArrayList;
import java.util.List;

class Personnel {
    private String personnelID;
    private String name;
    private String status;
    private String contact;
    private List<String> missions;

    public Personnel(String personnelID, String name,String contact) {
        this.personnelID = personnelID;
        this.name = name;
        this.status = "Available";
        this.contact = contact;
        this.missions = new ArrayList<>();
    }

    public Personnel(String personnelID, String name,String status, String contact, List<String>missions) {
        this.personnelID = personnelID;
        this.name = name;
        this.status = status;
        this.contact = contact;
        this.missions = new ArrayList<>(missions);
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(String personnelID) {
        this.personnelID = personnelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getMissions() {
        return missions;
    }

    public void setMissions(List<String> missions) {
        this.missions = missions;
    }

    public void addMission(String missionID) {
        missions.add(missionID);
    }

    
}
