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

## Log Message Examples

SBB is useful for building formatted log messages:

```java
import static com.github.rmarinsky.SBB.sbb;

// Basic log entry with level and timestamp
String log = sbb().sb("INFO").w().sb("2024-01-15 10:30:00").w()
    .append("User logged in successfully").build();
// Output: [INFO] [2024-01-15 10:30:00] User logged in successfully

// Log with request context
String errorLog = sbb().sb("ERROR").w().sb("RequestId:abc123").w()
    .append("Failed to process:").w().dq("Connection timeout").build();
// Output: [ERROR] [RequestId:abc123] Failed to process: "Connection timeout"

// Structured key-value logging
String debugLog = sbb().sb("DEBUG").w()
    .append("userId=").append(userId).coma().w()
    .append("action=").dq("checkout").coma().w()
    .append("items=").append(itemCount).build();
// Output: [DEBUG] userId=123, action="checkout", items=5

// Multi-line exception log
String exceptionLog = sbb().sb("ERROR").w().append("Exception occurred").n()
    .t().append("Type:").w().append(e.getClass().getSimpleName()).n()
    .t().append("Message:").w().dq(e.getMessage()).n()
    .t().append("Location:").w().append(className).append(":").append(lineNumber).build();
// Output:
// [ERROR] Exception occurred
//     Type: NullPointerException
//     Message: "Cannot invoke method on null"
//     Location: UserService:142

// HTTP request/response log
String httpLog = sbb().sb("HTTP").w().append(method).w().append(path).w()
    .p(sbb("status=").append(statusCode).coma().w()
        .append("time=").append(duration).append("ms").build()).build();
// Output: [HTTP] GET /api/users (status=200, time=45ms)
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