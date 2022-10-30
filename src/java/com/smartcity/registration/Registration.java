/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smartcity.registration;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
//@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register"})
@WebServlet(name = "Registration",urlPatterns = {"/register"})
public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
            String uname = request.getParameter("name");
            String ugender = request.getParameter("gender");
            String uadd= request.getParameter("address");
            String uphone = request.getParameter("phone_no");
            String uuser = request.getParameter("username");
            String upswd = request.getParameter("password");
//            RequestDispatcher dispatcher = null;
            Connection con = null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_city?useSSL=false","root","root");
                PreparedStatement pst = con.prepareStatement("INSERT INTO user(`name`,`gender`,`address`,`phone_no`,`username`,`password`)VALUES(?,?,?,?,?,?)");
                
                pst.setString(2, uname);
                pst.setString(3, ugender);
                pst.setString(4, uadd);
                pst.setString(5, uphone);
                pst.setString(6, uuser);
                pst.setString(7, upswd);
                
                
                
                PreparedStatement pst1 = con.prepareStatement("select username from user");
                ResultSet rs = pst1.executeQuery();
                if(rs.next()){
                    String email=rs.getString("username");
                    if(email.equals(uuser)){
                
//                        dispatcher = request.getRequestDispatcher("signup.jsp");
                           response.sendRedirect("signup.jsp");
                    }
                else{
                int rowCount = pst.executeUpdate();
                
                if(rowCount >0){
                    
                    response.sendRedirect("login.jsp");
                }
                }
                }
//                dispatcher.forward(request, response);
            } catch (Exception e){
                e.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }


}
