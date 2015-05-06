package pt.it.av.atnog.utils.ws;

import java.util.List;

/**
 * Created by mantunes on 5/6/15.
 */
public class BigHugeThesaurus implements Thesaurus {
    private final String key;

    public BigHugeThesaurus(final String key) {
        this.key = key;
    }

    @Override
    public List<String> synonyms(String s) {
        return null;
    }
}
