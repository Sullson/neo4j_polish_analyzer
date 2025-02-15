## neo4j_polish_analyzer

1. **Copy the JARs:**
    
    In your `$NEO4J_HOME/plugins` folder, place:
    
    - Your plugin JAR (`neo4j-polish-analyzer-1.0.jar`)
    - Extra dependencies from target/lib: `lucene-analyzers-morfologik-8.11.0.jar`, `morfologik-stemming-2.1.5.jar`, `morfologik-polish-2.1.5.jar`, `morfologik-fsa-2.1.5.jar`.

2. **Restart Neo4j:**
    
    Restart the database. Now, Neo4j should load its built-in Lucene libraries plus your extra jar and your plugin.
    
3. **Verify the Analyzer Registration:**
    
    Run in Cypher:
    
    ```
    CALL db.index.fulltext.listAvailableAnalyzers();
    
    ```
    You should see `"polish-custom"` in the list.