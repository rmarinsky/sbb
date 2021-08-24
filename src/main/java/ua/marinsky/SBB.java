package ua.marinsky;

/**
 * String Builder Builder
 */
public class SBB {

    private final StringBuilder strBuilder = new StringBuilder();

    public static SBB sbb() {
        return new SBB();
    }

    public static SBB sbb(Object basePlainText) {
        return sbb().append(basePlainText);
    }

    private SBB addToBuild(Object targetPlainText) {
        this.strBuilder.append(targetPlainText);
        return this;
    }

    /**
     * Append char
     */
    private SBB append(char targetChar) {
        this.strBuilder.append(targetChar);
        return this;
    }

    /**
     * Append plain text
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
        return this.append('\n');
    }

    /**
     * Add a tabulation
     */
    public SBB t() {
        return this.append('\t');
    }

    /**
     * Add new whitespace
     */
    public SBB w() {
        return this.append(' ');
    }

    /**
     * Append as single quoted value
     *
     * @return 'targetPlainText'
     */
    public SBB sQuote(Object targetPlainText) {
        return this.append('\'').append(targetPlainText).append('\'');
    }

    /**
     * Append as double quoted value
     *
     * @return "targetPlainText"
     */
    public SBB dQuote(Object targetPlainText) {
        return this.append('\"').append(targetPlainText).append('\"');
    }

    public String build() {
        return this.strBuilder.toString();
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
