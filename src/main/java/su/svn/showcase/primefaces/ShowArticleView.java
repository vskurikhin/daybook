/*
 * This file was last modified at 2020.04.07 23:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ShowArticleView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.services.ArticleCrudService;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Data
@ManagedBean
@RequestScoped
@EqualsAndHashCode(callSuper = false)
public class ShowArticleView extends AbstractView {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowArticleView.class);

    private String id;
    private String include;
    private String link;

    @EJB
    private ArticleCrudService articleService;

    private HttpServletRequest request;

    @PostConstruct
    private void init() {
        LOGGER.trace("init");
    }

    public void onload() {
        LOGGER.trace("onload");
        try {
            request = getHttpServletRequest();
            UUID uuid = getIdParameter(request);
            this.id = uuid.toString();
            ArticleFullDto dto = articleService.readById(uuid);
            include = dto.getInclude();
            link = dto.getLink() != null ? dto.getLink().getLink() : null;
        } catch (Exception e) {
            LOGGER.error("onload : ", e);
        }
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Nullable
    @Override
    FacesContext getFacesContext() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if ( ! fcontext.isPostback()) {
            return fcontext;
        }
        return fcontext;
    }
}
//EOF
