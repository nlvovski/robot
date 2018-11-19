package com.anz.robot.commands;

import com.anz.robot.MyRobot;
import com.anz.robot.RobotNotPlacedException;
import com.anz.robot.RobotOutOfBoundariesException;

public class MoveCommand implements Command {
    private MyRobot robot;

    public MoveCommand(MyRobot robot) {
        this.robot = robot;
    }
    @Override
    public void execute() throws RobotOutOfBoundariesException, RobotNotPlacedException {
        robot.move();

    }
}
