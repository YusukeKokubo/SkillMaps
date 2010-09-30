package com.appspot.skillmaps.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-09-30 16:58:20")
/** */
public final class SkillMeta extends org.slim3.datastore.ModelMeta<com.appspot.skillmaps.shared.model.Skill> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Skill> description = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Skill>(this, "description", "description");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.lang.Boolean> enable = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.lang.Boolean>(this, "enable", "enable", java.lang.Boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Skill> name = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Skill>(this, "name", "name");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Skill> ownerEmail = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Skill>(this, "ownerEmail", "ownerEmail");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.lang.Long> point = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.lang.Long>(this, "point", "point", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Skill, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final SkillMeta slim3_singleton = new SkillMeta();

    /**
     * @return the singleton
     */
    public static SkillMeta get() {
       return slim3_singleton;
    }

    /** */
    public SkillMeta() {
        super("Skill", com.appspot.skillmaps.shared.model.Skill.class);
    }

    @Override
    public com.appspot.skillmaps.shared.model.Skill entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.skillmaps.shared.model.Skill model = new com.appspot.skillmaps.shared.model.Skill();
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setDescription((java.lang.String) entity.getProperty("description"));
        model.setEnable((java.lang.Boolean) entity.getProperty("enable"));
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setOwnerEmail((java.lang.String) entity.getProperty("ownerEmail"));
        model.setPoint((java.lang.Long) entity.getProperty("point"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.skillmaps.shared.model.Skill m = (com.appspot.skillmaps.shared.model.Skill) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setProperty("description", m.getDescription());
        entity.setProperty("enable", m.getEnable());
        entity.setProperty("name", m.getName());
        entity.setProperty("ownerEmail", m.getOwnerEmail());
        entity.setProperty("point", m.getPoint());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.skillmaps.shared.model.Skill m = (com.appspot.skillmaps.shared.model.Skill) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.skillmaps.shared.model.Skill m = (com.appspot.skillmaps.shared.model.Skill) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.skillmaps.shared.model.Skill m = (com.appspot.skillmaps.shared.model.Skill) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.skillmaps.shared.model.Skill m = (com.appspot.skillmaps.shared.model.Skill) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        com.appspot.skillmaps.shared.model.Skill m = (com.appspot.skillmaps.shared.model.Skill) model;
        m.setCreatedAt(slim3_createdAtAttributeListener.prePut(m.getCreatedAt()));
        m.setUpdatedAt(slim3_updatedAtAttributeListener.prePut(m.getUpdatedAt()));
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

}