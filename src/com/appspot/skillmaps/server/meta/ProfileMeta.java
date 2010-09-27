package com.appspot.skillmaps.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-09-27 11:08:01")
/** */
public final class ProfileMeta extends org.slim3.datastore.ModelMeta<com.appspot.skillmaps.shared.model.Profile> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, com.google.appengine.api.datastore.Key> iconKey = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, com.google.appengine.api.datastore.Key>(this, "iconKey", "iconKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile> iconKeyString = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile>(this, "iconKeyString", "iconKeyString");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile> id = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile>(this, "id", "id");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile> name = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile>(this, "name", "name");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile> selfIntroduction = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile>(this, "selfIntroduction", "selfIntroduction");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile> userEmail = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Profile>(this, "userEmail", "userEmail");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Profile, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final org.slim3.datastore.CreationEmail slim3_userEmailAttributeListener = new org.slim3.datastore.CreationEmail();

    private static final ProfileMeta slim3_singleton = new ProfileMeta();

    /**
     * @return the singleton
     */
    public static ProfileMeta get() {
       return slim3_singleton;
    }

    /** */
    public ProfileMeta() {
        super("Profile", com.appspot.skillmaps.shared.model.Profile.class);
    }

    @Override
    public com.appspot.skillmaps.shared.model.Profile entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.skillmaps.shared.model.Profile model = new com.appspot.skillmaps.shared.model.Profile();
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setIconKey((com.google.appengine.api.datastore.Key) entity.getProperty("iconKey"));
        model.setIconKeyString((java.lang.String) entity.getProperty("iconKeyString"));
        model.setId((java.lang.String) entity.getProperty("id"));
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setSelfIntroduction((java.lang.String) entity.getProperty("selfIntroduction"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setUserEmail((java.lang.String) entity.getProperty("userEmail"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.skillmaps.shared.model.Profile m = (com.appspot.skillmaps.shared.model.Profile) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setProperty("iconKey", m.getIconKey());
        entity.setProperty("iconKeyString", m.getIconKeyString());
        entity.setProperty("id", m.getId());
        entity.setProperty("name", m.getName());
        entity.setProperty("selfIntroduction", m.getSelfIntroduction());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("userEmail", m.getUserEmail());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.skillmaps.shared.model.Profile m = (com.appspot.skillmaps.shared.model.Profile) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.skillmaps.shared.model.Profile m = (com.appspot.skillmaps.shared.model.Profile) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.skillmaps.shared.model.Profile m = (com.appspot.skillmaps.shared.model.Profile) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.skillmaps.shared.model.Profile m = (com.appspot.skillmaps.shared.model.Profile) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        com.appspot.skillmaps.shared.model.Profile m = (com.appspot.skillmaps.shared.model.Profile) model;
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