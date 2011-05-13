package com.appspot.skillmaps.shared.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FollowingTest extends AppEngineTestCase {

    private Following model = new Following();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
