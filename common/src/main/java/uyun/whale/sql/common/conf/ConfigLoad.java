package uyun.whale.sql.common.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 读取配置文件
 *
 * @author chensw
 * @since at 2016-3-23 下午3:04:46
 */
public class ConfigLoad {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoad.class);
    private Properties properties;

    /**
     * 构造函数II
     *
     * @param pathName
     */
    public ConfigLoad(String pathName) {
        setProperties(pathName);
    }

    /**
     * 构造函数III
     *
     * @param pathNameList
     */
    public ConfigLoad(List<String> pathNameList) {
        setProperties(pathNameList);
    }

    private void setProperties(String pathName) {
        properties = new Properties();
        try (FileInputStream in = new FileInputStream(pathName)) {
            properties.load(in);
        } catch (FileNotFoundException ex) {
            LOGGER.error("Config file is not exist!", ex);
        } catch (IOException e) {
            LOGGER.error("Loading config error!", e);
        }
    }

    private void setProperties(List<String> pathNameList) {
        properties = new Properties();
        for (String pathName : pathNameList) {
            try (FileInputStream in = new FileInputStream(pathName)) {
                properties.load(in);
            } catch (FileNotFoundException ex) {
                LOGGER.error("Config file is not exist!", ex);
            } catch (IOException e) {
                LOGGER.error("Loading config error!", e);
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }

}