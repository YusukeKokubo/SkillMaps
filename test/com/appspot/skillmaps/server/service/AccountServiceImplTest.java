package com.appspot.skillmaps.server.service;

import org.junit.Test;
import org.slim3.tester.ServletTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class AccountServiceImplTest extends ServletTestCase {

    private AccountServiceImpl service = new AccountServiceImpl();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
