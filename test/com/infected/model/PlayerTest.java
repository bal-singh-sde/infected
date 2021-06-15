package com.infected.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    @Before
    public void setUp(){
        Player.setContaminationLevel(5);
        Player.setCurrentLocation("home");
    }
    @Test
    public void getCurrentLocation_locationEqualsJsonValue() {
        String test = Player.getCurrentLocation();
        assertEquals("home",test);
    }

    @Test
    public void setCurrentLocation_locationEqualsSetValue() {
        Player.setCurrentLocation("park");
        String test = Player.getCurrentLocation();
        assertEquals("park",test);

    }

    @Test
    public void getContaminationLevel_equalsFive() {
        assertEquals(5,Player.getContaminationLevel());
    }

    @Test
    public void setContaminationLevel_positiveValue() {
        Player.setContaminationLevel(15);
        assertEquals(15,Player.getContaminationLevel());
    }
    @Test
    public void setContaminationLevel_negativeValue_ShouldNotTakeNewValue() {
        Player.setContaminationLevel(-15);
        assertNotEquals(-15,Player.getContaminationLevel());
    }

    @Test
    public void raiseContaminationLevel_positiveValue_shouldBeIncreasedBy1() {
        Player.raiseContaminationLevel(1);
        assertEquals(6,Player.getContaminationLevel());
    }
    @Test
    public void raiseContaminationLevel_negativeValue_shouldNotBeIncreasedBy1() {
        Player.raiseContaminationLevel(-1);
        assertEquals(5,Player.getContaminationLevel());
    }
    @Test
    public void lowerContaminationLevel_shouldBeDecreasedBy4() {
//        Player.lowerContaminationLevel();
        assertEquals(1,Player.getContaminationLevel());
    }

    @Test
    public void lowerContaminationLevel_ShouldEqual0() {
        Player.setContaminationLevel(2);
//        Player.lowerContaminationLevel();
        assertEquals(0,Player.getContaminationLevel());
    }

    @Test
    public void quarantine_contaminationShouldEqual0() {
        Player.quarantine();
        assertEquals(0,Player.getContaminationLevel());
    }
}