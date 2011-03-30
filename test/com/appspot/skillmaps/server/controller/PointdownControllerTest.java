package com.appspot.skillmaps.server.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.skillmaps.server.controller.cron.PointdownController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
