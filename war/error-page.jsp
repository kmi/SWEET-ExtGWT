<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <meta name="author" content="KMi, The Open University" />
	    <meta name="description" content="MicroWSMO Editor - the editor for semantic description of RESTfull services" />
	    <meta name="keywords" content="microWSMO editor RESTfull semantic web services descriptions annotations ontologies service operation input output method address" />
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />  
	    <link type="text/css" rel="stylesheet" href="MicroWSMOeditor.css" />
	    <title>MicroWSMO Editor - error page</title>
	</head>
	<body>
		<div>
			<div id="header">MicroWSMO error page</div>
			<div id="owner">
				<%
					String owner = request.getParameter("own");
					if( owner != null ) {
						out.print(owner);
					}
				%>
			</div>
			<div id="message">
				<% 
					String message = request.getParameter("msg");
					if( message != null ) {
						out.print(message);
					}
				%>
			</div>
		</div>
	</body>
</html>