/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.85
 * Generated at: 2024-03-04 00:57:44 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ex01_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta charset=\"UTF-8\">\n");
      out.write("<title>ex01</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<h1>JSP 구동 원리</h1>\n");
      out.write("<pre>\n");
      out.write("1) 웹브라우저 ==> 서블릿 컨테이너 \n");
      out.write("   - JSP 실행 요청\n");
      out.write("     예) http://localhost:8080/java-web/jsp/ex01.jsp\n");
      out.write("2) 서블릿 컨테이너가 실행\n");
      out.write("   2.1 JSP의 서블릿 객체를 찾는다\n");
      out.write("   2.2 있으면,\n");
      out.write("     2.2.1 그 서블릿 객체를 호출한다. service() ---> _jspService()\n");
      out.write("   2.3 없으면,\n");
      out.write("     2.3.1 JSP 엔진을 이용하여 JSP 파일을 가지고 서블릿 자바 소스 파일을 생성한다.\n");
      out.write("     2.3.2 자바 컴파일러를 이용하여 소스 파일을 컴파일 한다.\n");
      out.write("     2.3.3 서블릿 객체를 생성한다. - init() 호출 ---> jspInit()\n");
      out.write("     2.3.4 그 서블릿 객체를 호출한다. - service() 호출 ---> _jspService()\n");
      out.write("   2.4 JSP 파일이 변경된 상태라면,\n");
      out.write("     2.4.1 다시 \"2.3\"을 반복한다.\n");
      out.write("3) 서블릿 컨테이너 ==> 웹브라우저 \n");
      out.write("   - 서블릿 실행 결과를 응답\n");
      out.write("\n");
      out.write("JSP 파일을 가지고 생성한 서블릿 소스(.java)와 클래스 파일(.class)의 위치\n");
      out.write("- org.eclipse.wst.server.core/tmpx/work/...\n");
      out.write("\n");
      out.write("정리!\n");
      out.write("- JSP 파일은 Python이나 PHP 처럼 직접 그 스크립트가 인터프리팅 되는 것이 아니다.\n");
      out.write("- JSP 엔진의 역할은 JSP 파일을 분석하여 서블릿 클래스를 생성하는 것이다.\n");
      out.write("- 즉 JSP 파일이 직접 실행되지 않는다!\n");
      out.write("\n");
      out.write("JSP \n");
      out.write("- 자바 서블릿 클래스를 만드는 재료로 사용된다.\n");
      out.write("- 그래서 서블릿 클래스를 만드는 \"틀\"이라 해서 \"템플릿(template)\"이라 부른다.\n");
      out.write("- JSP를 템플릿 기술이라 부르기도 한다.\n");
      out.write("\n");
      out.write("JSP 공부법\n");
      out.write("- JSP를 작성할 때 사용하는 특정 태그들이 어떤 자바 코드를 생성하는지 이해하는 것이 중요하다.\n");
      out.write("\n");
      out.write("JSP 실행을 요청하기\n");
      out.write("- JSP 파일이 있는 위치를 지정한다.\n");
      out.write("  예) http://localhost:8080/java-web/jsp/ex01.jsp\n");
      out.write("\n");
      out.write("JSP를 변경한 후 실행을 요청하기\n");
      out.write("- 그냥 JSP 파일이 있는 위치를 지정하면 된다.\n");
      out.write("- 위에서 설명한대로 JSP 구동 원리에 따라 동작된다.\n");
      out.write("\n");
      out.write("JSP 엔진이 서블릿을 만들 때 지켜야할 규칙:\n");
      out.write("- JSP 파일을 가지고 서블릿을 만들 때 HttpJspPage를 구현해야 한다.\n");
      out.write("- 클래스 계층도\n");
      out.write("  Servlet\n");
      out.write("    - init(ServletConfig):void\n");
      out.write("    - destroy():void\n");
      out.write("    - service(ServletRequest, ServletResponse):void\n");
      out.write("    - getServletInfo():String\n");
      out.write("    - getServletConfig():ServletConfig\n");
      out.write("    +---> JspPage\n");
      out.write("      - jspInit():void\n");
      out.write("      - jspDestroy():void\n");
      out.write("      +---> HttpJspPage\n");
      out.write("        - _jspService(HttpServletRequest, HttpServletResponse):void\n");
      out.write("        \n");
      out.write("</pre>\n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}