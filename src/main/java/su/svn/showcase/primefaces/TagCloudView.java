/*
 * This file was last modified at 2020.02.26 17:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagCloudView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class TagCloudView {

    private TagCloudModel model;

    @PostConstruct
    public void init() {
        model = new DefaultTagCloudModel();
        model.addTag(new DefaultTagCloudItem("Java", 9));
        model.addTag(new DefaultTagCloudItem("RIA", "#", 3));
        model.addTag(new DefaultTagCloudItem("AJAX", 2));
        model.addTag(new DefaultTagCloudItem("jQuery", "#", 5));
        model.addTag(new DefaultTagCloudItem("Spring", 8));
        model.addTag(new DefaultTagCloudItem("JSF 2.0", "#", 2));
        model.addTag(new DefaultTagCloudItem("FCB", 5));
        model.addTag(new DefaultTagCloudItem("JavaScript", 3));
        model.addTag(new DefaultTagCloudItem("Themes", "#", 4));
        model.addTag(new DefaultTagCloudItem("Rocks", "#", 1));
    }

    public TagCloudModel getModel() {
        return model;
    }

    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
