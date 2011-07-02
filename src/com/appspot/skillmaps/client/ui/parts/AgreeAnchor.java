package com.appspot.skillmaps.client.ui.parts;

import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class AgreeAnchor extends Anchor {

    @Inject
    public AgreeAnchor(
            final Login login,
            final SkillServiceAsync skillService,
            @Assisted final SkillAssertion skillAssertion,
            @Assisted final ActionSuccessHandler handler) {

        super(skillAssertion.isAgreedBy(login.getProfile()) ? "やるね！を取り消す." : "やるね!");
        this.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                if(skillAssertion.isAgreedBy(login.getProfile())) {

                    disagree(skillService, skillAssertion, handler);
                } else {

                    agree(skillService, skillAssertion, handler);
                }
            }
        });

    }

    private void agree(final SkillServiceAsync skillService,
            final SkillAssertion skillAssertion,
            final ActionSuccessHandler handler) {
        skillService.agree(skillAssertion, new AsyncCallback<SkillAssertion>() {

            @Override
            public void onSuccess(SkillAssertion result) {
                UiMessage.info("やるね！");
                if (handler != null) {

                    handler.onSuccess(result);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.info(caught.getMessage());
            }
        });
    }

    private void disagree(final SkillServiceAsync skillService,
            final SkillAssertion skillAssertion,
            final ActionSuccessHandler handler) {
        skillService.disagree(skillAssertion, new AsyncCallback<SkillAssertion>() {

            @Override
            public void onSuccess(SkillAssertion result) {
                UiMessage.info("やるね！を取消しました.");
                if (handler != null) {

                    handler.onSuccess(result);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.info(caught.getMessage());
            }
        });
    }


    public static interface ActionSuccessHandler {
        void onSuccess(SkillAssertion result);

    }

}
