package com.appspot.skillmaps.shared.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.CreationEmail;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String name;

    private Long point;
    
    private Long agreedCount;

    private String description;

    private Boolean enable;

    @Attribute(persistent=false)
    private InverseModelListRef<SkillRelation, Skill> relation = 
        new InverseModelListRef<SkillRelation, Skill>(SkillRelation.class, "skill", this);

    private String ownerEmail;
    
    @Attribute(listener=CreationEmail.class)
    private String createdUserEmail;

    @Attribute(listener=CreationDate.class)
    private Date createdAt;

    @Attribute(listener=ModificationDate.class)
    private Date updatedAt;

    @Attribute(persistent=false)
    private Profile profile;



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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
        Skill other = (Skill) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
    
    public void calcPoint() {
        long point = 0L;
        for (SkillRelation srel : this.getRelation().getModelList()) {
            if (srel.getPoint() == null) continue;
            point += srel.getPoint();
        }
        this.point = point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getPoint() {
        return point;
    }

    public InverseModelListRef<SkillRelation, Skill> getRelation() {
        return relation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setCreatedUserEmail(String createdUserEmail) {
        this.createdUserEmail = createdUserEmail;
    }

    public String getCreatedUserEmail() {
        return createdUserEmail;
    }

    public void setAgreedCount(Long agreedCount) {
        this.agreedCount = agreedCount;
    }

    public Long getAgreedCount() {
        return agreedCount;
    }
}
