package com.anz.robot.commands;

import com.anz.robot.MyRobot;
import com.anz.robot.RobotNotPlacedException;
import com.anz.robot.RobotOutOfBoundariesException;

public interface Command {
    void execute() throws RobotOutOfBoundariesException, RobotNotPlacedException;
}
