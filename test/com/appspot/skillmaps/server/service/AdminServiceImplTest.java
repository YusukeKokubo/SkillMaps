package com.appspot.skillmaps.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slim3.tester.ServletTestCase;

public class AdminServiceImplTest extends ServletTestCase {

    private AdminServiceImpl service = new AdminServiceImpl();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
