# Java Based Web Indexer

## Purpose
This is supposed to be a java based web indexer that works with parallelization and a queue of to be scraped pages and a database of existing pages. There is no search engine logic within this codebase, this exists solely for the purposes of indexing websites and storing them but certain fields are computed to make implementing said logic easier. For example page rank is computed and updated while the program is running and llm based vector embeddings are also generated.

## Running
I created a custom copiler program for this project because it felt easier than learning Maven lol 😅. There is a `compile.bat` batch file that compiles the project into a build folder before running it and a `run.bat` that runs whatever the current build is. The file will compile the `Main.java` entry point to the project, as well as all the Classes inside the `utils` folder, copying it's directory structure. All jar files within the `modules` folder will be included in the class path.