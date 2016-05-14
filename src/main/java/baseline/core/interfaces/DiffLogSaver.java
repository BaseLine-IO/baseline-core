package baseline.core.interfaces;

import baseline.newdiff.DiffLog;

/**
 * Created by Home on 09.11.15.
 */
public interface DiffLogSaver {

    public DiffLogSaver setLog(DiffLog log);
    public DiffLogSaver configure(String filename,
                                  String delim,
                                  String encoding) throws Exception;
    public void generate() throws Exception;
}
