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

    public RobotRunner(InputStream in, PrintStream out, CommandInvoker commandInvoker) {
        this.is = in;
        this.os = out;
        this.commandInvoker = commandInvoker;
    }

    public static void main(String[] args) {
        RobotRunner runner = new RobotRunner(System.in, System.out, new CommandInvoker());
        runner.run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(this.is)){
            String command = "";
            while (scanner.hasNext()) {
                command = scanner.nextLine();
                String[] cmdArgs = command.split("\\s|,+");
                try {
                    process(cmdArgs);
                    commandInvoker.executeCommands();
                } catch (Exception e) {
                    this.os.println(e.getMessage());
                    this.os.println(helpCommands);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void process(String[] cmd) throws UnsupportedCommandException {
        switch (cmd[0].toLowerCase()) {
            case "move":
                assertArguments(cmd, 1);
                commandInvoker.collectCommand(new MoveCommand(new MyRobot()));
                break;
            case "left":
                assertArguments(cmd, 1);
                commandInvoker.collectCommand(new LeftCommand(new MyRobot()));
                break;
            case "right":
                assertArguments(cmd, 1);
                commandInvoker.collectCommand(new RightCommand(new MyRobot()));
                break;
            case "place":
                assertArguments(cmd, 4);
                try {
                    int x = Integer.parseInt(cmd[1]);
                    int y = Integer.parseInt(cmd[2]);
                    MyRobot.Face face = MyRobot.Face.valueOf(cmd[3].toUpperCase());
                    commandInvoker.collectCommand(new PlaceCommand(new MyRobot(), x, y, face));
                } catch (NumberFormatException en) {
                    throw new UnsupportedCommandException("One of arguments is not Integer");
                } catch (Exception e) {
                    throw new UnsupportedCommandException("One of commands is not supported");
                }
                break;
            case "report":
                assertArguments(cmd, 1);
                commandInvoker.collectCommand(new ReportCommand(new MyRobot()));
                break;
            default:
                this.os.println("Unknown command");
                break;
        }
    }
    private void assertArguments(String[] cmd, int length) throws UnsupportedCommandException {
        if (cmd.length != length) {
            throw new UnsupportedCommandException("One of commands is not supported");
        }
    }
}
