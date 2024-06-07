# LivePerson Compose Sample (Beta)

This project is created to demonstrate using SDK provided ConversationFragment within Compose environment.
It has an example of [composable function](/common-ui/src/main/java/com/liveperson/compose/common_ui/views/LPConversationScreen.kt#L11) that could be used to represent a conversation screen
in you compose app. 

This is a multi module application written using Clean Architecture approach. Almost each module 
contains its data, domain and presentation layers. 

Presentation layers is written following MVVM+ pattern. Flow, Coroutines and Compose states are used 
to hold and represent a state of the screen.

# Dependencies and versions

This project uses [version catalog](https://developer.android.com/build/migrate-to-catalogs) to 
organize dependencies and their version. To build this sample app such dependencies were used:

- Kotlin version `1.9.23`
- Kotlin compiler extensions: `kotlinCompilerExtensionVersion = "1.5.11"`
- Compose bom: `androidx.compose:compose-bom:2024.04.00`
- AndroidX Fragment-ktx: `androidx.fragment:fragment-compose:1.8.0-alpha01`
- AndroidX lifecycle-ktx: `androidx.lifecycle:lifecycle-runtime-ktx:2.7.0`

For more dependencies and their versions, please, check the [libs.versions.toml](/gradle/libs.versions.toml) file. 

# Modules

Current project contains 4 separate modules:

1. [`common`](/common) - module that contains shared logic to initialize LivePerson, reconnection logic and Hybrid SDK functions(Send message functions);
2. [`common-ui`](/common-ui) - module with LivePerson Conversation Fragment wrappers for Compose environment;
3. [`external-auth`](/external-auth) - module for authentication setup by inputting credentials;
4. [`sample`](/sample) - application module to authorize user and show conversation;

# Hybrid SDK methods (Alpha)

## Send message

To send message that wasn't typed by consumer use:

```java
ICallback<Unit, HybridSDKException> callback = ... // your callback implementation
LivePerson.sendTextMessage("Your message here", callback);
```

You can also check coroutine wrapper for this function in [`LPHybridCommandsInteractorImpl`](/common/src/main/java/com/liveperson/common/data/liveperson/LPHybridCommandsInteractorImpl.kt#L12).

```kotlin
override suspend fun sendMessage(message: String): AppResult<Unit, HybridSDKException> {
    return executeCommand { LivePerson.sendTextMessage(message, it) }
}
```

> **_NOTE:_** this function will send message successfully only if:
> - SDK is initialized;
> - Conversation Fragment is launched and in foreground state(shown);
> - Consumer is connected successfully or offline messaging is enabled;

## Open camera

To open camera and send required file to agent use:

```java
ICallback<Unit, HybridSDKException> callback = ... // your callback implementation
LivePerson.fileSharingOpenCamera(callback);
```

You can also check coroutine wrapper for this function in [`LPHybridCommandsInteractorImpl`](/common/src/main/java/com/liveperson/common/data/liveperson/LPHybridCommandsInteractorImpl.kt#L23).

```kotlin
override suspend fun openCamera(): AppResult<Unit, HybridSDKException> {
    return executeCommand { LivePerson.fileSharingOpenCamera(it) }
}
```

This method will ask consumer to grant an access to camera to capture an image. Once camera access 
permission is granted Camera App will be open. After photo capturing completed user will be able 
to send it to consumer.

> **_NOTE:_** this function will start camera flow only if these validations passed:
> - SDK is initialized;
> - ConversationFragment is launched and in foreground state(shown);
> - Consumer is connected successfully or offline messaging is enabled;
> - File sharing kill switch value is enabled.
> - File sharing site settings is enabled
> - `enable_photo_sharing` is set to true.

## Open file chooser

To open file chooser and send required file to agent use:

```java
ICallback<Unit, HybridSDKException> callback = ... // your callback implementation
LivePerson.fileSharingOpenFile(callback);
```

You can also check coroutine wrapper for this function in [`LPHybridCommandsInteractorImpl`](/common/src/main/java/com/liveperson/common/data/liveperson/LPHybridCommandsInteractorImpl.kt#L31).

```kotlin
override suspend fun fileSharingOpenFileChooser(): AppResult<Unit, HybridSDKException> {
    return executeCommand { LivePerson.fileSharingOpenFile(it) }
}
```

This method will not ask any permission and will open file chooser for consumer where user can 
select a file and send it to agent.

> Supported file formats are "pdf", "docx", "pptx","xlsx", "jpg", "jpeg", "png"

> **_NOTE:_** this function will start file choosing flow only if these validations passed:
> - SDK is initialized;
> - ConversationFragment is launched and in foreground state(shown);
> - Consumer is connected successfully or offline messaging is enabled;
> - File sharing kill switch value is enabled.
> - File sharing site settings is enabled
> - `enable_photo_sharing` is set to true.

## Open gallery

To open photo picket and send required photo to agent use:

```java
ICallback<Unit, HybridSDKException> callback = ... // your callback implementation
LivePerson.fileSharingOpenGallery(callback);
```

You can also check coroutine wrapper for this function in [`LPHybridCommandsInteractorImpl`](/common/src/main/java/com/liveperson/common/data/liveperson/LPHybridCommandsInteractorImpl.kt#L27).

```kotlin
override suspend fun openGallery(): AppResult<Unit, HybridSDKException> {
    return executeCommand { LivePerson.fileSharingOpenGallery(it) }
}
```

This method will not ask any permission and will open a photo picker to consumer where user can select
a required photo file and send it to agent.

> **_NOTE:_** this function will start gallery flow only if these validations passed:
> - SDK is initialized;
> - ConversationFragment is launched and in foreground state(shown);
> - Consumer is connected successfully or offline messaging is enabled;
> - File sharing kill switch value is enabled.
> - File sharing site settings is enabled
> - `enable_photo_sharing` is set to true.

> Supported photo formats are "jpg", "jpeg", "png"

## Change read-only mode

To show hide default ConversationFragment input use:

```java
boolean isReadOnlyModeEnabled = true;
ICallback<Unit, HybridSDKException> callback = ... // your callback implementation
LivePerson.changeReadOnlyMode(isReadOnlyModeEnabled, it);
```

You can also check coroutine wrapper for this function in[`LPHybridCommandsInteractorImpl`](/common/src/main/java/com/liveperson/common/data/liveperson/LPHybridCommandsInteractorImpl.kt#L35).

```kotlin
override suspend fun changeReadOnlyMode(isReadOnly: Boolean): AppResult<Unit, HybridSDKException> {
    return executeCommand { LivePerson.changeReadOnlyMode(isReadOnly, it) }
}
```
If read only mode is disabled user will see an input provided by LivePerson SDK in ConversationFragment.
To hide this input set read only mode to true. User will still able to send messages, however the 
default input will not be visible to consumer. 

> **_NOTE:_** this function will start gallery flow only if these validations passed:
> - SDK is initialized;
> - ConversationFragment is launched and in foreground state(shown);
> - History Control parameters for conversation filtering is set to closed conversations. 