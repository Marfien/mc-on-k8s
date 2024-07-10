## Configuration

The following values are configurable:
- `rbac`:
  - `enabled` - whether to enable RBAC. Default is `true`.
- `minecraftservers` - configuration related to minecraft servers:
  - `allocation`:
    - `defaultStrategy` - default strategy for allocating servers. One of `players`, `manual`, `always`.
- `proxyfleets` - configuration related to proxy fleets:
  - `allocation`:
    - `defaultStrategy` - default strategy for allocating servers. One of `players`, `manual`, `always`.
- `operator` - configuration related to the operator deployment
  - `image`:
    - `repository` - operator image repository
    - `tag` - operator image tag
  - `resources`
    - `requests` - operator deployment resource requests
      - `cpu` - operator deployment CPU request
      - `memory` - operator deployment memory request
    - `limits` - operator deployment resource limits
      - `cpu` - operator deployment CPU limit
      - `memory` - operator deployment memory limit
  - `nodeSelector` - operator pod node selector
  - `imagePullSecrets` - operator image pull secrets
  - `imagePullPolicy` - operator image pull policy
  - 
