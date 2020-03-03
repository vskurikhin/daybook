/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.UserRoleDao;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.UserRoleFullCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class UserRoleFullCrudServiceImpl extends AbstractCrudService implements UserRoleFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleFullCrudServiceImpl.class);

    @EJB(beanName = "UserRoleDaoJpa")
    private UserRoleDao userRoleDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Override
    @Transactional
    public void create(@Nonnull UserRoleFullDto dto) {
        validateUserRoleId(dto);
        saveUpdatedEntity(new UserRole(getOrGenerateUuidKey(dto)), dto);
    }

    @Override
    @Transactional
    public UserRoleFullDto readById(@Nonnull UUID id) {
        return new UserRoleFullDto(userRoleDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<UserRoleFullDto> readRange(int start, int size) {
        return userRoleDao.range(start, size).stream()
                .map(UserRoleFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull UserRoleFullDto dto) {
        validateId(dto);
        validateUserRoleId(dto);
        saveUpdatedEntity(getUserRole(dto.getId()), dto);
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

    private void saveUpdatedEntity(UserRole entity, UserRoleFullDto dto) {
        UserLogin userLogin = getUserLogin(dto.getUserLogin().getId());
        validateUserLoginDto(userLogin, dto.getUserLogin());
        entity.setUserLogin(userLogin);
        entity = dto.update(entity);
        userRoleDao.save(entity);
    }

    private UserRole getUserRole(UUID id) {
        return userRoleDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private UserLogin getUserLogin(UUID id) {
        return userLoginDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
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
}
//EOF
