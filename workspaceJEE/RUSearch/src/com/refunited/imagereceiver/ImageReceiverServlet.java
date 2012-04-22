package com.refunited.imagereceiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import com.refunited.datamodel.User;
import com.refunited.flickr.FlickHandler;
import com.refunited.main.UserServlet;
import com.refunited.sql.SqlConnection;

/**
 * @author Elena Servlet called from the Android app. Authenticate with
 *         com.refunited.security.EncryptionManager
 */
@SuppressWarnings("serial")
public class ImageReceiverServlet extends UserServlet implements Servlet {
	private User m_user = new User();
	private String m_lusername = "";
	private String m_lpassword = "";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SqlConnection sqlConnection = new SqlConnection();
		checkForPictures(request);
		User user = authenticate(request, sqlConnection, m_lusername, m_lpassword);
		if (user != null) {
			PrintWriter out = response.getWriter();
			out.print(m_user.getUsername() + "|" + m_user.getUrl());
		} else {
			PrintWriter out = response.getWriter();
			out.print("test|http://farm8.staticflickr.com/7138/7079238585_67079273b0_m.jpg");
		}
		return;
	}

	public void checkForPictures(HttpServletRequest request) {
		boolean isMultipartContent = ServletFileUpload
				.isMultipartContent(request);

		if (!isMultipartContent) {
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			if (!it.hasNext()) {
				return;
			}
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					setUser(fileItem.getFieldName(), fileItem.getString(), m_lusername, m_lpassword, m_user);
					System.out.println(fileItem.getString());
				} else {
					String strFileName = m_user.getUsername() + ".png";
					if (fileItem.getSize() != 0) {
						byte[] bytesToUpload = IOUtils.toByteArray(fileItem
								.getInputStream());
						FlickHandler flickHandler = new FlickHandler();
						String strImageUrl = flickHandler.upload(bytesToUpload,
								strFileName);
						m_user.setUrl(strImageUrl);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
