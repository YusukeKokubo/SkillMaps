package com.appspot.skillmaps.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-09-30 11:18:25")
/** */
public final class SkillAppealMeta extends org.slim3.datastore.ModelMeta<com.appspot.skillmaps.shared.model.SkillAppeal> {

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal> appealSkillName = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal>(this, "appealSkillName", "appealSkillName");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal> description = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal>(this, "description", "description");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal> url = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal>(this, "url", "url");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal> userEmail = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal>(this, "userEmail", "userEmail");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillAppeal, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final org.slim3.datastore.CreationEmail slim3_userEmailAttributeListener = new org.slim3.datastore.CreationEmail();

    private static final SkillAppealMeta slim3_singleton = new SkillAppealMeta();

    /**
     * @return the singleton
     */
    public static SkillAppealMeta get() {
       return slim3_singleton;
    }

    /** */
    public SkillAppealMeta() {
        super("SkillAppeal", com.appspot.skillmaps.shared.model.SkillAppeal.class);
    }

    @Override
    public com.appspot.skillmaps.shared.model.SkillAppeal entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.skillmaps.shared.model.SkillAppeal model = new com.appspot.skillmaps.shared.model.SkillAppeal();
        model.setAppealSkillName((java.lang.String) entity.getProperty("appealSkillName"));
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setDescription((java.lang.String) entity.getProperty("description"));
        model.setKey(entity.getKey());
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setUrl((java.lang.String) entity.getProperty("url"));
        model.setUserEmail((java.lang.String) entity.getProperty("userEmail"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.skillmaps.shared.model.SkillAppeal m = (com.appspot.skillmaps.shared.model.SkillAppeal) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("appealSkillName", m.getAppealSkillName());
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setProperty("description", m.getDescription());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("url", m.getUrl());
        entity.setProperty("userEmail", m.getUserEmail());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.skillmaps.shared.model.SkillAppeal m = (com.appspot.skillmaps.shared.model.SkillAppeal) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.skillmaps.shared.model.SkillAppeal m = (com.appspot.skillmaps.shared.model.SkillAppeal) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.skillmaps.shared.model.SkillAppeal m = (com.appspot.skillmaps.shared.model.SkillAppeal) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.skillmaps.shared.model.SkillAppeal m = (com.appspot.skillmaps.shared.model.SkillAppeal) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        com.appspot.skillmaps.shared.model.SkillAppeal m = (com.appspot.skillmaps.shared.model.SkillAppeal) model;
        m.setCreatedAt(slim3_createdAtAttributeListener.prePut(m.getCreatedAt()));
        m.setUpdatedAt(slim3_updatedAtAttributeListener.prePut(m.getUpdatedAt()));
        m.setUserEmail(slim3_userEmailAttributeListener.prePut(m.getUserEmail()));
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