# AGENTS

## Lessons learnt
- The build requires the Android SDK and may download dependencies from remote hosts. In restricted environments without the SDK or network access, `gradlew assembleDebug` fails or hangs.
- Do not attempt to commit large binary artifacts like APKs when compilation fails. Instead, note the failure and provide instructions to install the SDK if needed.
