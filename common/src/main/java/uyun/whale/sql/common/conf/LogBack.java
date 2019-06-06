package uyun.whale.sql.common.conf;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

/**
 * 读入新的logBack的conf
 *
 * @author chensw
 * @since at 2016-4-14下午3:04:46
 */
public class LogBack {

    /**
     * 配置logback配置文件路径
     *
     * @param logbackPath
     */
    public static void configureLogbackPath(String logbackPath) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        // Call context.reset() to clear any previous configuration, e.g. default
        // configuration. For multi-step configuration, omit calling
        // context.reset().
        context.reset();

        try {
            configurator.doConfigure(logbackPath);
        } catch (JoranException e) {
            e.printStackTrace();
        }

        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    public static void setDefaultLevel(Level level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        for (Logger logger : context.getLoggerList()) {
            logger.setLevel(level);
        }
    }

    public static void setLevel(String name, Level level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(name).setLevel(level);
    }
}
