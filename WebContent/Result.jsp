<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>Refugees united</TITLE>
<meta charset="utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="countries2.js"></script>
</HEAD>

<BODY>
	<FORM action="/Search" method="post">
		<div>
			<label class="htmltitle">Refugees United</label>
		</div>
		<hr />
		<div id="wrapper">
			<div id="tabContainer">
				<div class="tabs">
					<ul>
						<li id="tabHeader_1">Matches</li>
					</ul>
				</div>
				<div class="tabscontent">
					<div class="tabpage" id="tabpage_1">
						<table>
							<tr>
								<td><LABEL>First name</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Surname</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Age</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Message</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Send</LABEL></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="10"><span id="error" style="color:red;">${error}</span></td>
							</tr>
							<c:forEach var="row" items="${users}" varStatus="status">
								<tr>
									<td><c:out value="${row.fname}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><c:out value="${row.sname}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><c:out value="${row.age}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><textarea rows="4" cols="30" name="message${status.index}">
									</textarea><input type="hidden" name="username${status.index}" value="${row.username}"></td>
									<td colspan="3"><INPUT class="classname" type="submit"
										name="Send"></td>
									<td>&nbsp;</td>
								</tr>

							</c:forEach>
							<tr>
								<td colspan="3"><a href="/Profile">Back to Search</a></td>
							</tr>
						</table>

					</div>
				</div>
			</div>
		</div>
	</FORM>
	<script src="tabs.js"></script>

</BODY>
</HTML>