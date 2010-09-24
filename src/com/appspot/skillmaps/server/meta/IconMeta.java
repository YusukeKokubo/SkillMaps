package com.appspot.skillmaps.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-09-24 09:06:18")
/** */
public final class IconMeta extends org.slim3.datastore.ModelMeta<com.appspot.skillmaps.shared.model.Icon> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreUnindexedAttributeMeta<com.appspot.skillmaps.shared.model.Icon, byte[]> image = new org.slim3.datastore.CoreUnindexedAttributeMeta<com.appspot.skillmaps.shared.model.Icon, byte[]>(this, "image", "image", byte[].class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Icon> keyString = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Icon>(this, "keyString", "keyString");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Icon> userEmail = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.Icon>(this, "userEmail", "userEmail");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.Icon, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final org.slim3.datastore.CreationEmail slim3_userEmailAttributeListener = new org.slim3.datastore.CreationEmail();

    private static final IconMeta slim3_singleton = new IconMeta();

    /**
     * @return the singleton
     */
    public static IconMeta get() {
       return slim3_singleton;
    }

    /** */
    public IconMeta() {
        super("Icon", com.appspot.skillmaps.shared.model.Icon.class);
    }

    @Override
    public com.appspot.skillmaps.shared.model.Icon entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.skillmaps.shared.model.Icon model = new com.appspot.skillmaps.shared.model.Icon();
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setImage(blobToBytes((com.google.appengine.api.datastore.Blob) entity.getProperty("image")));
        model.setKey(entity.getKey());
        model.setKeyString((java.lang.String) entity.getProperty("keyString"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setUserEmail((java.lang.String) entity.getProperty("userEmail"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.skillmaps.shared.model.Icon m = (com.appspot.skillmaps.shared.model.Icon) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setUnindexedProperty("image", bytesToBlob(m.getImage()));
        entity.setProperty("keyString", m.getKeyString());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("userEmail", m.getUserEmail());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.skillmaps.shared.model.Icon m = (com.appspot.skillmaps.shared.model.Icon) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.skillmaps.shared.model.Icon m = (com.appspot.skillmaps.shared.model.Icon) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.skillmaps.shared.model.Icon m = (com.appspot.skillmaps.shared.model.Icon) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.skillmaps.shared.model.Icon m = (com.appspot.skillmaps.shared.model.Icon) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        com.appspot.skillmaps.shared.model.Icon m = (com.appspot.skillmaps.shared.model.Icon) model;
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