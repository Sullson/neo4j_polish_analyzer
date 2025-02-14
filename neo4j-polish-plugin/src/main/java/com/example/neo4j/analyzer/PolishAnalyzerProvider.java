package com.example.neo4j.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.neo4j.annotations.service.ServiceProvider;
import org.neo4j.graphdb.schema.AnalyzerProvider;

@ServiceProvider
public class PolishAnalyzerProvider extends AnalyzerProvider {

    public PolishAnalyzerProvider() {
        // "polish-custom" is the name you'll use in your Cypher index configuration.
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
