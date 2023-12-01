#!/usr/bin/env bash

set -e
set -x

git checkout -b develop || true
sed -i "s/^version=.*/version=${RELEASE_VERSION}/" gradle.properties
git add gradle.properties
git commit -m "Updated version to $RELEASE_VERSION"
git push origin develop