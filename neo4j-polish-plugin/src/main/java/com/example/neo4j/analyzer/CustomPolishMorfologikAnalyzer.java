package com.example.neo4j.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
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

public class CustomPolishMorfologikAnalyzer extends Analyzer {

    private static final CharArraySet POLISH_STOP_WORDS;
    
    static {
        CharArraySet tempSet;
        // Try to load the stopwords file from the root of the classpath.
        InputStream stopStream = CustomPolishMorfologikAnalyzer.class.getResourceAsStream("/polish.stop");
        if (stopStream != null) {
            System.out.println("polish.stop file found, loading stopwords...");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stopStream, StandardCharsets.UTF_8))) {
                List<String> stopwords = reader.lines().collect(Collectors.toList());
                tempSet = new CharArraySet(stopwords, true);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load polish.stop file", e);
            }
        } else {
            System.err.println("polish.stop not found in classpath! Using default stopwords.");
            tempSet = new CharArraySet(
                    List.of("a", "i", "oraz", "ale", "o", "w", "z", "na", "po", "za"),
                    true);
        }
        POLISH_STOP_WORDS = tempSet;
        System.out.println("Loaded " + POLISH_STOP_WORDS.size() + " stopwords.");
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        // Use StandardTokenizer, then apply filters in sequence.
        Tokenizer source = new StandardTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(source);   // Declare as TokenStream, not using var
        tokenStream = new StopFilter(tokenStream, POLISH_STOP_WORDS);
        tokenStream = new MorfologikFilter(tokenStream);
        return new TokenStreamComponents(source, tokenStream);
    }
}
