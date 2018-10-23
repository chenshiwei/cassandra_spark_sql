#!/bin/bash
source /etc/profile
# Java home directory
JAVA_DIR=

MAIN_CLASS=uyun.whale.sql.core.entry.SQLRunnerEntry

CURRENT_DIR=$(cd "$(dirname "$0")"; pwd)
cd $CURRENT_DIR

# Installation directory
APP_HOME=$(dirname $CURRENT_DIR)

#CLASSPATH=$APP_HOME/classes
for i in "$APP_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

OPTS="-ms256m -mx1024m -Xmn256m -Djava.awt.headless=true -Duser.timezone=GMT+08"

if [ "$JAVA_OPTS" != "" ]; then
   OPTS="$JAVA_OPTS -Duser.timezone=GMT+08 -Dapp.name=$APP_NAME -Duser.timezone=GMT+08"
fi


if [ "$JAVA_HOME" != "" ]; then
   JAVA_DIR="$JAVA_HOME"
else
   echo "error: JAVA_HOME is not set"
   exit 1
fi

$JAVA_DIR/bin/java $OPTS -classpath $CLASSPATH $MAIN_CLASS $APP_HOME $*