/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.dao.UserRoleDao;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.UserRoleCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class UserRoleCrudServiceImpl extends AbstractCrudService implements UserRoleCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleCrudServiceImpl.class);

    @EJB(beanName = "UserRoleDaoEjb")
    private UserRoleDao userRoleDao;

    @EJB(beanName = "UserRolePartConverter")
    private UserRoleConverter userRoleConverter;

    @Override
    @Transactional
    public void create(@Nonnull UserRoleFullDto dto) {
        validateUserRoleId(dto);
        createAndSave(dto);
    }

    @Override
    @Transactional
    public UserRoleFullDto readById(@Nonnull UUID id) {
        return userRoleConverter.convert(userRoleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<UserRoleFullDto> readRange(int start, int size) {
        return userRoleDao.range(start, size).stream()
                .map(userRoleConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull UserRoleFullDto dto) {
        validateId(dto);
        validateUserRoleId(dto);
        createAndSave(dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        userRoleDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) userRoleDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void validateUserRoleId(UserRoleFullDto dto) {
        Objects.requireNonNull(dto.getRole());
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
            dto.getRole().setId(id);
        }
        if ( ! dto.getId().equals(dto.getRole().getId())) {
            throw ErrorCase.doesntEquals("Ids of UserRole and Role DTO", dto.getId(), dto.getRole().getId());
        }
    }

    private void createAndSave(UserRoleFullDto dto) {
        UserRole entity = userRoleConverter.convert(dto);
        userRoleDao.save(entity);
    }
}
//EOF
