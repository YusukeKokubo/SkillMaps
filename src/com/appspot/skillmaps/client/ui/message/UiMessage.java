package com.appspot.skillmaps.client.ui.message;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.TextLogFormatter;
import com.google.gwt.user.client.ui.RootPanel;

public class UiMessage {

    private static final Logger logger = Logger.getLogger(UiMessage.class.getName());

    static {
        MessageController controller = new MessageController();
        HasWidgetsLogHandler handler = new HasWidgetsLogHandler(controller);
        handler.setFormatter(new TextLogFormatter(false){
            @Override
            public String format(LogRecord event) {
                return event.getMessage();
            }
        });
        logger.addHandler(handler);
        RootPanel.get("header").add(controller);
    }

    public static void info(String message){
        logger.info(message);
    }

    public static void severe(String message , Throwable t){
        logger.log(Level.SEVERE , message , t);
    }
}
