package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.client.ui.parts.AgreeAnchor;
import com.appspot.skillmaps.client.ui.parts.PartsFactory;
import com.appspot.skillmaps.shared.model.Comment;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillAssertionUI extends Composite {

    private static SkillThumnailUiBinder uiBinder = GWT
        .create(SkillThumnailUiBinder.class);

    interface SkillThumnailUiBinder extends UiBinder<Widget, SkillAssertionUI> {
    }

    @UiField
    Label skillName;

    @UiField
    Label skillPoint;

    @UiField
    UserThumnail profile;

    @UiField
    Anchor assertion;

    @UiField
    Label title;
    
    @UiField
    Label description;

    @UiField
    HorizontalPanel panel;

    @UiField
    SimplePanel count;

    @UiField
    SimplePanel agreeButton;

    @UiField
    VerticalPanel commentsPanel;

    @UiField
    Anchor commentButton;

    @UiField
    TextBox commentBox;

    @Inject
    Login login;

    SkillAssertion skillAssertion;

    SkillMapPopupPanel skillOwners;

    @Inject
    private Provider<UserThumnail> utProvider;
    @Inject
    private Provider<SkillServiceAsync> serviceProvider;
    @Inject
    private Provider<UserUIDisplay> displayProvider;
    @Inject
    private Provider<AccountServiceAsync> accountServiceProvider;
    @Inject
    private PartsFactory partsFactory;

    @Inject
    public SkillAssertionUI(Provider<UserThumnail> utProvider) {
        this.utProvider = utProvider;
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setSkill(final SkillAssertion sa){
        if (!login.isLoggedIn() || !login.getProfile().isActivate()) {
            commentButton.setVisible(false);
        }
        skillAssertion = sa;
        skillName.setText(sa.getSkill().getModel().getName());
        skillPoint.setText("(" + sa.getSkill().getModel().getPoint().toString() + ")");
        profile.setUser(sa.getSkill().getModel().getHolder().getModel());
        assertion.setHref(sa.getUrl());
        assertion.setText(sa.getUrl());
        title.setText(sa.getTitle());
        description.setText(sa.getDescription());
        count.setWidget(makeAgreeCount(sa));
        if (login.isLoggedIn() && login.getProfile().isActivate() && !sa.getSkill().getModel().isOwnBy(login.getProfile())) {

            AgreeAnchor anchor = partsFactory.createAgreeAnchor(sa, new AgreeAnchor.ActionSuccessHandler() {

                @Override
                public void onSuccess(SkillAssertion result) {
                    result.getSkill().setModel(sa.getSkill().getModel());
                    setSkill(result);
                }
            });
            agreeButton.clear();
            agreeButton.add(anchor);
        }

        serviceProvider.get().getComments(sa, new AsyncCallback<Comment[]>() {
            @Override
            public void onSuccess(Comment[] result) {
                commentsPanel.clear();
                for (Comment comment : result) {
                    commentsPanel.add(makeCommentWidget(comment));
                    if (login.isLoggedIn() && comment.isOwnBy(login.getProfile())) {
                        commentButton.setVisible(false);
                        commentBox.setVisible(true);
                    }
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.info(caught.getMessage());
            }
        });
    }

    @UiFactory
    UserThumnail makeProfile() {
        return utProvider.get();
    }

    @UiHandler("commentButton")
    public void onCommentButton(ClickEvent ev) {
        commentBox.setVisible(true);
    }

    @UiHandler("commentBox")
    public void onCommentBox(KeyDownEvent ev) {
        if (ev.getNativeKeyCode() != 13) return;

        serviceProvider.get().addComment(skillAssertion, commentBox.getValue(), new AsyncCallback<Comment>() {
            @Override
            public void onSuccess(Comment result) {
                commentBox.setValue("");
                result.getCreatedBy().setModel(login.getProfile());
                commentsPanel.add(makeCommentWidget(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.info(caught.getMessage());
            }
        });

    }

    private HorizontalPanel makeCommentWidget(Comment comment) {
        HorizontalPanel p = new HorizontalPanel();
        UserThumnail ut = new UserThumnail(displayProvider);
        ut.setUser(comment.getCreatedBy().getModel());
        p.add(ut);
        p.add(new Label(comment.getComment()));
        Label datetime = new Label(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).format(comment.getCreatedAt()));
        p.add(datetime);
        return p;
    }

    private SimplePanel makeAgreeCount(final SkillAssertion sassertion) {
        final SimplePanel panel = new SimplePanel();
        Anchor count = new Anchor(sassertion.getAgrees().size() + "人");
        count.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                accountServiceProvider.get().getUsers(sassertion, new AsyncCallback<Profile[]>() {
                    @Override
                    public void onSuccess(Profile[] result) {
                        VerticalPanel vpanel = new VerticalPanel();
                        for (Profile p : result) {
                            UserThumnail tm = new UserThumnail(displayProvider);
                            tm.setUser(p);
                            vpanel.add(tm);
                        }
                        panel.setWidget(vpanel);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        UiMessage.info(caught.getMessage());
                    }
                });
            }
        });
        panel.add(count);
        return panel;
    }
}
