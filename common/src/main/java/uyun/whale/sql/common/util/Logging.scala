package uyun.whale.sql.common.util

import org.slf4j.{Logger, LoggerFactory}

/**
  * This is a copy of what Spark Previously held in org.apache.spark.Logging.
  * That class is now private so we will expose similar functionality here.
  *
  * @author chensw
  * @since 2016-12-16 下午4:25:45
  */
trait Logging {
    @transient private var _log: Logger = _

    protected def logName: String = {
        this.getClass.getName.stripSuffix("$")
    }

    protected def log: Logger = {
        if (_log == null) {
            _log = LoggerFactory.getLogger(logName)
        }
        _log
    }

    protected def logInfo(msg: => String) {
        if (log.isInfoEnabled) log.info(msg)
    }

    protected def logDebug(msg: => String) {
        if (log.isDebugEnabled) log.debug(msg)
    }

    protected def logTrace(msg: => String) {
        if (log.isTraceEnabled) log.trace(msg)
    }

    protected def logWarning(msg: => String) {
        if (log.isWarnEnabled) log.warn(msg)
    }

    protected def logError(msg: => String) {
        if (log.isErrorEnabled) log.error(msg)
    }

    protected def logInfo(msg: => String, throwable: Throwable) {
        if (log.isInfoEnabled) log.info(msg, throwable)
    }

    protected def logDebug(msg: => String, throwable: Throwable) {
        if (log.isDebugEnabled) log.debug(msg, throwable)
    }

    protected def logTrace(msg: => String, throwable: Throwable) {
        if (log.isTraceEnabled) log.trace(msg, throwable)
    }

    protected def logWarning(msg: => String, throwable: Throwable) {
        if (log.isWarnEnabled) log.warn(msg, throwable)
    }

    protected def logError(msg: => String, throwable: Throwable) {
        if (log.isErrorEnabled) log.error(msg, throwable)
    }

    protected def isTraceEnabled: Boolean = {
        log.isTraceEnabled
    }

    protected def isDebugEnabled: Boolean = {
        log.isDebugEnabled
    }

}

