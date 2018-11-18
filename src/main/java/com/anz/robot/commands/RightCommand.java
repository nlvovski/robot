package com.anz.robot.commands;

import com.anz.robot.MyRobot;
import com.anz.robot.RobotNotPlacedException;
import com.anz.robot.RobotOutOfBoundariesException;

public class RightCommand implements Command {
    private MyRobot robot;

    public RightCommand(MyRobot robot) {
        this.robot = robot;
    }
    @Override
    public void execute() throws RobotOutOfBoundariesException, RobotNotPlacedException {
        if(!robot.isOnTheTable())
            throw new RobotOutOfBoundariesException("Robot must not fall from the table");
        robot.right();

    }
}
