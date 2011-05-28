package com.appspot.skillmaps.client.ui;

import java.util.Arrays;
import java.util.List;

import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserListUI extends Composite implements UserListDisplay{

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    interface UsersUiBinder extends UiBinder<Widget, UserListUI> {
    }

    @UiField
    VerticalPanel usersPanel;

    @UiField
    ListBox layoutSelect;

    @UiField
    DivElement usersToolBar;

    @UiField
    FocusPanel addUserPanel;

    @UiField
    Image loaderImage;

    @UiField
    Button searchButton;

    @UiField
    TextBox id;

    @UiField
    Anchor return2List;

    @UiField
    HTMLPanel layout;

    private UserListResultDto userListResultDto;

    private final Provider<UserThumnail> utProvier;

    private Presenter presenter;

    private final Injector injector;

    @Inject
    public UserListUI(Provider<UserThumnail> utProvier , Injector injector) {
        this.utProvier = utProvier;
        this.injector = injector;
        initWidget(uiBinder.createAndBindUi(this));
        layoutSelect.setSelectedIndex(0);
    }

    @Override
    public void setUserList(UserListResultDto userListResultDto){
        if(this.userListResultDto == null) {
            this.userListResultDto = userListResultDto;

        } else {
            List<Profile> list = Lists.newArrayList(this.userListResultDto.getUsers());
            list.addAll(Arrays.asList(userListResultDto.getUsers()));

            this.userListResultDto.setUsers(list.toArray(new Profile[0]));
            this.userListResultDto.setEncodedCursor(userListResultDto.getEncodedCursor());
            this.userListResultDto.setEncodedFilter(userListResultDto.getEncodedFilter());
            this.userListResultDto.setEncodedSorts(userListResultDto.getEncodedSorts());
            this.userListResultDto.setEncodedUnit(userListResultDto.getEncodedUnit());
            this.userListResultDto.setHasNext(userListResultDto.getHasNext());
        }

        addUsers(userListResultDto.getUsers(), layoutSelect.getSelectedIndex() + 1);

        if(!userListResultDto.getHasNext()) {
            addUserPanel.setVisible(false);
        } else {
            addUserPanel.setVisible(true);
        }
    }

    @Override
    public void setUserList(Profile[] users) {
        reloadUsersPanel(users,  layoutSelect.getSelectedIndex() + 1);
    }

    private void reloadUsersPanel(Profile[] users, int viewColumn) {

        int scrollTop = Window.getScrollTop();
        usersPanel.clear();

        addUsers(users, viewColumn);

        Window.scrollTo(0, scrollTop);

    }

    private void addUsers(Profile[] users, int viewColumn) {
        loaderImage.setVisible(true);
        HorizontalPanel hPanel = null;
        int column = 0;
        int thumnailWidth = usersPanel.getOffsetWidth() / viewColumn;
        if(thumnailWidth == 0){
            thumnailWidth = usersToolBar.getOffsetWidth() / viewColumn;
            if(thumnailWidth == 0){
                thumnailWidth = injector.getContentPanel().getOffsetWidth() / viewColumn;
                if(thumnailWidth == 0){
                    thumnailWidth = 500 / viewColumn;
                }
            }
        }
        for (final Profile user : users) {
            if(column == 0){
                hPanel = new HorizontalPanel();
            }
            column++;

            FocusPanel panel = new FocusPanel();
            panel.setWidth(thumnailWidth + "px");
            UserThumnail userThumnail = utProvier.get();
            userThumnail.setUser(user);
            panel.add(userThumnail);

            if(column < viewColumn){
                hPanel.add(panel);
            }else if(column == viewColumn){
                hPanel.add(panel);
                usersPanel.add(hPanel);
                column = 0;
                hPanel = null;
            }
        }
        if(hPanel != null){
            usersPanel.add(hPanel);
        }

        if(users.length == 0) {
            usersPanel.add(new Label("ユーザが見つかりませんでした。"));
        }

        loaderImage.setVisible(false);
    }

    @UiHandler("layoutSelect")
    void onLayoutSelectChange(ChangeEvent ce){
        usersPanel.clear();
        reloadUsersPanel(this.userListResultDto.getUsers(), layoutSelect.getSelectedIndex() + 1);
    }

    @UiHandler("addUserPanel")
    void onAddUserPanelClick(ClickEvent e) {
        addUserPanel.setVisible(false);
        loaderImage.setVisible(true);
        presenter.loadNextUsers(userListResultDto);
    }

    @UiHandler("searchButton")
    void onSearchButtonClick(ClickEvent e) {

        if(Strings.isNullOrEmpty(id.getText())) {
            UiMessage.info("検索するIDを入力してください。");
            return;
        }
        layout.setVisible(false);
        usersPanel.clear();
        addUserPanel.setVisible(false);
        loaderImage.setVisible(true);
        return2List.setVisible(true);

        presenter.findUsers(id.getText());
    }

    @UiHandler("return2List")
    void onReturn2ListClick(ClickEvent e) {
        return2List.setVisible(false);
        layout.setVisible(true);
        usersPanel.clear();
        UserListResultDto ulrd = this.userListResultDto;
        this.userListResultDto = null;
        setUserList(ulrd);
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }

    @Override
    public HasWidgets getUserListPanel() {
        return usersPanel;
    }

    @Override
    public void clearData() {
        userListResultDto = null;
    }
}
