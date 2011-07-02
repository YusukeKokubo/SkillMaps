
package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.display.UserUIDisplay.Presenter;
import com.appspot.skillmaps.client.event.SkillAddSubmitEvent;
import com.appspot.skillmaps.client.event.SkillAddSubmitHandler;
import com.appspot.skillmaps.client.place.UserPlace;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.SkillAddDialog;
import com.appspot.skillmaps.client.ui.SkillMapPopupPanel;
import com.appspot.skillmaps.client.ui.UserThumnail;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.client.ui.parts.AgreeAnchor;
import com.appspot.skillmaps.client.ui.parts.PartsFactory;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserUIActivity extends SkillMapActivity implements Presenter {

    public interface SkillDriver extends SimpleBeanEditorDriver<SkillA, SkillAddDialog>{}

    SkillDriver skillDriver = GWT.create(SkillDriver.class);

    private Profile profile;

    @Inject
    private EventBus eventBus;

    @Inject
    private PlaceController placeController;

    @Inject
    private Provider<SkillAddDialog> skillAddDialogProvider;
    @Inject
    private Provider<UserUIDisplay> displayProvider;
    @Inject
    private Provider<SkillServiceAsync> serviceProvider;
    @Inject
    private Provider<UserThumnail> utProvider;
    @Inject
    private Provider<Anchor> permalinkProvider;
    @Inject
    private Provider<AccountServiceAsync> accountServiceProvider;
    @Inject
    private Provider<UserPlace> placeProvider;
    @Inject
    private PartsFactory partsFactory;

    private UserUIDisplay display;

    private HandlerRegistration hr;

    @Inject
    Login login;

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
                        UiMessage.info("追加しました");
                        skillAddDialog.hide();
                        removeEventHandler(hr);
                        hr = null;
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        UiMessage.info(caught.getMessage());
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
    public void gotoUser(String id) {
        placeController.goTo(placeProvider.get().user(id));
    }

    @Override
    public void addAssertion(SkillAssertion assertion) {
        serviceProvider.get().addAssert(assertion, new AsyncCallback<SkillAssertion>() {
            @Override
            public void onSuccess(SkillAssertion result) {
                UiMessage.info("投下完了!");
                reloadSkills();
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.info(caught.getMessage());
            }
        });
    }

    @Override
    public void getAssertions(final SkillA skill, final VerticalPanel assertions) {
        serviceProvider.get().getAssertion(skill, new AsyncCallback<SkillAssertion[]>() {
            @Override
            public void onSuccess(SkillAssertion[] result) {
                assertions.clear();
                for (final SkillAssertion sassertion : result) {
                    VerticalPanel vpanel = new VerticalPanel();
                    vpanel.setSpacing(5);
                    HorizontalPanel panel = new HorizontalPanel();
                    Anchor sa = new Anchor(sassertion.getUrl(), sassertion.getUrl(), "_blank");
                    Label desc = new Label(sassertion.getDescription());
                    Label msg = new Label("がやるね！と言っています.");
                    vpanel.add(sa);
                    vpanel.add(desc);
                    panel.add(makeAgreeCount(sassertion));
                    panel.add(msg);
                    if (login.isLoggedIn() && login.getProfile().isActivate() && !skill.isOwnBy(login.getProfile())) {
                        AgreeAnchor anchor = partsFactory.createAgreeAnchor(sassertion,
                            new AgreeAnchor.ActionSuccessHandler() {

                            @Override
                            public void onSuccess(SkillAssertion result) {
                                assertions.clear();
                                getAssertions(skill, assertions);
                            }
                        });

                        panel.add(anchor);
                    }
                    vpanel.add(panel);
                    assertions.add(vpanel);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                UiMessage.info(caught.getMessage());
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
                            }
                        });
                    }
                });
                panel.add(count);
                return panel;
            }
        });
    }
}
