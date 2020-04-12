/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.dao.RoleDao;
import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.RoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RoleCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class RoleCrudServiceImpl extends AbstractCrudService implements RoleCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleCrudServiceImpl.class);

    @EJB(beanName = "RoleDaoEjb")
    private RoleDao roleDao;

    @EJB(beanName = "RoleBaseConverter")
    private RoleConverter roleConverter;

    @Override
    @Transactional
    public void create(@Nonnull RoleFullDto dto) {
        createAndSave(dto);
    }

    @Override
    @Transactional
    public RoleFullDto readById(@Nonnull UUID id) {
        return roleConverter.convert(roleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<RoleFullDto> readRange(int start, int size) {
        return roleDao.rangeOrderByRoleAsc(start, size).stream()
                .map(roleConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull RoleFullDto dto) {
        validateId(dto);
        createAndSave(dto); // TODO update
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        roleDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) roleDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void createAndSave(RoleFullDto dto) {
        Role entity = roleConverter.convert(dto);
        roleDao.save(entity);
    }
}
//EOF
