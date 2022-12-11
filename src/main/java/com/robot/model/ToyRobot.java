package com.robot.model;

import java.util.UUID;

public class ToyRobot {

    String uniqueId;
    String name;
    //Table dimensions
    //Robot's coordinates
    int coordX;
    int coordY;
    String robotDir;
    String commands;
    Boolean isActive;

    public ToyRobot() {
        this.uniqueId = UUID.randomUUID().toString();
        this.coordX = 0;
        this.coordY = 0;
        this.commands = "";
        this.isActive = false;
    }

    public String getUniqueId() {
        return uniqueId;
    }


    public String getName() {
        return name;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String getRobotDir() {
        return robotDir;
    }

    public String getCommands() {
        return commands;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void setRobotDir(String robotDir) {
        this.robotDir = robotDir;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

}
