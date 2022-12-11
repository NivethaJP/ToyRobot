package com.robot.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ToyRobotConstants {

    public static final int TABLE_LENGTH = 5;
    public static final int TABLE_BREADTH = 5;
    public static final String NORTH = "NORTH";
    public static final String SOUTH = "SOUTH";
    public static final String EAST = "EAST";
    public static final String WEST = "WEST";
    public static final String PLACE = "PLACE";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String MOVE = "MOVE";
    public static final String REPORT = "REPORT";
    public static final String AVAILABLE = "AVAILABLE";
    public static final String ERROR_MESSAGE = "Coordinates beyond table's limit or position isn't available";
    public static final List<String> DIRECTIONS = new ArrayList<>(Arrays.asList(ToyRobotConstants.NORTH, ToyRobotConstants.SOUTH, ToyRobotConstants.EAST, ToyRobotConstants.WEST));

    private ToyRobotConstants() {
        throw new AssertionError();
    }
}
