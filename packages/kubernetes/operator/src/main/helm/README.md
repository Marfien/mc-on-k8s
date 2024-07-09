## Configuration

The following values are configurable:

- `minecraftservers` - configuration related to minecraft servers:
  - `allocation`:
    - `defaultStrategy` - default strategy for allocating servers. One of `players`, `manual`, `always`.
- `proxyfleets` - configuration related to proxy fleets:
  - `allocation`:
    - `defaultStrategy` - default strategy for allocating servers. One of `players`, `manual`, `always`.
  - `drainage`:
    - `delay` - the delay after which a proxy starts draining players (see [Duration format](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-))
    - `duration` - the duration for which a proxy waits for players to leave before shutting down (see [Duration format](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-))
  - `cache`:
    - `rebuildInterval` - the interval at which the cache is rebuilt (see [Duration format](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-))

