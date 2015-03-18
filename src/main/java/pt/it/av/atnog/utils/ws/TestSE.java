package pt.it.av.atnog.utils.ws;


import pt.it.av.atnog.utils.Utils;

public class TestSE {
    private final static String FAROO_KEY = "XWyjTp6De1S0axhVD14voLV5vjk_";

    public static void main(String[] args) {
        Faroo f = new Faroo(FAROO_KEY);
        Utils.printList(f.search("humidity"));
    }
}
