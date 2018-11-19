package com.anz.robot;

import com.anz.robot.commands.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class RobotRunner {
    private static final String helpCommands = "Acceptable (case insensitive) commands:\n" +
            "MOVE\n" +
            "LEFT\n" +
            "RIGHT\n" +
            "PLACE <X> <Y> <NORTH|SOUTH|EAST|WEST>\n";

    private InputStream is;
    private PrintStream os;
    private CommandInvoker commandInvoker;
    private  MyRobot robot;


    public RobotRunner(InputStream in, PrintStream out, CommandInvoker commandInvoker, MyRobot robot) {
        this.is = in;
        this.os = out;
        this.commandInvoker = commandInvoker;
        this.robot = robot;
    }

    public static void main(String[] args) {
        RobotRunner runner = new RobotRunner(System.in, System.out, new CommandInvoker(), new MyRobot());
        runner.run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(this.is)){
            String command = "";
            while (scanner.hasNext()) {
                command = scanner.nextLine();
                String[] cmdArgs = command.split("\\s|,+");
                try {
                    process(cmdArgs, robot);
                    commandInvoker.executeCommands();
                } catch (UnsupportedCommandException e) {
                    this.os.println(e.getMessage());
                    this.os.println(helpCommands);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void process(String[] cmd, MyRobot robot) throws UnsupportedCommandException {
        switch (cmd[0].toLowerCase()) {
            case "move":
                assertArgumentsNumber(cmd, 1);
                commandInvoker.collectCommand(new MoveCommand(robot));
                break;
            case "left":
                assertArgumentsNumber(cmd, 1);
                commandInvoker.collectCommand(new LeftCommand(robot));
                break;
            case "right":
                assertArgumentsNumber(cmd, 1);
                commandInvoker.collectCommand(new RightCommand(robot));
                break;
            case "place":
                assertArgumentsNumber(cmd, 4);
                try {
                    int x = Integer.parseInt(cmd[1]);
                    int y = Integer.parseInt(cmd[2]);
                    MyRobot.Face face = MyRobot.Face.valueOf(cmd[3].toUpperCase());
                    commandInvoker.collectCommand(new PlaceCommand(robot, x, y, face));
                } catch (NumberFormatException en) {
                    throw new UnsupportedCommandException("One of arguments is not Integer");
                }
                break;
            case "report":
                assertArgumentsNumber(cmd, 1);
                commandInvoker.collectCommand(new ReportCommand(robot));
                break;
            default:
                this.os.println("Unknown command");
                break;
        }
    }
    private void assertArgumentsNumber(String[] cmd, int length) throws UnsupportedCommandException {
        if (cmd.length != length) {
            throw new UnsupportedCommandException("Wrong number of arguments for the command");
        }
    }
}
