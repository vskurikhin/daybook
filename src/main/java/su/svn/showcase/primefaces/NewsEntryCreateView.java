/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCreateView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.services.*;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Data
@ManagedBean
@RequestScoped
@EqualsAndHashCode(callSuper = false)
public class NewsEntryCreateView extends AbstractView {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryCreateView.class);

    private String title;
    private String tags;
    private String date;
    private String content;
    private String group = "Default";

    @EJB
    private NewsEntryFullCrudService newsEntryService;

    @EJB
    private NewsGroupBaseCrudService newsGroupService;

    @EJB
    private RecordTagsStorageService recordTagsStorageService;

    private NewsEntryCreateModel.Builder newsEntryModelBuilder;

    private HttpServletRequest request;

    @PostConstruct
    private void init() {
        LOGGER.trace("init");
        newsEntryModelBuilder = NewsEntryCreateModel.builder()
                .newsEntryCrudService(newsEntryService)
                .newsGroupCrudService(newsGroupService)
                .recordTagsStorageService(recordTagsStorageService);
    }

    public void onload() {
        LOGGER.trace("onload");
        try {
            newsEntryModelBuilder.login(getCurrentUserName());
            request = getHttpServletRequest();
        } catch (Exception e) {
            LOGGER.error("onload : ", e);
        }
    }

    public void save() {
        LOGGER.trace("save");
        try {
            NewsEntryCreateModel model = newsEntryModelBuilder
                    .login(getCurrentUserName())
                    .title(this.title)
                    .tags(this.tags)
                    .date(this.date)
                    .content(this.content)
                    .group(this.group)
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
