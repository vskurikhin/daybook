/*
 * This file was last modified at 2020.03.21 21:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkBaseCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.LinkDao;
import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.LinkBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.exceptions.NotFound;
import su.svn.showcase.services.LinkBaseCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class LinkBaseCrudServiceImpl extends AbstractCrudService implements LinkBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkBaseCrudServiceImpl.class);

    @EJB(beanName = "LinkDaoEjb")
    private LinkDao linkDao;

    @Override
    @Transactional
    public void create(@Nonnull LinkBaseDto dto) {
        checkAndSetId(dto);
        saveUpdatedEntity(new Link(dto.getId()), dto);
    }

    @Override
    @Transactional
    public LinkBaseDto readById(@Nonnull UUID id) {
        return new LinkBaseDto(linkDao.findById(id)
                .orElseThrow(NotFound::is));
    }

    @Override
    @Transactional
    public List<LinkBaseDto> readRange(int start, int size) {
        return linkDao.range(start, size).stream()
                .map(LinkBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull LinkBaseDto dto) {
        validateId(dto);
        saveUpdatedEntity(getLink(dto.getId()), dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        Objects.requireNonNull(id);
        linkDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) linkDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void saveUpdatedEntity(Link entity, LinkBaseDto dto) {
        entity = dto.update(entity);
        linkDao.save(entity);
    }

    private Link getLink(UUID id) {
        return linkDao.findById(id).orElseThrow(ErrorCase::notFound);
    }
}
//EOF
