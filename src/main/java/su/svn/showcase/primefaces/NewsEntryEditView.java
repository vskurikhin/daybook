/*
 * This file was last modified at 2020.04.07 23:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryEditView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagDto;
import su.svn.showcase.services.*;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static su.svn.shared.Constants.DEV_LOGIN;
import static su.svn.shared.Constants.RELEASE;

@Data
@ManagedBean
@ViewScoped
@EqualsAndHashCode(callSuper = false)
public class NewsEntryEditView extends AbstractView {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryEditView.class);

    private String id;
    private String title;
    private String tags;
    private String date;
    private String content;
    private String group = "Default";

    @EJB
    private NewsEntryCrudService newsEntryService;

    @EJB
    private RecordTagsStorageService recordTagsStorageService;

    private NewsEntryEditModel.Builder newsEntryModelBuilder;

    private HttpServletRequest request;

    @PostConstruct
    private void init() {
        LOGGER.info("init");
    }

    public void onload() {
        LOGGER.info("onload");
        try {
            request = getHttpServletRequest();
            assert request != null;
            newsEntryModelBuilder = NewsEntryEditModel.builder()
                    .newsEntryCrudService(newsEntryService)
                    .recordTagsStorageService(recordTagsStorageService)
                    .login(getCurrentUserName());
            UUID uuid = getIdParameter(request);
            NewsEntryFullDto newsEntry = newsEntryService.readById(uuid);
            newsEntryModelBuilder.uuid(getIdParameter(request))
                    .title(loadTitle(newsEntry))
                    .tags(loadTags(newsEntry))
                    .date(loadDate(newsEntry))
                    .content(loadContent(newsEntry));
        } catch (Exception e) {
            LOGGER.error("onload : ", e);
        }
    }

    private String loadTitle(NewsEntryFullDto newsEntry) {
        this.title = newsEntry.getTitle();
        return this.title;
    }

    @Nullable
    private String loadTags(NewsEntryFullDto newsEntry) {
        if (newsEntry.getRecord() instanceof RecordFullDto) {
            RecordFullDto recordFullDto = (RecordFullDto) newsEntry.getRecord();
            this.tags = recordFullDto.getTags().stream()
                    .map(TagDto::getTag)
                    .reduce("", (s1, s2) -> s1 + " | " + s2);
            return this.tags;
        }
        return null;
    }

    private String loadDate(NewsEntryFullDto newsEntry) {
        this.date = newsEntry.toDateDDMMYYYY();
        return this.date;
    }

    private String loadContent(NewsEntryFullDto newsEntry) {
        this.content = newsEntry.getContent();
        return this.content;
    }

    public void save() {
        LOGGER.info("save");
        try {
            System.out.println("newsEntryModelBuilder = " + newsEntryModelBuilder);
            NewsEntryEditModel model = newsEntryModelBuilder
                    .uuid(UUID.fromString(id)) // TODO fis bug NPE
                    .login(RELEASE ? getCurrentUserName() : DEV_LOGIN)
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
