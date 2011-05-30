package com.appspot.skillmaps.shared.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

public class SkillMapTest extends AppEngineTestCase {

    private SkillMap model = new SkillMap();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
