/*
 * This file was last modified at 2020.02.17 22:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserRoleDao;
import su.svn.showcase.dao.jpa.UserRoleDaoJpa;
import su.svn.showcase.domain.Role;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RoleBaseCrudService;
import su.svn.showcase.services.UserRoleFullCrudService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserRoleFullCrudServiceImpl extends AbstractUserTransactionService implements UserRoleFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleFullCrudServiceImpl.class);

    @EJB(beanName = "UserRoleDaoJpa")
    UserRoleDao userRoleDao;

    @Inject
    UserTransaction userTransaction;

    private Consumer<UserRole> tagSavingConsumer(UserRoleFullDto tdo) {
        return entity -> {
            tdo.update(entity);
            userRoleDao.save(entity);
        };
    }

    @Override
    public void create(UserRoleFullDto dto) {
        Objects.requireNonNull(dto);
        consume(tagSavingConsumer(dto), new UserRole(UUID.randomUUID()));
    }

    @Override
    public UserRoleFullDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new UserRoleFullDto(userRoleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<UserRoleFullDto> readRange(int start, int size) {
        return userRoleDao.findAll().stream().map(UserRoleFullDto::new).collect(Collectors.toList());
        /* return userRoleDao.range(start, size).stream()
                .map(UserRoleFullDto::new)
                .collect(Collectors.toList()); */
    }

    @Override
    public void update(UserRoleFullDto dto) {
        validateId(dto);
        consume(tagSavingConsumer(dto), new UserRole(dto.getId()));
    }

    @Override
    public void delete(UUID id) {
        Objects.requireNonNull(id);
        userRoleDao.delete(id);
    }

    @Override
    public int count() {
        return (int) userRoleDao.count();
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
