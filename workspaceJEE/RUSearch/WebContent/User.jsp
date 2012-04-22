<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>Refugees united</TITLE>
<meta charset="utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="countries2.js"></script>
<script type="text/javascript" src="filedrag.js"></script>
</HEAD>

<BODY>
	<FORM action="/RU/Profile" method="post" enctype="multipart/form-data">
		<div>
			<label class="htmltitle">Refugees United</label>
		</div>
		<hr />
		<div id="wrapper">
			<div id="tabContainer">
				<div class="tabs">
					<ul>
						<li id="tabHeader_1">Login</li>
						<li id="tabHeader_2">Signup</li>
					</ul>
				</div>
				<div class="tabscontent">
					<div class="tabpage" id="tabpage_1">
						<table>
							<tr>
								<td><label>Username</label></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="lusername"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><label>Password</label></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="password"
									name="lpassword"></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="3"><INPUT class="classname" type="submit"
									name="Submit" value="Login"></td>
							</tr>
						</table>
					</div>
					<div class="tabpage" id="tabpage_2">
						<table>
							<tr>
								<td><LABEL>Username</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="username"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Password</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="password"
									name="password"></td>
							</tr>
							<tr>
								<td><LABEL>First name</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="fname"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Surname</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="sname"></td>
							</tr>
							<tr>
								<td><LABEL>Nickname</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="nickname"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Email</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="email"></td>
							</tr>
							<tr>
								<td><LABEL>Town</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="town"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><LABEL>Country</LABEL></td>
								<td>&nbsp;</td>
								<td><select id="country" name="country"></select> <script>
									print_country("country");
								</script></td>
							</tr>
							<tr>
								<td><LABEL>Telephone number</LABEL></td>
								<td>&nbsp;</td>
								<td><INPUT class="classtext" type="text" name="phone"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td class="classradio"><INPUT type="radio" name="sex"
									value="Female" checked>Female <INPUT type="radio"
									name="sex" value="Male">Male</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="8">
										<fieldset>
											<input type="hidden" id="MAX_FILE_SIZE" name="MAX_FILE_SIZE"
												value="300000" />
											<div>
												<input
													type="file" id="fileselect" name="fileselect[]"
													multiple="multiple"/>
											</div>
											
										</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="3"><INPUT class="classname" type="submit"
									name="Signup" value="Signup"></td>
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