/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTagsUtxServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.services.RecordTagsStorageService;
import su.svn.showcase.utils.CollectionUtil;
import su.svn.showcase.utils.StringUtil;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;
import static su.svn.showcase.utils.CollectionUtil.convertList;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecordTagsUtxServiceImpl extends AbstractUserTransactionService
       implements RecordTagsStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordTagsUtxServiceImpl.class);

    private static final Pattern validating = Pattern.compile("[\\w\\sа-яА-Я]+");

    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    @Inject
    private UserTransaction userTransaction;

    @Override
    public void addTagsToRecord(@Nonnull RecordFullDto record, @Nonnull Iterable<TagBaseDto> tags) {
        utxExecuteBySupplier(() -> acceptTagsToRecord(record, tags));
    }

    private EntityManager acceptTagsToRecord(RecordFullDto dto, Iterable<TagBaseDto> tags) {
        EntityManager entityManager = emf.createEntityManager();
        Record record = getRecord(entityManager, dto);
        acceptTagsToRecord(entityManager, record, tags);

        return entityManager;
    }

    private Record getRecord(EntityManager em, RecordFullDto dto) {
        return em.find(Record.class, dto.getId());
    }

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void acceptTagsToRecord(EntityManager entityManager, Record record, Iterable<TagBaseDto> tags) {
        Set<String> setLabels = CollectionUtil.iterableToStream(tags)
                .map(TagBaseDto::getTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToRecord setLabels: {}", setLabels);
        Set<String> newTagLabels = new HashSet<>(outerSection(entityManager, setLabels));
        getLogger().info("acceptTagsToRecord newTagLabels: {}", newTagLabels);
        Set<Tag> newTags = newTagLabels.stream()
                .map(this::constructTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToRecord newTags: {}", newTags);
        if ( record != null && ! newTags.isEmpty()) {
            saveAll(entityManager, newTags);
            List<Tag> savedTags = findAllByTagIn(entityManager, setLabels);
            record.setTags(new HashSet<>(savedTags));
            entityManager.merge(record);
            entityManager.flush();
            getLogger().info("acceptTagsToRecord entry: {}", record);
            getLogger().info("acceptTagsToRecord entry.getTags(): {}", record.getTags());
        }
    }

    private List<Tag> findAllByTagIn(EntityManager em, Set<String> tags) {
        return em.createNamedQuery(Tag.FIND_ALL_WHERE_TAG_IN, Tag.class)
                .setParameter("tags", tags)
                .getResultList();
    }

    private void saveAll(EntityManager em, Set<Tag> newTags) {
        List<String> ids = new ArrayList<>();
        for (Tag entity : newTags) {
            if (null == entity.getId()) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }
            ids.add(entity.getId());
        }
        getLogger().info("Save {} with ids: {}", Tag.class.getSimpleName(), ids);
    }

    private List<String> outerSection(EntityManager em, Iterable<String> tags) {
        if ( ! isValidListOfTags(tags)) {
            throw new RuntimeException("Isn't valid tag in list: " + tags);
        }
        String values = CollectionUtil.iterableToStream(tags)
                .map(s -> "('" + s + "')")
                .collect(Collectors.joining(","));
        String sql = String.format(Tag.OUTER_SECTION, values);

        return convertList(em.createNativeQuery(sql).getResultList(), String.class);
    }

    private boolean isValidListOfTags(Iterable<String> tags) {
        for (String tag : tags) {
            if ( ! validating.matcher(tag).matches()) {
                return false;
            }
        }
        return true;
    }

    private Tag constructTag(String tag) {
        return Tag.builder()
                .id(StringUtil.generateTagId(tag))
                .visible(true)
                .tag(tag)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
//EOF
