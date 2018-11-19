package com.anz.robot;

import com.anz.robot.commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {

    private List<Command> commandList = new ArrayList<>();


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
