package com.appspot.skillmaps.shared.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class MailQueue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(listener=CreationDate.class)
    private Date createdAt;

    @Attribute(listener=ModificationDate.class)
    private Date updatedAt;

    private String subject = "";
    
    @Attribute(lob=true)
    private String textBody = "";
    
    private String sender = "";
    
    private String to = "";
    
    private String bcc = "";

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
        MailQueue other = (MailQueue) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
