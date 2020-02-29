/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
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
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RoleBaseCrudServiceImpl extends AbstractUserTransactionService implements RoleBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleBaseCrudServiceImpl.class);

    @EJB(beanName = "RoleDaoJpa")
    private RoleDao roleDao;

    @Inject
    private UserTransaction userTransaction;

    @Override
    public void create(@Nonnull RoleBaseDto dto) {
        consume(storageConsumer(dto), new Role(getOrGenerateUuidKey(dto)));
    }

    @Override
    public RoleBaseDto readById(@Nonnull UUID id) {
        return new RoleBaseDto(roleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<RoleBaseDto> readRange(int start, int size) {
        return roleDao.rangeOrderByRoleAsc(start, size).stream()
                .map(RoleBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull RoleBaseDto dto) {
        validateId(dto);
        consume(storageConsumer(dto), new Role(dto.getId()));
    }

    @Override
    public void delete(@Nonnull UUID id) {
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

    private Consumer<Role> storageConsumer(RoleBaseDto dto) {
        return entity -> {
            if (entity == null) {
                entity = roleDao.findById(dto.getId())
                        .orElseThrow(ErrorCase::notFound);
            }
            dto.update(entity);
            roleDao.save(entity);
        };
    }
}
//EOF
