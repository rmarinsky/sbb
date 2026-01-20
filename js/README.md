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
