/*
 * This file was last modified at 2020.02.15 14:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseCrudServiceImpl.java$
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.dto.TagDto;
import su.svn.showcase.services.TagBaseCrudService;
import su.svn.showcase.utils.StringUtil;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TagBaseCrudServiceImpl extends AbstractUserTransactionService implements TagBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagBaseCrudServiceImpl.class);

    @EJB(beanName = "TagDaoJpa")
    TagDao tagDao;

    @Inject
    UserTransaction userTransaction;

    private Consumer<Tag> tagSavingConsumer(TagDto tdo) {
        return entity -> {
            tdo.update(entity);
            tagDao.save(entity);
        };
    }

    @Override
    public void create(TagDto dto) {
        Objects.requireNonNull(dto);
        if (dto.getId() == null) {
            dto.setId(StringUtil.generateTagId(dto.getTag()));
        }
        consume(tagSavingConsumer(dto), new Tag());
    }

    @Override
    public TagBaseDto readById(String id) {
        Objects.requireNonNull(id);
        return new TagBaseDto(tagDao.findById(id).orElse(null));
    }

    @Override
    public List<TagBaseDto> readRange(int start, int size) {
        return tagDao.rangeOrderByTagAsc(start, size).stream()
                .map(TagBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(TagDto dto) {
        validateId(dto);
        consume(tagSavingConsumer(dto), new Tag(dto.getId()));
    }

    private void validateId(TagDto dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.getId());
    }

    @Override
    public void delete(String id) {
        Objects.requireNonNull(id);
        tagDao.delete(id);
    }

    @Override
    public int count() {
        return (int) tagDao.count();
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
