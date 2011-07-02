package com.appspot.skillmaps.client.ui.parts.user;

import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.ui.UserThumnail;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAssertion;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
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

    @UiField
    @Editor.Ignore
    Label openLabel;

    @UiField
    SimplePanel agreedActionPanel;
    
    @UiField
    VerticalPanel assertions;
    
    @UiField
    @Editor.Ignore
    TextBox url;
    
    @UiField
    Button addAssertion;

    UserUIDisplay.Presenter presenter;

    Key key;

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
        driver.edit(skill);
        if(skill.getName().length() > 10){
            name.setText(skill.getName().substring(0, 10) + "…");
        }
        name.setTitle(skill.getName());
        key = skill.getKey();
    }
    
    @UiHandler("addAssertion")
    public void onClickAddAssertion(ClickEvent e) {
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl(url.getValue());
        assertion.getSkill().setModel(driver.flush());
        presenter.addAssertion(assertion );
    }

    @UiHandler("skillPanel")
    public void onOpenSkillPanel(OpenEvent<DisclosurePanel> openEvent) {
        openLabel.setText("[－]");

        presenter.getAssertions(driver.flush(), assertions);
    }

    @UiHandler("skillPanel")
    public void onCloseSkillPanel(CloseEvent<DisclosurePanel> closeEvent) {
        openLabel.setText("[＋]");
    }
}
