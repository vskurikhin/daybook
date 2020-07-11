/*
 * This file was last modified at 2020.07.11 21:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * InfinispanConfiguration.java
 * $Id$
 */

package su.svn.thorntail.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@ApplicationScoped
public class InfinispanConfiguration {

    @Produces
    @ApplicationScoped
    public EmbeddedCacheManager cacheManager() {
        GlobalConfigurationBuilder cacheManagerConfiguration = new GlobalConfigurationBuilder();
        cacheManagerConfiguration.globalJmxStatistics().allowDuplicateDomains(true).enable();
        cacheManagerConfiguration.transport().defaultTransport();

        ConfigurationBuilder cacheConfiguration = new ConfigurationBuilder();
        cacheConfiguration.clustering().cacheMode(CacheMode.DIST_SYNC);

        return new DefaultCacheManager(cacheManagerConfiguration.build(), cacheConfiguration.build());
    }
}
