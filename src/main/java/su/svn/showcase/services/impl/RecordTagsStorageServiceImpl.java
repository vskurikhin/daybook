/*
 * This file was last modified at 2020.02.24 22:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTagsStorageServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordTagsStorageService;
import su.svn.showcase.utils.CollectionUtil;
import su.svn.showcase.utils.StringUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecordTagsStorageServiceImpl extends AbstractUserTransactionService
       implements RecordTagsStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordTagsStorageServiceImpl.class);

    @EJB(beanName = "TagDaoJpa")
    private TagDao tagDao;

    @EJB(beanName = "RecordDaoJpa")
    private RecordDao recordDao;

    @Inject
    private UserTransaction userTransaction;

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void addTagsToRecord(RecordFullDto record, Iterable<TagBaseDto> tags) {
        execute(acceptTagsToRecord(record, tags));
    }

    private Runnable acceptTagsToRecord(RecordFullDto dto, Iterable<TagBaseDto> tags) {
        getLogger().info("acceptTagsToRecord dto: {}", dto);
        Optional<Record> recordOptional = recordDao.fetchById(dto.getId());
        if ( ! recordOptional.isPresent()) {
            throw ErrorCase.open(getLogger(), "Don't accept a tags: {} to NewsEntryFullDto {}", tags, dto);
        }
        return () -> acceptTagsToRecord(dto.update(recordOptional.get()), tags);
    }

    private void acceptTagsToRecord(Record entry, Iterable<TagBaseDto> tags) {
        Set<String> setLabels = CollectionUtil.iterableToStream(tags)
                .map(TagBaseDto::getTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToRecord setLabels: {}", setLabels);
        Set<String> newTagLabels = new HashSet<>(tagDao.outerSection(setLabels));
        getLogger().info("acceptTagsToRecord newTagLabels: {}", newTagLabels);
        Set<Tag> newTags = newTagLabels.stream()
                .map(this::constructTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToRecord newTags: {}", newTags);
        if ( ! newTags.isEmpty()) {
            tagDao.saveAll(newTags);
            List<Tag> savedTags = tagDao.findAllByTagIn(setLabels);
            entry.setTags(new HashSet<>(savedTags));
            recordDao.save(entry);
            getLogger().info("acceptTagsToRecord entry: {}", entry);
            getLogger().info("acceptTagsToRecord entry.getTags(): {}", entry.getTags());
        }
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
