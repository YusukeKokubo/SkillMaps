package com.appspot.skillmaps.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-10-02 22:17:56")
/** */
public final class SkillRelationMeta extends org.slim3.datastore.ModelMeta<com.appspot.skillmaps.shared.model.SkillRelation> {

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation> comment = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation>(this, "comment", "comment");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, org.slim3.datastore.ModelRef<com.appspot.skillmaps.shared.model.Skill>, com.appspot.skillmaps.shared.model.Skill> skill = new org.slim3.datastore.ModelRefAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, org.slim3.datastore.ModelRef<com.appspot.skillmaps.shared.model.Skill>, com.appspot.skillmaps.shared.model.Skill>(this, "skill", "skill", org.slim3.datastore.ModelRef.class, com.appspot.skillmaps.shared.model.Skill.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation> userEmail = new org.slim3.datastore.StringAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation>(this, "userEmail", "userEmail");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.skillmaps.shared.model.SkillRelation, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final org.slim3.datastore.CreationEmail slim3_userEmailAttributeListener = new org.slim3.datastore.CreationEmail();

    private static final SkillRelationMeta slim3_singleton = new SkillRelationMeta();

    /**
     * @return the singleton
     */
    public static SkillRelationMeta get() {
       return slim3_singleton;
    }

    /** */
    public SkillRelationMeta() {
        super("SkillRelation", com.appspot.skillmaps.shared.model.SkillRelation.class);
    }

    @Override
    public com.appspot.skillmaps.shared.model.SkillRelation entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.skillmaps.shared.model.SkillRelation model = new com.appspot.skillmaps.shared.model.SkillRelation();
        model.setComment((java.lang.String) entity.getProperty("comment"));
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setKey(entity.getKey());
        if (model.getSkill() == null) {
            throw new NullPointerException("The property(skill) is null.");
        }
        model.getSkill().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("skill"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setUserEmail((java.lang.String) entity.getProperty("userEmail"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.skillmaps.shared.model.SkillRelation m = (com.appspot.skillmaps.shared.model.SkillRelation) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("comment", m.getComment());
        entity.setProperty("createdAt", m.getCreatedAt());
        if (m.getSkill() == null) {
            throw new NullPointerException("The property(skill) must not be null.");
        }
        entity.setProperty("skill", org.slim3.datastore.ModelMeta.assignKeyIfNecessary(m.getSkill().getModelMeta(), m.getSkill().getModel()));
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("userEmail", m.getUserEmail());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.skillmaps.shared.model.SkillRelation m = (com.appspot.skillmaps.shared.model.SkillRelation) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.skillmaps.shared.model.SkillRelation m = (com.appspot.skillmaps.shared.model.SkillRelation) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.skillmaps.shared.model.SkillRelation m = (com.appspot.skillmaps.shared.model.SkillRelation) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.skillmaps.shared.model.SkillRelation m = (com.appspot.skillmaps.shared.model.SkillRelation) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        com.appspot.skillmaps.shared.model.SkillRelation m = (com.appspot.skillmaps.shared.model.SkillRelation) model;
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