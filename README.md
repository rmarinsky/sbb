# SBB - String Builder Builder

A fluent, chainable API for building strings across multiple languages.

| Language | Package | Version | Install |
|----------|---------|---------|---------|
| Java | [sbb](java/) | 1.2 | `implementation 'com.github.rmarinsky:string-builder-builder:1.2'` |
| Python | [python-sbb](python/) | 1.0.0 | `pip install python-sbb` |
| JavaScript | [js-sbb](js/) | 1.0.0 | `npm install js-sbb` |

## Overview

SBB provides a consistent, fluent interface for string building across Java, Python, and JavaScript. Write the same patterns in any language:

```java
// Java
sbb("Hello").w().append("World").build()  // "Hello World"
```

```python
# Python
sbb("Hello").w().append("World").build()  # "Hello World"
```

```javascript
// JavaScript/TypeScript
sbb("Hello").w().append("World").build()  // "Hello World"
```

## API Reference

All implementations share the same API:

### Factory Methods
| Method | Description |
|--------|-------------|
| `sbb()` | Create empty builder |
| `sbb(base)` | Create builder with initial text |

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

## Examples

### SQL Query Building
```
sbb("SELECT").w().append("*").w().append("FROM").w().append("users")
    .w().append("WHERE").w().append("name").w().append("=").w().sq("John")
    .build()
// Result: "SELECT * FROM users WHERE name = 'John'"
```

### JSON-like Structure
```
sbb().cb(
    sbb().dq("name").append(":").w().dq("value").build()
).build()
// Result: '{"name": "value"}'
```

### Multi-line Output
```
sbb("Line 1").n().append("Line 2").n().append("Line 3").build()
// Result:
// Line 1
// Line 2
// Line 3
```

## Thread Safety

All implementations are thread-safe by design. Each `sbb()` call creates a new independent instance, making it safe for concurrent/parallel operations.

## Contributing

See individual package directories for language-specific development instructions:
- [Java](java/README.md)
- [Python](python/README.md)
- [JavaScript/TypeScript](js/README.md)

## License

MIT
