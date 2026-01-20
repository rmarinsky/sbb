"""
SBB (String Builder Builder) - Fluent string concatenation for Python.

A lightweight library that provides a fluent, decorator-style API for building strings.

Example:
    >>> from sbb import sbb
    >>> result = sbb("Hello").w().append("World").build()
    >>> print(result)
    Hello World
"""

from typing import Any

__version__ = "1.0.0"
__all__ = ["SBB", "sbb"]


class SBB:
    """
    String Builder Builder - Fluent string concatenation.

    Provides a chainable API for building strings with convenient methods
    for common operations like adding whitespace, quotes, and brackets.

    Example:
        >>> SBB.sbb("name").w().dq("value").build()
        'name "value"'
    """

    __slots__ = ("_parts",)

    def __init__(self, base: Any = None) -> None:
        """Initialize SBB with optional base text."""
        self._parts: list[str] = []
        if base is not None:
            self._parts.append(str(base))

    @staticmethod
    def sbb(base: Any = None) -> "SBB":
        """Factory method to create a new SBB instance."""
        return SBB(base)

    # ==================== Core Append Methods ====================

    def append(self, obj: Any) -> "SBB":
        """Append object as string. None values are ignored."""
        if obj is not None:
            self._parts.append(str(obj))
        return self

    def add(self, obj: Any) -> "SBB":
        """Alias for append()."""
        return self.append(obj)

    def join(self, obj: Any) -> "SBB":
        """Alias for append()."""
        return self.append(obj)

    # ==================== Whitespace & Special Characters ====================

    def n(self) -> "SBB":
        """Append newline character."""
        self._parts.append("\n")
        return self

    def t(self) -> "SBB":
        """Append tab character."""
        self._parts.append("\t")
        return self

    def w(self) -> "SBB":
        """Append whitespace character."""
        self._parts.append(" ")
        return self

    def coma(self) -> "SBB":
        """Append comma character."""
        self._parts.append(",")
        return self

    def dot(self) -> "SBB":
        """Append dot/period character."""
        self._parts.append(".")
        return self

    # ==================== Wrapper Methods ====================

    def sq(self, obj: Any) -> "SBB":
        """Wrap object in single quotes and append."""
        if obj is not None:
            self._parts.append(f"'{obj}'")
        return self

    def sQuote(self, obj: Any) -> "SBB":
        """Alias for sq() - wrap in single quotes."""
        return self.sq(obj)

    def dq(self, obj: Any) -> "SBB":
        """Wrap object in double quotes and append."""
        if obj is not None:
            self._parts.append(f'"{obj}"')
        return self

    def dQuote(self, obj: Any) -> "SBB":
        """Alias for dq() - wrap in double quotes."""
        return self.dq(obj)

    def sb(self, obj: Any) -> "SBB":
        """Wrap object in square brackets and append."""
        if obj is not None:
            self._parts.append(f"[{obj}]")
        return self

    def squareBrackets(self, obj: Any) -> "SBB":
        """Alias for sb() - wrap in square brackets."""
        return self.sb(obj)

    def cb(self, obj: Any) -> "SBB":
        """Wrap object in curly brackets and append."""
        if obj is not None:
            self._parts.append(f"{{{obj}}}")
        return self

    def curlyBrackets(self, obj: Any) -> "SBB":
        """Alias for cb() - wrap in curly brackets."""
        return self.cb(obj)

    def p(self, obj: Any) -> "SBB":
        """Wrap object in parentheses and append."""
        if obj is not None:
            self._parts.append(f"({obj})")
        return self

    def parentheses(self, obj: Any) -> "SBB":
        """Alias for p() - wrap in parentheses."""
        return self.p(obj)

    def ab(self, obj: Any) -> "SBB":
        """Wrap object in angle brackets and append."""
        if obj is not None:
            self._parts.append(f"<{obj}>")
        return self

    def angleBrackets(self, obj: Any) -> "SBB":
        """Alias for ab() - wrap in angle brackets."""
        return self.ab(obj)

    # ==================== Build Methods ====================

    def build(self) -> str:
        """Build and return the final string. Clears internal buffer."""
        result = "".join(self._parts)
        self._parts.clear()
        return result

    def bld(self) -> str:
        """Alias for build()."""
        return self.build()

    def __str__(self) -> str:
        """Return built string when converting to str."""
        return self.build()

    def __repr__(self) -> str:
        """Return representation of current state."""
        preview = "".join(self._parts)
        if len(preview) > 50:
            preview = preview[:47] + "..."
        return f"SBB({preview!r})"


def sbb(base: Any = None) -> SBB:
    """
    Convenience function to create a new SBB instance.

    Example:
        >>> from sbb import sbb
        >>> sbb("Hello").w().append("World").build()
        'Hello World'
    """
    return SBB.sbb(base)
