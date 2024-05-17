#!/usr/bin/env sh

GIT_BRANCH="$(git ref-parse --abbrev-ref HEAD)"
CLUSTER_NAME="mc-on-k8s-$GIT_BRANCH"

if [ -z "$GITHUB_TOKEN" ]; then
  echo "Please provide a personal access token for github in GITHUB_TOKEN"
  exit 1;
fi

echo "Creating cluster '$CLUSTER_NAME'..."
if where minikube; then
  minikube start -p "$CLUSTER_NAME"
elif where kind; then
  kind create cluster --name "$CLUSTER_NAME"
else
  echo "No supported kubernetes environment found!"
  exit 1
fi

echo "Bootstraping flux on branch '$GIT_GIT_BRANCH'..."
flux bootstrap github \
  --token-auth \
  --owner='marfien' \
  --repository='mc-on-k8s' \
  --branch="$GIT_BRANCH" \
  --path='.k8s/flux'
