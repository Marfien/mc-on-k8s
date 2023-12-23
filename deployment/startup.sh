#!/usr/bin/env bash

kind create cluster --config kind.yaml

helm repo add agones-stable https://agones.dev/chats/stable
helm install agones --namespace agones-system --create-namespace agones-stable/agones

kubectl kustomize