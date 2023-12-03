#!/usr/bin/env bash

increment_version() {
  local IFS='.'
  read -ra array <<< "$1"

  array[$2]=$((array[$2] + 1))
  arr_length=${#array[@]}
  for ((i=$2+1; i<$((arr_length < 3 ? 3 : arr_length)); i++)); do
    array[$i]=0;
  done

  echo "${array[*]}"
}

get_increment_index() {
  if [[ "$INPUT_VERSION_UPGRADE" == "major" ]]; then echo "0";
  elif [[ "$INPUT_VERSION_UPGRADE" == "minor" ]]; then echo "1";
  elif [[ "$INPUT_VERSION_UPGRADE" == "patch" ]]; then echo "2";
  else
    echo "Unknown increment: $INPUT_VERSION_UPGRADE" >&2;
    exit 1;
  fi
}

if [[ "$INPUT_CUSTOM_VERSION" ]]; then
  echo "RELEASE_VERSION=$INPUT_CUSTOM_VERSION" > .env;
else
  raw_version="$(grep "^version=" gradle.properties)"
  version=$(increment_version "${raw_version#*=}" "$(get_increment_index)")
  echo "RELEASE_VERSION=$version" > .env
fi