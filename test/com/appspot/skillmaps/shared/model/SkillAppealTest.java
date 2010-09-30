package com.appspot.skillmaps.shared.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SkillAppealTest extends AppEngineTestCase {

    private SkillAppeal model = new SkillAppeal();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
