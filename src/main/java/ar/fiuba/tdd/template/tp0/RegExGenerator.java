package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {

    private int maxLength;
    private static final String REGEX_EMPTY = "La expresion regular esta vacia.";
    private static final String REGEX_QUANTIFIER = "La ubicacion del cuantificador es incorrecta.";
    private static final String REGEX_BRACKET_END = "La ubicacion de ] es incorrecta.";
    private static final String REGEX_NO_BRACKET_END = "No se encontro ].";

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<String> generate(String regEx, int numberOfResults) throws Exception {

        List<String> listaResultados = new ArrayList<String>();

        for (int i = 0; i < numberOfResults; i++) {
            String generatedExpression = "";
            if (regEx.length() == 0) throw new Exception(REGEX_EMPTY);

            int position = 0;
            int move = 0;
            int lastPosition = regEx.length() - 1;

            while (position <= lastPosition) {

                String character = stringAt(regEx, position);
                if (isBracketEnd(character)) throw new Exception(REGEX_BRACKET_END);
                if (isQuatifier(character)) throw new Exception(REGEX_QUANTIFIER);

                if (isBracketBegining(character)) {
                    int bracketEndPosition = regEx.indexOf("]", position);
                    if (bracketEndPosition == -1) {
                        throw new Exception(REGEX_NO_BRACKET_END);
                    } else {
                        if (bracketEndPosition == lastPosition) {
                            move = 1;
                        } else if (isQuatifier(stringAt(regEx, bracketEndPosition + 1))) {
                            move = 2;
                        } else {
                            move = 1;
                        }
                        String expression = regEx.substring(position, bracketEndPosition + move);
                        generatedExpression = generatedExpression.concat(generateSequence(expression, generateAlphabetBracket(expression)));
                        position = bracketEndPosition + move;
                    }

                } else if (isBar(character)) {
                    if (position == lastPosition) {
                        move = 1;
                    } else if (isQuatifier(stringAt(regEx, position + 2))) {
                        move = 3;
                    } else {
                        move = 2;
                    }
                    String expression = regEx.substring(position, position + move);
                    generatedExpression = generatedExpression.concat(generateSequence(expression, generateAlphabetBar(expression)));
                    position = position + move;

                } else if (isDot(character)) {
                    move = getMovementDotAndLiteral(regEx, position);
                    String expression = regEx.substring(position, position + move);
                    generatedExpression = generatedExpression.concat(generateSequence(expression, generateAlphabetDot()));
                    position = position + move;

                } else {
                    //Es un literal
                    move = getMovementDotAndLiteral(regEx, position);
                    String expression = regEx.substring(position, position + move);
                    generatedExpression = generatedExpression.concat(generateSequence(expression, generateAlphabetLiteral(expression)));
                    position = position + move;
                }

            }
            listaResultados.add(generatedExpression);
        }
        return listaResultados;
    }

    //Calcula el desplazamiento hasta el siguiente token para . y literales
    public int getMovementDotAndLiteral(String regEx, int position) {
        if (position == (regEx.length() - 1)) {
            return 1;
        } else if (isQuatifier(stringAt(regEx, position + 1))) {
            return 2;
        } else {
            return 1;
        }
    }

    public boolean isBracketEnd(String character) {
        return character.equals("]");
    }

    public boolean isBracketBegining(String character) {
        return character.equals("[");
    }

    public boolean isBar(String character) {
        return character.equals("\\");
    }

    public boolean isDot(String character) {
        return character.equals(".");
    }

    public boolean isQuatifier(String character) {
        return character.equals("+") || character.equals("?") || character.equals("*");
    }

    public String stringAt(String regEx, int position) {
        return String.valueOf(regEx.charAt(position));
    }

    public String generateSequence(String expression, String alphabet) throws Exception{
        String sequence = "";
        Random random = new Random();
        int iterations = 1;
        String lastCharacter = stringAt(expression, expression.length() - 1 );
        if (isQuatifier(lastCharacter)) {
            iterations = getIterationsForQuantifier(lastCharacter);
        }
        for (int i = 0; i < iterations; i++) {
            sequence = sequence.concat(String.valueOf(alphabet.charAt(random.nextInt(alphabet.length()))));
        }
        return sequence;
    }

    //Ejemplos: [ABC] o [ABC]+
    public String generateAlphabetBracket (String expression){
        String alphabet = "";
        String lastCharacter = stringAt(expression, expression.length() - 1 );
        if (isQuatifier(lastCharacter)){
            alphabet = expression.substring(1, expression.length() - 2);
        } else {
            alphabet = expression.substring(1, expression.length() - 1);
        }
        return alphabet;
    }

    //Ejemplos: /[ o /[+
    public String generateAlphabetBar(String expression){
        String alphabet = "";
        if (expression.length() > 2){
            alphabet = expression.substring(1, expression.length() - 1);
        } else {
            alphabet = expression.substring(1, expression.length());
        }
        return alphabet;
    }

    //Ejemplos: A o  A+
    public String generateAlphabetLiteral(String expression){
        String alphabet = "";
        String lastCharacter = stringAt(expression, expression.length() - 1 );
        if (isQuatifier(lastCharacter)){
            alphabet = expression.substring(0, expression.length() - 1);
        } else {
            alphabet = expression.substring(0, expression.length());
        }
        return alphabet;
    }

    //Hipotesis: supongo que el "." puede tomar cualquier valor de la "A" a la "Z" y numeros
    public String generateAlphabetDot(){
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    }

    public int getIterationsForQuantifier (String character) throws Exception {
        Random random = new Random();
        if (character.equals("+")){
            return random.nextInt(maxLength - 1 + 1) + 1;
        } else if (character.equals("?") ){
            return random.nextInt(1 - 0 + 1) + 0;
        } else if (character.equals("*")) {
            return random.nextInt(maxLength - 0 + 1) + 0;
        } else {
            throw new Exception(REGEX_QUANTIFIER);
        }
    }
}
