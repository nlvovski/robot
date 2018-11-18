package com.anz.robot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandInvokerTest {

    @Mock
    MyRobot robot;

    @Mock
    CommandInvoker invoker;

    RobotRunner runner;;
    InputStream in;
    PrintStream out;
    ByteArrayOutputStream baos;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        baos = new ByteArrayOutputStream();
        out = new PrintStream(baos, true, "UTF-8");
    }

    @Test
    public void shouldProcessIncludedCommands() throws Exception {
        in =  new ByteArrayInputStream("place 1 2 NORTH\nmove\nleft\nright\nreport\n".getBytes(StandardCharsets.UTF_8));
        runner = new RobotRunner(in, out, invoker);
        runner.run();
        verify(robot, times(1)).place(1, 2, MyRobot.Face.NORTH);
        verify(robot, times(1)).move();
        verify(robot, times(1)).left();
        verify(robot, times(1)).right();
        verify(robot, times(1)).report();
    }
}
