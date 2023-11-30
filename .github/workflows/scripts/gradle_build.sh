#!/usr/bin/env bash

set -e

GRADLE_COMMAND="clean"
if [[ "$COMMIT_MESSAGE" == *"--deploy"* ]]; then
  # set version for snapshot deployment
  GRADLE_COMMAND="-Pversion=$GRADLE_SNAPSHOT_VERSION $GRADLE_COMMAND"

  GRADLE_COMMAND="$GRADLE_COMMAND deploy"
else
  GRADLE_COMMAND="$GRADLE_COMMAND build"
fi

if [[ $COMMIT_MESSAGE == *"--skiptest"* ]]; then
  GRADLE_COMMAND="$GRADLE_COMMAND -x check"
fi

set -x

./gradlew -Porg.gradle.deamon=false -Dpublish.user="$GITHUB_ACTOR" -Dpublish.password="$GITUB_TOKEN" $GRADLE_COMMAND