package com.anz.robot;

import com.anz.robot.commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {

    private List<Command> commandList = new ArrayList<>();

    private Command command;
    private static final String helpCommands = "Acceptable (case insensitive) commands:\n" +
            "MOVE\n" +
            "LEFT\n" +
            "RIGHT\n" +
            "PLACE <X> <Y> <NORTH|SOUTH|EAST|WEST>\n";

    public void collectCommand (Command command) {
        commandList.add(command);
    }

    public void executeCommands() {
        for (Command command : commandList) {
            try {
                command.execute();
            } catch (RobotOutOfBoundariesException | RobotNotPlacedException e) {

            }

        }
        commandList.clear();
    }
}
