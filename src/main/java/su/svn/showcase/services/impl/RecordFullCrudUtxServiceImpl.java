/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudUtxServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordFullCrudUtxService;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecordFullCrudUtxServiceImpl extends AbstractUserTransactionService implements RecordFullCrudUtxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordFullCrudUtxServiceImpl.class);

    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction userTransaction;

    @Override
    public void create(@Nonnull RecordFullDto dto) {
        validateId(dto);
        utxConsumeByFunction(this::createFunction, dto);
    }

    @Override
    public RecordFullDto readById(@Nonnull UUID id) {
        return utxFindById(emf.createEntityManager(), Record.class, id)
                .map(RecordFullDto::new)
                .orElseThrow(ErrorCase::notFound);
    }

    @Override
    public List<RecordFullDto> readRange(int start, int size) {
        return utxRange(emf.createEntityManager(), Record.class, Record.RANGE, start, size)
                .stream()
                .map(RecordFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull RecordFullDto dto) {
        validateId(dto);
        validateRecordUserLogin(dto.getUserLogin());
        utxConsumeByFunction(this::updateFunction, dto);
    }

    @Override
    public void delete(@Nonnull UUID id) {
        // TODO recordDao.delete(id);
    }

    @Override
    public int count() {
        return (int) 0; // TODO recordDao.count();
    }

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private EntityManager createFunction(RecordFullDto dto) {
        EntityManager entityManager = emf.createEntityManager();
        UserLogin userLogin = getUserLogin(entityManager, dto);
        validateUserLoginDto(userLogin, dto.getUserLogin());
        Record entity = new Record(UUID.randomUUID(), userLogin);
        entity = dto.update(entity);
        entityManager.merge(entity);
        entityManager.flush();

        return entityManager;
    }

    private EntityManager updateFunction(RecordFullDto dto) {
        EntityManager entityManager = emf.createEntityManager();
        UserLogin userLogin = getUserLogin(entityManager, dto);
        validateUserLoginDto(userLogin, dto.getUserLogin());
        Record entity = entityManager.find(Record.class, dto.getId());
        entity = dto.update(entity);
        entityManager.merge(entity);
        entityManager.flush();

        return entityManager;
    }

    private UserLogin getUserLogin(EntityManager em, RecordFullDto dto) {
        return UserLoginEntityUtil.get(em, dto.getUserLogin());
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
    }

    private void validateRecordUserLogin(UserLoginDto dto) {
        if ( ! (dto instanceof UserOnlyLoginBaseDto)) {
            throw ErrorCase.bad("user login DTO", String.valueOf(dto));
        }
    }
}
//EOF
