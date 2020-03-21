/*
 * This file was last modified at 2020.03.21 21:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleCreateView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.services.ArticleFullCrudService;
import su.svn.showcase.services.LinkBaseCrudService;
import su.svn.showcase.services.RecordTagsStorageService;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import static su.svn.shared.Constants.DEV_LOGIN;
import static su.svn.shared.Constants.RELEASE;

@Data
@ManagedBean
@RequestScoped
@EqualsAndHashCode(callSuper = false)
public class ArticleCreateView extends AbstractView {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleCreateView.class);

    private String title;
    private String include;
    private String date;
    private String summary;
    private String link = "Default";

    @EJB
    private ArticleFullCrudService articleService;

    @EJB
    private LinkBaseCrudService linkBaseCrudService;

    @EJB
    private RecordTagsStorageService recordTagsStorageService;

    private ArticleCreateModel.Builder articleModelBuilder;

    private HttpServletRequest request;

    @PostConstruct
    private void init() {
        LOGGER.trace("init");
        articleModelBuilder = ArticleCreateModel.builder()
                .articleCrudService(articleService)
                .linkBaseCrudService(linkBaseCrudService)
                .recordTagsStorageService(recordTagsStorageService);
    }

    public void onload() {
        LOGGER.trace("onload");
        try {
            articleModelBuilder.login(getCurrentUserName());
            request = getHttpServletRequest();
        } catch (Exception e) {
            LOGGER.error("onload : ", e);
        }
    }

    public void save() {
        LOGGER.trace("save");
        try {
            ArticleCreateModel model = articleModelBuilder
                    .login(RELEASE ? getCurrentUserName() : DEV_LOGIN)
                    .title(this.title)
                    .include(this.include)
                    .date(this.date)
                    .summary(this.summary)
                    .link(this.link)
                    .build();
            model.save();
            showSaveInfo();
        } catch (Exception e) {
            LOGGER.error("save : ", e);
            showSaveError(e);
        }
    }

    private void showSaveInfo() {
        LOGGER.trace("showSaveInfo");
        facesContextMessage("Ok saved", this.title);
    }

    private void showSaveError(Exception e) {
        LOGGER.trace("showSaveError");
        facesContextErrorMessage("Error on save", "save", e);
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
