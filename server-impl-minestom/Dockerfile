FROM eclipse-temurin:17-jre-alpine

WORKDIR /home/server/

RUN wget -O server.jar https://github.com/KlainStom/microstom/releases/download/v7.0.0/Microstom-7.0.0.jar

RUN cat <<EOF > micro-settings.json
{
  "SERVER_IP": "0.0.0.0",
  "SERVER_PORT": 25565,
  "MODE": "OFFLINE",
  "VELOCITY_SECRET": "",
  "TPS": null,
  "CHUNK_VIEW_DISTANCE": null,
  "ENTITY_VIEW_DISTANCE": null,
  "TERMINAL_DISABLED": false
}
EOF

RUN echo "java -jar server.jar" >> start.sh
RUN chmod +rx ./start.sh

ADD build/libs/server-impl-minestom-1.0.0-all.jar extensions/bridge.jar

ENTRYPOINT ["java", "-jar", "server.jar"]