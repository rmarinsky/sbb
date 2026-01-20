# python-sbb

**String Builder Builder** - Fluent string concatenation for Python.

A lightweight library that provides a fluent, decorator-style API for building strings. Python port of the [Java SBB library](https://github.com/rmarinsky/string-builder-builder).

## Installation

```bash
pip install python-sbb
```

## Quick Start

```python
from sbb import sbb

# Simple example
result = sbb("Hello").w().append("World").build()
print(result)  # Hello World

# With quotes and brackets
result = sbb("key").append(":").w().dq("value").build()
print(result)  # key: "value"

# Complex chaining
sql = (
    sbb("SELECT")
    .w().append("*")
    .w().append("FROM")
    .w().append("users")
    .w().append("WHERE")
    .w().append("name")
    .w().append("=")
    .w().sq("John")
    .build()
)
print(sql)  # SELECT * FROM users WHERE name = 'John'
```

## Log Message Examples

SBB is useful for building formatted log messages:

```python
from sbb import sbb

# Basic log entry with level and timestamp
log = sbb().sb("INFO").w().sb("2024-01-15 10:30:00").w().append("User logged in successfully").build()
# Output: [INFO] [2024-01-15 10:30:00] User logged in successfully

# Log with request context
error_log = (
    sbb().sb("ERROR").w().sb(f"RequestId:{request_id}").w()
    .append("Failed to process:").w().dq("Connection timeout").build()
)
# Output: [ERROR] [RequestId:abc123] Failed to process: "Connection timeout"

# Structured key-value logging
debug_log = (
    sbb().sb("DEBUG").w()
    .append("userId=").append(user_id).coma().w()
    .append("action=").dq("checkout").coma().w()
    .append("items=").append(item_count).build()
)
# Output: [DEBUG] userId=123, action="checkout", items=5

# Multi-line exception log
exception_log = (
    sbb().sb("ERROR").w().append("Exception occurred").n()
    .t().append("Type:").w().append(type(e).__name__).n()
    .t().append("Message:").w().dq(str(e)).n()
    .t().append("Location:").w().append(f"{filename}:{line_number}").build()
)
# Output:
# [ERROR] Exception occurred
#     Type: ValueError
#     Message: "Invalid input"
#     Location: user_service.py:142

# HTTP request/response log
http_log = (
    sbb().sb("HTTP").w().append(method).w().append(path).w()
    .p(sbb("status=").append(status_code).coma().w()
       .append("time=").append(duration).append("ms").build()).build()
)
# Output: [HTTP] GET /api/users (status=200, time=45ms)
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

## Thread Safety

Each `sbb()` call creates a new independent instance, making it inherently thread-safe:

```python
from concurrent.futures import ThreadPoolExecutor

def process(i):
    return sbb("item-").append(i).build()

with ThreadPoolExecutor(max_workers=10) as executor:
    results = list(executor.map(process, range(1000)))
```

## License

MIT
