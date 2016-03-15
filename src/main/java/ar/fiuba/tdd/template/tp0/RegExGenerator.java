package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RegExGenerator {

    //private int maxLength;

    public RegExGenerator(/*int maxLength*/) {
        //this.maxLength = maxLength;
    }

    public List<String> generate(String regEx, int numberOfResults) {
        return new ArrayList<String>() {
            {
                for (int i=0; i< numberOfResults ; i++) {
                    StringTokenizer multiTokenizer = new StringTokenizer(regEx, "?*+");
                    while (multiTokenizer.hasMoreTokens()) {
                        add(multiTokenizer.nextToken());
                    }
                }
            }
        };
    }
}