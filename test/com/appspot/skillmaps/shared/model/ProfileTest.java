package com.appspot.skillmaps.shared.model;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class ProfileTest extends AppEngineTestCase {

    private Profile model = new Profile();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
