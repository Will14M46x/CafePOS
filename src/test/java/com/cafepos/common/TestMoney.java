package com.cafepos.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestMoney {

    @Test
    public void testOf(){
        Money testMoney = Money.of(5);
        assertEquals("5.00", testMoney.toString());
    }

    @Test
    public void testZero(){
        Money zero = Money.zero();
        assertEquals("0.00", zero.toString());
        assertEquals(zero, Money.of(0));
    }

    @Test
    public void testAdd(){
        Money one = Money.of(1.5);
        Money two = Money.of(2.5);
        Money result = one.add(two);
        assertEquals(Money.of(4), result);
    }

    @Test
    public void testMultiply(){
        Money one = Money.of(1.5);
        Money result = one.multiply(3);
        assertEquals(Money.of(4.5), result);
    }

    @Test
    public void testCompareToP1() {
        Money m1 = Money.of(2.50);
        Money m2 = Money.of(2.50);
        assertEquals(0, m1.compareTo(m2));
    }

    @Test
    public void testCompareToP2() {
        Money smaller = Money.of(1.00);
        Money larger = Money.of(2.00);
        assertTrue(smaller.compareTo(larger) < 0);
        assertTrue(larger.compareTo(smaller) > 0);
    }

    @Test
    public void testEquals() {
        Money one = Money.of(1.50);
        Money two = Money.of(1.500);
        assertEquals(one, two);

        Money three = Money.of(2.5);
        Money four = Money.of(2.6);
        assertNotEquals(three, four);
    }

    @Test
    public void testToString() {
        Money one = Money.of(2.45);
        assertEquals("2.45", one.toString());
    }
}
