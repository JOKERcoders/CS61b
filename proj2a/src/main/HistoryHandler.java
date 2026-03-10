package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {

    private final NGramMap map;

    /** 构造函数，接收 NGramMap 对象 */
    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    /**
     * 当用户点击 History 按钮时调用
     * @param nq NgordnetQuery 对象，包含单词列表和年份范围
     * @return base64 编码的图像字符串
     */
    @Override
    public String handle(NgordnetQuery nq) {
        List<String> words = nq.words();  // 获取用户输入的单词列表
        int startYear = nq.startYear();
        int endYear = nq.endYear();

        List<TimeSeries> seriesList = new ArrayList<>();

        // 遍历单词列表，获取每个单词的历史数据
        for (String word : words) {
            TimeSeries ts = map.countHistory(word, startYear, endYear);
            seriesList.add(ts);
        }

        // 使用 Plotter 生成 XYChart
        XYChart chart = Plotter.generateTimeSeriesChart(words, seriesList);

        // 将图表转换为 base64 字符串返回
        return Plotter.encodeChartAsString(chart);
    }
}
