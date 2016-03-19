package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    public static final int MAX_LENGTH = 5;

    private boolean validate(String regEx, int numberOfResults) throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);

        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                        (acc, item) -> {
                            Matcher matcher = pattern.matcher(item);
                            return acc && matcher.find();
                        },
                        (item1, item2) -> item1 && item2);
    }

    @Test(expected = Exception.class)
    public void testGenerateEmpty() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        generator.generate("", 1);
    }

    @Test(expected = Exception.class)
    public void testGenerateOnlyQuatifiers() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        generator.generate("***", 1);
    }

    @Test(expected = Exception.class)
    public void testGenerateNoEndBracket() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        generator.generate("a.[c", 1);
    }

    @Test(expected = Exception.class)
    public void testGenerateWrongBackSlash() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        generator.generate("a.\\", 1);
    }

    @Test
    public void testAnyCharacter() throws Exception {
        assertTrue(validate(".", 2));
    }

    @Test
    public void testMultipleCharacters() throws Exception {
        assertTrue(validate("...", 2));
    }

    @Test
    public void testCharacterSet() throws Exception {
        assertTrue(validate("[abc]", 2));
    }

    @Test
    public void testCharacterSetWithQuantifiers() throws Exception {
        assertTrue(validate("[abc]+", 2));
    }

    @Test
    public void testLiteral() throws Exception{
        assertTrue(validate("\\[", 2));
    }
    @Test
    public void testLiteralDotCharacter() throws Exception{
        assertTrue(validate("\\[..", 2));
    }
    @Test
    public void testZeroOrOneCharacter() throws Exception{
        assertTrue(validate("\\[.h?", 2));
    }

    @Test
    public void testGenerateExample() throws Exception {
        assertTrue(validate("...+[ab]*d?c", 5));
    }

    @Test
    public void testGenerateBracketAtTheEnd() throws Exception {
        assertTrue(validate("a.*[t]", 5));
    }

    @Test
    public void testGenerateComplete() throws Exception {
        assertTrue(validate("...a?[hola]+.*[t].", 2));
    }

}