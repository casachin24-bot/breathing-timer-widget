#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

appname=`basename "$0"`
apppath=`dirname "$0"`
appjar="$apppath"/gradle/wrapper/gradle-wrapper.jar
appconf="$apppath"/gradle/wrapper/gradle-wrapper.properties

if test ! -r "$appconf" ; then
    echo "Error: $appconf not found"
    exit 1
fi

. "$appconf"

dist="$apppath"/gradle/wrapper
jar="$dist"/gradle-wrapper.jar
properties="$dist"/gradle-wrapper.properties

if [ -z "$JAVA_HOME" ] ; then
    JAVA_CMD="java"
else
    JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" -classpath "$jar" org.gradle.wrapper.GradleWrapperMain "$@"
