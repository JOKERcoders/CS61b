package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private Map<String, TimeSeries> wordCounts;
    public TimeSeries totalCounts;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordCounts = new HashMap<String, TimeSeries>();
        totalCounts = new TimeSeries();

        In wordsFile = new In(wordsFilename);

        while(wordsFile.hasNextLine()){
            String line = wordsFile.readLine();
            String[] parts = line.split("\\s+");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double count = Double.parseDouble(parts[2]);

            if (!wordCounts.containsKey(word)) {
                wordCounts.put(word, new TimeSeries());
            }

            wordCounts.get(word).put(year, count);
        }

        In countsFile = new In(countsFilename);

        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            String[] parts = line.split(",");

            int year = Integer.parseInt(parts[0]);
            double count = Double.parseDouble(parts[1]);

            totalCounts.put(year, count);
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if(!wordCounts.containsKey(word)) {
            return new TimeSeries();
        }else{
            TimeSeries original = wordCounts.get(word);
            return new TimeSeries(original, startYear, endYear);
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordCounts.containsKey(word)) {
            return new TimeSeries();
        }

        return new TimeSeries(wordCounts.get(word),TimeSeries.MIN_YEAR,
                TimeSeries.MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(
                totalCounts,
                TimeSeries.MIN_YEAR,
                TimeSeries.MAX_YEAR
        );
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordCounts.containsKey(word)) {
            return new TimeSeries(); // 单词不存在，返回空 TimeSeries
        }

        // 1. 获取该单词的 TimeSeries
        TimeSeries ts = wordCounts.get(word);
        TimeSeries newTs = new TimeSeries();

        // 2. 遍历年份，计算相对频率并放入新 TimeSeries
        for (Integer year : ts.years()) {
            if (year >= startYear && year <= endYear && totalCounts.containsKey(year)) {
                newTs.put(year, ts.get(year) / totalCounts.get(year));
            }
        }

        return newTs;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordCounts.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = wordCounts.get(word);
        TimeSeries newTs = new TimeSeries();
        for(Integer year: ts.years()){
            newTs.put(year, ts.get(year)/totalCounts.get(year));
        }
        return newTs;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();

        for (String word : words) {

            TimeSeries ts = weightHistory(word);

            TimeSeries filtered =
                    new TimeSeries(ts, startYear, endYear);

            result = result.plus(filtered);
        }

        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

}
