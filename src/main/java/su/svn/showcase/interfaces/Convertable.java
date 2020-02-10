package su.svn.showcase.interfaces;

import su.svn.showcase.dto.Dto;

public interface Convertable<T extends Dto> {
    T convert();
}
