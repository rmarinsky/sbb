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
