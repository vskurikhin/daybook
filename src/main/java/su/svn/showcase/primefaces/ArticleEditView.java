/*
 * This file was last modified at 2020.03.22 17:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleEditView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagDto;
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

import java.util.UUID;

import static su.svn.shared.Constants.DEV_LOGIN;
import static su.svn.shared.Constants.RELEASE;

@Data
@ManagedBean
@RequestScoped
@EqualsAndHashCode(callSuper = false)
public class ArticleEditView extends AbstractView {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleEditView.class);

    private String id;
    private String title;
    private String include;
    private String date;
    private String anchor;
    private String summary;
    private String link = "Default";
    private String tags;

    @EJB
    private ArticleFullCrudService articleService;

    @EJB
    private LinkBaseCrudService linkBaseCrudService;

    @EJB
    private RecordTagsStorageService recordTagsStorageService;

    private ArticleEditModel.Builder articleModelBuilder;

    private HttpServletRequest request;

    @PostConstruct
    private void init() {
        LOGGER.trace("init");
        articleModelBuilder = ArticleEditModel.builder()
                .articleCrudService(articleService)
                .linkBaseCrudService(linkBaseCrudService)
                .recordTagsStorageService(recordTagsStorageService);
    }

    public void onload() {
        LOGGER.trace("onload");
        try {
            request = getHttpServletRequest();
            assert request != null;
            articleModelBuilder = ArticleEditModel.builder()
                    .articleCrudService(articleService)
                    .recordTagsStorageService(recordTagsStorageService)
                    .login(getCurrentUserName());
            UUID uuid = getIdParameter(request);
            ArticleFullDto article = articleService.readById(uuid);
            articleModelBuilder.uuid(getIdParameter(request))
                    .title(loadTitle(article))
                    .tags(loadTags(article))
                    .include(loadInclude(article))
                    .link(loadLink(article))
                    .date(loadDate(article))
                    .anchor(loadAnchor(article))
                    .summary(loadSummary(article));
            articleModelBuilder.login(getCurrentUserName());
            request = getHttpServletRequest();
        } catch (Exception e) {
            LOGGER.error("onload : ", e);
        }
    }

    private String loadAnchor(ArticleFullDto article) {
        this.anchor = article.getAnchor();
        return article.getAnchor();
    }

    private String loadTitle(ArticleFullDto article) {
        this.title = article.getTitle();
        return this.title;
    }

    @Nullable
    private String loadTags(ArticleFullDto article) {
        if (article.getRecord() instanceof RecordFullDto) {
            RecordFullDto recordFullDto = (RecordFullDto) article.getRecord();
            this.tags = recordFullDto.getTags().stream()
                .map(TagDto::getTag)
                .reduce("", (s1, s2) -> s1 + " | " + s2);
            return this.tags;
        }
        return null;
    }

    private String loadInclude(ArticleFullDto article) {
        this.include = article.getInclude();
        return this.include;
    }

    private String loadLink(ArticleFullDto article) {
        this.link = article.getLink() != null ? article.getLink().getLink() : null;
        return this.link;
    }

    private String loadDate(ArticleFullDto article) {
        this.date = article.toDateDDMMYYYY();
        return this.date;
    }

    private String loadSummary(ArticleFullDto article) {
        this.summary = article.getSummary();
        return this.summary;
    }

    public void save() {
        LOGGER.trace("save");
        try {
            ArticleEditModel model = articleModelBuilder
                    .uuid(UUID.fromString(id)) // TODO fis bug NPE
                    .login(RELEASE ? getCurrentUserName() : DEV_LOGIN)
                    .title(this.title)
                    .include(this.include)
                    .date(this.date)
                    .anchor(this.anchor)
                    .summary(this.summary)
                    .link(this.link)
                    .tags(this.tags)
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
