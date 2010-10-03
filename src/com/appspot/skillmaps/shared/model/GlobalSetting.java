package com.appspot.skillmaps.shared.model;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.CreationEmail;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;
import org.slim3.datastore.ModificationEmail;

@Model(schemaVersion = 1)
public class GlobalSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String twitterConsumerKey;

    private String twitterConsumerSecret;

    private ModelRef<Profile> twitterNotifier = new ModelRef<Profile>(Profile.class);


    @Attribute(listener=CreationEmail.class)
    private String createdUserEmail;

    @Attribute(listener=ModificationEmail.class)
    private String modificatedUserEmail;

    @Attribute(listener=CreationDate.class)
    private Date createdAt;

    @Attribute(listener=ModificationDate.class)
    private Date updatedAt;



    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Long getVersion() {
        return version;
    }

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
        GlobalSetting other = (GlobalSetting) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public String getTwitterConsumerKey() {
        return twitterConsumerKey;
    }

    public void setTwitterConsumerKey(String twitterConsumerKey) {
        this.twitterConsumerKey = twitterConsumerKey;
    }

    public void setTwitterConsumerSecret(String twitterConsumerSecret) {
        this.twitterConsumerSecret = twitterConsumerSecret;
    }

    public String getTwitterConsumerSecret() {
        return twitterConsumerSecret;
    }

    public ModelRef<Profile> getTwitterNotifier() {
        return twitterNotifier;
    }

    public void setCreatedUserEmail(String createdUserEmail) {
        this.createdUserEmail = createdUserEmail;
    }

    public String getCreatedUserEmail() {
        return createdUserEmail;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setModificatedUserEmail(String modificatedUserEmail) {
        this.modificatedUserEmail = modificatedUserEmail;
    }

    public String getModificatedUserEmail() {
        return modificatedUserEmail;
    }

}
