/*
 * This file was last modified at 2020.04.01 14:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseConverterImplTest.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.RecordFullDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.cloneRecordFullDto0;

@DisplayName("A RecordBaseConverterImplTest unit test cases")
@AddPackages(value = {RecordConverter.class, RecordBaseConverterImpl.class})
@ExtendWith({WeldJunit5Extension.class})
class RecordBaseConverterImplTest {

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordBaseConverterImpl.class)
            .activate(RequestScoped.class)
            .inject(this)
            .build();

    @Inject
    @Named("recordBase")
    RecordConverter converter;

    private Record entity;
    private RecordFullDto dto;
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        entity = cloneRecord0();
        dto = cloneRecordFullDto0();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convert() {
        RecordFullDto test = converter.convert(entity);
    }

    @Test
    void testConvert() {
    }
}