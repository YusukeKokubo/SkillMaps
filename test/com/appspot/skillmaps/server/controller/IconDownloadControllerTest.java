package com.appspot.skillmaps.server.controller;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class IconDownloadControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/IconDownload");
        IconDownloadController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
