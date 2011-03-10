package com.appspot.skillmaps.server.controller;

import org.slim3.controller.router.RouterImpl;

public class AppRouter extends RouterImpl {

	public AppRouter() {
        addRouting("/images/icon/{key}", "/IconDownload?key={key}");
        addRouting("/cron/pointdown",    "/Pointdown");
	}
}
