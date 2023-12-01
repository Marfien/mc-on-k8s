#!/usr/bin/env bash

read -ra branches <<< "$BRANCHES"

for branch in ${branches[*]}; do
  echo "Updating version on [$branch] to $RELEASE_VERSION"
  git checkout -b "$branch" || true
  sed -i "/^version=.*/version=$RELEASE_VERSION/" gradle.properties
  git add gradle.properties
  git commit -m "Updated version to $RELEASE_VERSION"
  git push origin "$branch"
done