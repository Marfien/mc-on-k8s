#!/usr/bin/env bash

set -e
set -x

read -ra branches <<< "$BRANCHES"

for branch in ${branches[*]}; do
  echo "Updating version on [$branch] to $RELEASE_VERSION"
  git checkout -b "$branch" || true
  sed -i "s/^version=.*/version=${RELEASE_VERSION}/" gradle.properties || exit 1
  git add gradle.properties
  git commit -m "Updated version to $RELEASE_VERSION"
  git push origin "$branch"
done