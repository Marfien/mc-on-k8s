#!/usr/bin/env bash

GRADLE_COMMAND="clean"
if [[ "$COMMIT_MESSAGE" == *"--deploy"* ]]; then
  # set version for snapshot deployment
  GRADLE_COMMAND="-Pversion=$GRADLE_SNAPSHOT_VERSION $GRADLE_COMMAND"

  GRADLE_COMMAND="$GRADLE_COMMAND deploy"
else
  GRADLE_COMMAND="$GRADLE_COMMAND build"
fi

if [[ $COMMIT_MESSAGE == *"--skip-test"* ]]; then
  GRADLE_COMMAND="$GRADLE_COMMAND -x check"
fi

./gradlew $GRADLE_COMMAND