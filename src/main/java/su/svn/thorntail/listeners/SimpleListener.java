/*
 * This file was last modified at 2020.07.11 21:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SimpleListener.java
 * $Id$
 */

package su.svn.thorntail.listeners;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStopped;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStartedEvent;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStoppedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listener(clustered = true)
public class SimpleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    @CacheEntryCreated
    public void addKey(CacheEntryCreatedEvent event) {
        LOGGER.info("New entry {} created in the cache with value {}", event.getKey(), event.getValue());
    }

    @CacheEntryRemoved
    public void removeKey(CacheEntryRemovedEvent event) {
        LOGGER.info("Entry {} removed from the cache", event.getKey());
    }

    @CacheStarted
    public void cacheStarted(CacheStartedEvent event) {
        LOGGER.info("Cache Started");
    }

    @CacheStopped
    public void cacheStopped(CacheStoppedEvent event) {
        LOGGER.info("Cache Stopped");
    }
}
