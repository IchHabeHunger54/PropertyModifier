package ihh.propertymodifier;

import org.apache.logging.log4j.LogManager;

public final class Logger {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    public static void debug(Object s) {
        if (Config.LOG_SUCCESSFUL.get()) LOGGER.debug(s);
    }

    public static void info(Object s) {
        if (Config.LOG_SUCCESSFUL.get()) LOGGER.info(s);
    }

    public static void warn(Object s) {
        if (Config.LOG_ERRORS.get()) LOGGER.warn(s);
    }

    public static void error(Object s) {
        if (Config.LOG_ERRORS.get()) LOGGER.error(s);
    }
}
