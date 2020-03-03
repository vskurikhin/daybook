/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleBaseCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RoleDao;
import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.RoleBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RoleBaseCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class RoleBaseCrudServiceImpl extends AbstractCrudService implements RoleBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleBaseCrudServiceImpl.class);

    @EJB(beanName = "RoleDaoJpa")
    private RoleDao roleDao;

    @Override
    @Transactional
    public void create(@Nonnull RoleBaseDto dto) {
        saveUpdatedEntity(new Role(getOrGenerateUuidKey(dto)), dto);
    }

    @Override
    @Transactional
    public RoleBaseDto readById(@Nonnull UUID id) {
        return new RoleBaseDto(roleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<RoleBaseDto> readRange(int start, int size) {
        return roleDao.rangeOrderByRoleAsc(start, size).stream()
                .map(RoleBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull RoleBaseDto dto) {
        validateId(dto);
        saveUpdatedEntity(getNewsGroup(dto.getId()), dto);
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

    private void saveUpdatedEntity(Role entity, RoleBaseDto dto) {
        entity = dto.update(entity);
        roleDao.save(entity);
    }

    private Role getNewsGroup(UUID id) {
        return roleDao.findById(id).orElseThrow(ErrorCase::notFound);
    }
}
//EOF
