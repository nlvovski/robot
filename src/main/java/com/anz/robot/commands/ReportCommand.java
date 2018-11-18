package com.anz.robot.commands;

import com.anz.robot.MyRobot;
import com.anz.robot.RobotNotPlacedException;
import com.anz.robot.RobotOutOfBoundariesException;
import com.anz.robot.commands.Command;

import java.io.PrintStream;

public class ReportCommand implements Command {
    private PrintStream os;
    private MyRobot robot;

    public ReportCommand(MyRobot robot) {
        this.robot = robot;
        os = System.out;
    }
    @Override
    public void execute() throws RobotOutOfBoundariesException, RobotNotPlacedException {
        if(!robot.isOnTheTable())
            throw new RobotOutOfBoundariesException("Robot must not fall from the table");
        this.os.println(robot.report());
    }
}
