# Axiom
A simple library for serializing and deserializing java objects

All information avaliable in wiki - axiom.arial.su

# Gradle configuration

Repository (Add it in your root build.gradle at the end of repositories):
````groovy
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
````

Dependency:
````groovy
implementation 'com.github.AxieFeat:Axiom:1.0'
````

# Maven configuration

Repository:
````xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
````

Dependency:
````xml
<dependency>
    <groupId>com.github.AxieFeat</groupId>
    <artifactId>Axiom</artifactId>
    <version>1.0</version>
</dependency>
````
