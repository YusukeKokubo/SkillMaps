package com.appspot.skillmaps.server.controller.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.DateUtil;

import com.appspot.skillmaps.server.meta.IconMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Icon;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class CheckUserImageController extends Controller {

    private static final Logger logger = Logger.getLogger(CheckUserImageController.class.getName());


    private long startedTimeMillis;

    private int taskTimeMillis = 15000;

    /**
     * タスクの実行経過時間を返す．
     * @return ミリ秒
     */
    protected long getElapsedTimeMillis() {
        return System.currentTimeMillis() - startedTimeMillis;
    }

    @Override
    public Navigation run() throws Exception {
        logger.info("Profileのイメージ画像フラグ更新処理を実行します。");
        startedTimeMillis = System.currentTimeMillis();
        ProfileMeta profileMeta = ProfileMeta.get();
        List<Profile> resultList = null;
        Date seeAt = asDate("createdAt" , "yyyyMMddHHmmss");
        if(seeAt == null){
            logger.info("初回処理です。全件取得します。");

            resultList = Datastore.query(profileMeta).sortInMemory(profileMeta.createdAt.asc).asList();
        } else {
            logger.info("継続処理です。" + asString("createdAt") + "以降に作成されたProfileを取得します。");
            resultList = Datastore.query(profileMeta)
                                    .filter(profileMeta.createdAt.greaterThan(seeAt))
                                    .sortInMemory(profileMeta.createdAt.asc).asList();
        }

        logger.info("残処理件数は" + resultList.size() + "です。");

        List<Profile> updateList = new ArrayList<Profile>();
        for (Profile profile : resultList) {

            if(profile.getHasIcon() != null){
                continue;
            }

            Icon icon = Datastore.get(IconMeta.get(), profile.getIconKey());

            profile.setHasIcon(icon.getImage() != null);

            updateList.add(profile);

            if(getElapsedTimeMillis() > taskTimeMillis){

                logger.info("所定処理時間をオーバしたため、後続タスクへ処理を渡します。");

                Datastore.put(updateList);
                logger.info(updateList.size() + "件更新しました。");

                String lastSee = DateUtil.toString(profile.getCreatedAt() , "yyyyMMddHHmmss");
                logger.info(lastSee + "まで更新しました。");

                Queue queue = QueueFactory.getDefaultQueue();

                queue.add(Builder.withParam("createdAt", lastSee).url("/sys/checkUserImage"));

                logger.info("処理を終了します。");
                return null;
            }
        }

        logger.info("すべてのProfile処理を終え、これより、更新を行います、");
        Datastore.put(updateList);
        logger.info(updateList.size() + "件更新しました。");


        logger.info("処理を終了します。");

        return null;
    }
}
