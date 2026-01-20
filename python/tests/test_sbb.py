"""Tests for SBB (String Builder Builder)."""

import pytest
from sbb import SBB, sbb


class TestSBBCreation:
    """Test SBB instance creation."""

    def test_create_empty(self):
        """Create empty SBB instance."""
        builder = sbb()
        assert builder.build() == ""

    def test_create_with_string(self):
        """Create SBB with initial string."""
        builder = sbb("Hello")
        assert builder.build() == "Hello"

    def test_create_with_number(self):
        """Create SBB with number."""
        builder = sbb(42)
        assert builder.build() == "42"

    def test_create_with_none(self):
        """Create SBB with None."""
        builder = sbb(None)
        assert builder.build() == ""

    def test_factory_method(self):
        """Test static factory method."""
        builder = SBB.sbb("test")
        assert builder.build() == "test"


class TestAppendMethods:
    """Test append/add/join methods."""

    def test_append(self):
        """Test append method."""
        result = sbb().append("Hello").append("World").build()
        assert result == "HelloWorld"

    def test_add(self):
        """Test add alias."""
        result = sbb().add("Hello").add("World").build()
        assert result == "HelloWorld"

    def test_join(self):
        """Test join alias."""
        result = sbb().join("Hello").join("World").build()
        assert result == "HelloWorld"

    def test_append_none(self):
        """Test append with None is ignored."""
        result = sbb("Hello").append(None).append("World").build()
        assert result == "HelloWorld"

    def test_append_numbers(self):
        """Test append with numbers."""
        result = sbb().append(1).append(2).append(3).build()
        assert result == "123"


class TestWhitespaceAndSpecialChars:
    """Test whitespace and special character methods."""

    def test_newline(self):
        """Test newline method."""
        result = sbb("Line1").n().append("Line2").build()
        assert result == "Line1\nLine2"

    def test_tab(self):
        """Test tab method."""
        result = sbb("Col1").t().append("Col2").build()
        assert result == "Col1\tCol2"

    def test_whitespace(self):
        """Test whitespace method."""
        result = sbb("Hello").w().append("World").build()
        assert result == "Hello World"

    def test_comma(self):
        """Test comma method."""
        result = sbb("a").coma().append("b").coma().append("c").build()
        assert result == "a,b,c"

    def test_dot(self):
        """Test dot method."""
        result = sbb("Hello").dot().build()
        assert result == "Hello."


class TestWrapperMethods:
    """Test wrapper/bracket methods."""

    def test_single_quote(self):
        """Test single quote wrapper."""
        result = sbb().sq("text").build()
        assert result == "'text'"

    def test_single_quote_alias(self):
        """Test sQuote alias."""
        result = sbb().sQuote("text").build()
        assert result == "'text'"

    def test_double_quote(self):
        """Test double quote wrapper."""
        result = sbb().dq("text").build()
        assert result == '"text"'

    def test_double_quote_alias(self):
        """Test dQuote alias."""
        result = sbb().dQuote("text").build()
        assert result == '"text"'

    def test_square_brackets(self):
        """Test square brackets wrapper."""
        result = sbb().sb("text").build()
        assert result == "[text]"

    def test_square_brackets_alias(self):
        """Test squareBrackets alias."""
        result = sbb().squareBrackets("text").build()
        assert result == "[text]"

    def test_curly_brackets(self):
        """Test curly brackets wrapper."""
        result = sbb().cb("text").build()
        assert result == "{text}"

    def test_curly_brackets_alias(self):
        """Test curlyBrackets alias."""
        result = sbb().curlyBrackets("text").build()
        assert result == "{text}"

    def test_parentheses(self):
        """Test parentheses wrapper."""
        result = sbb().p("text").build()
        assert result == "(text)"

    def test_parentheses_alias(self):
        """Test parentheses alias."""
        result = sbb().parentheses("text").build()
        assert result == "(text)"

    def test_angle_brackets(self):
        """Test angle brackets wrapper."""
        result = sbb().ab("text").build()
        assert result == "<text>"

    def test_angle_brackets_alias(self):
        """Test angleBrackets alias."""
        result = sbb().angleBrackets("text").build()
        assert result == "<text>"

    def test_wrapper_with_none(self):
        """Test wrapper methods with None."""
        result = sbb("before").sq(None).append("after").build()
        assert result == "beforeafter"


class TestBuildMethods:
    """Test build/finalization methods."""

    def test_build(self):
        """Test build method."""
        builder = sbb("test")
        result = builder.build()
        assert result == "test"

    def test_bld_alias(self):
        """Test bld alias."""
        builder = sbb("test")
        result = builder.bld()
        assert result == "test"

    def test_build_clears_buffer(self):
        """Test that build clears internal buffer."""
        builder = sbb("first")
        assert builder.build() == "first"
        assert builder.build() == ""

    def test_str(self):
        """Test __str__ method."""
        builder = sbb("test")
        assert str(builder) == "test"

    def test_repr(self):
        """Test __repr__ method."""
        builder = sbb("test")
        # Note: repr doesn't consume the buffer like str does
        repr_str = repr(builder)
        assert "SBB" in repr_str
        assert "test" in repr_str


class TestFluentChaining:
    """Test fluent method chaining."""

    def test_complex_chain(self):
        """Test complex method chaining."""
        result = (
            sbb("SELECT")
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
            .build()
        )
        assert result == "SELECT * FROM users WHERE name = 'John'"

    def test_json_like_building(self):
        """Test building JSON-like structure."""
        result = (
            sbb()
            .cb(
                sbb()
                .dq("name")
                .append(":")
                .w()
                .dq("value")
                .build()
            )
            .build()
        )
        assert result == '{"name": "value"}'

    def test_readme_example(self):
        """Test example from Java README."""
        target_string = "appended"
        result = (
            sbb(target_string)
            .t()
            .append(target_string)
            .w()
            .sq(target_string)
            .n()
            .dq(target_string)
            .w()
            .add(target_string)
            .build()
        )
        expected = "appended\tappended 'appended'\n\"appended\" appended"
        assert result == expected


class TestThreadSafety:
    """Test thread safety with concurrent operations."""

    def test_concurrent_usage(self):
        """Test concurrent SBB usage with threads."""
        import threading
        from concurrent.futures import ThreadPoolExecutor, as_completed

        results = []
        errors = []
        num_threads = 100
        iterations_per_thread = 100

        def worker(thread_id: int):
            thread_results = []
            for i in range(iterations_per_thread):
                result = (
                    sbb("thread-")
                    .append(thread_id)
                    .append("-iter-")
                    .append(i)
                    .build()
                )
                expected = f"thread-{thread_id}-iter-{i}"
                if result != expected:
                    return None, f"Mismatch: {result} != {expected}"
                thread_results.append(result)
            return thread_results, None

        with ThreadPoolExecutor(max_workers=num_threads) as executor:
            futures = [executor.submit(worker, i) for i in range(num_threads)]
            for future in as_completed(futures):
                result, error = future.result()
                if error:
                    errors.append(error)
                elif result:
                    results.extend(result)

        assert len(errors) == 0, f"Errors: {errors}"
        assert len(results) == num_threads * iterations_per_thread

    def test_parallel_processing(self):
        """Test with parallel map operations."""
        from multiprocessing.pool import ThreadPool

        def process(i: int) -> str:
            return sbb("item-").append(i).w().sb(i * 2).build()

        with ThreadPool(10) as pool:
            results = pool.map(process, range(1000))

        assert len(results) == 1000
        assert results[0] == "item-0 [0]"
        assert results[999] == "item-999 [1998]"


class TestEdgeCases:
    """Test edge cases and special scenarios."""

    def test_empty_string_append(self):
        """Test appending empty string."""
        result = sbb("a").append("").append("b").build()
        assert result == "ab"

    def test_unicode_support(self):
        """Test Unicode string support."""
        result = sbb("Hello").w().append("世界").w().append("🌍").build()
        assert result == "Hello 世界 🌍"

    def test_special_characters_in_wrappers(self):
        """Test special characters inside wrappers."""
        result = sbb().dq("it's \"quoted\"").build()
        assert result == '"it\'s "quoted""'

    def test_multiple_builds(self):
        """Test reusing builder after build."""
        builder = sbb("first")
        assert builder.build() == "first"

        builder.append("second")
        assert builder.build() == "second"

        builder.append("third")
        assert builder.build() == "third"

    def test_chained_wrappers(self):
        """Test chaining multiple wrapper types."""
        result = sbb().ab(sbb().sb(sbb().cb("inner").build()).build()).build()
        assert result == "<[{inner}]>"
