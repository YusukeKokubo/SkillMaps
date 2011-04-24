package com.appspot.skillmaps.server.controller.sys;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.appspot.skillmaps.server.meta.OldSkillRelationMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.shared.model.OldSkillRelation;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.gwt.thirdparty.guava.common.base.Strings;


public class SeparateSkillCommentController extends Controller {
    static SkillRelationMeta m = SkillRelationMeta.get();
    static OldSkillRelationMeta om = OldSkillRelationMeta.get();
    @Override
    public Navigation run() throws Exception {
        List<OldSkillRelation> oldList = Datastore.query(om).filter(om.getSchemaVersionName() , FilterOperator.EQUAL , 2).asList();

        System.out.println(oldList);

        for (OldSkillRelation oldSkillRelation : oldList) {

            Key key = oldSkillRelation.getSkill().getKey();
            if(!Strings.isNullOrEmpty(oldSkillRelation.getComment())){
                SkillComment sc = new SkillComment();

                BeanUtil.copy(oldSkillRelation, sc);

                sc.setKey(null);

                sc.getSkill().setKey(key);

                Datastore.putAsync(sc);
            }

            SkillRelation skillRelation = new SkillRelation();

            BeanUtil.copy(oldSkillRelation, skillRelation);

            skillRelation.getSkill().setKey(key);

            //かなりな回数putするのでAsyncで skillRelationのバージョンが上がるのでやり直しても2回目は取得しない
            Datastore.putAsync(skillRelation);
        }
        return null;
    }
}
