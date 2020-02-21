/*
 * This file was last modified at 2020.02.21 15:32 by Victor N. Skurikhin.
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
public class RoleBaseCrudServiceImpl extends AbstractUserTransactionService implements RoleBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleBaseCrudServiceImpl.class);

    @EJB(beanName = "RoleDaoJpa")
    RoleDao roleDao;

    @Inject
    UserTransaction userTransaction;

    private Consumer<Role> tagSavingConsumer(RoleBaseDto tdo) {
        return entity -> {
            tdo.update(entity);
            roleDao.save(entity);
        };
    }

    @Override
    public void create(RoleBaseDto dto) {
        Objects.requireNonNull(dto);
        consume(tagSavingConsumer(dto), new Role(getOrGenerateUuidKey(dto)));
    }

    @Override
    public RoleBaseDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new RoleBaseDto(roleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<RoleBaseDto> readRange(int start, int size) {
        return roleDao.rangeOrderByRoleAsc(start, size).stream()
                .map(RoleBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(RoleBaseDto dto) {
        validateId(dto);
        consume(tagSavingConsumer(dto), new Role(dto.getId()));
    }

    @Override
    public void delete(UUID id) {
        Objects.requireNonNull(id);
        roleDao.delete(id);
    }

    @Override
    public int count() {
        return (int) roleDao.count();
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
