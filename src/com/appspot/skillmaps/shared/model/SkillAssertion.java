package com.appspot.skillmaps.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class SkillAssertion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private ModelRef<SkillA> skill = new ModelRef<SkillA>(SkillA.class);
    
    private String url;
    
    private String description;
    
    private List<Key> agrees = new ArrayList<Key>();

    private ModelRef<Profile> createdBy = new ModelRef<Profile>(Profile.class);
    
    private List<Key> comments = new ArrayList<Key>();

    @Attribute(listener=CreationDate.class)
    private Date createdAt;

    @Attribute(listener=ModificationDate.class)
    private Date updatedAt;
    
    public boolean isCreatedByOwn() {
        return isCreatedByOwn(getSkill().getModel());
    }
    
    public boolean isCreatedByOwn(SkillA skill) {
        return getCreatedBy().getKey().equals(skill.getHolder().getKey());
    }
    
    public boolean isAgreedBy(Profile profile) {
        if (profile == null || profile.getKey() == null) return false;
        if (getAgrees() == null) return false;
        return getAgrees().indexOf(profile.getKey()) > -1;
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
        SkillAssertion other = (SkillAssertion) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public ModelRef<SkillA> getSkill() {
        return skill;
    }

    public ModelRef<Profile> getCreatedBy() {
        return createdBy;
    }

    public void setAgrees(List<Key> agrees) {
        this.agrees = agrees;
    }

    public List<Key> getAgrees() {
        return agrees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Key> getComments() {
        return comments;
    }

    public void setComments(List<Key> comments) {
        this.comments = comments;
    }

}
