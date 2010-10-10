package com.appspot.skillmaps.shared.dto;

import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserListResultDto implements IsSerializable {

    private Profile[] users;

    private String encodedCursor;

    private String encodedFilter;

    private String encodedSorts;

    private String encodedUnit;

    private int pn;

    private boolean hasNext;

    /**
     * usersを取得します。
     * @return users
     */
    public Profile[] getUsers() {
        return users;
    }

    /**
     * usersを設定します。
     * @param users users
     */
    public void setUsers(Profile[] users) {
        this.users = users;

    }

    /**
     * encodedCursorを取得します。
     * @return encodedCursor
     */
    public String getEncodedCursor() {
        return encodedCursor;
    }

    /**
     * encodedCursorを設定します。
     * @param encodedCursor encodedCursor
     */
    public void setEncodedCursor(String encodedCursor) {
        this.encodedCursor = encodedCursor;
    }

    /**
     * encodedFilterを取得します。
     * @return encodedFilter
     */
    public String getEncodedFilter() {
        return encodedFilter;
    }

    /**
     * encodedFilterを設定します。
     * @param encodedFilter encodedFilter
     */
    public void setEncodedFilter(String encodedFilter) {
        this.encodedFilter = encodedFilter;
    }

    /**
     * encodedSortsを取得します。
     * @return encodedSorts
     */
    public String getEncodedSorts() {
        return encodedSorts;
    }

    /**
     * encodedSortsを設定します。
     * @param encodedSorts encodedSorts
     */
    public void setEncodedSorts(String encodedSorts) {
        this.encodedSorts = encodedSorts;
    }

    /**
     * encodedUnitを取得します。
     * @return encodedUnit
     */
    public String getEncodedUnit() {
        return encodedUnit;
    }

    /**
     * encodedUnitを設定します。
     * @param encodedUnit encodedUnit
     */
    public void setEncodedUnit(String encodedUnit) {
        this.encodedUnit = encodedUnit;
    }

    public void setPn(int pageNumber) {
        this.pn = pageNumber;

    }

    /**
     * pnを取得します。
     * @return pn
     */
    public int getPn() {
        return pn;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;

    }

    /**
     * hasNextを取得します。
     * @return hasNext
     */
    public boolean getHasNext() {
        return hasNext;
    }

}
