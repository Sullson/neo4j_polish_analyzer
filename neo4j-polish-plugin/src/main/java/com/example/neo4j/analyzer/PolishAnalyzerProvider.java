package com.example.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.neo4j.annotations.service.ServiceProvider;
import org.neo4j.graphdb.schema.AnalyzerProvider;

/**
 * Registers the custom Polish analyzer with Neo4j under the name "polish-custom".
 * Ensure the class is discoverable (either via @ServiceProvider or META-INF/services).
 */
@ServiceProvider
public class PolishAnalyzerProvider extends AnalyzerProvider {

    public PolishAnalyzerProvider() {
        // This name is what you'll use in your Cypher index config: { analyzer: "polish-custom" }
        super("polish-custom");
    }

    @Override
    public Analyzer createAnalyzer() {
        // Return a new instance of our Morfologik-based Polish analyzer
        return new CustomPolishMorfologikAnalyzer();
    }

    @Override
    public String description() {
        return "Morfologik-based Polish analyzer with stopword removal and lowercasing.";
    }
}
