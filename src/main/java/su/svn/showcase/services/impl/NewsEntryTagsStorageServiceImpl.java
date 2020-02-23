/*
 * This file was last modified at 2020.02.22 11:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagNewsEntryStorageServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.NewsEntryTagsStorageService;
import su.svn.showcase.utils.CollectionUtil;
import su.svn.showcase.utils.StringUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NewsEntryTagsStorageServiceImpl extends AbstractUserTransactionService
       implements NewsEntryTagsStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryTagsStorageServiceImpl.class);

    @EJB(beanName = "TagDaoJpa")
    private TagDao tagDao;

    @EJB(beanName = "NewsEntryDaoJpa")
    private NewsEntryDao newsEntryDao;

    @Inject
    private UserTransaction userTransaction;

    @Override
    public void addTagsToNews(NewsEntryFullDto dto, Iterable<TagBaseDto> tags) {
        execute(acceptTagsToNewsEntry(dto, tags));
    }

    private Runnable acceptTagsToNewsEntry(NewsEntryFullDto dto, Iterable<TagBaseDto> tags) {
        getLogger().info("acceptTagsToNewsEntry dto: {}", dto);
        Optional<NewsEntry> newsEntryOptional = newsEntryDao.findById(dto.getId());
        if ( ! newsEntryOptional.isPresent()) {
            throw ErrorCase.open(getLogger(), "Don't accept a tags: {} to NewsEntryFullDto {}", tags, dto);
        }
        return () -> acceptTagsToNewsEntry(dto.update(newsEntryOptional.get()), tags);
    }

    private void acceptTagsToNewsEntry(NewsEntry entry, Iterable<TagBaseDto> tags) {
        Set<String> setLabels = CollectionUtil.iterableToStream(tags)
                .map(TagBaseDto::getTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToNewsEntry setLabels: {}", setLabels);
        Set<String> newTagLabels = new HashSet<>(tagDao.outerSection(setLabels));
        getLogger().info("acceptTagsToNewsEntry newTagLabels: {}", newTagLabels);
        Set<Tag> newTags = newTagLabels.stream()
                .map(this::constructTag)
                .collect(Collectors.toSet());
        getLogger().info("acceptTagsToNewsEntry newTags: {}", newTags);
        if ( ! newTags.isEmpty()) {
            tagDao.saveAll(newTags);
            List<Tag> savedTags = tagDao.findAllByTagIn(setLabels);
            entry.getRecord().setTags(new HashSet<>(savedTags));
            newsEntryDao.save(entry);
            getLogger().info("acceptTagsToNewsEntry entry: {}", entry);
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

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }
}
