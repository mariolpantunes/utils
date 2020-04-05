package pt.it.av.tnav.utils.structures.cache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Mário Antunes
 * @version 1.0
 */
public abstract class AbstractPCache<K, T> implements Cache<K, T> {
  private final Path pCache;

  public AbstractPCache(final Path pCache) {
    this.pCache = pCache;
    File directory = pCache.toFile();
    if (!directory.exists()) {
      directory.mkdir();
    }
  }

  @Override
  public T fetch(K key) {
    T rv = null;
    Path file = pCache.resolve(key + ".gz");

    synchronized (key.toString().intern()) {

      if (Files.isReadable(file)) {
        try (BufferedReader in = new BufferedReader(
            new InputStreamReader(new GZIPInputStream(Files.newInputStream(file)), "UTF-8"))) {
          rv = load(in);
        } catch (IOException ex) {
          ex.printStackTrace();
          rv = null;
          try {
            Files.delete(file);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      if (rv == null) {
        rv = build(key);
        try (BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(new GZIPOutputStream(Files.newOutputStream(file)), "UTF-8"))) {
          store(rv, out);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return rv;
  }

  /**
   *
   * @param in
   * @return
   */
  protected abstract T load(Reader in) throws IOException;

  /**
   *
   * @param key
   * @return
   */
  protected abstract T build(K key);

  /**
   *
   * @param o
   * @param out
   */
  protected abstract void store(T o, Writer out) throws IOException;

  @Override
  public void clear() {
    try (Stream<Path> walk = Files.walk(pCache)) {
      walk.map(Path::toFile).forEach(File::delete);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}