package EmergencyApp;

import java.util.List;

class Mission {
    private String missionID;
    private String location;
    private String status;
    private List<String> personnel;
    private String managerID;

    public Mission(String missionID, String location, String status, List<String> personnel, String managerID) {
        this.missionID = missionID;
        this.location = location;
        this.status = status;
        this.personnel = personnel;
        this.managerID = managerID;
    }

    public String getMissionID() {
        return missionID;
    }

    public void setMissionID(String missionID) {
        this.missionID = missionID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPersonnel() {
        return personnel;
    }

    public void setPersonnel(List<String> personnel) {
        this.personnel = personnel;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

}
