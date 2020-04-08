/*
 * This file was last modified at 2020.04.08 20:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.TagCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TagCrudServiceImpl extends AbstractCrudService implements TagCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagCrudServiceImpl.class);

    @EJB(beanName = "TagDaoEjb")
    private TagDao tagDao;

    @EJB(beanName = "TagBaseConverter")
    private TagConverter tagConverter;

    @Override
    public void create(@Nonnull TagFullDto dto) {
        save(dto);
    }

    @Override
    @Transactional
    public TagFullDto readById(@Nonnull String id) {
        return tagConverter.convert(tagDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<TagFullDto> readRange(int start, int size) {
        return tagDao.rangeOrderByTagAsc(start, size).stream()
                .map(tagConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull TagFullDto dto) {
        validateId(dto);
        save(dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull String id) {
        tagDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) tagDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void save(TagFullDto dto) {
        Tag entity = tagConverter.convert(dto);
        tagDao.save(entity);
    }

    private Tag getTag(String id) {
        return tagDao.findById(id).orElseThrow(ErrorCase::notFound);
    }
}
//EOF
