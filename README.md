# Interactive Wiki History Book 
* Java prototype of the wiki history book [create by Flühmann et al.][fluehmann]

## Technology Stack

The architecture of this software is structured into the following modules based on [Maven][maven].

### wikihistorybook-graph

Contains the main java implementation of the [student version][fluehmann] without any [swing ui][swing] components.

### wikihistorybook-webapp

The web server component, a java servlet container implemented with [JSF-2][jsf] and [Spring][spring].

[jsf]: https://javaserverfaces.java.net/
[spring]: http://spring.io/

### wikihistorybook-applet

The applet version of the [student implementation][fluehmann] including [swing ui][swing] components.

### wikihistorybook-svg

The implementation of the [SVG][svg] alternative. 

[svg]: http://www.w3.org/Graphics/SVG/
[fluehmann]: https://github.com/fluehmann/wikihistorybook/
[swing]: http://docs.oracle.com/javase/tutorial/uiswing/

## How to use it

The following [maven][maven] command builds the whole application to a [war archive][war]

    mvn clean package

Find the results in the maven target directories. e.g.

* wikihistorybook/wikihistorybook-webapp/target/wikihistorybook.war
* wikihistorybook/wikihistorybook-applet/target/wikihistorybook-applet-0.4.0-jar-with-dependencies.jar
* wikihistorybook/wikihistorybook-\*/target/\*

[war]: http://en.wikipedia.org/wiki/WAR_%28file_format%29

## Useful Links 

### Basics
* [Maven build and dependency management][maven]
* [Component-based user interfaces with JSF-2][jsf]
* [Spring application framework][spring]
* [A simple Swing-based applet][3]

[maven]: http://maven.apache.org/
[jsf]: https://javaserverfaces.java.net/
[spring]: http://spring.io/

### Advanced
* [Java Applets Communicating with JavaScript ][5]
* [Find managed beans in JSF2][11]

### Security issues
* [Why are Java applications blocked by your security settings with the latest Java?][1]
* [user might have accepted a certificate permanently][6]

### Technologies and Libraries
* [GraphStream - A Dynamic Graph Library][7]

### Testing
* [Testing Applets][8]

### Build and Dependency Management
* [How to deploy Applet with dependencies jar using maven and sign it?][2]
* [A Minimal Java Applet built with Maven project][4]

### Prospects
#### HTML5
* [Canvas Tutorial: An Introduction][10]
* [HTML5: JavaScript library for drawing Graphviz graphs to a web browser canvas][9]

[1]: https://www.java.com/en/download/help/java_blocked.xml
[2]: http://stackoverflow.com/questions/2027753/how-to-deploy-applet-with-dependencies-jar-using-maven-and-sign-it
[3]: http://www.java2s.com/Tutorial/Java/0120__Development/AsimpleSwingbasedapplet.htm
[4]: https://github.com/ansoncat/minimal-java-applet-maven
[5]: http://rostislav-matl.blogspot.ch/2011/10/java-applets-building-with-maven.html
[6]: http://blog.gemserk.com/2010/02/07/signing-jars-for-applet-and-webstart/
[7]: http://graphstream-project.org/
[8]: http://docs.codehaus.org/display/FEST/Testing+Applets
[9]: https://code.google.com/p/canviz/
[10]: http://www.sitepoint.com/html5-canvas-tutorial-introduction/
[11]: http://stackoverflow.com/questions/15053996/how-to-get-jsf2-0-sessionmap-reference-from-a-servlet