#!/usr/bin/env bash

increment_version() {
  local IFS='.'
  read -ra array <<< "$1"

  array[$2]=$((array[$2] + 1))
  for ((i=$2+1; i<${#array[@]}; i++)); do
    array[$i]=0;
  done

  echo "${array[*]}"
}

if [[ "$INPUT_CUSTOM_VERSION" ]]; then
  echo "RELEASE_VERSION=$INPUT_CUSTOM_VERSION" >> .env;
else
  raw_version="$(grep "^version=" gradle.properties)"
  version=$(increment_version "${raw_version#*=}" 1)
  echo "RELEASE_VERSION=$version" >> .env
fi