/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudUtxServiceImpl.java
 * $Id$
 */

package su.svn.showcase.dao.utx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordFullCrudService;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecordFullCrudUtxServiceImpl extends AbstractUserTransactionService implements RecordFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordFullCrudUtxServiceImpl.class);

    @EJB(beanName = "RecordDaoJpa")
    private RecordDao recordDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

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
        recordDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) recordDao.count();
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
        UserLogin userLogin = getUserLogin(dto.getUserLogin());
        validateUserLoginDto(userLogin, dto.getUserLogin());
        Record entity = new Record(UUID.randomUUID(), userLogin);
        entity = dto.update(entity);
        entityManager.merge(entity);
        entityManager.flush();

        return entityManager;
    }

    private EntityManager updateFunction(RecordFullDto dto) {
        EntityManager entityManager = emf.createEntityManager();
        UserLogin userLogin = getUserLogin(dto.getUserLogin());
        validateUserLoginDto(userLogin, dto.getUserLogin());
        Record entity = entityManager.find(Record.class, dto.getId());
        entity = dto.update(entity);
        entityManager.merge(entity);
        entityManager.flush();

        return entityManager;
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateRecordUserLogin(UserLoginDto dto) {
        if ( ! (dto instanceof UserOnlyLoginBaseDto)) {
            throw ErrorCase.bad("user login DTO", String.valueOf(dto));
        }
    }

    private void validateRecordId(RecordFullDto dto) {
        Objects.requireNonNull(dto.getUserLogin());
        RecordTypesEnum type = RecordTypesEnum.valueOf(dto.getType());
        switch (type) {
            case NewsEntryBaseDto:
            case NewsEntryFullDto:
                validateRecordNewsEntry(dto);
                break;
            default:
                throw ErrorCase.unknownType(type.toString());
        }
    }

    private void validateRecordNewsEntry(RecordFullDto dto) {
        Objects.requireNonNull(dto.getNewsEntry());
        if ( ! dto.getId().equals(dto.getNewsEntry().getId())) {
            throw ErrorCase.doesntEquals("Ids of Record and NewsEntry DTO", dto.getId(), dto.getNewsEntry().getId());
        }
        if ( ! NewsEntryDtoEnum.containsValue(dto.getType())) {
            throw ErrorCase.unknownType(dto.getType());
        }
    }




    private Consumer<Record> storageConsumer(RecordFullDto dto) {
        return entity -> {
            if (entity == null) {
                entity = recordDao.findById(dto.getId())
                        .orElseThrow(ErrorCase::notFound);
            }
            UserLogin userLogin = getUserLogin(dto.getUserLogin());
            validateUserLoginDto(userLogin, dto.getUserLogin());
            entity.setUserLogin(userLogin);
            entity = dto.update(entity);
            recordDao.save(entity);
        };
    }
}
//EOF
