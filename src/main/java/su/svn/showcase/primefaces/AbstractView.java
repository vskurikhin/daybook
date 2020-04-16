/*
 * This file was last modified at 2020.03.22 17:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

abstract class AbstractView {

    abstract Logger getLogger();

    abstract FacesContext getFacesContext();

    @Nullable
    HttpServletRequest getHttpServletRequest() {
        FacesContext fcontext = getFacesContext();
        if (fcontext != null) {
            Object o = fcontext.getExternalContext().getRequest();
            if (o instanceof HttpServletRequest) {
                return (HttpServletRequest) o;
            }
        }
        return null;
    }

    @Nullable
    String getCurrentUserName() {
        FacesContext fContext = getFacesContext();
        if (fContext != null) {
            ExternalContext externalContext = fContext.getExternalContext();
            Principal principal = externalContext.getUserPrincipal();
            if (principal != null) {
                return principal.getName();
            }
        }
        return null;
    }

    @Nullable
    UUID getIdParameter(@Nonnull HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            return UUID.fromString(id);
        } catch (NullPointerException ignore) {
            return null;
        }
    }

    FacesContext facesContextMessage(String summary, String detail) {
        FacesContext facesContext = getFacesContext();
        if (facesContext != null) {
            facesContext.addMessage(null, createFacesMessage(summary, detail));
        }
        return facesContext;
    }

    private FacesMessage createFacesMessage(String summary, String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    FacesContext facesContextErrorMessage(String summary, String method, Exception e) {
        FacesContext facesContext = getFacesContext();
        if (facesContext != null) {
            StringBuilder detail = constructDetail(method, e);
            facesContext.addMessage(null, createFacesMessageSeverityError(summary, detail));
        }
        return facesContext;
    }

    private StringBuilder constructDetail(String method, Exception e) {
        StringBuilder detail = new StringBuilder();
        if (e.getMessage() != null)
            detail.append(' ').append(e.getMessage());
        if (e.getCause() != null && e.getCause().getMessage() != null)
            detail.append(' ').append(e.getCause().getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            if (isRightStackTraceElement(ste, method)) {
                detail.append(' ').append(ste.getFileName())
                      .append(' ').append(ste.getLineNumber());
                return detail;
            }
        }
        return detail;
    }

    private FacesMessage createFacesMessageSeverityError(String summary, StringBuilder detail) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail.toString());
    }

    private boolean isRightStackTraceElement(StackTraceElement ste, String method) {
        String packageName = this.getClass().getPackage().getName();
        String className = ste.getClassName();

        return method.equals(ste.getMethodName()) && className.contains(packageName);
    }
}
//EOF
