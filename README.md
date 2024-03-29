[![Maven Central](https://img.shields.io/maven-central/v/hu.simplexion.z2/z2-lib)](https://mvnrepository.com/artifact/hu.simplexion.z2/z2-lib)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Libraries for Kotlin Multiplatform (mostly browser + JVM backend) development.

Status: **initial development**

**====  Important ====**

**Z2 is under its initial development. Links may be broken, parts may be missing.**

**Gradle dependencies do not work yet as the library is not published on Maven Central and on Gradle Plugin Repo.**

## Structure

Z2 has three major parts:

| Library | Purpose                                                      |
|---------|--------------------------------------------------------------|
| Lib     | Application level code built on `Core` with use of `Plugin`. |
| Plugin  | The compiler plugin that makes use of the `Core` natural.    |
| Core    | Fundamental parts of the library.                            |

In addition, the `Site` contains the code of the documentation site.

## Gradle Dependency

You have to add the plugin and the `core` **OR** the `lib` dependency to your `build.gradle.kts`:

Plugin:

```kotlin
plugins {
    kotlin("multiplatform") version "1.9.10"
    id("hu.simplexion.z2") version "<z2-version>"
}
```

Lib dependency (includes the application level libraries):

```kotlin
sourceSets["commonMain"].dependencies {
    implementation("hu.simplexion.z2:z2-lib:$z2_version")
}
```

Core dependency (includes only the fundamentals)

```kotlin
sourceSets["commonMain"].dependencies {
    implementation("hu.simplexion.z2:z2-core:$z2_version")
}
```

## Functionality

### Base

* [Adaptive] - reactive, Svelte-like UI components
* [Schematic] - schema based data definitions for automatic UI building, serialization, validation etc.
* [Services] - client-server communication with simple function calls defined in an interface
* [Localization] - multi-language support

### Lib

* Web
  * Material 3 styled components with Svelte-like reactivity
  * Tailwind-like CSS
  * Extra components:
    * Schematic table - format, sort, filter, export, label, edit automatically (based on data schema information)
    * Schematic field - format, validate and label automatically (based on data schema information)
    * Schematic Select - handle the complexity of select field easily
    * File upload
    * Calendar
* Ktor and Exposed integration
  * ready-to go integration
  * automatic Schematic - Exposed mapping
* Authentication, role-based authorization
* E-mail sending

## License

> Copyright (c) 2020-2024 Simplexion Kft, Hungary and contributors
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this work except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
