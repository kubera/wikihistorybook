# Interactive Wiki History Book 

This is a platform for different samples of the web based Wikihistorybook. This application is [based on the work of a student project][fluehmann] from the [University of Applied Sciences Northwestern Switzerland][fhnw].  

Acknowledgment to: Michael Sidler, Simon Fluehmann, Yulia Schmitt; Nicolas Zehnder and Silvio Pirozzi. 

The platform was created by Stefan Wagner, Scientific Assistant, Software Engineer Dipl. Ing. FH.

[fhnw]: http://www.fhnw.ch/homepage?set_language=en

## Technology Stack

The architecture of this software is structured into the following modules based on requires [Maven][maven] version 3.1.0.

### wikihistorybook-graph

Contains the main java implementation of the [student version][fluehmann] without any [swing ui][swing] components.

### wikihistorybook-webapp

The web server component, a java servlet container implemented with [JSF-2][jsf].

[jsf]: https://javaserverfaces.java.net/

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

### Known issues

* There is a [known bug][maven-jarsigner-plugin-bug] under MS-Windows in the [maven-jarsigner-plugin][maven-jarsigner-plugin] which is used for the wikihistorybook applet version. 
* The plugin com.github.klieber:phantomjs-maven-plugin:0.4 requires Maven version 3.1.0

[war]: http://en.wikipedia.org/wiki/WAR_%28file_format%29
[maven-jarsigner-plugin]: http://maven.apache.org/plugins/maven-jarsigner-plugin/
[maven-jarsigner-plugin-bug]: http://maven.apache.org/plugins/maven-jarsigner-plugin/faq.html#sign_and_assembly

## Useful Links 

### Basics
* [GraphStream - A Dynamic Graph Library][7]
* [Maven build and dependency management][maven]
* [Component-based user interfaces with JSF-2][jsf]
* [A simple Swing-based applet][3]
* [Scalable Vector Graphics (SVG)][svg]
* [Simple API for XML][sax]
* [Deployment to Tomcat 7][tomcat7]

[maven]: http://maven.apache.org/
[jsf]: https://javaserverfaces.java.net/
[svg]: http://www.w3.org/TR/SVG11/
[tomcat7]: http://tomcat.apache.org/download-70.cgi
[sax]: http://www.saxproject.org/

### Advanced
* [Java Applets Communicating with JavaScript ][5]
* [SVG zooming with Ariutta's svg-pan-zoom library][14]
* [Find managed beans in JSF2][11]

### Security issues
* [Why are Java applications blocked by your security settings with the latest Java?][1]
* [user might have accepted a certificate permanently][6]

### Testing
* [Testing Applets][8]
* [Testing Javascript with QUnit][12]
* [Javascript: testing without any browser with PhantomJS][13]

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
[12]: http://qunitjs.com/
[13]: http://phantomjs.org/
[14]: https://github.com/ariutta/svg-pan-zoom