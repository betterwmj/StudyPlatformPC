<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*"%>
<jsp:useBean id="db" class="com.wmj.dao.OperatorUser" scope="session" />
<html>
<body>
	<%
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=gbk");
		String action = request.getParameter("action");
		System.out.println(action);
        if (action.equals("register")) {
			String name = request.getParameter("user").trim();
			String password = request.getParameter("password").trim();
			String passwords = request.getParameter("password1").trim();
			String email = request.getParameter("email").trim();
			String rank=request.getParameter("denger");
			int dnumber;
			if(rank==null)
				out.println("��ѡ��ѧ�����߽�ʦ");
			else if(!password.equals(passwords))
				 out.println("���벻һ��");
			else{
				dnumber= Integer.parseInt(rank);
				//Person user = new Person();
		   		// user.setName(name);
		    	//user.setPassword(password);
			//	user.setRank(dnumber);
		   		//user.setEmail(email);
		   		 //if(db.insertUser(user)){
					out.println("ע���ɹ�");
		   		 %><a href="login.html" target=_red>���ص�¼ҳ��</a><%
				//}else{
					//out.println("���û����ѱ�ע�ᣬ������ע��");
				//}
			}
		 }
		if(action.equals("denglu")) {
			String dname = request.getParameter("name").trim();
			String dpassword = request.getParameter("password").trim();
			int dnumber = Integer.parseInt(request.getParameter("denger"));
			System.out.println(dname+" "+dpassword+" "+dnumber);
			if (dname.equals("") || dname == null || dpassword == null
					|| dpassword.equals(""))
				out.println("�û����������벻��Ϊ��");
			else {
				//String school_numbers=db.isUserPasswordCorrect(dname, dpassword, dnumber);
				
				//if(school_numbers.equals("") ){
					//out.println("���û������ڻ�������֤ʧ��");
					session.setAttribute("isLogin", false);
				//}else{
				//��֤�ɹ�����Ȩ�޲�ͬ���벻ͬҳ��
			      switch(dnumber){
			      case 0:
			    	  %><jsp:forward page="student.jsp"/><% 
			    	  break;
			      case 1:
			    	  %><jsp:forward page="teacher.jsp"/><% 
			    	  break;
			      case 2:
			    	  %><jsp:forward page="admin.jsp"/><% 
			    	  break;
			      }
				session.setAttribute("isLogin", true);	
			}

		}
	 // }
	%>
</body>
</html>