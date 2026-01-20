/**
 * SBB (String Builder Builder) - Fluent string concatenation for JavaScript/TypeScript.
 *
 * A lightweight library that provides a fluent, decorator-style API for building strings.
 *
 * @example
 * ```typescript
 * import { sbb } from 'js-sbb';
 *
 * const result = sbb("Hello").w().append("World").build();
 * console.log(result); // "Hello World"
 * ```
 */

/**
 * String Builder Builder - Fluent string concatenation.
 *
 * Provides a chainable API for building strings with convenient methods
 * for common operations like adding whitespace, quotes, and brackets.
 *
 * @example
 * ```typescript
 * SBB.sbb("name").w().dq("value").build();
 * // Returns: 'name "value"'
 * ```
 */
export class SBB {
  private parts: string[] = [];

  private constructor(base?: unknown) {
    if (base != null) {
      this.parts.push(String(base));
    }
  }

  /**
   * Factory method to create a new SBB instance.
   * @param base - Optional initial text
   * @returns New SBB instance
   */
  static sbb(base?: unknown): SBB {
    return new SBB(base);
  }

  // ==================== Core Append Methods ====================

  /**
   * Append object as string. Null/undefined values are ignored.
   * @param obj - Object to append
   * @returns this for chaining
   */
  append(obj?: unknown): this {
    if (obj != null) {
      this.parts.push(String(obj));
    }
    return this;
  }

  /**
   * Alias for append().
   * @param obj - Object to append
   * @returns this for chaining
   */
  add(obj?: unknown): this {
    return this.append(obj);
  }

  /**
   * Alias for append().
   * @param obj - Object to append
   * @returns this for chaining
   */
  join(obj?: unknown): this {
    return this.append(obj);
  }

  // ==================== Whitespace & Special Characters ====================

  /**
   * Append newline character.
   * @returns this for chaining
   */
  n(): this {
    this.parts.push("\n");
    return this;
  }

  /**
   * Append tab character.
   * @returns this for chaining
   */
  t(): this {
    this.parts.push("\t");
    return this;
  }

  /**
   * Append whitespace character.
   * @returns this for chaining
   */
  w(): this {
    this.parts.push(" ");
    return this;
  }

  /**
   * Append comma character.
   * @returns this for chaining
   */
  coma(): this {
    this.parts.push(",");
    return this;
  }

  /**
   * Append dot/period character.
   * @returns this for chaining
   */
  dot(): this {
    this.parts.push(".");
    return this;
  }

  // ==================== Wrapper Methods ====================

  /**
   * Wrap object in single quotes and append.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  sq(obj: unknown): this {
    if (obj != null) {
      this.parts.push(`'${obj}'`);
    }
    return this;
  }

  /**
   * Alias for sq() - wrap in single quotes.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  sQuote(obj: unknown): this {
    return this.sq(obj);
  }

  /**
   * Wrap object in double quotes and append.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  dq(obj: unknown): this {
    if (obj != null) {
      this.parts.push(`"${obj}"`);
    }
    return this;
  }

  /**
   * Alias for dq() - wrap in double quotes.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  dQuote(obj: unknown): this {
    return this.dq(obj);
  }

  /**
   * Wrap object in square brackets and append.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  sb(obj: unknown): this {
    if (obj != null) {
      this.parts.push(`[${obj}]`);
    }
    return this;
  }

  /**
   * Alias for sb() - wrap in square brackets.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  squareBrackets(obj: unknown): this {
    return this.sb(obj);
  }

  /**
   * Wrap object in curly brackets and append.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  cb(obj: unknown): this {
    if (obj != null) {
      this.parts.push(`{${obj}}`);
    }
    return this;
  }

  /**
   * Alias for cb() - wrap in curly brackets.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  curlyBrackets(obj: unknown): this {
    return this.cb(obj);
  }

  /**
   * Wrap object in parentheses and append.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  p(obj: unknown): this {
    if (obj != null) {
      this.parts.push(`(${obj})`);
    }
    return this;
  }

  /**
   * Alias for p() - wrap in parentheses.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  parentheses(obj: unknown): this {
    return this.p(obj);
  }

  /**
   * Wrap object in angle brackets and append.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  ab(obj: unknown): this {
    if (obj != null) {
      this.parts.push(`<${obj}>`);
    }
    return this;
  }

  /**
   * Alias for ab() - wrap in angle brackets.
   * @param obj - Object to wrap
   * @returns this for chaining
   */
  angleBrackets(obj: unknown): this {
    return this.ab(obj);
  }

  // ==================== Build Methods ====================

  /**
   * Build and return the final string. Clears internal buffer.
   * @returns The built string
   */
  build(): string {
    const result = this.parts.join("");
    this.parts = [];
    return result;
  }

  /**
   * Alias for build().
   * @returns The built string
   */
  bld(): string {
    return this.build();
  }

  /**
   * Return built string when converting to string.
   * @returns The built string
   */
  toString(): string {
    return this.build();
  }
}

/**
 * Convenience function to create a new SBB instance.
 *
 * @param base - Optional initial text
 * @returns New SBB instance
 *
 * @example
 * ```typescript
 * import { sbb } from 'js-sbb';
 *
 * sbb("Hello").w().append("World").build();
 * // Returns: "Hello World"
 * ```
 */
export function sbb(base?: unknown): SBB {
  return SBB.sbb(base);
}

export default SBB;
