## Annotation Processor
Follow instructions below for installing the annotation processor in Eclipse IDE.

0. Download the jar file.

1. Select a project that you will be installing the annotation processor for (PROJECT\_NAME-pojo-bl).

2. Select the project in the Package Explorer and open the *Properties* menu.

3. Go to *Java Compiler* > *Annotation Processing*. Enable annotation processing and enter the names for generated sources directories (with respect to the base directory of the project)

![annotation-processing](project-properties.png)

4. Go to *Factory Path*. Add the downloaded jar by clicking *Add External JARs* and selecting it.

![factory-path](factory-path.png)

5. Add `target/generated-sources` directory to the build path of the project. This can be done by right-clicking on the directory in the Package Explorer in Eclipse and selecting *Build Path* > *Use as Source Folder*.

6. Lastly, you need to include the jar either as a *maven depenendecy* or as a *library dependency in Eclipse*.

    a. **maven dependency**
    1. Install the jar into your local maven repository.
    ```bash
    mvn install:install-file -Dfile=platform-annotation-processor-1.4.6-SNAPSHOT-jar-with-dependencies.jar -DgroupId=fielden -DartifactId=platform-annotation-processor -Dversion=1.4.6-SNAPSHOT -Dpackaging=jar
    ```
    
    2. Modify the `pom.xml` file of your project by including the following:
    ```
    <dependency>
        <groupId>fielden</groupId>
        <artifactId>platform-annotation-processor</artifactId>
        <version>1.4.6-SNAPSHOT</version>
    </dependency>
    ```

    ```
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>16</source>
                    <target>16</target>
                    <encoding>UTF-8</encoding>
                    <generatedSourcesDirectory>${project.build.directory}/generated-sources/</generatedSourcesDirectory>
                    <annotationProcessors>
                        <annotationProcessor>
                            ua.com.fielden.platform.processors.meta_model.MetaModelProcessor
                        </annotationProcessor>
                    </annotationProcessors>
                </configuration>
            </plugin>
        </plugins>
    </build>
    ```

    b. **Eclipse library dependency**

    Select your project in the *Package Explorer* and open *Properties* menu. Then go to *Java Build Path* > *Libraries* and select *Add External JARs*.

    ![libraries](libraries.png)
