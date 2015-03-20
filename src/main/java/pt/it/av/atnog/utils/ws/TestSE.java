package pt.it.av.atnog.utils.ws;


import pt.it.av.atnog.utils.Utils;

public class TestSE {
    private final static String FAROO_KEY = "XWyjTp6De1S0axhVD14voLV5vjk_";

    public static void main(String[] args) {
        SearchEngine s = new Wikipedia();
        Utils.printList(s.search("humidity|temperature|ice cream|banana|chocolate|stack overflow|atnog"));
    }
}
