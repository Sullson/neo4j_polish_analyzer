# 🇵🇱 Neo4j Polish Morfologik Analyzer

A custom **Morfologik-based Polish Analyzer** for Neo4j, providing:

✔️ **Lowercasing**

✔️ **Polish stopword removal**

✔️ **Morphological analysis**

---

## 📂 Directory Overview

```
neo4j-polish-plugin/
  ├─ src/
  │   ├─ main/
  │   │   ├─ java/com/example/neo4j/analyzer/
  │   │   │   ├─ CustomPolishMorfologikAnalyzer.java
  │   │   │   └─ PolishAnalyzerProvider.java
  │   │   └─ resources/META-INF/services/org.neo4j.graphdb.schema.AnalyzerProvider
  ├─ pom.xml
  ├─ .gitattributes
  ├─ LICENSE
  └─ README.md

```

---

## ⚡ Installation

### 1️⃣ Copy Required JARs

Move the following files to your **Neo4j plugins folder** (`$NEO4J_HOME/plugins`):

### **🔹 Main Plugin JAR**

- `neo4j-polish-plugin/target/neo4j-polish-analyzer-1.0.jar`

### **🔹 Required Dependencies** (from `target/lib`):

- `neo4j-polish-plugin/target/lib/lucene-analyzers-morfologik-8.11.0.jar`
- `neo4j-polish-plugin/target/lib/morfologik-stemming-2.1.5.jar`
- `neo4j-polish-plugin/target/lib/morfologik-polish-2.1.5.jar`
- `neo4j-polish-plugin/target/lib/morfologik-fsa-2.1.5.jar`

### 2️⃣ Restart Neo4j

Restart the Neo4j database so it loads the plugin and dependencies.

---

## 🔍 Usage

### ✅ Verify Analyzer Registration

Run the following **Cypher command** in Neo4j Browser or Cypher Shell:

```sql
CALL db.index.fulltext.listAvailableAnalyzers();
```

You should see `"polish-custom"` in the list.

### ✅ Create a Fulltext Index Using the Analyzer

```sql
CREATE FULLTEXT INDEX idx_polish FOR (n:YourLabel) ON (n.yourProperty)
OPTIONS { analyzer: 'polish-custom' };
```

---

## 📜 License

This project is licensed under the [**MIT License**](https://github.com/Sullson/neo4j_polish_analyzer/blob/main/LICENSE).

🚀 **Enjoy Polish text analysis in Neo4j!**

Made for [**Sejmofil**](https://sejmofil.pl).