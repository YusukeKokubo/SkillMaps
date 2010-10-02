package com.appspot.skillmaps.server.service;

import org.junit.Test;
import org.slim3.tester.ServletTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class SkillServiceImplTest extends ServletTestCase {

    private SkillServiceImpl service = new SkillServiceImpl();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
