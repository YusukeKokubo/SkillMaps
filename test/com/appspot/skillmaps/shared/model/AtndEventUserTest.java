package com.appspot.skillmaps.shared.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AtndEventUserTest extends AppEngineTestCase {

    private AtndEventUser model = new AtndEventUser();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
