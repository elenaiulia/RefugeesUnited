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
						<li id="tabHeader_1">Search</li>
						<li id="tabHeader_2">Edit Profile</li>
					</ul>
				</div>
				<div class="tabscontent">
					<div class="tabpage" id="tabpage_1">
						<table>
							<tr>
								<td><LABEL>First name</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="sfname"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Surname</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="ssname"></td>
							</tr>
							<tr>
								<td><LABEL>Nickname</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="snickname"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Email</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="semail"></td>
							</tr>
							<tr>
								<td><LABEL>Town</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="stown"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Country</LABEL></td>
								<td>&nbsp;</td>
								<td><select id="country" name="country"></select> <script>
									print_country("country")
								</script></td>
							</tr>
							<tr>
								<td><LABEL>Telephone number</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="sphone"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td class="classradio"><INPUT type="radio" name="ssex" value="Female" checked>Female
									<INPUT type="radio" name="ssex" value="Male">Male
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="3"><INPUT class="classname" type="submit"
									name="Submit" value="Search"></td>
							</tr>
						</table>
					</div>
					<div class="tabpage" id="tabpage_2">
						<table>
							<tr>
								<td><LABEL>User name</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="fusername" value="<%= request.getParameter("fusername") %>"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Password</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="password"
									name="fpassword" value="<%= request.getParameter("fpassword") %>"></td>
							</tr>
							<tr>
								<td><LABEL>First name</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="ffname" value="<%= request.getParameter("ffname") %>"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Surname</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="fsname" value="<%= request.getParameter("fsname") %>"></td>
							</tr>
							<tr>
								<td><LABEL>Nickname</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="fnickname" value="<%= request.getParameter("fnickname") %>"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Email</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="femail" value="<%= request.getParameter("femail") %>"></td>
							</tr>
							<tr>
								<td><LABEL>Town</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="ftown" value="<%= request.getParameter("ftown") %>"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Country</LABEL></td>
								<td>&nbsp;</td>
								<td><select id="fcountry" name="fcountry"></select> <script>
									print_country("fcountry");
								</script></td>
							</tr>
							<tr>
								<td><LABEL>Telephone number</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="fphone" value="<%= request.getParameter("fphone") %>"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td class="classradio"><INPUT type="radio" name="fsex" value="Female" checked>Female
									<INPUT type="radio" name="fsex" value="Male">Male
								</td>
							</tr>
							<tr>
								<td><img src="<%= request.getParameter("url") %>"/>
								<input type="text" value="<%= request.getParameter("url") %>" name="url" style="display:none"/></td>
							</tr>
							<tr>
								<td colspan="3"><INPUT class="classname" type="submit"
									name="Signup" value="Save"></td>
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