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
import su.svn.showcase.dao.jpa.RecordDaoJpa;
import su.svn.showcase.dao.jpa.TagDaoJpa;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
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
import java.util.stream.Collectors;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecordTagsUtxServiceImpl extends AbstractUserTransactionService
       implements RecordTagsStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordTagsUtxServiceImpl.class);

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
        RecordDaoJpa recordDaoJpa = new RecordDaoJpa(entityManager);
        Record record = recordDaoJpa.findById(dto.getId()).orElseThrow(ErrorCase::notFound);
        acceptTagsToRecord(entityManager, record, tags);

        return entityManager;
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
        RecordDaoJpa recordDaoJpa = new RecordDaoJpa(entityManager);
        TagDaoJpa tagDaoJpa = new TagDaoJpa(entityManager);
        Set<String> setLabels = CollectionUtil.iterableToStream(tags)
                .map(TagBaseDto::getTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToRecord setLabels: {}", setLabels);
        Set<String> newTagLabels = new HashSet<>(tagDaoJpa.outerSection(setLabels));
        getLogger().info("acceptTagsToRecord newTagLabels: {}", newTagLabels);
        Set<Tag> newTags = newTagLabels.stream()
                .map(this::constructTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToRecord newTags: {}", newTags);
        if (record != null && notEqualsCardinalities(setLabels, record.getTags())) {
            tagDaoJpa.saveAll(newTags);
            List<Tag> savedTags = tagDaoJpa.findAllByTagIn(setLabels);
            record.setTags(new HashSet<>(savedTags));
            recordDaoJpa.save(record);
            getLogger().info("acceptTagsToRecord entry: {}", record);
            getLogger().info("acceptTagsToRecord entry.getTags(): {}", record.getTags());
        }
    }

    private boolean notEqualsCardinalities(Collection<?> collection1, Collection<?> collection2) {
        return collection1.size() != collection2.size();
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
