package pt.it.av.tnav.utils.structures.cache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
        try {
          BufferedReader in = null;
          in = new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(file)), "UTF-8"));
          try {
            rv = load(in);
          }  finally {
            in.close();
          }
        } catch (IOException ex) {
          System.err.println("Problem occured : " + ex.getMessage());
          rv = null;
          try {Files.delete(file);} catch(IOException e) {
            System.err.println("Problem occured : " + e.getMessage());
          }
        }
      }

      if (rv != null) {
        rv = buid(key);
        try {
          BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(Files.newOutputStream(file)), "UTF-8"));
          try{store(rv, out);}finally{out.close();}
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
  protected abstract T buid(K key);

  /**
   *
   * @param o
   * @param out
   */
  protected abstract void store(T o, Writer out) throws IOException;
}
