/*
 * This file was last modified at 2020.07.11 21:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * InfinispanEndpoint.java
 * $Id$
 */

package su.svn.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.infinispan.Cache;

@Path("/infinispan")
@ApplicationScoped
public class InfinispanEndpoint {
    @Inject
    Cache<String, String> cache;

    @GET
    @Path("cache")
    public Response printContent() {
        return Response.ok(cache.entrySet().toString()).build();
    }

    @POST
    @Path("cache")
    public Response addSomethingToTheCache(@FormParam("key") String key, @FormParam("value") String value) {
        cache.put(key, value);
        return Response.created(null).build();
    }
}
