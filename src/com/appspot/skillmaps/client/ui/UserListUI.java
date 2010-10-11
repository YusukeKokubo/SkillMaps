package com.appspot.skillmaps.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserListUI extends Composite {

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    HashMap<String, UserUI> usersMap = new HashMap<String, UserUI>();

    AccountServiceAsync service = GWT.create(AccountService.class);


    interface UsersUiBinder extends UiBinder<Widget, UserListUI> {
    }

    @UiField
    VerticalPanel usersPanel;

    @UiField
    PopupPanel userDialog;

    @UiField
    ListBox layoutSelect;

    @UiField
    Anchor nextAnchor;

    @UiField
    Anchor prevAnchor;

    @UiField
    InlineLabel page;

    private int pageIndex = 0;

    private Map<Integer , UserListResultDto> pageMap = new HashMap<Integer, UserListResultDto>();

    private final Login login;

    private Profile[] users;

    private UserListResultDto userListResultDto;

    public UserListUI(Login login, UserListResultDto userListResultDto) {
        this.userListResultDto = userListResultDto;
        this.login = login;
        this.users = userListResultDto.getUsers();
        initWidget(uiBinder.createAndBindUi(this));
        if(!this.userListResultDto.getHasNext()){

            nextAnchor.setVisible(false);
        }
        layoutSelect.addItem("1");
        layoutSelect.addItem("2");
        layoutSelect.addItem("3");
        layoutSelect.addItem("4");
        layoutSelect.setSelectedIndex(3);
        pageMap.put(pageIndex, userListResultDto);
        reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
    }

    @UiHandler("nextAnchor")
    void onNextAnchorClick(ClickEvent e){
        if(!nextAnchor.isEnabled()){
            return;
        }
        nextAnchor.setEnabled(false);
        prevAnchor.setEnabled(false);
        prevAnchor.setVisible(true);
        usersPanel.clear();
        pageIndex++;
        page.setText(String.valueOf(pageIndex));
        if(!pageMap.containsKey(pageIndex)){
            service.getUsers(0,
                        userListResultDto.getEncodedCursor(),
                        userListResultDto.getEncodedFilter(),
                        userListResultDto.getEncodedSorts(),
                new AsyncCallback<UserListResultDto>() {

                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(UserListResultDto result) {
                        userListResultDto = result;
                        pageMap.put(pageIndex, result);
                        users = result.getUsers();
                        reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
                        nextAnchor.setEnabled(true);
                        prevAnchor.setEnabled(true);
                        if(!result.getHasNext()){
                            nextAnchor.setVisible(false);
                        }
                    }
            });

        } else {
            userListResultDto = pageMap.get(pageIndex);
            users = userListResultDto.getUsers();
            reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
            nextAnchor.setEnabled(true);
            prevAnchor.setEnabled(true);
            if(!userListResultDto.getHasNext()){
                nextAnchor.setVisible(false);
            }
        }
    }

    @UiHandler("prevAnchor")
    void onPrevAnchorClick(ClickEvent e){
        if(!nextAnchor.isEnabled()){
            return;
        }
        nextAnchor.setEnabled(false);
        nextAnchor.setVisible(true);

        prevAnchor.setEnabled(false);
        usersPanel.clear();
        pageIndex--;
        if(pageIndex <= 0){
            prevAnchor.setVisible(false);
        }
        page.setText(String.valueOf(pageIndex));
        if(!pageMap.containsKey(pageIndex)){
            service.getUsers(pageIndex * -1,
                userListResultDto.getEncodedCursor(),
                userListResultDto.getEncodedFilter(),
                userListResultDto.getEncodedSorts(),
            new AsyncCallback<UserListResultDto>() {

                @Override
                public void onFailure(Throwable caught) {
                }

                @Override
                public void onSuccess(UserListResultDto result) {
                    userListResultDto = result;
                    pageMap.put(pageIndex, result);
                    users = result.getUsers();
                    reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
                    nextAnchor.setEnabled(true);
                    prevAnchor.setEnabled(true);
                }
            });

        } else {
            userListResultDto = pageMap.get(pageIndex);
            users = userListResultDto.getUsers();
            reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
            nextAnchor.setEnabled(true);
            prevAnchor.setEnabled(true);
        }
    }

    protected void reloadUsersPanel(final Login login, Profile[] users, int viewColumn) {

        HorizontalPanel hPanel = null;
        int column = 0;
        int thumnailWidth = RootPanel.get("users").getOffsetWidth() / viewColumn;
        for (final Profile user : users) {

            if(column == 0){
                hPanel = new HorizontalPanel();
            }
            column++;

            FocusPanel panel = new FocusPanel();
            panel.setWidth(thumnailWidth + "px");
            panel.add(new UserThumnail(login, user, userDialog));

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
    }

    @UiHandler("layoutSelect")
    void onLayoutSelectChange(ChangeEvent ce){
        usersPanel.clear();
        reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
    }

}
