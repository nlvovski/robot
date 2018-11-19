package com.anz.robot;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class MyRobotTest {
    private MyRobot robot;
    private CommandInvoker invoker;

    @Before
    public void setUp() {
        robot = new MyRobot();
        invoker = new CommandInvoker();
    }

    @Test
    public void shouldProduceReport() throws RobotOutOfBoundariesException, RobotNotPlacedException {
       robot.place(2, 2, MyRobot.Face.WEST);
       String report = robot.report();
       assertTrue(report.contains("2,2,WEST"));
    }

    @Test
    public void shouldProduceRobotOutOfBoundariesExceptionWhenMove() throws RobotOutOfBoundariesException {
        try {
            robot.move();
            fail("Expected RobotNotPlacedException");
        } catch (RobotNotPlacedException ex) {
            assertThat(ex.getMessage(), is("First command must be PLACE"));
        }
    }

    @Test
    public void shouldProduceRobotOutOfBoundariesExceptionOnLeft(){
        try {
            robot.left();
            fail("Expected RobotNotPlacedException");
        } catch (RobotNotPlacedException ex) {
            assertThat(ex.getMessage(), is("First command must be PLACE"));
        }
    }

    @Test
    public void shouldProduceRobotOutOfBoundariesExceptionOnRight(){
        try {
            robot.right();
            fail("Expected RobotNotPlacedException");
        } catch (RobotNotPlacedException ex) {
            assertThat(ex.getMessage(), is("First command must be PLACE"));
        }
    }

    @Test
    public void example1Test() throws Exception {
        String input = "PLACE 0,0,NORTH\n" +
                "MOVE\n" +
                "REPORT\n";
        String output = "Output: 0,1,NORTH";
        RobotRunner runner;
        InputStream is;
        PrintStream ps;
        ByteArrayOutputStream baos;

        is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos, true, "UTF-8");
        runner = new RobotRunner(is, ps, invoker, robot);
        runner.run();
        String data = robot.report(); //new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertThat(data.trim(), is(output));
    }

    @Test
    public void example2Test() throws Exception {
        String input = "PLACE 0,0,NORTH\n" +
                "LEFT\n" +
                "REPORT\n";
        String output = "Output: 0,0,WEST";
        RobotRunner runner;
        InputStream is;
        PrintStream ps;
        ByteArrayOutputStream baos;

        is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos, true, "UTF-8");
        runner = new RobotRunner(is, ps, invoker, robot);
        runner.run();
        String data = robot.report();
        assertThat(data.trim(), is(output));
    }

    @Test
    public void example3Test() throws Exception {
        String input = "PLACE 1,2,EAST\n" +
                "MOVE\n" +
                "MOVE\n" +
                "LEFT\n" +
                "MOVE\n" +
                "REPORT\n";
        String output = "Output: 3,3,NORTH";
        RobotRunner runner;
        InputStream is;
        PrintStream ps;
        ByteArrayOutputStream baos;

        is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos, true, "UTF-8");
        runner = new RobotRunner(is, ps, invoker, robot);
        runner.run();
        String data = robot.report();
        assertThat(data.trim(), is(output));

    }
}
