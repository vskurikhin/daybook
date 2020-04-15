/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordCrudUtxServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordCrudUtxService;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
public class RecordCrudUtxServiceImpl extends AbstractUserTransactionService implements RecordCrudUtxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordCrudUtxServiceImpl.class);

    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction userTransaction;

    @EJB(beanName = "RecordFullConverter")
    private RecordConverter recordFullConverter;

    @EJB(beanName = "RecordPartConverter")
    private RecordConverter recordPartConverter;

    @Override
    public void create(@Nonnull RecordJdo dto) {
        validateId(dto);
        utxConsumeByFunction(this::createFunction, dto);
    }

    @Override
    public RecordJdo readById(@Nonnull UUID id) {
        return utxFindById(emf.createEntityManager(), Record.class, id)
                .map(recordFullConverter::convert)
                .orElseThrow(ErrorCase::notFound);
    }

    @Override
    public List<RecordJdo> readRange(int start, int size) {
        return utxRange(emf.createEntityManager(), Record.class, Record.RANGE, start, size)
                .stream()
                .map(recordPartConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull RecordJdo dto) {
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

    private EntityManager createFunction(RecordJdo dto) {
        EntityManager entityManager = emf.createEntityManager();
        UserLogin userLogin = getUserLogin(entityManager, dto);
        validateUserLoginDto(userLogin, dto.getUserLogin());
        Record entity = recordFullConverter.convert(dto);
        entityManager.persist(entity);
        entityManager.flush();

        return entityManager;
    }

    private EntityManager updateFunction(RecordJdo dto) {
        EntityManager entityManager = emf.createEntityManager();
        UserLogin userLogin = getUserLogin(entityManager, dto);
        validateUserLoginDto(userLogin, dto.getUserLogin());
        Record entity = entityManager.find(Record.class, dto.getId());
        entity = recordFullConverter.convert(dto);
        entityManager.merge(entity);
        entityManager.flush();

        return entityManager;
    }

    private UserLogin getUserLogin(EntityManager em, RecordJdo dto) {
        return UserLoginEntityUtil.get(em, dto.getUserLogin());
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
    }

    private void validateRecordUserLogin(UserLoginDto dto) {
        if ( ! (dto instanceof UserOnlyLoginDto)) {
            throw ErrorCase.bad("user login DTO", String.valueOf(dto));
        }
    }
}
//EOF
