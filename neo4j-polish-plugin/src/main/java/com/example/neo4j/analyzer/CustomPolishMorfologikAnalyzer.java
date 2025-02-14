package com.example.analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.morfologik.MorfologikFilter;
import org.apache.lucene.analysis.CharArraySet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class CustomPolishMorfologikAnalyzer extends Analyzer {

    // We'll load Polish stopwords from polish.stop
    private static final CharArraySet POLISH_STOP_WORDS;

    static {
        try {
            // Load the polish.stop file from classpath (src/main/resources)
            InputStream stopStream = CustomPolishMorfologikAnalyzer.class
                    .getResourceAsStream("/polish.stop");
            if (stopStream == null) {
                throw new IOException("polish.stop not found in resources");
            }

            // Read lines
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stopStream, StandardCharsets.UTF_8))) {
                POLISH_STOP_WORDS = new CharArraySet(
                        reader.lines().collect(Collectors.toList()),
                        true // ignoreCase
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Polish stopwords", e);
        }
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        // 1) Tokenizer: StandardTokenizer for general text
        Tokenizer source = new StandardTokenizer();

        // 2) Convert tokens to lowercase
        TokenStream tokenStream = new LowerCaseFilter(source);

        // 3) Remove stopwords
        tokenStream = new StopFilter(tokenStream, POLISH_STOP_WORDS);

        // 4) Apply Morfologik for Polish lemmatization
        tokenStream = new MorfologikFilter(tokenStream);

        return new TokenStreamComponents(source, tokenStream);
    }
}
