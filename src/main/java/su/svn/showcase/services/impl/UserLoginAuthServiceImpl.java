/*
 * This file was last modified at 2020.03.14 13:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginAuthServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserLoginAuthDto;
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.dto.UserLoginFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.UserLoginAuthService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class UserLoginAuthServiceImpl extends AbstractCrudService implements UserLoginAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginAuthServiceImpl.class);

    @EJB(beanName = "UserLoginDaoEjb")
    private UserLoginDao userLoginDao;

    @Override
    public void create(@Nonnull UserLoginAuthDto dto) {
        validateUserLoginId(dto);
        saveUpdatedEntity(createUserLogin(dto), dto);
    }

    @Override
    public UserLoginAuthDto readById(@Nonnull UUID id) {
        return new UserLoginFullDto(userLoginDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    public UserLoginAuthDto readByLogin(String login) {
        return new UserLoginFullDto(userLoginDao.findWhereLogin(login)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<UserLoginAuthDto> readRange(int start, int size) {
        return userLoginDao.range(start, size).stream()
                .map(UserLoginFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull UserLoginAuthDto dto) {
        validateUserLoginId(dto);
        saveUpdatedEntity(getUserLogin(dto.getLogin()), dto);
    }

    @Override
    public void delete(@Nonnull UUID id) {
        userLoginDao.delete(id);
    }

    @Override
    public int count() {
        return (int) userLoginDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void saveUpdatedEntity(UserLogin entity, UserLoginAuthDto dto) {
        entity = dto.update(entity);
        userLoginDao.save(entity);
    }

    private UserLogin createUserLogin(UserLoginAuthDto dto) {
        userLoginDao.findWhereLogin(dto.getLogin()).ifPresent(userLogin -> {
            throw ErrorCase.bad("login already exists", dto.getLogin());
        });
        return new UserLogin(getOrGenerateUuidKey(dto));
    }

    private UserLogin getUserLogin(String login) {
        return userLoginDao.findWhereLogin(login).orElseThrow(ErrorCase::notFound);
    }

    private void validateUserLoginId(UserLoginDto dto) {
        Objects.requireNonNull(dto.getLogin());
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
        }
    }
}
//EOF
