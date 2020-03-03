/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginRoServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dto.UserOnlyLoginBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.UserOnlyLoginRoService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class UserOnlyLoginRoServiceImpl extends AbstractCrudService implements UserOnlyLoginRoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOnlyLoginRoServiceImpl.class);

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Override
    @Transactional
    public void create(@Nonnull UserOnlyLoginBaseDto dto) {
        throw ErrorCase.unsupportedOperation(dto.getClass());
    }

    @Override
    @Transactional
    public UserOnlyLoginBaseDto readById(@Nonnull UUID id) {
        return new UserOnlyLoginBaseDto(userLoginDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public UserOnlyLoginBaseDto readByLogin(@Nonnull String login) {
        return new UserOnlyLoginBaseDto(userLoginDao.findWhereLogin(login)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<UserOnlyLoginBaseDto> readRange(int start, int size) {
        return userLoginDao.range(start, size).stream()
                .map(UserOnlyLoginBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull UserOnlyLoginBaseDto dto) {
        throw ErrorCase.unsupportedOperation(dto.getClass());
    }

    @Override
    public void delete(@Nonnull UUID id) {
        throw ErrorCase.unsupportedOperation(UserOnlyLoginBaseDto.class);
    }

    @Override
    @Transactional
    public int count() {
        return (int) userLoginDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }
}
//EOF
