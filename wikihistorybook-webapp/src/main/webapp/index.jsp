<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"
	import="java.util.ResourceBundle,ch.fhnw.business.iwi.wikihistorybook.WikiBookContainer"%>
<%
	ResourceBundle res = ResourceBundle
			.getBundle("wikihistorybook-webapp");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Wikihistorybook">
<meta name="author" content="Stefan Wagner">

<title><%=res.getString("webapp.main.title")%></title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="styles/wikihistorybook-custom-style.css" />
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Wiki History Book</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>

	<div class="container">
		<div class="alert alert-warning alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<strong>Warning!</strong> Probably you'll need to explicitly edit
			your local <a
				href="https://www.java.com/en/download/help/java_blocked.xml">java
				security policy</a> to run the applet. Find the <i>Java Control
				Panel</i> on your local machine and add this URL to the <i>Security
				Exception Site List</i>.
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<object type="application/x-java-applet"
					codetype="application/java-vm"
					classid="java:<%=WikiBookContainer.class.getName()%>.class"
					height="600" width="900">
					<param name="code"
						value="<%=WikiBookContainer.class.getName()%>.class" />
					<param name="codebase"
						value="/<%=res.getString("webapp.url.base")%>/" />
					<param name="archive"
						value="<%=res.getString("webapp.applet.jar")%>" />
					Applet failed to run. No Java plug-in was found.
				</object>
			</div>
		</div>
		<p class="text-muted">
			&nbsp;&nbsp;&nbsp;&copy; Wiki History Book 2014 - Version Webapp
			<%=res.getString("webapp.version")%>
			- Version Applet
			<%=res.getString("applet.version")%></p>
	</div>
</body>
</html>
