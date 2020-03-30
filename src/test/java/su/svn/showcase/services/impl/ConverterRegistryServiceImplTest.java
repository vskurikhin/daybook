/*
 * This file was last modified at 2020.03.30 11:01 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ConverterRegistryServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.services.ConverterRegistryService;

import javax.enterprise.context.RequestScoped;

import static org.junit.jupiter.api.Assertions.*;

@AddPackages(value = {EntityConverter.class, ConverterRegistryService.class})
@ExtendWith({WeldJunit5Extension.class})
class ConverterRegistryServiceImplTest {


    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            ConverterRegistryServiceImpl.class)
            .activate(RequestScoped.class)
            .inject(this)
            .build();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test1(ConverterRegistryService service) {
        Assertions.assertNotNull(service);
    }
}