package com.epam.training;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    public static Logger log = LogManager.getRootLogger();
    public static Logger traceLogger = LogManager.getLogger("traceLogger");
}
