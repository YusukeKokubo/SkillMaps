package com.appspot.skillmaps.server.controller.cron;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillAssertionMeta;
import com.appspot.skillmaps.shared.model.MailQueue;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

/**
 * その日更新のあったスキルをユーザーにメールで通知します。
 * @author y.kokubo
 *
 */
public class SkillNotificationController extends Controller {
    SkillAssertionMeta m = SkillAssertionMeta.get();
    ProfileMeta pm = ProfileMeta.get();
    
    long day = 1000 * 60 * 60 * 24;

    @Override
    public Navigation run() throws Exception {
        // 24時間前以降に更新されているスキルを取得する
        Date h24ago = new Date(new Date().getTime() - day);
        List<SkillAssertion> assertions = Datastore.query(m).filter(m.updatedAt.greaterThanOrEqual(h24ago)).asList();
        
        // ユーザーごとに集計
        HashMap<Profile, List<SkillAssertion>> notifMap = new HashMap<Profile, List<SkillAssertion>>();
        for (SkillAssertion assertion : assertions) {
            SkillA skill = assertion.getSkill().getModel();
            if (!notifMap.containsKey(skill.getHolder().getModel().getUserEmail())) {
                List<SkillAssertion> list = new ArrayList<SkillAssertion>();
                list.add(assertion);
                notifMap.put(skill.getHolder().getModel(), list);
            } else {
                List<SkillAssertion> list = notifMap.get(skill.getHolder().getModel());
                list.add(assertion);
            }
        }

        // キューを作成
        List<MailQueue> queues = new ArrayList<MailQueue>();
        for (Profile profile : notifMap.keySet()) {
            if (profile.getAllowFromMailNotifier() == null || profile.getAllowFromMailNotifier() == false) {
                continue;
            }
            StringBuilder body = new StringBuilder();
            body.append(String.format("%s(%s)さんの今日のスキルレポートです。\n\n", profile.getName(), profile.getId()));
            List<SkillAssertion> updatedSkills = notifMap.get(profile);
            for (SkillAssertion assertion : updatedSkills) {
                SkillA skill = assertion.getSkill().getModel();
                body.append(String.format("■ %s (%dポイント) <- %s (%s)\n", skill.getName(), skill.getPoint(), assertion.getUrl(), assertion.getDescription()));
                body.append(String.format("%d人がやるね！と言っています.\n", assertion.getAgrees().size()));
                List<Profile> agrees = Datastore.get(pm, assertion.getAgrees());
                for (Profile p : agrees) {
                    body.append(String.format("- %s(%s) \n", p.getName(), p.getId()));
                }
                body.append("\n\n");
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
