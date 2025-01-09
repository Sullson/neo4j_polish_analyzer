package com.example.neo4j.polish;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.pl.PolishAnalyzer;
import org.neo4j.graphdb.schema.AnalyzerProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class PolishAnalyzerProvider extends AnalyzerProvider {

    public PolishAnalyzerProvider() {
        super("polish");
    }

    @Override
    public Analyzer createAnalyzer() {
        InputStream stopStream = getClass().getResourceAsStream("/polish.stop");
        if (stopStream == null) {
            return new PolishAnalyzer();
        }

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(stopStream, StandardCharsets.UTF_8)
        );

        List<String> stopWordsList = reader.lines()
            .filter(line -> !line.startsWith("#"))
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .collect(Collectors.toList());

        CharArraySet stopWords = new CharArraySet(stopWordsList, true);
        return new PolishAnalyzer(stopWords);
    }

    @Override
    public String description() {
        return "Polish analyzer using Lucene's built-in PolishAnalyzer with optional custom stopwords.";
    }
}
