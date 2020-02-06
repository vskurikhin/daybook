/*
 * This file was last modified at 2020.02.06 22:26 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SerializeUtil.java
 * $Id$
 */

package su.svn.utils;

import java.io.*;

@SuppressWarnings("unchecked")
public class SerializeUtil {
    public static <T extends Serializable> T clone(T o) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // сохраняем состояние в поток и закрываем поток
            try (ObjectOutputStream ous = new ObjectOutputStream(baos)) {
                ous.writeObject(o);
            } catch (IOException e) {
                throw e;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            // создаём и инициализируем состояние
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//EOF
