package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.display.UserUIDisplay.Presenter;
import com.appspot.skillmaps.client.event.SkillAddSubmitEvent;
import com.appspot.skillmaps.client.event.SkillAddSubmitHandler;
import com.appspot.skillmaps.client.event.SkillCommentAddSubmitEvent;
import com.appspot.skillmaps.client.event.SkillCommentAddSubmitHandler;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.place.UserPlace;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.SkillAddDialog;
import com.appspot.skillmaps.client.ui.SkillMapPopupPanel;
import com.appspot.skillmaps.client.ui.UserThumnail;
import com.appspot.skillmaps.client.ui.form.skill.SkillCommentForm;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.client.ui.parts.skill.SkillCommentThumnail;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class UserUIActivity extends SkillMapActivity implements Presenter {

    public interface SkillDriver extends SimpleBeanEditorDriver<SkillA, SkillAddDialog>{}

    SkillDriver skillDriver = GWT.create(SkillDriver.class);

    private Profile profile;
    private EventBus eventBus;
    private final Injector injector;
    private final PlaceController placeController;

    private UserUIDisplay display;
    private HandlerRegistration hr;
    private HandlerRegistration commentHr;

    private final Provider<SkillAddDialog> skillAddDialogProvider;
    private final Provider<UserUIDisplay> displayProvider;
    private final Provider<SkillServiceAsync> serviceProvider;
    private final Provider<UserThumnail> utProvider;
    private final Provider<Anchor> permalinkProvider;
    private final Provider<AccountServiceAsync> accountServiceProvider;
    private final Provider<UserPlace> placeProvider;

    @Inject
    public UserUIActivity(Provider<UserUIDisplay> displayProvider,
                          Provider<SkillAddDialog> skillAddDialogProvider,
                          Provider<SkillServiceAsync> serviceProvider,
                          Provider<AccountServiceAsync> accountServiceProvider,
                          Provider<UserThumnail> utProvider,
                          @Named("skillOwnersPermalink") Provider<Anchor> permalinkProvider,
                          PlaceController placeController,
                          Provider<UserPlace> placeProvider,
                          Injector injector,
                          EventBus eventBus){
        this.displayProvider = displayProvider;
        this.skillAddDialogProvider = skillAddDialogProvider;
        this.serviceProvider = serviceProvider;
        this.accountServiceProvider = accountServiceProvider;
        this.utProvider = utProvider;
        this.permalinkProvider = permalinkProvider;
        this.placeController = placeController;
        this.placeProvider = placeProvider;
        this.injector = injector;
        this.eventBus = eventBus;
    }

    @Override
    public void setProfile(Profile profile){
        this.profile = profile;
    }

    private void initDisplay(final AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
        accountServiceProvider.get().getUser(((UserPlace)place).getUserId(), new AsyncCallback<Profile>() {
            @Override
            public void onSuccess(Profile result) {
                setProfile(result);
                display = displayProvider.get();
                display.setProfile(result);
                panel.setWidget(display);
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    @Override
    public void setDisplay(UserUIDisplay display){
        this.display = display;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onSuccess() {
                initDisplay(panel, eventBus);
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    @Override
    public void showSkillAddDialog() {
        final SkillAddDialog skillAddDialog = skillAddDialogProvider.get();
        skillDriver.initialize(skillAddDialog);
        skillDriver.edit(new SkillA());
        removeEventHandler(hr);
        hr = eventBus.addHandler(SkillAddSubmitEvent.TYPE, new SkillAddSubmitHandler() {
            @Override
            public void onSubmit(SkillAddSubmitEvent e) {
                SkillA skill = skillDriver.flush();
                skill.getHolder().setModel(profile);
                serviceProvider.get().addSkill(skill, new AsyncCallback<SkillA>() {
                    @Override
                    public void onSuccess(SkillA arg0) {
                        reloadSkills();
                        Window.alert("追加しました");
                        skillAddDialog.hide();
                        removeEventHandler(hr);
                        hr = null;
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                });
            }
        });

        serviceProvider.get().getSkillNames(new AsyncCallback<SkillMap[]>() {
            @Override
            public void onSuccess(SkillMap[] result) {
                skillAddDialog.setSkillNames(result);
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
        skillAddDialog.center();
    }

    private void removeEventHandler(HandlerRegistration hr) {
        if(hr != null){
            hr.removeHandler();
        }
    }

    @Override
    public void reloadSkills() {
        SimplePanel panel = display.getSkillsPanel();
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
        serviceProvider.get().getSkill(profile, new AsyncCallback<SkillA[]>() {
            @Override
            public void onSuccess(SkillA[] result) {
                display.reloadSkills(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.severe(caught.getMessage(), caught);
            }
        });
    }

    @Override
    public void showSkillOwnersPopup(final Skill skill) {
        serviceProvider.get().getSkillOwners(skill, new AsyncCallback<Skill[]>() {
            @Override
            public void onSuccess(Skill[] result) {
                SkillMapPopupPanel userDialog = new SkillMapPopupPanel();
                FlexTable table = new FlexTable();
                table.setText(0, 0, "スキル");
                table.setText(0, 1, "賛同者");
                table.setText(0, 2, "ポイント");
                table.setText(0, 3, "ユーザー");
                table.getRowFormatter().addStyleName(0, "grid-columns");
                for (int i = 0; i < result.length; i ++) {
                    Skill s = result[i];
                    table.setText(i + 1, 0, s.getName());
                    table.setText(i + 1, 1, String.valueOf(s.getAgreedCount()));
                    table.setText(i + 1, 2, String.valueOf(s.getPoint()));
                    UserThumnail u = utProvider.get();
                    u.setUser(s.getProfile());
                    table.setWidget(i + 1, 3, u);
                }
                userDialog.setContents(table);
                Anchor permalink = permalinkProvider.get();
                permalink.setName(skill.getName());
                userDialog.setFooter(permalink);
                userDialog.center();
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    @Override
    public void getSkillRelations(Skill skill,AsyncCallback<SkillRelation[]> asyncCallback) {
        serviceProvider.get().getSkillRelations(skill, asyncCallback);
    }

    @Override
    public void showAgreedDialog(final Anchor agreedForm, final Skill skill, final SkillRelation rel) {
        agreedForm.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                agreedForm.setEnabled(false);
                serviceProvider.get().putSkill(skill, rel, new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    UiMessage.info("だよね！しました！");
                    reloadSkills();
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                }
            });
            }
        });
    }

    @Override
    public void gotoUser(String id) {
        placeController.goTo(placeProvider.get().user(id));
    }

    @Override
    public void getSkillComments(Key key,final HasWidgets commentPanel) {
        serviceProvider.get().getSkillComments(key, new AsyncCallback<SkillComment[]>() {
            @Override
            public void onSuccess(SkillComment[] result) {
                commentPanel.clear();
                for (SkillComment comment : result) {
                    SkillCommentThumnail thumnail = injector.getSkillCommentThumnail();
                    thumnail.setSkillComment(comment);
                    commentPanel.add(thumnail);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    @Override
    public void showSkillCommentForm(final Key key,final VerticalPanel commentsPanel) {
        final SkillCommentForm skillCommentForm = injector.getSkillCommentForm();
        skillCommentForm.setSkillComment(key, new SkillComment());
        commentHr = eventBus.addHandler(SkillCommentAddSubmitEvent.TYPE, new SkillCommentAddSubmitHandler() {
            @Override
            public void onSubmit(SkillCommentAddSubmitEvent e) {
                SkillComment sc = skillCommentForm.getComment();
                if(Strings.isNullOrEmpty(sc.getComment())){
                    UiMessage.info("コメント欄が空です。");
                    return;
                }
                serviceProvider.get().putComment(key, sc.getComment(), new AsyncCallback<SkillComment>() {
                    @Override
                    public void onSuccess(SkillComment result) {
                        UiMessage.info("更新しました");
                        skillCommentForm.hide();
                        removeEventHandler(commentHr);

                        SkillCommentThumnail thumnail = injector.getSkillCommentThumnail();
                        thumnail.setSkillComment(result);
                        commentsPanel.insert(thumnail, 0);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                    }
                });
            }
        });
        skillCommentForm.center();
    }

    @Override
    public void addAssertion(SkillAssertion assertion) {
        serviceProvider.get().addAssert(assertion, new AsyncCallback<SkillAssertion>() {
            @Override
            public void onSuccess(SkillAssertion result) {
                reloadSkills();
            }
            
            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

}
