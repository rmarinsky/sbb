# Builder for StringBuilder

Optimized for Java 8 decorator for StringBuilder.

```java
String targetString = "appended";
String actualText = sbb(targetString).t().append(targetString).w().sq(targetString)
    .n().dq(targetString).w().add(targetString).bld();

Assertions.assertThat(actualText).isEqualTo("appended\tappended 'appended'\n\"appended\" appended");
```
Output:
```
appended    appended 'appended'
"appended" appended
```

## Add dependency

### Gradle

Add to setting.gradle file:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
and to dependencies section in build.gradle file:
```groovy
implementation 'com.github.rmarinsky:string-builder-builder:1.0'
```

### Maven

```xml
<repositories>
    <repository> 
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
and to dependencies section:
```xml
<dependency>
    <groupId>com.github.rmarinsky</groupId>
    <artifactId>string-builder-builder</artifactId>
    <version>1.0</version>
</dependency>
```