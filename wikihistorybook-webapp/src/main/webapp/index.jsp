<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"
	import="java.util.ResourceBundle,ch.fhnw.business.iwi.wikihistorybook.WikiBookContainer"%>
<%
	ResourceBundle resource = ResourceBundle
			.getBundle("wikihistorybook-webapp");
%>

<html>
<body>
	<p>
		Probably you'll need to explicitly edit your local java security
		policy to run the applet. Find the <i>Java Control Panel</i> on your
		local machine and add the URL to the <i>Security Exception Site
			List</i>
	</p>
	<object type="application/x-java-applet" height="600" width="1000">
		<param name="codebase"
			value="/<%=resource.getString("webapp.url.base")%>/applet" />
		<param name="code"
			value="<%=WikiBookContainer.class.getName()%>.class" />
		<param name="archive"
			value="wikihistorybook-applet-<%=resource.getString("webapp.version")%>-jar-with-dependencies.jar" />
		Applet failed to run. No Java plug-in was found.
	</object>
</body>
</html>
