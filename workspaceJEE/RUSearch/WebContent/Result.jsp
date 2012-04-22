<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>Refugees united</TITLE>
<meta charset="utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="countries2.js"></script>
</HEAD>

<BODY>
	<FORM action="/RU/Search" method="post">
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
								<td><LABEL>Email</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Town</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Country</LABEL></td>
								<td>&nbsp;</td>
								<td><LABEL>Sex</LABEL></td>
								<td>&nbsp;</td>
							</tr>
							<c:forEach var="row" items="${users}">
								<tr>
									<td><c:out value="${row.fname}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><c:out value="${row.sname}" escapeXml="false" /></td>

									<td>&nbsp;</td>
									<td><c:out value="${row.email}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><c:out value="${row.town}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><c:out value="${row.country}" escapeXml="false" /></td>
									<!-- 								<td>&nbsp;</td> -->
									<%-- 								<td><c:out value="${row.phone}" escapeXml="false" /></td> --%>
									<td>&nbsp;</td>
									<td><c:out value="${row.sex}" escapeXml="false" /></td>
									<td>&nbsp;</td>
									<td><img src="${row.url}" /></td>
								</tr>

							</c:forEach>
							<tr>
								<td colspan="3"><a href="/RU/Profile">Back to Search</a></td>
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