package com.anz.robot;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RobotRunnerTest {


    MyRobot robot;


    CommandInvoker invoker;

    RobotRunner runner;;
    InputStream in;
    PrintStream out;
    ByteArrayOutputStream baos;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        baos = new ByteArrayOutputStream();
        out = new PrintStream(baos, true, "UTF-8");
        robot = new MyRobot();
        invoker = new CommandInvoker();

    }

    @Test
    public void shouldProcessIncludedCommands() throws Exception {
        String output = "Output: 1,3,NORTH";

        in =  new ByteArrayInputStream("place 1 2 NORTH\nmove\nleft\nright\nreport\n".getBytes(StandardCharsets.UTF_8));
        runner = new RobotRunner(in, out, invoker, robot);
        runner.run();
        String data = robot.report();
        assertThat(data.trim(), is(output));

    }

    @Test
    public void shouldProcessUnknownCommands() {
        in = new ByteArrayInputStream("place 1 2 NORTH\nmove\nleft\nright\nunknowncommand\nreport\n".getBytes(StandardCharsets.UTF_8));

        runner = new RobotRunner(in, out, invoker, robot);
        runner.run();
        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(data.contains("Unknown command"));
    }

    @Test
    public void shouldProcessUnsupportedCommands() {
        in =  new ByteArrayInputStream("place X 2 NORTH\nplace 1 2 WESTEAST\n".getBytes(StandardCharsets.UTF_8));

        runner = new RobotRunner(in, out, invoker, robot);
        runner.run();
        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(data.contains("One of arguments is not Integer"));
        assertTrue(data.contains("One of commands is not supported"));
    }
    @Test
    public void shouldGetUnsupportedCommandOption() {
        in =  new ByteArrayInputStream("place 1 2\nmove\nleft\nright\nreport\nunknowncommand\n".getBytes(StandardCharsets.UTF_8));

        runner = new RobotRunner(in, out, invoker, robot);
        runner.run();
        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(data.contains("Wrong number of arguments for the command"));
    }
}
