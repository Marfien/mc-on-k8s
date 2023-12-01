#!/usr/bin/env bash

sed -i "s/^version=.*/version=$GITHUB_REF_NAME/" gradle.properties

git add gradle.properties

git push origin/main