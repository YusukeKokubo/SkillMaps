package com.appspot.skillmaps.shared.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SkillATest extends AppEngineTestCase {

    private SkillA model = new SkillA();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
