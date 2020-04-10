/*
 * This file was last modified at 2020.04.10 21:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginRoServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.UserOnlyLoginRoService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless(name = "UserOnlyLoginRoService")
public class UserOnlyLoginRoServiceImpl extends AbstractCrudService implements UserOnlyLoginRoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOnlyLoginRoServiceImpl.class);

    @EJB(beanName = "UserLoginDaoEjb")
    private UserLoginDao userLoginDao;

    @EJB(beanName = "UserOnlyLoginConverter")
    UserLoginConverter userLoginConverter;

    @Override
    @Transactional
    public void create(@Nonnull UserOnlyLoginDto dto) {
        throw ErrorCase.unsupportedOperation(dto.getClass());
    }

    @Override
    @Transactional
    public UserOnlyLoginDto readById(@Nonnull UUID id) {
        return userLoginConverter.convert(userLoginDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public UserOnlyLoginDto readByLogin(@Nonnull String login) {
        return userLoginConverter.convert(userLoginDao.findWhereLogin(login).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<UserOnlyLoginDto> readRange(int start, int size) {
        return userLoginDao.range(start, size).stream()
                .map(userLoginConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull UserOnlyLoginDto dto) {
        throw ErrorCase.unsupportedOperation(dto.getClass());
    }

    @Override
    public void delete(@Nonnull UUID id) {
        throw ErrorCase.unsupportedOperation(UserOnlyLoginDto.class);
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
