package com.example.neo4j.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.morfologik.MorfologikFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class CustomPolishMorfologikAnalyzer extends StopwordAnalyzerBase {

    private static final CharArraySet POLISH_STOP_WORDS;
    
    static {
        CharArraySet tempSet;
        // Load polish.stop from the classpath using the class loader
        InputStream stopStream = CustomPolishMorfologikAnalyzer.class.getClassLoader().getResourceAsStream("polish.stop");
        if (stopStream != null) {
            System.out.println("polish.stop file found, loading stopwords...");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stopStream, StandardCharsets.UTF_8))) {
                List<String> stopwords = reader.lines()
                                               .map(String::trim)
                                               .filter(line -> !line.isEmpty())
                                               .collect(Collectors.toList());
                tempSet = new CharArraySet(stopwords, true);
                System.out.println("Successfully loaded " + tempSet.size() + " stopwords from polish.stop.");
            } catch (IOException e) {
                throw new RuntimeException("Failed to load polish.stop file", e);
            }
        } else {
            System.err.println("polish.stop not found in classpath! Using default stopwords.");
            tempSet = new CharArraySet(List.of("a", "i", "oraz", "ale", "o", "w", "z", "na", "po", "za"), true);
            System.out.println("Using default stopwords (" + tempSet.size() + " words).");
        }
        POLISH_STOP_WORDS = tempSet;
    }

    public CustomPolishMorfologikAnalyzer() {
        // Pass the loaded stopwords to the superclass
        super(POLISH_STOP_WORDS);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        // Build the token stream chain: StandardTokenizer -> LowerCaseFilter -> StopFilter -> MorfologikFilter
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new LowerCaseFilter(source);
        result = new StopFilter(result, this.stopwords);
        result = new MorfologikFilter(result);
        return new TokenStreamComponents(source, result);
    }
}
