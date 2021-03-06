package com.appspot.skillmaps.shared.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.CreationEmail;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.GwtTransient;

@Model(schemaVersion = 1)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String id;

    private String name;

    private String address;

    private String selfIntroduction;

    private Key iconKey;
    private String iconKeyString;

    private String profileUrl1;

    private String profileUrl2;

    @GwtTransient
    private String twitterToken;

    @GwtTransient
    private String twitterTokenSecret;

    private String twitterScreenName;

    private Boolean allowFromTwitterNotifier;

    private Boolean allowFromMailNotifier;

    @GwtTransient
    private String githubToken;

    private String githubLogin;

    private Boolean hasIcon;

    @Attribute(listener = CreationEmail.class)
    @GwtTransient
    private String userEmail;

    @Attribute(listener = CreationDate.class)
    private Date createdAt;

    @Attribute(listener = ModificationDate.class)
    private Date updatedAt;

    public boolean isActivate() {
        return id != null && !id.isEmpty();
    }

    public boolean isEnabledTwitter() {
        return twitterScreenName != null && !twitterScreenName.isEmpty();
    }

    public boolean isEnabledGitHub() {
        return githubLogin != null && !githubLogin.isEmpty();
    }

    public String getIconUrl() {
        if (getHasIcon() != null && getHasIcon()) {
            return "/images/icon/" + getIconKeyString();
        }
        return null;
    }

    /**
     * Returns the key.
     * 
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     * 
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Profile other = (Profile) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setIconKey(Key iconKey) {
        this.iconKey = iconKey;
    }

    public Key getIconKey() {
        return iconKey;
    }

    public String getIconKeyString() {
        return iconKeyString;
    }

    public void setIconKeyString(String iconKeyString) {
        this.iconKeyString = iconKeyString;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getProfileUrl1() {
        return profileUrl1;
    }

    public void setProfileUrl1(String profileUrl1) {
        this.profileUrl1 = profileUrl1;
    }

    public String getProfileUrl2() {
        return profileUrl2;
    }

    public void setProfileUrl2(String profileUrl2) {
        this.profileUrl2 = profileUrl2;
    }

    /**
     * Returns the twitterToken.
     * 
     * @return the twitterToken
     */
    public String getTwitterToken() {
        return twitterToken;
    }

    /**
     * Sets the twitter
     * 
     * @param twitterToken
     */
    public void setTwitterToken(String twitterToken) {
        this.twitterToken = twitterToken;
    }

    /**
     * Returns the twitterTokenSecret.
     * 
     * @return the twitterTokenSecret
     */
    public String getTwitterTokenSecret() {
        return twitterTokenSecret;
    }

    /**
     * Sets the twitterTokenSecret
     * 
     * @param twitterTokenSecret
     */
    public void setTwitterTokenSecret(String twitterTokenSecret) {
        this.twitterTokenSecret = twitterTokenSecret;
    }

    public void setTwitterScreenName(String twitterScreenName) {
        this.twitterScreenName = twitterScreenName;
    }

    public String getTwitterScreenName() {
        return twitterScreenName;
    }

    public void setHasIcon(Boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public Boolean getHasIcon() {
        return hasIcon;
    }

    public void setAllowFromTwitterNotifier(Boolean allowFromTwitterNotifier) {
        this.allowFromTwitterNotifier = allowFromTwitterNotifier;
    }

    public Boolean getAllowFromTwitterNotifier() {
        return allowFromTwitterNotifier;
    }

    public void setAllowFromMailNotifier(Boolean allowFromMailNotifier) {
        this.allowFromMailNotifier = allowFromMailNotifier;
    }

    public Boolean getAllowFromMailNotifier() {
        return allowFromMailNotifier;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public void setGithubLogin(String githubLogin) {
        this.githubLogin = githubLogin;
    }

    public String getGithubLogin() {
        return githubLogin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

}
