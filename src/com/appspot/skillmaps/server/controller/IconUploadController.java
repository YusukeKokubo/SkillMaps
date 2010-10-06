package com.appspot.skillmaps.server.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.IconMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Icon;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class IconUploadController extends Controller {

    @Override
    public Navigation run() throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        FileItem fileImage = requestScope("uploadFormElement");

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        Image upImage = ImagesServiceFactory.makeImage(fileImage.getData());
        Transform resize = ImagesServiceFactory.makeResize(150, 150);
        Image image = imagesService.applyTransform(resize, upImage);

        IconMeta m = IconMeta.get();
        Icon i = Datastore.query(m).filter(m.userEmail.equal(user.getEmail())).asSingle();
        i.setImage(image.getImageData());
        Datastore.put(i);

        ProfileMeta pm = ProfileMeta.get();
        Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).limit(1).asSingle();
        profile.setHasIcon(true);

        return null;
    }
}
