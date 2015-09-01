package com.jd.panda.code.tools;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * @author hanjuntao
 * @datetime 2015-08-20 14:53
 */
public class FactoryUtils {

    protected static Log logger = LogFactory.getLog(FactoryUtils.class);

    public static final String CONFIG_FILE = "config.xml";


    public static XMLConfiguration xmlConfiguration;

    static {
        try {
            xmlConfiguration = new XMLConfiguration(CONFIG_FILE);
        } catch (ConfigurationException e) {
            logger.error("加载XML配置文件出现异常", e);
        }
    }


    public static VelocityEngine createVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        velocityEngine.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        //resource.loader = file
        //file.resource.loader.class = org.apache.velocity.runtime.resource.loader.FileResourceLoader
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
        return velocityEngine;
    }

}
