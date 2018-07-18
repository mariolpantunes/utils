package pt.it.av.tnav.utils.ws.thesaurus;

import java.util.List;

/**
 * Created by mantunes on 5/6/15.
 */
public interface Thesaurus {
    List<String> synonyms(String s);

    List<String> antonyms(String s);
}
