package com.appspot.skillmaps.server.controller;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

import com.appspot.skillmaps.server.meta.IconMeta;
import com.appspot.skillmaps.shared.model.Icon;

public class IconDownloadController extends Controller {

    private SimpleDateFormat rfc1123DateFormat = new SimpleDateFormat(
        "E, dd MMM yyyy HH:mm:ss zzz",
        java.util.Locale.US);
    {
        rfc1123DateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public Navigation run() throws Exception {
        IconMeta m = IconMeta.get();
        Icon i =
            Datastore
                .query(m)
                .filter(m.keyString.equal(request.getParameter("key")))
                .asSingle();
        if (i == null || i.getImage() == null) {
            response.setStatus(404);
            response.getWriter().write("image file not found.");
            return null;
        }
        if (request.getHeader("If-Modified-Since") != null) {
            long v =
                rfc1123DateFormat
                    .parse(request.getHeader("If-Modified-Since"))
                    .getTime();
            if (v / 1000 >= i.getUpdatedAt().getTime() / 1000) {
                response.setHeader("Last-Modified", rfc1123DateFormat.format(i.getUpdatedAt()));
                response.setStatus(304);
                return null;
            }
        }

        Image image = ImagesServiceFactory.makeImage(i.getImage());

        response.setContentType("image/"
            + image.getFormat().name().toLowerCase());
        response.setHeader("Last-Modified", rfc1123DateFormat.format(i.getUpdatedAt()));
        ServletOutputStream out = response.getOutputStream();
        try {
            out.write(i.getImage());
        } finally {
            out.close();
        }
        return null;
    }
}
