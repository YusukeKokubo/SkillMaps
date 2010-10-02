package com.appspot.skillmaps.shared.model;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class SkillRelationTest extends AppEngineTestCase {

    private SkillRelation model = new SkillRelation();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
