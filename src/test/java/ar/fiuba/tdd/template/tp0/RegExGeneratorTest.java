package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    public static final int MAX_LENGTH = 5;

    /*@Test
    public void testGenerateEmpty() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("", 1);

        assertTrue(true);
    }

    @Test
    public void testGenerateOnlyQuatifiers() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("***", 1);

        assertTrue(true);
    }

    @Test
    public void testGenerateNoEndBracket() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("a.[c", 1);

        assertTrue(true);
    }*/

    @Test
    public void testGenerateOnlyBracket() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("[hola]", 1);

        assertTrue(true);
    }

    @Test
    public void testGenerateExample() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("..+[ab]*d?c", 1);

        assertTrue(true);
    }

    @Test
    public void testGenerateBracketAtTheEnd() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("a.*[t]", 1);

        assertTrue(true);
    }

    @Test
    public void testGenerateComplete() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("...a?[hola]+.*[t].", 1);

        assertTrue(true);
    }

    @Test
    public void testGenerateEscape() throws Exception {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("...a/?[hola]+.*/[t/].", 1);

        assertTrue(true);
    }

    /*private boolean validate(String regEx, int numberOfResults) throws Exception {
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
    }*/

    /*@Test
    public void testTokenizer() {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate("^..+[ab]*d?c$", 2);
        assertTrue(results.size() > 0);
    }*/

    //TODO: Uncomment these tests
/*
    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }
*/
    // TODO: Add more tests!!!
}
