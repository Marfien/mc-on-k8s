FROM eclipse-temurin:17-jre-alpine AS build

RUN wget https://api.papermc.io/v2/projects/paper/versions/1.20.2/builds/318/downloads/paper-1.20.2-318.jar -O paper.jar

RUN java -jar paper.jar

FROM eclipse-temurin:17-jre-alpine

WORKDIR /home/app/

RUN echo "eula=true" >> eula.txt

COPY --from=build paper.jar .
COPY --from=build cache/ ./cache/
COPY --from=build libraries/ ./libraries/
COPY --from=build versions/ ./versions/

ADD build/libs/server-impl-bukkit-1.0.0-all.jar plugins/bridge.jar

EXPOSE 25565

ENTRYPOINT ["java", "-jar", "paper.jar"]