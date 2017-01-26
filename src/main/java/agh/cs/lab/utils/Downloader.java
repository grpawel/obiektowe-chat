package agh.cs.lab.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Paweł Grochola on 05.01.2017.
 */
public class Downloader {
    private static final Logger LOGGER = Logger.getLogger(Downloader.class.getName());

    public String downloadUrl(final String url) {
        LOGGER.log(Level.FINE, "Downloading from {0}", url);
        String downloaded = null;
        try {
            downloaded = IOUtils.toString(new URL(url), UTF_8);
            LOGGER.log(Level.FINE, "Finished downloading from {0}", url);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not access {0}: \"{1}\"", new Object[]{url, e.getMessage()});
        }
        return downloaded;
    }
}
