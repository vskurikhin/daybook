/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
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
import su.svn.showcase.dto.UserOnlyLoginBaseDto;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
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
    private UserRoleDao userRoleDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Inject
    private UserTransaction userTransaction;

    private Consumer<UserRole> tagSavingConsumer(UserRoleFullDto dto) {
        if (dto.getUserLogin() instanceof UserOnlyLoginBaseDto) {
            return entity -> {
                UserLogin userLogin = getUserLogin(dto);
                validateUserLoginDto(userLogin, dto.getUserLogin());
                entity.setUserLogin(userLogin);
                dto.update(entity);
                userRoleDao.save(entity);
            };
        }
        throw ErrorCase.unsupportedOperation(dto.getUserLogin().getClass());
    }

    @Override
    public void create(UserRoleFullDto dto) {
        validateUserRoleId(dto);
        consume(tagSavingConsumer(dto), new UserRole(getOrGenerateUuidKey(dto)));
    }

    @Override
    public UserRoleFullDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new UserRoleFullDto(userRoleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<UserRoleFullDto> readRange(int start, int size) {
        return userRoleDao.range(start, size).stream()
                .map(UserRoleFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserRoleFullDto dto) {
        validateId(dto);
        validateUserRoleId(dto);
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

    private UserLogin getUserLogin(UserRoleFullDto dto) {
        return userLoginDao.findById(dto.getUserLogin().getId()).orElseThrow();
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
    }

    private void validateUserRoleId(UserRoleFullDto dto) {
        Objects.requireNonNull(dto);
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

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }
}
