/*
 * This file was last modified at 2020.07.11 21:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Producer.java
 * $Id$
 */

package su.svn.thorntail.producers;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.infinispan.manager.EmbeddedCacheManager;
import su.svn.thorntail.listeners.SimpleListener;

@ApplicationScoped
public class Producer {

    @Resource(lookup = "java:jboss/infinispan/container/web")
    private EmbeddedCacheManager container;

    private org.infinispan.Cache<String, String> cache;

    @Inject
    SimpleListener listener;

    @PostConstruct
    public void initCache() {

        this.cache = container.getCache( "default");

        System.out.println("Got cache " + cache.getName());
        cache.addListener(listener);

    }

    @Produces
    public org.infinispan.Cache<String, String> getCache() {
        return cache;
    }

    public void setCache(org.infinispan.Cache<String, String> cache) {
        this.cache = cache;
    }
}
