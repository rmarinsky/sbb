package com.github.rmarinsky;

import java.util.Optional;

/**
 * String Builder Builder
 * <p>
 * Thread-safe fluent string builder.
 * Each call to {@link #sbb()} creates a new instance,
 * making it safe for use in multi-threaded environments
 * (RxJava, Reactor, CompletableFuture, parallel streams, etc.)
 * </p>
 */
public class SBB {

    private final StringBuilder strBuilder;

    private SBB() {
        this.strBuilder = new StringBuilder();
    }

    /**
     * Creates a new SBB instance.
     * Thread-safe: each call returns a new independent instance.
     *
     * @return new SBB instance
     */
    public static SBB sbb() {
        return new SBB();
    }

    /**
     * Creates a new SBB instance with initial text.
     * Thread-safe: each call returns a new independent instance.
     *
     * @param basePlainText initial text to append
     * @return new SBB instance with the text appended
     */
    public static SBB sbb(Object basePlainText) {
        return new SBB().append(basePlainText);
    }

    private SBB addToBuild(Object targetPlainText) {
        Optional.ofNullable(targetPlainText).ifPresent(this.strBuilder::append);
        return this;
    }

    /**
     * Append char
     */
    private SBB charAppend(char targetChar) {
        this.strBuilder.append(targetChar);
        return this;
    }

    /**
     * Append Object to text
     */
    public SBB append(Object targetPlainText) {
        return this.addToBuild(targetPlainText);
    }

    /**
     * Append plain text
     * Synonym for append
     */
    public SBB add(Object targetPlainText) {
        return this.append(targetPlainText);
    }

    /**
     * Join text
     * Synonym for append
     */
    public SBB join(Object targetPlainText) {
        return this.append(targetPlainText);
    }

    /**
     * Add new line caret
     */
    public SBB n() {
        return this.charAppend('\n');
    }

    /**
     * Add a tabulation
     */
    public SBB t() {
        return this.charAppend('\t');
    }

    /**
     * Add new whitespace
     */
    public SBB w() {
        return this.charAppend(' ');
    }

    public SBB coma() {
        return this.charAppend(',');
    }

    public SBB dot() {
        return this.charAppend('.');
    }

    /**
     * Append as single quoted value
     *
     * @return 'SBB'
     */
    public SBB sQuote(Object targetPlainText) {
        return this.charAppend('\'').append(targetPlainText).charAppend('\'');
    }

    /**
     * Short synonym of sQuote
     */
    public SBB sq(Object targetPlainText) {
        return sQuote(targetPlainText);
    }

    /**
     * Append as double quoted value
     *
     * @return "SBB"
     */
    public SBB dQuote(Object targetPlainText) {
        return this.charAppend('\"').append(targetPlainText).charAppend('\"');
    }

    /**
     * Short synonym of dQuote
     */
    public SBB dq(Object targetPlainText) {
        return dQuote(targetPlainText);
    }

    /**
     * Append as square brackets
     *
     * @return [targetPlainText]
     */
    public SBB squareBrackets(Object targetPlainText) {
        return this.charAppend('[').append(targetPlainText).charAppend(']');
    }

    /**
     * Short synonym of squareBrackets
     */
    public SBB sb(Object targetPlainText) {
        return squareBrackets(targetPlainText);
    }

    /**
     * Append as curly brackets
     *
     * @return {targetPlainText}
     */
    public SBB curlyBrackets(Object targetPlainText) {
        return this.charAppend('{').append(targetPlainText).charAppend('}');
    }

    /**
     * Short synonym of curlyBrackets
     */
    public SBB cb(Object targetPlainText) {
        return curlyBrackets(targetPlainText);
    }

    /**
     * Append as parentheses
     *
     * @return (targetPlainText)
     */
    public SBB parentheses(Object targetPlainText) {
        return this.charAppend('(').append(targetPlainText).charAppend(')');
    }

    /**
     * Short synonym of parentheses
     */
    public SBB p(Object targetPlainText) {
        return parentheses(targetPlainText);
    }

    /**
     * Append as angle brackets
     *
     * @return &lt;targetPlainText&gt;
     */
    public SBB angleBrackets(Object targetPlainText) {
        return this.charAppend('<').append(targetPlainText).charAppend('>');
    }


    /**
     * Short synonym of angleBrackets
     */
    public SBB ab(Object targetPlainText) {
        return angleBrackets(targetPlainText);
    }

    public String build() {
        String result = this.strBuilder.toString();
        this.strBuilder.setLength(0);
        return result;
    }

    /**
     * Short synonym of build
     */
    public String bld() {
        return build();
    }

    @Override
    public String toString() {
        return build();
    }

}
