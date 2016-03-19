package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

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

        List<String> lista = new ArrayList<String>();

        for (int i = 0; i < numberOfResults; i++) {

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
                        }
                        else if (isQuatifier(stringAt(regEx, bracketEndPosition + 1))) {
                            move = 2;
                           } else {
                            move = 1;
                        }
                        lista.add(regEx.substring(position, bracketEndPosition + move));
                        position = bracketEndPosition + move;
                    }

                } else if (isBar (character)){
                    if (position == lastPosition) {
                        move = 1;
                    }
                    else if (isQuatifier(stringAt(regEx, position + 2))) {
                        move = 3;
                    } else {
                        move = 2;
                    }
                    lista.add(regEx.substring(position, position + move));
                    position = position + move;

                } else if (isDot (character)){
                    move = getMovementDotAndLiteral (regEx, position);
                    lista.add(regEx.substring(position, position + move));
                    position = position + move;
                    getMaxLength(); //

                } else {
                    //Es un literal
                    move = getMovementDotAndLiteral (regEx, position);
                    lista.add(regEx.substring(position, position + move));
                    position = position + move;
                }

            }

        }
        return lista;
    }

    //Calcula el desplazamiento hasta el siguiente token para . y literales
    public int getMovementDotAndLiteral (String regEx, int position){
        if (position == (regEx.length() - 1)) {
            return 1;
        }
        else if (isQuatifier(stringAt(regEx, position + 1))) {
            return 2;
        } else {
            return 1;
        }
    }

    public boolean isBracketEnd (String character){
        return character.equals ("]");
    }

    public boolean isBracketBegining (String character){
        return character.equals ("[");
    }

    public boolean isBar (String character){
        return character.equals ("/");
    }

    public boolean isDot (String character){
        return character.equals (".");
    }

    public boolean isQuatifier(String character){
        return character.equals ("+") || character.equals ("?") || character.equals ("*");
    }

    public String stringAt (String regEx, int position){
        return String.valueOf(regEx.charAt(position));
    }

    public int getMaxLength () {
        return this.maxLength;
    }

}