package pt.it.av.atnog.utils.ws;


import pt.it.av.atnog.utils.Utils;

public class TestSE {

    public static void main(String[] args) throws Exception {
        SearchEngine s = new Bing("---");
        Utils.printList(s.snippets("humidity"));
    }
}
