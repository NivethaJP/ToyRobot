package com.robot.service;

import com.robot.ToyRobotException;
import com.robot.model.ToyRobot;
import com.robot.utils.ToyRobotUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static com.robot.constants.ToyRobotConstants.*;
import static com.robot.utils.ToyRobotUtils.*;

/**
 * @author Nivetha Jayapathy -December 2022
 * Toy Robot Simulator on a table with 5*5 dimensions
 */
public class ToyRobotService {

    public void start(ToyRobot robot) throws ToyRobotException {
        //using Stack for executing commands one by one
        Stack<String> commands = prepareStackCommands(robot);

        //ignore all commands until a valid place command is encountered
        while (Boolean.FALSE.equals(commands.isEmpty())) {
            String currentCommand = commands.pop();
            if (currentCommand.equals(PLACE)) {
                break;
            }
            System.out.println(currentCommand + " command ignored as a valid place command to position the Robot on the table wasn't encountered");
        }

        //executeRobotCommands one by one
        System.out.println("---------Robot command Execution starts-------------");
        while (commands.size() > 0) {
            String robotCommand = commands.pop();
            executeRobotCommands(robot, commands, robotCommand);
        }
        System.out.println("---------Robot command Execution Ends-------------");
    }

    private void executeRobotCommands(ToyRobot robot, Stack<String> commands, String robotCommand) throws ToyRobotException {
        switch (robotCommand) {
            case PLACE:
                robotCommand = commands.pop();
                placeRobotInTable(robot, robotCommand);
                break;
            case LEFT:
                if (Boolean.TRUE.equals(robot.getIsActive())) {
                    //checks the next direction in the map
                    String nextDir = leftDirMarp.get(robot.getRobotDir());
                    robot.setRobotDir(nextDir);
                }
                break;
            case RIGHT:
                if (Boolean.TRUE.equals(robot.getIsActive())) {
                    String nextDir = rightDirMarp.get(robot.getRobotDir());
                    robot.setRobotDir(nextDir);
                }
                break;
            case MOVE:
                startMoving(robot);
                break;
            case REPORT:
                if (Boolean.TRUE.equals(robot.getIsActive())) {
                    System.out.println("OUTPUT: Current Robot Position, table coordinates: " + robot.getCoordX() + "," + robot.getCoordY() + " " + robot.getRobotDir());
                } else {
                    System.out.println("Robot is inactive!!!");
                }
                break;
            default:
                //place coord commands
                placeRobotInTable(robot, robotCommand);
        }
    }

    /**
     * @param robot this method populates the stack with given inputs
     * @return
     */
    private Stack<String> prepareStackCommands(ToyRobot robot) {
        List<String> robotCommands = Arrays.asList(robot.getCommands().toUpperCase().replace("\\[\\]", "").split(" "));
        Stack<String> commands = new Stack<>();
        boolean isvalidPlaceCommand = true;
        Collections.reverse(robotCommands);
        System.out.println("Validating each commands to push the valid ones into the stack");
        System.out.println("----------Validation starts---------");
        for (String command : robotCommands) {
            if (ToyRobotUtils.validateRobotCommand(command)) {
                if (Boolean.TRUE.equals(isvalidPlaceCommand)) {
                    commands.push(command);
                } else {
                    //reset flag for next command
                    isvalidPlaceCommand = true;
                }
            } else {
                //invalid place command, remove it from stack
                if (Boolean.FALSE.equals(commands.isEmpty()) && command.contains(",") && (command.split(",").length == 3)) {
                    isvalidPlaceCommand = false;
                }

                System.out.println("invalid robot Command encountered: " + command + " will be ignored");
            }

        }
        System.out.println("----------Validation ends---------");
        return commands;
    }

    /**
     * Robot moves with respect to the current location and direction
     *
     * @param robot
     */
    private void startMoving(ToyRobot robot) throws ToyRobotException {
        int x = robot.getCoordX();
        int y = robot.getCoordY();
        if (Boolean.TRUE.equals(robot.getIsActive())) {
            switch (robot.getRobotDir()) {
                case NORTH:
                    moveNorth(robot, x, y);
                    break;
                case SOUTH:
                    moveSouth(robot, x, y);
                    break;
                case EAST:
                    moveEast(robot, x, y);
                    break;
                case WEST:
                    moveWest(robot, x, y);
                    break;
                default:
                    throw new ToyRobotException("invalid Command" + robot.getRobotDir());

            }
        } else {
            System.out.println("Robot is inactive, cannot move");
        }
    }


}
