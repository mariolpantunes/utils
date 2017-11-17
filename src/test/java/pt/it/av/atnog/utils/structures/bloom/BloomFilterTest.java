package pt.it.av.atnog.utils.structures.bloom;

import org.junit.Test;
import pt.it.av.atnog.utils.ArrayUtils;
import pt.it.av.atnog.utils.HashUtils;

import static org.junit.Assert.assertTrue;

public class BloomFilterTest {

  @Test
  public void test_bloom() {
    String set[] = {"Rincewind", "Death", "TwoFlower", "Lord Vetinari", "Cohen the Barbarian",
        "Ysabell", "Conina"},
        test[] = {"Rincewind", "Death", "TwoFlower", "Lord Vetinari", "Cohen the Barbarian",
            "Ysabell", "Conina", "Homer", "Bart", "Lisa", "Bender", "Fry", "Leela"};

    BloomFilter<String> bf = new BloomFilter<>(33, 3, (String s) -> {
      return HashUtils.fnv1a(s.getBytes());
    });

    // Insert all the elements of the set
    for (int i = 0; i < set.length; i++) {
      bf.add(set[i]);
    }

    // Verify all elements in test
    for (int i = 0; i < test.length; i++) {
      if (ArrayUtils.contains(set, test[i])) {
        assertTrue(bf.contains(test[i]) == true);
      } else {
        assertTrue(bf.contains(test[i]) == false);
      }
    }
  }
}
