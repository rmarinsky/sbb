# js-sbb

**String Builder Builder** - Fluent string concatenation for JavaScript/TypeScript.

A lightweight library that provides a fluent, decorator-style API for building strings. JavaScript/TypeScript port of the [Java SBB library](https://github.com/rmarinsky/string-builder-builder).

## Installation

```bash
npm install js-sbb
```

## Quick Start

```typescript
import { sbb } from "js-sbb";

// Simple example
const result = sbb("Hello").w().append("World").build();
console.log(result); // Hello World

// With quotes and brackets
const keyValue = sbb("key").append(":").w().dq("value").build();
console.log(keyValue); // key: "value"

// Complex chaining
const sql = sbb("SELECT")
  .w().append("*")
  .w().append("FROM")
  .w().append("users")
  .w().append("WHERE")
  .w().append("name")
  .w().append("=")
  .w().sq("John")
  .build();
console.log(sql); // SELECT * FROM users WHERE name = 'John'
```

## Log Message Examples

SBB is useful for building formatted log messages:

```typescript
import { sbb } from "js-sbb";

// Basic log entry with level and timestamp
const log = sbb().sb("INFO").w().sb("2024-01-15 10:30:00").w()
  .append("User logged in successfully").build();
// Output: [INFO] [2024-01-15 10:30:00] User logged in successfully

// Log with request context
const errorLog = sbb().sb("ERROR").w().sb(`RequestId:${requestId}`).w()
  .append("Failed to process:").w().dq("Connection timeout").build();
// Output: [ERROR] [RequestId:abc123] Failed to process: "Connection timeout"

// Structured key-value logging
const debugLog = sbb().sb("DEBUG").w()
  .append("userId=").append(userId).coma().w()
  .append("action=").dq("checkout").coma().w()
  .append("items=").append(itemCount).build();
// Output: [DEBUG] userId=123, action="checkout", items=5

// Multi-line exception log
const exceptionLog = sbb().sb("ERROR").w().append("Exception occurred").n()
  .t().append("Type:").w().append(error.name).n()
  .t().append("Message:").w().dq(error.message).n()
  .t().append("Stack:").w().append(error.stack?.split("\n")[1]?.trim()).build();
// Output:
// [ERROR] Exception occurred
//     Type: TypeError
//     Message: "Cannot read property of undefined"
//     Stack: at processRequest (app.js:142:15)

// HTTP request/response log
const httpLog = sbb().sb("HTTP").w().append(method).w().append(path).w()
  .p(sbb("status=").append(statusCode).coma().w()
    .append("time=").append(duration).append("ms").build()).build();
// Output: [HTTP] GET /api/users (status=200, time=45ms)
```

## API Reference

### Factory Methods

| Method | Description |
|--------|-------------|
| `sbb()` | Create empty builder |
| `sbb(base)` | Create builder with initial text |
| `SBB.sbb(base)` | Static factory method |

### Append Methods

| Method | Description |
|--------|-------------|
| `append(obj)` | Append object as string |
| `add(obj)` | Alias for append |
| `join(obj)` | Alias for append |

### Whitespace & Special Characters

| Method | Output |
|--------|--------|
| `n()` | Newline `\n` |
| `t()` | Tab `\t` |
| `w()` | Whitespace ` ` |
| `coma()` | Comma `,` |
| `dot()` | Period `.` |

### Wrapper Methods

| Method | Alias | Output |
|--------|-------|--------|
| `sq(obj)` | `sQuote(obj)` | `'obj'` |
| `dq(obj)` | `dQuote(obj)` | `"obj"` |
| `sb(obj)` | `squareBrackets(obj)` | `[obj]` |
| `cb(obj)` | `curlyBrackets(obj)` | `{obj}` |
| `p(obj)` | `parentheses(obj)` | `(obj)` |
| `ab(obj)` | `angleBrackets(obj)` | `<obj>` |

### Build Methods

| Method | Description |
|--------|-------------|
| `build()` | Return final string and clear buffer |
| `bld()` | Alias for build |

## TypeScript Support

Full TypeScript support with type definitions included:

```typescript
import { SBB, sbb } from "js-sbb";

// Type-safe chaining
const builder: SBB = sbb("start");
const result: string = builder.w().append("end").build();
```

## Thread Safety

Each `sbb()` call creates a new independent instance, making it safe for concurrent async operations:

```typescript
const promises = items.map((item, i) =>
  sbb("item-").append(i).w().append(item).build()
);
const results = await Promise.all(promises);
```

## License

MIT
