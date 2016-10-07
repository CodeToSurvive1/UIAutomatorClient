/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.uiautomator.client.XmlUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

    public static Class getClass(final String name) throws Exception {
        try {
            return Class.forName(name);
        } catch (final ClassNotFoundException e) {
            final String msg = String.format("unable to find class %s", name);
            throw new Exception(msg, e);
        }
    }

    public static Object getField(final Class clazz, final String fieldName, final Object object) throws Exception {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(object);
        } catch (final Exception e) {
            final String msg = String.format("error while getting field %s from object %s", fieldName, object);
            throw new Exception(msg, e);
        }
    }

    public static Object getField(final String field, final Object object) throws Exception {
        return getField(object.getClass(), field, object);
    }

    public static Object getField(final String className, final String field, final Object object) throws Exception {
        return getField(getClass(className), field, object);
    }

    public static Object invoke(final Method method, final Object object, final Object... parameters) throws Exception {
        try {
            return method.invoke(object, parameters);
        } catch (final Exception e) {
            final String msg = String.format("error while invoking method %s on object %s with parameters %s", method, object, Arrays.toString(parameters));
            throw new Exception(msg, e);
        }
    }

    public static Method method(final Class clazz, final String methodName, final Class... parameterTypes) throws Exception {
        try {
            final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);

            return method;
        } catch (final Exception e) {
            final String msg = String.format("error while getting method %s from class %s with parameter types %s", methodName, clazz, Arrays.toString(parameterTypes));
            throw new Exception(msg, e);
        }
    }

    public static Method method(final String className, final String method, final Class... parameterTypes) throws Exception {
        return method(getClass(className), method, parameterTypes);
    }
}
