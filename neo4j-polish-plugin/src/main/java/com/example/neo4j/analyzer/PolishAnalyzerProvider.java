package com.example.neo4j.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.neo4j.annotations.service.ServiceProvider;
import org.neo4j.graphdb.schema.AnalyzerProvider;

@ServiceProvider
public class PolishAnalyzerProvider extends AnalyzerProvider {

    public PolishAnalyzerProvider() {
        // This is the identifier you'll use when configuring your full-text index.
        super("polish-custom");
    }

    @Override
    public Analyzer createAnalyzer() {
        return new CustomPolishMorfologikAnalyzer();
    }

    @Override
    public String description() {
        return "Morfologik-based Polish Analyzer with stopword removal and lowercasing.";
    }
}
