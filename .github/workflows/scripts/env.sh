echo "COMMIT_MESSAGE=$COMMIT_MESSAGE" >> .env

GRADLE_VERSION=$(grep "^version=" gradle.properties)
echo "GRADLE_VERSION=${GRADLE_VERSION#*=}" >> .env
echo "GRADLE_SNAPSHOT_VERSION=${GRADLE_VERSION#*=}-$GITHUB_REF_NAME" >> .env