package com.appspot.skillmaps.client.ui.parts.user;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.ui.UserThumnail;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserSkillDetailPanel extends Composite implements Editor<SkillA> {

    public interface Css extends CssResource {
        String button();
        String skillPanel();
        String header();
        String delimiter();
        String detailPanel();
        String descriptionLabel();
        String description();
        String buttonPanel();
    }

    interface UserSkillDetailPanelUiBinder extends
            UiBinder<Widget, UserSkillDetailPanel> {
    }

    interface Driver extends
            SimpleBeanEditorDriver<SkillA, UserSkillDetailPanel> {
    }

    Driver driver = GWT.create(Driver.class);

    private static UserSkillDetailPanelUiBinder uiBinder = GWT.create(UserSkillDetailPanelUiBinder.class);

    @UiField
    Css style;

    @UiField
    Label name;

    @UiField
    NumberLabel<Long> point;

//    @UiField
//    NumberLabel<Long> agreedCount;

//    @UiField
//    Label description;

    @UiField
    @Editor.Ignore
    Label openLabel;

    @UiField
    SimplePanel agreedActionPanel;

    @UiField
    Button addCommentButton;

    @UiField
    VerticalPanel commentsPanel;

    @UiField
    VerticalPanel skillRelationList;

    UserUIDisplay.Presenter presenter;

    Key key;

    private boolean isGotComment;

    @Inject
    Login login;

    @Inject
    Provider<UserThumnail> utProvider;

    @Inject
    public UserSkillDetailPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    public void setPresenter(UserUIDisplay.Presenter presenter){
        this.presenter = presenter;
    }

    public void setSkill(final SkillA skill) {
        addCommentButton.setEnabled(login.isLoggedIn());
        driver.edit(skill);
        if(skill.getName().length() > 10){
            name.setText(skill.getName().substring(0, 10) + "…");
        }
        name.setTitle(skill.getName());
        key = skill.getKey();
//        presenter.getSkillRelations(skill, new AsyncCallback<SkillRelation[]>() {
//            @Override
//            public void onSuccess(SkillRelation[] result) {
//                agreedActionPanel.setWidget(makeAgreedButton(skill , result));
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//            }
//        });
    }

    @UiHandler("skillRelationPanel")
    public void onOpenSkillOwnersPanel(OpenEvent<DisclosurePanel> e) {
        skillRelationList.clear();
        skillRelationList.add(new Image(Resources.INSTANCE.loader()));

//        presenter.getSkillRelations(driver.flush()
//            , new AsyncCallback<SkillRelation[]>() {
//
//            @Override
//            public void onSuccess(SkillRelation[] result) {
//
//                skillRelationList.clear();
//
//                for (SkillRelation skillRelation : result) {
//                    FocusPanel panel = new FocusPanel();
//                    UserThumnail userThumnail = utProvider.get();
//                    userThumnail.setUser(skillRelation.getProfile());
//                    panel.add(userThumnail);
//
//                    skillRelationList.add(panel);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//
//            }
//        });
    }

    @UiHandler("addCommentButton")
    public void onClickAddCommentButton(ClickEvent e){
        presenter.showSkillCommentForm(key , commentsPanel);
    }

    @UiHandler("skillPanel")
    public void onOpenSkillPanel(OpenEvent<DisclosurePanel> openEvent) {
        openLabel.setText("[－]");

        if(!isGotComment) {
            isGotComment = true;
            presenter.getSkillComments(key , commentsPanel);
        }
    }

    @UiHandler("skillPanel")
    public void onCloseSkillPanel(CloseEvent<DisclosurePanel> closeEvent) {
        openLabel.setText("[＋]");
    }

    private Widget makeAgreedButton(final Skill skill, SkillRelation[] rs) {
        if (!login.isLoggedIn()
                || !login.getProfile().isActivate()
                || login.getProfile().equals(skill.getProfile())) {
            return null;
        }

        for (final SkillRelation rel : rs) {
            if (rel.getProfile().equals(login.getProfile())) {
                if (rel.getPoint() != null && rel.getPoint() >= 10L) {         // ここはアドホックにマジックリテラルしてるけど本当はもっとやり方を考えたい
                    Label label = new Label("賛同済み");
                    label.addStyleName(style.button());
                    return label;
                }
                final Anchor addButton = new Anchor("さらにだよね！");
                addButton.setTitle("このスキルにポイントを加算します");
                addButton.addStyleName(style.button());
                rel.setPoint(10L);
                presenter.showAgreedDialog(addButton, skill, rel);
                return addButton;
            }
        }
        final Anchor agreedButton = new Anchor("だよね！");
        agreedButton.addStyleName(style.button());
        agreedButton.setTitle("このスキルに賛同します");
        presenter.showAgreedDialog(agreedButton, skill, new SkillRelation());

        return agreedButton;
    }
}
