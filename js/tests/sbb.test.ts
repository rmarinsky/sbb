import { describe, it, expect } from "vitest";
import { SBB, sbb } from "../src/index";

describe("SBB Creation", () => {
  it("should create empty SBB instance", () => {
    const builder = sbb();
    expect(builder.build()).toBe("");
  });

  it("should create SBB with initial string", () => {
    const builder = sbb("Hello");
    expect(builder.build()).toBe("Hello");
  });

  it("should create SBB with number", () => {
    const builder = sbb(42);
    expect(builder.build()).toBe("42");
  });

  it("should create SBB with null", () => {
    const builder = sbb(null);
    expect(builder.build()).toBe("");
  });

  it("should create SBB with undefined", () => {
    const builder = sbb(undefined);
    expect(builder.build()).toBe("");
  });

  it("should use static factory method", () => {
    const builder = SBB.sbb("test");
    expect(builder.build()).toBe("test");
  });
});

describe("Append Methods", () => {
  it("should append strings", () => {
    const result = sbb().append("Hello").append("World").build();
    expect(result).toBe("HelloWorld");
  });

  it("should use add alias", () => {
    const result = sbb().add("Hello").add("World").build();
    expect(result).toBe("HelloWorld");
  });

  it("should use join alias", () => {
    const result = sbb().join("Hello").join("World").build();
    expect(result).toBe("HelloWorld");
  });

  it("should ignore null in append", () => {
    const result = sbb("Hello").append(null).append("World").build();
    expect(result).toBe("HelloWorld");
  });

  it("should ignore undefined in append", () => {
    const result = sbb("Hello").append(undefined).append("World").build();
    expect(result).toBe("HelloWorld");
  });

  it("should append numbers", () => {
    const result = sbb().append(1).append(2).append(3).build();
    expect(result).toBe("123");
  });
});

describe("Whitespace and Special Characters", () => {
  it("should append newline", () => {
    const result = sbb("Line1").n().append("Line2").build();
    expect(result).toBe("Line1\nLine2");
  });

  it("should append tab", () => {
    const result = sbb("Col1").t().append("Col2").build();
    expect(result).toBe("Col1\tCol2");
  });

  it("should append whitespace", () => {
    const result = sbb("Hello").w().append("World").build();
    expect(result).toBe("Hello World");
  });

  it("should append comma", () => {
    const result = sbb("a").coma().append("b").coma().append("c").build();
    expect(result).toBe("a,b,c");
  });

  it("should append dot", () => {
    const result = sbb("Hello").dot().build();
    expect(result).toBe("Hello.");
  });
});

describe("Wrapper Methods", () => {
  it("should wrap in single quotes with sq()", () => {
    const result = sbb().sq("text").build();
    expect(result).toBe("'text'");
  });

  it("should wrap in single quotes with sQuote()", () => {
    const result = sbb().sQuote("text").build();
    expect(result).toBe("'text'");
  });

  it("should wrap in double quotes with dq()", () => {
    const result = sbb().dq("text").build();
    expect(result).toBe('"text"');
  });

  it("should wrap in double quotes with dQuote()", () => {
    const result = sbb().dQuote("text").build();
    expect(result).toBe('"text"');
  });

  it("should wrap in square brackets with sb()", () => {
    const result = sbb().sb("text").build();
    expect(result).toBe("[text]");
  });

  it("should wrap in square brackets with squareBrackets()", () => {
    const result = sbb().squareBrackets("text").build();
    expect(result).toBe("[text]");
  });

  it("should wrap in curly brackets with cb()", () => {
    const result = sbb().cb("text").build();
    expect(result).toBe("{text}");
  });

  it("should wrap in curly brackets with curlyBrackets()", () => {
    const result = sbb().curlyBrackets("text").build();
    expect(result).toBe("{text}");
  });

  it("should wrap in parentheses with p()", () => {
    const result = sbb().p("text").build();
    expect(result).toBe("(text)");
  });

  it("should wrap in parentheses with parentheses()", () => {
    const result = sbb().parentheses("text").build();
    expect(result).toBe("(text)");
  });

  it("should wrap in angle brackets with ab()", () => {
    const result = sbb().ab("text").build();
    expect(result).toBe("<text>");
  });

  it("should wrap in angle brackets with angleBrackets()", () => {
    const result = sbb().angleBrackets("text").build();
    expect(result).toBe("<text>");
  });

  it("should ignore null in wrapper methods", () => {
    const result = sbb("before").sq(null).append("after").build();
    expect(result).toBe("beforeafter");
  });
});

describe("Build Methods", () => {
  it("should build string", () => {
    const builder = sbb("test");
    const result = builder.build();
    expect(result).toBe("test");
  });

  it("should use bld alias", () => {
    const builder = sbb("test");
    const result = builder.bld();
    expect(result).toBe("test");
  });

  it("should clear buffer after build", () => {
    const builder = sbb("first");
    expect(builder.build()).toBe("first");
    expect(builder.build()).toBe("");
  });

  it("should convert to string with toString()", () => {
    const builder = sbb("test");
    expect(builder.toString()).toBe("test");
  });
});

describe("Fluent Chaining", () => {
  it("should handle complex chain", () => {
    const result = sbb("SELECT")
      .w()
      .append("*")
      .w()
      .append("FROM")
      .w()
      .append("users")
      .w()
      .append("WHERE")
      .w()
      .append("name")
      .w()
      .append("=")
      .w()
      .sq("John")
      .build();

    expect(result).toBe("SELECT * FROM users WHERE name = 'John'");
  });

  it("should build JSON-like structure", () => {
    const result = sbb()
      .cb(sbb().dq("name").append(":").w().dq("value").build())
      .build();

    expect(result).toBe('{"name": "value"}');
  });

  it("should match README example", () => {
    const targetString = "appended";
    const result = sbb(targetString)
      .t()
      .append(targetString)
      .w()
      .sq(targetString)
      .n()
      .dq(targetString)
      .w()
      .add(targetString)
      .build();

    const expected = "appended\tappended 'appended'\n\"appended\" appended";
    expect(result).toBe(expected);
  });
});

describe("Concurrency Safety", () => {
  it("should handle parallel async operations", async () => {
    const iterations = 1000;

    const promises = Array.from({ length: iterations }, (_, i) =>
      Promise.resolve().then(() =>
        sbb("item-").append(i).w().sb(i * 2).build()
      )
    );

    const results = await Promise.all(promises);

    expect(results.length).toBe(iterations);
    expect(results[0]).toBe("item-0 [0]");
    expect(results[999]).toBe("item-999 [1998]");
  });

  it("should isolate instances in concurrent usage", async () => {
    const threadCount = 100;
    const iterationsPerThread = 100;

    const allPromises: Promise<string>[] = [];

    for (let threadId = 0; threadId < threadCount; threadId++) {
      for (let i = 0; i < iterationsPerThread; i++) {
        const promise = Promise.resolve().then(() =>
          sbb("thread-").append(threadId).append("-iter-").append(i).build()
        );
        allPromises.push(promise);
      }
    }

    const results = await Promise.all(allPromises);

    expect(results.length).toBe(threadCount * iterationsPerThread);

    // Verify each result matches expected pattern
    let idx = 0;
    for (let threadId = 0; threadId < threadCount; threadId++) {
      for (let i = 0; i < iterationsPerThread; i++) {
        expect(results[idx]).toBe(`thread-${threadId}-iter-${i}`);
        idx++;
      }
    }
  });
});

describe("Edge Cases", () => {
  it("should handle empty string append", () => {
    const result = sbb("a").append("").append("b").build();
    expect(result).toBe("ab");
  });

  it("should support Unicode", () => {
    const result = sbb("Hello").w().append("世界").w().append("🌍").build();
    expect(result).toBe("Hello 世界 🌍");
  });

  it("should handle special characters in wrappers", () => {
    const result = sbb().dq("it's \"quoted\"").build();
    expect(result).toBe('"it\'s "quoted""');
  });

  it("should allow reusing builder after build", () => {
    const builder = sbb("first");
    expect(builder.build()).toBe("first");

    builder.append("second");
    expect(builder.build()).toBe("second");

    builder.append("third");
    expect(builder.build()).toBe("third");
  });

  it("should chain multiple wrapper types", () => {
    const result = sbb().ab(sbb().sb(sbb().cb("inner").build()).build()).build();
    expect(result).toBe("<[{inner}]>");
  });

  it("should handle boolean values", () => {
    const result = sbb("value:").w().append(true).build();
    expect(result).toBe("value: true");
  });

  it("should handle objects with toString", () => {
    const obj = {
      toString() {
        return "custom";
      },
    };
    const result = sbb().append(obj).build();
    expect(result).toBe("custom");
  });
});
