#!/bin/sh
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
MAX_FD="maximum"
warn () { echo "$*"; }
die () { echo; echo "$*"; echo; exit 1; }
if [ "$APP_HOME" = "" ] ; then
  APP_HOME="`pwd -P`"
fi
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec java -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"