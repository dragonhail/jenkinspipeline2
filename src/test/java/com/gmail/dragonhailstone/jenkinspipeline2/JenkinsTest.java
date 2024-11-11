package com.gmail.dragonhailstone.jenkinspipeline2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JenkinsTest {
    private JenkinsService service = new JenkinsService();

    @Test
    public void testService(){
        assertEquals(55, service.hap(10));
    }
}
