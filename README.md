## neo4j_polish_analyzer

1. **Copy the JARs:**
    
    In your `$NEO4J_HOME/plugins` folder, place:
    
    - Your plugin JAR (`neo4j-polish-analyzer-1.0.jar`)
    - The extra dependency: `lucene-analyzers-morfologik-8.11.0.jar`(You can download this from the [Maven repository](https://repo1.maven.org/maven2/org/apache/lucene/lucene-analyzers-morfologik/8.11.0/)).
2. **Restart Neo4j:**
    
    Restart the database. Now, Neo4j should load its built-in Lucene libraries plus your extra jar and your plugin.
    
3. **Verify the Analyzer Registration:**
    
    Run in Cypher:
    
    ```
    CALL db.index.fulltext.listAvailableAnalyzers();
    
    ```
    You should see `"polish-custom"` in the list.