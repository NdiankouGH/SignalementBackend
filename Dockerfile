FROM openjdk:17-jdk-slim
LABEL authors="Ndiankou Ndoye"
LABEL version="1.0"
LABEL description="Système de signalement d'épaves - API Spring Boot"


# Créer un utilisateur non-root pour la sécurité
#RUN addgroup --system spring && addgroup  --system --group spring

# Créer le répertoire de travail
WORKDIR /app

# Variables d'environnement pour l'optimisation JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m -Djava.security.egd=file:/dev/./urandom"

VOLUME /tmp
# Copier le JAR
ARG Jar_File=target/signalement-0.0.1-SNAPSHOT.jar
COPY ${Jar_File} app.jar

# Changer le propriétaire du fichier
#RUN chown spring:spring app.jar

# Changer vers l'utilisateur non-root
#USER spring

# Port exposé
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Point d'entrée avec optimisations JVM
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
