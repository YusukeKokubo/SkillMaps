package com.appspot.skillmaps.server.controller.sys;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.MailQueueMeta;
import com.appspot.skillmaps.shared.model.MailQueue;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;
import com.google.apphosting.api.ApiProxy.OverQuotaException;

/**
 * その日更新のあったスキルをユーザーにメールで通知します。
 * @author y.kokubo
 *
 */
public class MailSendController extends Controller {
    MailQueueMeta m = new MailQueueMeta();

    @Override
    public Navigation run() throws Exception {
        List<MailQueue> queues = Datastore.query(m).asList();

        // メール送付
        for (MailQueue queue : queues) {
            Message msg = queue.getMessage();
            try {
                MailService ms = MailServiceFactory.getMailService();
                ms.send(msg);
                Datastore.delete(queue.getKey());   // 正常に送信した場合はキューから削除
            } catch (OverQuotaException ex) {
                // 制限にひっかかった場合は1分待ってから再実行
                QueueFactory.getDefaultQueue().add(Builder.withUrl("/sys/mailSend").countdownMillis(1000 * 60));
            }
        }

        return null;
    }
}
