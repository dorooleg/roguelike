package ru.spbau.mit.roguelike;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleg on 5/9/2017.
 */
public class PointTest {
    @Test
    public void hashCodeTest() throws Exception {
        Point p = new Point(3, 4);
        Assert.assertEquals((31 + 3) * 31 + 4, p.hashCode());
    }

    @Test
    public void equals() throws Exception {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        Assert.assertEquals(p1, p2);
    }

    @Test
    public void neighbors8() throws Exception {
        Point p = new Point(4, 7);
        Set<Point> s = new HashSet<>();
        for (Point t : p.getNeighbors8()) {
            s.add(t);
        }
        Assert.assertEquals(8, s.size());
    }

}