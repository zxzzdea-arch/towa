# Get the jar without installing anything

1. Create an empty repository on GitHub and upload everything in this folder (including .github).
2. Check aaaVersion in gradle.properties (already filled in if it was detected).
3. Open the Actions tab, run 'Build mod', then download the artifact: it contains the finished jar with all commands.

Locally: install JDK 17 and Gradle 8.8, then run 'gradle build'. The jar appears in build/libs.
