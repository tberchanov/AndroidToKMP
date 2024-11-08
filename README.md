# AndroidToKMP

This is a sample project to demonstrate how to convert an existing Android project to a Kotlin Multiplatform project.

|          | Android | iOS |
|----------|---------|-----|
| Branch   | `main`  | `kmp_ios` |
| DI   | 🟢 Koin  | 🟢 Koin |
| Presentation layer   | 🟢 ViewModel  | 🟢 ViewModel |
| Domain layer   | 🟢 Coroutines  | 🟢 Coroutines |
| Data layer   | 🟢 DataStore<br>🟡 Gson<br>🟡 OkHttp<br>🟡 Retrofit  | 🟢 DataStore<br>🟡 KotlinxSerialization<br>🟡 Ktor<br>🟡 Ktorfit |

🟢 - Migration was KMP friendly, without significant changes.  
🟡 - Migration to another library was needed.

UI is not shared and implemented natively, XML is for Android, and SwiftUI is for iOS.

This project showcases the scenario for migration when an Android app may have a lot of XML UI and full migration to Compose may not be an option.

In such a scenario, migration to KMP can still be beneficial as it allows for the sharing of a significant part of logic at a low cost.

| Android | iOS |
|---------|-----|
| <div style="text-align: center;"><img src="pictures/android_screenshot.png" style="width: 60%;"/></div>  | <div style="text-align: center;"><img src="pictures/ios_screenshot.png" style="width: 75%;"/></div> |

#### To see the step by step migration flow to KMP go throug commits history:

<img src="pictures/commits.png" alt="Commits" style="width: 70%;"/>