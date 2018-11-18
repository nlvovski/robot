package com.anz.robot.commands;

import com.anz.robot.MyRobot;
import com.anz.robot.RobotOutOfBoundariesException;

public class PlaceCommand implements Command {
    private MyRobot robot;

    private int x;
    private int y;
    private MyRobot.Face face;

    public PlaceCommand (MyRobot robot, int x, int y, MyRobot.Face face) {
        this.face = face;
        this.robot = robot;
        this.x = x;
        this.y = y;

    }

    @Override
    public void execute() throws RobotOutOfBoundariesException {
        robot.place(x, y, face);
    }
}
