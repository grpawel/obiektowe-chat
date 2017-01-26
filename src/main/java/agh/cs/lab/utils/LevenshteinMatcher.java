package agh.cs.lab.utils;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class LevenshteinMatcher {
    public Boolean matches(final String string1, final String string2, final Integer maxDistance) {
        final LevenshteinDistance matcher = new LevenshteinDistance(maxDistance + 1);
        final Integer distance = matcher.apply(string1, string2);
        return distance <= maxDistance && distance != -1;
    }
}
