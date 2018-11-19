package com.anz.robot;

public class MyRobot {
    private static final int MIN_X = 0;
    private static final int MAX_X = 4;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 4;

    private Boolean isPlaced;
    private Integer x;
    private Integer y;
    private Face face;

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Face getFace() {
        return face;
    }

    public enum Face {NORTH, SOUTH, EAST, WEST}

    public MyRobot() {
        isPlaced = false;
    }

    public String report() throws RobotNotPlacedException {
        assertIsPlaced();
        return String.format("Output: "+"%s,%s,%s", x, y, face.toString());
    }

    private void assertIsPlaced() throws RobotNotPlacedException {
        if (!isPlaced) {
            throw new RobotNotPlacedException("First command must be PLACE");
        }
    }

    public void place(int x, int y, Face face) throws RobotOutOfBoundariesException {
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new RobotOutOfBoundariesException("Robot must not fall of the table");
        }
        this.x = x;
        this.y = y;
        this.face = face;
        this.isPlaced = true;
    }

    public void move() throws RobotOutOfBoundariesException, RobotNotPlacedException {
        assertIsPlaced();

        int newX = -1;
        int newY = -1;

        switch (face) {
            case NORTH:
                newX = x;
                newY = y + 1;
                break;
            case SOUTH:
                newX = x;
                newY = y - 1;
                break;
            case WEST:
                newX = x - 1;
                newY = y;
                break;
            case EAST:
                newX = x + 1;
                newY = y;
                break;
        }

        place(newX, newY, face);
    }

    public void left() throws RobotNotPlacedException {
        assertIsPlaced();

        switch (face) {
            case NORTH:
                face = Face.WEST;
                break;
            case SOUTH:
                face = Face.EAST;
                break;
            case WEST:
                face = Face.SOUTH;
                break;
            case EAST:
                face = Face.NORTH;
                break;
        }
    }
    public void right() throws RobotNotPlacedException {
        assertIsPlaced();

        switch (face) {
            case NORTH:
                face = Face.EAST;
                break;
            case SOUTH:
                face = Face.WEST;
                break;
            case WEST:
                face = Face.NORTH;
                break;
            case EAST:
                face = Face.SOUTH;
                break;
        }
    }
}
