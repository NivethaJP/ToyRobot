package com.robot.utils;

import com.robot.ToyRobotException;
import com.robot.constants.ToyRobotConstants;
import com.robot.model.ToyRobot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.robot.constants.ToyRobotConstants.*;

public class ToyRobotUtils {
    public ToyRobotUtils() {
        //not called
    }


    public static final int tableCoordXMax = 5;
    public static final int tableCoordYMax = 5;
    public static final String[][] table = {{AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE}, {AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE}, {AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE}, {AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE}, {AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE}};

    protected static final List<String> validCommands = new ArrayList<>();

    static {
        validCommands.add("PLACE");
        validCommands.add("LEFT");
        validCommands.add("RIGHT");
        validCommands.add("MOVE");
        validCommands.add("REPORT");
    }

    public static final Map<String, String> leftDirMarp = new HashMap<>();

    static {
        leftDirMarp.put(EAST, NORTH);
        leftDirMarp.put(NORTH, WEST);
        leftDirMarp.put(SOUTH, EAST);
        leftDirMarp.put(WEST, SOUTH);
    }

    public static final Map<String, String> rightDirMarp = new HashMap<>();

    static {
        rightDirMarp.put(NORTH, EAST);
        rightDirMarp.put(SOUTH, WEST);
        rightDirMarp.put(WEST, NORTH);
        rightDirMarp.put(EAST, SOUTH);
    }

    public boolean isDirection(String value) {
        return ToyRobotConstants.DIRECTIONS.contains(value.toUpperCase());
    }

    public void invalidCommand(String command) {
        System.out.println("invalid command :" + command);
    }

    public boolean isBelowZero(int val) {
        return val < 0;
    }

    public static boolean beyondTableLimit(int limit, int value) {
        return value >= limit;
    }

    public static boolean validateRobotCommand(String commands) {
        boolean isCommandValid = false;

        if (commands == null || commands.equals("")) {
            System.out.println("Command cannot be parsed, it is null or empty.");
            return false;
        }

        //validate Place commands
        if (commands.contains(",")) {
            String[] placeCommands = commands.split(",");
            if (placeCommands.length == 3 && validatePlaceCoord(placeCommands)) {
                if (ToyRobotConstants.DIRECTIONS.contains(placeCommands[2])) {
                    return true;
                }

            }
        }

        if (validCommands.contains(commands)) {
            return true;
        }

        return false;
    }

    private static boolean validatePlaceCoord(String[] placeCommands) {
        return (isNumeric(placeCommands[0]) && Integer.parseInt(placeCommands[0]) < tableCoordXMax) && isNumeric(placeCommands[1]) && Integer.parseInt(placeCommands[1]) < tableCoordYMax;
    }

    private static boolean isNumeric(String command) {
        try {
            int coord = Integer.parseInt(command);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param robot        place the robot in a valid table position
     *                     check if the place command is valid
     *                     enables the robot active
     * @param robotCommand
     */
    public static void placeRobotInTable(ToyRobot robot, String robotCommand) {
        String[] command = robotCommand.split(",");
        if ((command != null || command.equals("")) && command.length > 0) {
            int xCoord = Integer.parseInt(command[0]);
            int yCoord = Integer.parseInt(command[1]);
            String dir = command[2];
            if (!ToyRobotUtils.beyondTableLimit(tableCoordXMax, xCoord) && !ToyRobotUtils.beyondTableLimit(tableCoordYMax, yCoord)) {
                robot.setCoordX(xCoord);
                robot.setCoordY(yCoord);
                //set table position to robot unique id-can be used to identify the robot's position
                if (table[xCoord][yCoord].equals(AVAILABLE)) {
                    table[xCoord][yCoord] = robot.getUniqueId().toString();
                    robot.setRobotDir(dir);
                    robot.setIsActive(Boolean.TRUE);
                }
            }
        }
    }

    public static void moveNorth(ToyRobot robot, int x, int y) throws ToyRobotException {

        //check if the next position is available for the robot to move
        if (y + 1 < tableCoordYMax) {
            checkAvailability(x, y + 1);
            robot.setCoordY(y + 1);
            table[x][y] = AVAILABLE;
            table[x][y + 1] = robot.getUniqueId().toString();
        } else {
            throw new ToyRobotException("Coordinates beyond table's limit: " + x + "," + (y + 1));
        }
    }


    public static void moveSouth(ToyRobot robot, int x, int y) throws ToyRobotException {

        //check if the next position is available for the robot to move
        if (y - 1 >= 0) {
            checkAvailability(x, y - 1);
            robot.setCoordY(y - 1);
            table[x][y] = AVAILABLE;
            table[x][y - 1] = robot.getUniqueId().toString();
        } else {
            throw new ToyRobotException("Coordinates beyond table's limit: " + x + "," + (y - 1));
        }
    }

    public static void moveEast(ToyRobot robot, int x, int y) throws ToyRobotException {
        //check if the next position is available for the robot to move
        if (x + 1 < tableCoordYMax) {
            checkAvailability(x + 1, y);
            robot.setCoordX(x + 1);
            table[x][y] = AVAILABLE;
            table[x + 1][y] = robot.getUniqueId().toString();
        } else {
            throw new ToyRobotException("Coordinates beyond table's limit: " + (x + 1) + "," + y);
        }
    }

    public static void moveWest(ToyRobot robot, int x, int y) throws ToyRobotException {
        //check if the next position is available for the robot to move
        if (x - 1 >= 0) {
            checkAvailability(x - 1, y);
            robot.setCoordX(x - 1);
            table[x][y] = AVAILABLE;
            table[x - 1][y] = robot.getUniqueId().toString();
        } else {
            throw new ToyRobotException("Coordinates beyond table's limit: " + (x - 1) + "," + y);
        }
    }

    private static void checkAvailability(int x, int y) throws ToyRobotException {
        if (Boolean.FALSE.equals(table[x][y].equals(AVAILABLE))) {
            throw new ToyRobotException("Position " + x + "," + y + " in the table is not Available");
        }
    }

}
