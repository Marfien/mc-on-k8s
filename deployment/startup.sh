#!/usr/bin/env bash

kind create cluster --config kind.yaml

# Load all images relating to this project into the kind cluster
kind load docker-image $(docker images ghcr.io/marfien/mc-on-k8s/* | grep --color=NEVER -oE '^[^(REPOSIOTRY)][^ ]+')

helm repo add agones-stable https://agones.dev/chats/stable
helm install agones --namespace agones-system --create-namespace agones-stable/agones

# Apply test deployments
kubectl kustomize