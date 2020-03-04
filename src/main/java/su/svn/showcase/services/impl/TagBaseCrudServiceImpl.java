/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.TagBaseCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TagBaseCrudServiceImpl extends AbstractCrudService implements TagBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagBaseCrudServiceImpl.class);

    @EJB(beanName = "TagDaoEjb")
    private TagDao tagDao;

    @Override
    public void create(@Nonnull TagBaseDto dto) {
        saveUpdatedEntity(new Tag(getOrGenerateStringKey(dto)), dto);
    }

    @Override
    @Transactional
    public TagBaseDto readById(@Nonnull String id) {
        return new TagBaseDto(tagDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<TagBaseDto> readRange(int start, int size) {
        return tagDao.rangeOrderByTagAsc(start, size).stream()
                .map(TagBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull TagBaseDto dto) {
        validateId(dto);
        saveUpdatedEntity(getTag(dto.getId()), dto);
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

    private void saveUpdatedEntity(Tag entity, TagBaseDto dto) {
        entity = dto.update(entity);
        tagDao.save(entity);
    }

    private Tag getTag(String id) {
        return tagDao.findById(id).orElseThrow(ErrorCase::notFound);
    }
}
//EOF
