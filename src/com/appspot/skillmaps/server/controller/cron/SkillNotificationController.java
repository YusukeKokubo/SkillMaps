package com.appspot.skillmaps.server.controller.cron;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.shared.model.MailQueue;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

/**
 * その日更新のあったスキルをユーザーにメールで通知します。
 * @author y.kokubo
 *
 */
public class SkillNotificationController extends Controller {
    SkillMeta m = SkillMeta.get();
    ProfileMeta pm = ProfileMeta.get();
    long day = 1000 * 60 * 60 * 24;

    @Override
    public Navigation run() throws Exception {
        // 24時間前以降に更新されているスキルを取得する
        Date h24ago = new Date(new Date().getTime() - day);
        List<Skill> skills = Datastore.query(m).filter(m.updatedAt.greaterThanOrEqual(h24ago)).asList();
        
        // ユーザーごとに集計
        HashMap<String, List<Skill>> notifMap = new HashMap<String, List<Skill>>();
        for (Skill skill : skills) {
            if (!notifMap.containsKey(skill.getOwnerEmail())) {
                List<Skill> list = new ArrayList<Skill>();
                list.add(skill);
                notifMap.put(skill.getOwnerEmail(), list);
            } else {
                List<Skill> list = notifMap.get(skill.getOwnerEmail());
                list.add(skill);
            }
        }

        // キューを作成
        List<MailQueue> queues = new ArrayList<MailQueue>();
        for (String user : notifMap.keySet()) {
            Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(user)).limit(1).asSingle();
            if (profile.getAllowFromMailNotifier() == null || profile.getAllowFromMailNotifier() == false) {
                continue;
            }
            StringBuilder body = new StringBuilder();
            body.append(String.format("%s(%s)さんの今日のスキルレポートです。\n\n", profile.getName(), profile.getId()));
            List<Skill> updatedSkills = notifMap.get(user);
            for (Skill skill : updatedSkills) {
                if (!skill.getEnable()) {
                    body.append("消失しました。 ");
                } else if (skill.getCreatedAt().after(h24ago)) {
                    body.append("追加されました。 ");
                } else {
                    body.append("更新されました。 ");
                }
                body.append(String.format("[%s] : 賛同者[%d] : ポイント[%d]\n", skill.getName(), skill.getAgreedCount(), skill.getPoint()));
            }
            body.append("\n");
            body.append(String.format("http://skillmaps.appspot.com/index.html#!user:%s", profile.getId()));
            body.append("\n\n");
            body.append("--\n");
            body.append("skillmaps\n");
            body.append("http://skillmaps.appspot.com/\n\n");
            body.append("--\n");
            body.append("お知らせを止めたい場合はこちらからお願いします\n");
            body.append("http://skillmaps.appspot.com/#!myPage:\n\n");
            body.append("--\n");
            body.append("何かありましたらyusuke.kokubo@gmail.comもしくはhttp://twitter.com/yusuke_kokubo/ までお知らせください\n");
            
            MailQueue q = new MailQueue();
            q.setSubject(String.format("[skillmaps]%s(%s)さんのスキルレポート", profile.getName(), profile.getId()));
            q.setTextBody(body.toString());
            q.setTo(profile.getUserEmail());
            q.setSender("yusuke.kokubo@gmail.com");
            q.setBcc("yusuke.in.action@gmail.com");
            queues.add(q);
        }
        Datastore.put(queues);
        QueueFactory.getDefaultQueue().add(Builder.withUrl("/sys/mailSend"));

        return null;
    }
}