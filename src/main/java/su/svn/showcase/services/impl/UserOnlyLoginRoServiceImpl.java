/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserOnlyLoginRoServiceImpl extends AbstractUserTransactionService implements UserOnlyLoginRoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOnlyLoginRoServiceImpl.class);

    @EJB(beanName = "UserLoginDaoJpa")
    UserLoginDao userLoginDao;

    @Inject
    UserTransaction userTransaction;

    @Override
    public void create(UserOnlyLoginBaseDto dto) {
        throw ErrorCase.unsupportedOperation(dto.getClass());
    }

    @Override
    public UserOnlyLoginBaseDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new UserOnlyLoginBaseDto(userLoginDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<UserOnlyLoginBaseDto> readRange(int start, int size) {
        return userLoginDao.range(start, size).stream()
                .map(UserOnlyLoginBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserOnlyLoginBaseDto dto) {
        throw ErrorCase.unsupportedOperation(dto.getClass());
    }

    @Override
    public void delete(UUID id) {
        throw ErrorCase.unsupportedOperation(UserOnlyLoginBaseDto.class);
    }

    @Override
    public int count() {
        return (int) userLoginDao.count();
    }

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }
}
