FROM eclipse-temurin:17-jre-alpine AS pre

WORKDIR /home/server/

RUN wget -O server.jar https://repo.spongepowered.org/repository/maven-releases/org/spongepowered/spongevanilla/1.16.5-8.2.0/spongevanilla-1.16.5-8.2.0-universal.jar
RUN java -jar server.jar

FROM eclipse-temurin:17-jre-alpine

WORKDIR /home/server/

RUN echo "online-mode=false" >> server.properties
RUN echo "eula=true" >> eula.txt
RUN echo "java -jar server.jar" >> start.sh

COPY --from=pre server.jar .
COPY --from=pre libraries/ libraries/

ADD build/libs/server-impl-sponge-1.0.0-all.jar mods/plugins/bridge.jar

ENTRYPOINT ["java", "-jar", "server.jar"]