package com.appspot.skillmaps.server.controller;

import javax.servlet.ServletOutputStream;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

import com.appspot.skillmaps.server.meta.IconMeta;
import com.appspot.skillmaps.shared.model.Icon;

public class IconDownloadController extends Controller {

    @Override
    public Navigation run() throws Exception {
        IconMeta m = IconMeta.get();
        Icon i = Datastore.query(m).filter(m.keyString.equal(request.getParameter("key"))).asSingle();
        if (i == null || i.getImage() == null) {
            response.setStatus(404);
            response.getWriter().write("image file not found.");
            return null;
        }
        if (request.getHeader("If-Modified-Since") != null) {
            if (Long.valueOf(request.getHeader("If-Modified-Since")) >= i.getUpdatedAt().getTime()) {
                response.setHeader("Last-Modified", String.valueOf(i.getUpdatedAt().getTime()));
                response.setStatus(304);
                return null;
            }
        }

        Image image = ImagesServiceFactory.makeImage(i.getImage());

        response.setContentType("image/" + image.getFormat().name().toLowerCase());
        response.setHeader("Last-Modified", String.valueOf(i.getUpdatedAt().getTime()));
        ServletOutputStream out = response.getOutputStream();
        try {
            out.write(i.getImage());
        } finally {
            out.close();
        }
        return null;
    }
}
