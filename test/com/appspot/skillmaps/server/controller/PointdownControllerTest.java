package com.appspot.skillmaps.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

import com.appspot.skillmaps.server.controller.cron.PointdownController;

public class PointdownControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/Pointdown");
        PointdownController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
