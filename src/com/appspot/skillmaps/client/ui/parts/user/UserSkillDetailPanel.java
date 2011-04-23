package com.appspot.skillmaps.client.ui.parts.user;

import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class UserSkillDetailPanel extends Composite implements Editor<Skill> {

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
            SimpleBeanEditorDriver<Skill, UserSkillDetailPanel> {
    }

    Driver driver = GWT.create(Driver.class);

    private static UserSkillDetailPanelUiBinder uiBinder = GWT.create(UserSkillDetailPanelUiBinder.class);

    @UiField
    Css style;

    @UiField
    Label name;

    @UiField
    NumberLabel<Long> point;

    @UiField
    NumberLabel<Long> agreedCount;

    @UiField
    Label description;

    @UiField
    @Editor.Ignore
    Label openLabel;

    @UiField
    SimplePanel agreedActionPanel;

    @Inject
    Login login;

    @UiField
    Button addCommentButton;

    @UiField
    VerticalPanel commentsPanel;

    UserUIDisplay.Presenter presenter;

    Key key;

    boolean openComment = false;

    @Inject
    public UserSkillDetailPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    public void setPresenter(UserUIDisplay.Presenter presenter){
        this.presenter = presenter;
    }

    public void setSkill(final Skill skill) {
        addCommentButton.setEnabled(login.isLoggedIn());
        driver.edit(skill);
        key = skill.getKey();

        presenter.getSkillRelations(skill, new AsyncCallback<SkillRelation[]>() {

            @Override
            public void onSuccess(SkillRelation[] result) {
                agreedActionPanel.setWidget(makeAgreedButton(skill , result));
            }

            @Override
            public void onFailure(Throwable caught) {

            }
        });
    }

    @UiHandler("addCommentButton")
    public void onClickAddCommentButton(ClickEvent e){
        presenter.showSkillCommentForm(key , commentsPanel);
    }

    @UiHandler("commentsDisclosurePanel")
    public void onOpenShowCommentButton(OpenEvent<DisclosurePanel> e){
        if(openComment){
            return;
        }
        presenter.getSkillComments(key , commentsPanel);
        openComment = true;
    }

    @UiHandler("skillPanel")
    public void onOpenSkillPanel(OpenEvent<DisclosurePanel> openEvent) {
        openLabel.setText("[－]");
    }

    @UiHandler("skillPanel")
    public void onCloseSkillPanel(CloseEvent<DisclosurePanel> closeEvent) {
        openLabel.setText("[＋]");
    }

    private Widget makeAgreedButton(final Skill skill, SkillRelation[] rs) {

        if (!login.isLoggedIn()
                || !login.getProfile().isActivate()
                || login.getEmailAddress().equals(skill.getProfile().getUserEmail())) {
            return null;
        }

        for (final SkillRelation rel : rs) {
            if (rel.getUserEmail().equals(login.getEmailAddress())) {
                if (rel.getPoint() != null && rel.getPoint() >= 10L) {         // ここはアドホックにマジックリテラルしてるけど本当はもっとやり方を考えたい
                    Label label = new Label("賛同済み");
                    label.addStyleName(style.button());
                    return label;
                }
                Button addButton = new Button("ポイント加算");
                addButton.setTitle("このスキルにポイントを加算します");
                addButton.addStyleName(style.button());
                addButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        rel.setPoint(10L);
                        presenter.showAgreedDialog(skill, rel);
                    }
                });

                return addButton;
            }
        }

        Button agreedButton = new Button("賛同する");
        agreedButton.addStyleName(style.button());
        agreedButton.setTitle("このスキルに賛同します");
        agreedButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.showAgreedDialog(skill, new SkillRelation());
            }
        });

        return agreedButton;
    }

}
