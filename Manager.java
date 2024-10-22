package EmergencyApp;

import java.util.*;

class Manager {
    private String name;
    private String id;
    private String password;
    private List<String> missions;

    public Manager(String name ,String id, String password, List<String> missions) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.missions = new ArrayList<>(missions);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getMissions() {
        return missions;
    }

    public void setMissions(List<String> missions) {
        this.missions = missions;
    }

    public void addMission(String missionID) {
        if (missions == null) {
            missions = new ArrayList<>();
        }
        missions.add(missionID);
    }
    


}