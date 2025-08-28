pm2 delete users-service || true
export $(grep -v '^#' .env | xargs) && pm2 start "java -jar ./presentations/build/libs/presentations-0.0.1-SNAPSHOT.jar" --name users-service
