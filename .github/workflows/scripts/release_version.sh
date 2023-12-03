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

get_increment_index() {
  if [[ "$INPUT_VERSION_UPGRADE" == "major" ]]; then return "0";
  elif [[ "$INPUT_VERSION_UPGRADE" == "minor" ]]; then return "1";
  elif [[ "$INPUT_VERSION_UPGRADE" == "patch" ]]; then return "2";
  else
    echo "Unknown increment: $INPUT_VERSION_UPGRADE";
    exit 1;
  fi
}

if [[ "$INPUT_CUSTOM_VERSION" ]]; then
  echo "RELEASE_VERSION=$INPUT_CUSTOM_VERSION" >> .env;
else
  raw_version="$(grep "^version=" gradle.properties)"
  version=$(increment_version "${raw_version#*=}" "$(get_increment_index)")
  echo "RELEASE_VERSION=$version" >> .env
fi