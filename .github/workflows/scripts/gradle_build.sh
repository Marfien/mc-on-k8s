#!/usr/bin/env bash

set -e

GRADLE_COMMAND="clean"
if [[ "$COMMIT_MESSAGE" == *"--deploy"* ]]; then
  # set version for snapshot deployment
  GRADLE_COMMAND="-Pversion=$GRADLE_SNAPSHOT_VERSION -Dpublish.user=$GITHUB_ACTOR -Dpublish.password=$GITUB_TOKEN $GRADLE_COMMAND publish"
elif [[ "$RELEASE_BUILD" == "true" ]]; then
  # set version for releases
  GRADLE_COMMAND="-Pversion=$RELEASE_VERSION -Dpublish.user=$GITHUB_ACTOR -Dpublish.password=$GITUB_TOKEN $GRADLE_COMMAND build publish"
else
  GRADLE_COMMAND="$GRADLE_COMMAND build"
fi

if [[ $COMMIT_MESSAGE == *"--skiptest"* ]]; then
  GRADLE_COMMAND="$GRADLE_COMMAND -x check"
fi

set -x

./gradlew $GRADLE_COMMAND --no-daemon