package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery nq) {
        StringBuilder sb = new StringBuilder();
        List<String> words = nq.words();  // 获取用户输入的单词列表
        int startYear = nq.startYear();
        int endYear = nq.endYear();

        for (String word : words) {
            TimeSeries ts = map.countHistory(word, startYear, endYear); // 获取单词历史数据
            sb.append(word).append(": ").append(ts.toString()).append("\n");
        }

        return sb.toString();
    }
}
