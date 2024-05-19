#!/usr/bin/env bash

GIT_BRANCH="$(git rev-parse --abbrev-ref HEAD)"
CLUSTER_NAME="mc-on-k8s-$GIT_BRANCH"

if false && [ -z "$GITHUB_TOKEN" ]; then
  echo "Please provide a personal access token for github in GITHUB_TOKEN"
  exit 1;
fi

echo "Creating cluster '$CLUSTER_NAME'..."
if minikube >> /dev/null; then
  minikube delete -p "$CLUSTER_NAME"
  minikube start -p "$CLUSTER_NAME"
elif kind >> /dev/null; then
  kind delete cluster --name "$CLUSTER_NAME"
  kind create cluster --name "$CLUSTER_NAME"
else
  echo "No supported kubernetes environment found!"
  exit 1
fi

echo "Bootstraping flux on branch '$GIT_BRANCH'..."
flux bootstrap github \
  --owner='marfien' \
  --repository='mc-on-k8s' \
  --branch="$GIT_BRANCH" \
  --path='.k8s/flux/base'
