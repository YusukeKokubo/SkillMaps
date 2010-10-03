package com.appspot.skillmaps.server.service;

import org.slim3.tester.ServletTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AdminServiceImplTest extends ServletTestCase {

    private AdminServiceImpl service = new AdminServiceImpl();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
