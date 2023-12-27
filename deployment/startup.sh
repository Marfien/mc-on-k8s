#!/usr/bin/env bash

if ! [[ -f kind.yaml && -f kustomization.yaml ]]; then
  echo "Cannot find king.yaml and kustomization.yaml"
  exit 1
fi

if [[ $(kind get clusters) == *"minecraft-on-k8s"* ]]; then
  kind delete cluster --name 'minecraft-on-k8s'
fi

kind create cluster --config kind.yaml

# Load all docker images of
kind load docker-image $(docker images 'ghcr.io/marfien/mc-on-k8s/*' --format '{{.Repository}}:{{.Tag}}') --name minecraft-on-k8s

if [[ $(helm repo list) != *"agones-stable"* ]]; then
  echo "Agones repo not found. Adding it"
  helm repo add agones-stable https://agones.dev/chart/stable
fi

helm repo update
helm install agones --namespace agones-system --create-namespace agones-stable/agones

kubectl apply --kustomize .
