package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
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
    Anchor nextAnchor;

    @UiField
    Anchor prevAnchor;

    @UiField
    InlineLabel page;

    private int pageIndex = 0;

    private UserListResultDto userListResultDto;

    private final Provider<UserThumnail> utProvier;

    private Presenter presenter;

    @Inject
    public UserListUI(Provider<UserThumnail> utProvier) {
        this.utProvier = utProvier;
        initWidget(uiBinder.createAndBindUi(this));
        layoutSelect.setSelectedIndex(3);
    }

    @Override
    public void setUserList(int pn ,UserListResultDto userListResultDto){
        pageIndex = pn;
        this.userListResultDto = userListResultDto;

        reloadUsersPanel(userListResultDto.getUsers(), layoutSelect.getSelectedIndex() + 1);

        nextAnchor.setVisible(userListResultDto.getHasNext());

        prevAnchor.setVisible(pageIndex > 0);

        nextAnchor.setEnabled(true);

        prevAnchor.setEnabled(true);

    }

    @UiHandler("nextAnchor")
    void onNextAnchorClick(ClickEvent e){
        if(!nextAnchor.isEnabled()){
            return;
        }
        prevAnchor.setVisible(true);
        pageIndex++;
        setupUsersLoad();
        presenter.loadNextUsers(pageIndex ,userListResultDto);
    }

    @UiHandler("prevAnchor")
    void onPrevAnchorClick(ClickEvent e){
        if(!nextAnchor.isEnabled()){
            return;
        }
        nextAnchor.setVisible(true);
        pageIndex--;
        setupUsersLoad();
        presenter.loadPrevUsers(pageIndex,userListResultDto);
    }

    private void setupUsersLoad() {
        nextAnchor.setEnabled(false);
        prevAnchor.setEnabled(false);
        usersPanel.clear();
        usersPanel.add(new Image(Resources.INSTANCE.loader()));
        page.setText(String.valueOf(pageIndex + 1));
    }

    private void reloadUsersPanel(Profile[] users, int viewColumn) {

        HorizontalPanel hPanel = null;
        usersPanel.clear();
        int column = 0;
        int thumnailWidth = usersPanel.getOffsetWidth() / viewColumn;
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
    }

    @UiHandler("layoutSelect")
    void onLayoutSelectChange(ChangeEvent ce){
        usersPanel.clear();
        reloadUsersPanel(userListResultDto.getUsers(), layoutSelect.getSelectedIndex() + 1);
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

}
