#!/usr/bin/env bash

GRADLE_COMMAND="clean"
if [[ "$COMMIT_MESSAGE" == *"--deploy"* ]]; then
  # set version for snapshot deployment
  CURRENT_VERSION=$(grep "^version=" gradle.properties)
  GRADLE_COMMAND="-Pversion=${CURRENT_VERSION#*=}-$GITHUB_REF_NAME $GRADLE_COMMAND"

  GRADLE_COMMAND="$GRADLE_COMMAND deploy"
else
  GRADLE_COMMAND="$GRADLE_COMMAND build"
fi

if [[ $COMMIT_MESSAGE == *"--skip-test"* ]]; then
  GRADLE_COMMAND="$GRADLE_COMMAND -x check"
fi