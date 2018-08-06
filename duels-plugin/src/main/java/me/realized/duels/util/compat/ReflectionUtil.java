package me.realized.duels.util.compat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.realized.duels.util.Log;
import org.bukkit.Bukkit;

public final class ReflectionUtil {

    private final static String VERSION;

    static {
        VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
    }

    private ReflectionUtil() {}

    public static Class<?> getClassUnsafe(final String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public static Class<?> getNMSClass(final String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static Class<?> getCBClass(final String path) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + path);
        } catch (ClassNotFoundException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... parameters) {
        try {
            return clazz.getMethod(name, parameters);
        } catch (NoSuchMethodException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static Field getField(final Class<?> clazz, final String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }
}
