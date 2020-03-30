/*
 * This file was last modified at 2020.03.30 11:01 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ConverterRegistryServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.ArticleDao;
import su.svn.showcase.services.ConverterRegistryService;
import su.svn.showcase.services.impl.support.JtaEnvironment;

import static org.junit.jupiter.api.Assertions.*;

@AddPackages(value = {ArticleDao.class, ConverterRegistryService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class ConverterRegistryServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}